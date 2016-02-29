package server;

import ocsf.server.*;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import common.*;

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
  
  private HashSet<String> usernames;
  private Hashtable<String, String> usernamePasswords;
  private Hashtable<String, Boolean> loggedIn;
  private HashSet<Channel> channels;

  //Constructors ****************************************************

  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer1(int port)
  {
    super(port);
    usernames = new HashSet<String>();
    usernamePasswords = new Hashtable<String, String>();
    loggedIn = new Hashtable<String, Boolean>();
    channels = new HashSet<Channel>();
  }

  //Instance methods ************************************************
  
  
  public void createChannel(String channelName) {
	Channel c = new Channel(channelName);
	channels.add(c);
  }
  
  public boolean usernameExists(String username){
	  return usernames.contains(username);
  }
  
  public void addUsernameWithPassword(String username, String password){
	  usernames.add(username);
	  usernamePasswords.put(username, password);
  }
  
  public boolean passwordMatchesUsername(String username, String password){
	  return usernamePasswords.get(username).equals(password);
  }
  
  public boolean userLoggedIn(String username){
	  return loggedIn.get(username) && usernames.contains(username);
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
	  message = message.substring(1); // eliminates first character
	  createAndDoCommand(message);
	}
  }
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received, an instance of a subclass of ServerMessageHandler
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient(Object msg, ConnectionToClient client){
    ServerMessageHandler handler = (ServerMessageHandler) msg;
    handler.setServer(this);
    handler.setConnectionToClient(client);
    handler.handleMessage();
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
  
  private void createAndDoCommand(String message){
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



  //Class methods ***************************************************

}

