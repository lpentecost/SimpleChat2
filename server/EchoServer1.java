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
  
  //private HashSet<String> usernames;
  private HashMap<String, String> usernamePasswords;
  private HashMap<String, Boolean> loggedIn;
  private HashSet<Channel> channels;
  private HashMap<String, String> usernameChannels;
  private Channel globalChannel;
  
  //added:
  private ArrayList<ConnectionToClient> clients; //(unsure if needed for prugh's unused channel component)
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
    //usernames = new HashSet<String>();
    usernamePasswords = new HashMap<String, String>();
    loggedIn = new HashMap<String, Boolean>();
    channels = new HashSet<Channel>();
    usernameChannels = new HashMap<String, String>();
    createGlobalChannel();
    
    
    //clients = new ArrayList<ConnectionToClient>();//unsure if needed still
  }

  private void createGlobalChannel() {
	  globalChannel = new Channel("global");
	  channels.add(globalChannel);
  }

//Instance methods ************************************************
  
  public void sendToAllClients(Object msg)
  {	 
	  
	// Checks the username of the person who sent the message
	// Checks if this user is in a channel
	// If they are, then the message only gets sent to people in their channel
  	  
	int indexOfCarrot = msg.toString().indexOf(">");
	String username = msg.toString().substring(0, indexOfCarrot);
	
	// If it's a server message, or if the user is not in a channel, everyone gets it 
		
	if (username.equals("SERVER MSG")){
		Thread[] clientThreadList = getClientConnections();
	    for (int i=0; i<clientThreadList.length; i++){
	      try {
	         ((ConnectionToClient)clientThreadList[i]).sendToClient(msg);
	      } catch (Exception ex) {}
	    }
	} else {
		String usersChannelName = usernameChannels.get(username);
		Channel c = getChannelByName(usersChannelName);
		c.sendToMembers(msg, this);
	}
  }

  public void changeUserChannel(String username, String channel){
	  
	  if (channelExists(channel)){
		  
		  ConnectionToClient connectionToClient = getConnectionToClientByUsername(username);
		  connectionToClient.setInfo("channelName", channel);
		  
		  Channel c = getChannelByName(channel);
		  
		  // Desperate attempt
		  removeUserFromAllChannels(username);
		  usernameChannels.remove(username);
		  		  
		  usernameChannels.put(username, channel);
		  c.addUser(username);
	  } else {
		  // Don't know what to do here...It will silently fail I guess
	  }
  }
  
  private void removeUserFromAllChannels(String username) {
	for (Channel c : channels){
		if (c.containsUser(username)){
			c.removeUser(username);
		}
	}
  }

/**
   * @param channelName
   * @returns a channel, or null
   */
  public Channel getChannelByName(String channelName) {
	  for (Channel c : channels) {
		  if (c.name.equals(channelName)){
			  return c;
		  }
	  }
	  return null;
  }
  
  public ArrayList<ConnectionToClient> getAllClients(){
	  return clients;
  }
  
  public HashMap<String, String> getUsernameChannels(){
	  return usernameChannels;
  }
  
  public boolean channelExists(String channelName){
	  return channels.contains(getChannelByName(channelName));
  }
  
  public void createChannel(String channelName) {
	Channel c = new Channel(channelName);
	channels.add(c);
  }
  
  public boolean usernameExists(String username){
	  System.out.println("anything");
	  return usernamePasswords.containsKey(username);
  }
  
  public void addUsernameWithPassword(String username, String password){
	  //usernames.add(username);
	  usernamePasswords.put(username, password);
  }
  
  public boolean passwordMatchesUsername(String username, String password){
	  return usernamePasswords.get(username).equals(password);
  }
  
  public boolean userLoggedIn(String username){
//	  return loggedIn.get(username) && usernames.contains(username);
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
//    ServerMessageHandler handler = (ServerMessageHandler) msg;
//    handler.setServer(this);
//    handler.setConnectionToClient(client);
//    handler.handleMessage();
	    	  
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
  
  public ConnectionToClient getConnectionToClientByUsername(String username){
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
	  // This must be getting called before ServerLoginHandler.handleMessage()
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
	      //clientUI().display("\nNo such command " + commandStr + "\nNo action taken.");
		  System.out.println("Error In EchoServer");
		  try {
			connectionToClient.sendToClient("\nNo such command " + commandStr + "\nNo action taken.");
		} catch (IOException e) {}
	  }
		  
  }


  //Class methods ***************************************************

}

