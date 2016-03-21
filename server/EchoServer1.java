package server;

import ocsf.server.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import client.ChatClient1;
import client.ClientCommand;
import common.*;

//added from Prugh's code, seems unnecessary:
//import java.io.*;
//import client.ChatClient1;
//import client.ClientCommand;
//-----

/**
 *  This class modifies EchoServer to complete to begin Chat phase 2.
 *  It uses messages from Client to Server that are instances
 *  of command classes that extend the class ServerMessageHandler.
 *  This class delegates responsibility for handling a message to the
 *  message itself.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @author Chris Nevison
 * @version February 2012
 */
public class EchoServer1 extends AbstractServer
{
  //Class variables *************************************************

  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;
  
  public static ChatIF serverUI;
  
  private HashMap<String, String> usernamePasswords;
  private HashMap<String, Boolean> loggedIn;
  private HashSet<String> channels;
  
  // PersonBeingMonitored, PersonReceivingMonitorMessages
  private HashMap<String, String> monitors;
  
  
  //added:
  //private ArrayList<ConnectionToClient> clients; //(unsure if needed for prugh's unused channel component)
  //----

  //Constructors ****************************************************

  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer1(int port)
  {
    super(port);
    usernamePasswords = new HashMap<String, String>();
    loggedIn = new HashMap<String, Boolean>();
    channels = new HashSet<String>();

    //clients = new ArrayList<ConnectionToClient>();//unsure if needed still
  }

//Instance methods ************************************************
  
  /**
   * This method name is a bit of a misnomer. It only sends messages to the people who are
   * in your channel. It still sends server messages to everyone. 
   */
  
  public void sendToAllClients(Object msg) {	

	// For the future, don't let usernames have carrots!
	  
	int indexOfCarrot = msg.toString().indexOf(">");
	String username = msg.toString().substring(0, indexOfCarrot);
	
	// If it's a server message, or if the user is not in a channel, everyone gets it 
	Thread[] clientThreadList = getClientConnections();
	
	if (username.equals("SERVER MSG"))
	{
	    for (int i=0; i<clientThreadList.length; i++){
	      try {
	         ((ConnectionToClient)clientThreadList[i]).sendToClient(msg);
	      } catch (Exception ex) {}
	    }
	}
	
	// Otherwise only people in the same channel as the person who sent it receive the message
	else 
	{
		String sendersChannel = "";
		
		// get the senders channel
		for (int i=0; i<clientThreadList.length; i++){
			if (((ConnectionToClient)clientThreadList[i]).getInfo("id").equals(username)){				
				sendersChannel = (String) ((ConnectionToClient)clientThreadList[i]).getInfo("channel");
				break;
			}
	    }
		
		// send the message to everyone in the channel
		for (int i=0; i<clientThreadList.length; i++){
			
			if (((ConnectionToClient)clientThreadList[i]).getInfo("channel").equals(sendersChannel)){
				try {
					
					// if this client has a monitor(s), send the message to the monitor(s) instead
					ConnectionToClient c = (ConnectionToClient)clientThreadList[i];
					
					if (c.getMonitorList().size() > 0){
						
						System.out.println("(EchoServer1.sendToAllClients) " + c.getInfo("id") + " has a monitor");
						
						for (String receiverName : c.getMonitorList()){
							ConnectionToClient receiver = getConnectionToClientByName(receiverName);
							receiver.sendToClient("(Forwarded message to " + c.getInfo("id") + ") "+ msg);
						}
						
					} else {
						((ConnectionToClient)clientThreadList[i]).sendToClient(msg);
					}
					       
			    } catch (Exception ex) {}
			}
	    }
	}
  }
  
  public boolean usernameExists(String username){
	  return usernamePasswords.containsKey(username);
  }
  
  public void addUsernameWithPassword(String username, String password){
	  usernamePasswords.put(username, password);
  }
  
  public boolean passwordMatchesUsername(String username, String password){
	  return usernamePasswords.get(username).equals(password);
  }
  
  public boolean userLoggedIn(String username){
	  return loggedIn.get(username) && usernamePasswords.containsKey(username);
  }
  
  public void setUsernameLoggedIn(String username){
	  loggedIn.put(username, true);
  }
  
  public void setUsernameLoggedOut(String username){
	  loggedIn.put(username, false);
  }
  
  public void setServerUI(ChatIF ui){
	  EchoServer1.serverUI = ui;
  }
  
  public void handleMessageFromServerConsole(String message){
	if(message.charAt(0) != '#'){
	  sendToAllClients("SERVER MSG> " + message);
	} else {
	  message = message.substring(1);
	  createAndDoServerCommand(message);
	}
  }
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received, a String. Need to differentiate between command and message
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient(Object msg, ConnectionToClient connectionToClient){
	String message = msg.toString();
	  
	if(message.charAt(0) != '#'){

	  String channel = (String) connectionToClient.getInfo("channel");
	  String nameOfSender = (String) connectionToClient.getInfo("id");
	  String outString = nameOfSender + "> " + message;
	  
	  sendToAllClients(outString);
	  serverUI.display(outString);
		
	} else {
	  message = message.substring(1); // eliminates first character
	  createAndDoClientCommand(message, connectionToClient);
	}
  }
  
