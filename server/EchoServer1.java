package server;

import java.io.*;

import client.ChatClient1;
import client.ClientCommand;
import ocsf.server.*;
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

  //Constructors ****************************************************

  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer1(int port)
  {
    super(port);
  }

  //Instance methods ************************************************

  public void handleMessageFromServerConsole(String message){
	if(message.charAt(0) != '#'){
	  sendToAllClients("SERVER MSG> " + message);
	} else {
	  message = message.substring(1); // eliminates first character
	  createAndDoCommand(message);
	}
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
		System.out.println("Not a command");
	  }
  }


  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received, an instance of a subclass of ServerMessageHandler
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient(Object msg, ConnectionToClient client)
  {
    ServerMessageHandler handler = (ServerMessageHandler) msg;
    handler.setServer(this);
    handler.setConnectionToClient(client);
    handler.handleMessage();
  }

  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    System.out.println("Server listening for connections on port " + getPort());
  }

  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
    System.out.println("Server has stopped listening for connections.");
  }

  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of
   * the server instance (there is no UI in this phase).
   *
   * @param args[0] The port number to listen on.  Defaults to 5555
   *          if no argument is entered.
   */
  public static void main(String[] args){
    int port = 0; //Port to listen on

    try{
      port = Integer.parseInt(args[0]); //Get port from command line
    } catch (Throwable t) {
      port = DEFAULT_PORT; //Set port to 5555
    }

    EchoServer1 sv = new EchoServer1(port);

    try {
	  sv.listen(); //Start listening for connections
	} catch (Exception ex) {
	  System.out.println("ERROR - Could not listen for clients!");
	}
    
    // Creates a new server console
    try {
      new ServerConsole(sv, port).accept();
    } catch (Exception e) {
      System.out.println("Something happened");
    }
  }

  public void setPort(String p) {
	setPort(Integer.valueOf(p));
  }
}