  public ConnectionToClient getConnectionToClientByName(String username){
	Thread[] clientThreadList = getClientConnections();
		
	for (int i=0; i<clientThreadList.length; i++){
	  try {
	    if ( username.equals( ((ConnectionToClient)clientThreadList[i]).getInfo("id"))) {
	    	return (ConnectionToClient)clientThreadList[i]; 
	    }
	  }catch (Exception ex) {}
	}
	return null;
  }

  /**
   * Sets the port
   */
  public void setPort(String p) {
	setPort(Integer.valueOf(p));
  }
  
  
  /**
   * This references the serverUI that's hooked up
   */
  public ChatIF serverUI(){
    return serverUI;
  }
  
  /**
   * Override method of clientConnected in AbstractServer
   * Called each time a new client is connected
   * @param client the connection connected to the client.
   */
  @Override
  protected void clientConnected(ConnectionToClient client){
	  serverUI().display(client.getInfo("id") + " has connected");
  }
  
  /**
   * Hook method called each time a client disconnects.
   * The default implementation does nothing. The method
   * may be overridden by subclasses but should remains synchronized.
   *
   * @param client the connection with the client.
   */
  @Override
  synchronized protected void clientDisconnected(ConnectionToClient client) {
	  serverUI().display(client.getInfo("id") + " has disconnected");
  }
  
  /**
   * Hook method called each time an exception is thrown in a
   * ConnectionToClient thread.
   * @param client the client that raised the exception.
   * @param Throwable the exception thrown.
   *
   */
  @Override
  synchronized protected void clientException(ConnectionToClient client, Throwable exception) {
	  serverUI().display(client.getName() + " raised a " + exception.getClass()+ " exception");
  }

  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted(){
	  serverUI.display("Server listening for connections on port " + getPort());
  }

  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped(){
      serverUI.display("Server has stopped listening for connections.");
  }
  
  private void createAndDoServerCommand(String message){
	  String commandStr;
	  int indexBlank = message.indexOf(' '); // returns -1 if no space, otherwise index
	  if(indexBlank == -1)
	  {
	    commandStr = "server." + message;
	    message = "";
	  }
	  else
	  {
	    commandStr = "server." + message.substring(0, indexBlank);
	    message = message.substring(indexBlank+1);
	  }
	
	  try
	  {
	    Class[] param = {String.class, EchoServer1.class};
	    ServerCommand cmd = (ServerCommand)Class.forName(commandStr).getConstructor(param).newInstance(message, this);  
	    cmd.doCommand();
	  }
	  catch(Exception ex)
	  {
		serverUI.display("Not a command");
	  }
  }
  
  private void createAndDoClientCommand(String message, ConnectionToClient connectionToClient){

	  String commandStr = "";
	  
	  int indexBlank = message.indexOf(' '); // returns -1 if no space, otherwise index
	  
	  String rawCommand = message.substring(0, indexBlank);
	  
	  if(indexBlank == -1) { // no space
		  commandStr = "client." + message;
		  message = "";	  
	  } else {
		  // The arguments to the command
		  commandStr = "common.Server" + rawCommand.substring(0, 1).toUpperCase() + rawCommand.substring(1) + "Handler";
		  message = message.substring(indexBlank+1);
	  }
	  
	  System.out.println("EchoServer commandStr: " + commandStr);
		
	  int numParams = message.split(" ").length;
		
	  try{
		  // Reflection that creates a Server___Handler object
		  
	      Class[] param = new Class[numParams];
	      
	      for (int i = 0; i < numParams; i++){
	    	  param[i] = String.class;
	      }
	      
	      String[] params = new String[numParams];	      
	      ArrayList<String> argsArray = new ArrayList<String>(Arrays.asList(message.split(" ")));;
	      
	      ServerMessageHandler handler = (ServerMessageHandler)Class.forName(commandStr).getConstructor(param).newInstance(argsArray.toArray(new String[argsArray.size()])); 
	      handler.setServer(this);
	      handler.setConnectionToClient(connectionToClient);
	      handler.handleMessage();
	  }
	  catch(Exception ex)
	  {
		  try {
			System.out.println("Server problem...");
			connectionToClient.sendToClient("\nNo such command " + commandStr + "\nNo action taken.");
		} catch (IOException e) {}
	  }
		  
  }

  public HashSet<String> getChannels() {
	  return channels;
  }

  public void addToListOfChannels(String channelName) {
	  channels.add(channelName);
  }
  
  public HashMap<String, String> getMonitors(){
	  return monitors;
  }
  
  public void addToMonitors(String personBeingMonitored, String personReceivingMonitorMessages){
	  monitors.put(personBeingMonitored, personReceivingMonitorMessages);
  }
  
  public void removeFromMonitors(String personBeingMonitored, String personReceivingMonitorMessages){
	  monitors.remove(personBeingMonitored);
  }
  

  //Class methods ***************************************************
}

