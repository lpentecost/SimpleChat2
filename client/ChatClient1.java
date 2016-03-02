// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com

package client;

import ocsf.client.*;
import server.Channel;
import common.*;
import java.io.*;
import java.util.ArrayList;

import SimpleChatClient.ClientConsole;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * Modified to complete exercises E50 and E51
 * Uses reflection to create ClientCommand subclasses for each command
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Chris Nevison
 * @version July 2012
 */
public class ChatClient1 extends AbstractClient
{
  //Instance variables **********************************************

  /**
   * The interface type variable.  It allows the implementation of
   * the display method in the client.
   */
  private ChatIF myClientUI;
	  
  protected ArrayList<String> blocked;
	  
  protected ArrayList<String> blockedMe;

  String myId;
  private String password;
  private String channelName;

  //Constructors ****************************************************

  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */

  public ChatClient1(String host, int port, ChatIF clientUI, String channelName) throws IOException {
	  
    super(host, port); //Call the superclass constructor
    myClientUI = clientUI;
    this.channelName = channelName;
    blocked = new ArrayList<String>();
    blockedMe = new ArrayList<String>();
    
    displayGreeting(channelName);
  }

  private void displayGreeting(String cName) {
	if (cName == Channel.DEFAULT_CHANNEL){
		clientUI().display("Use '#setport <portNumber>' and '#sethost <hostname>' to set them");
	    clientUI().display("Defaults are port 5555 and localhost");
	    clientUI().display("Then type '#login <username> <password>' to login");
	} else {
		clientUI().display("This is what gets displayed when we're not in the general chat");
	}
  }

public ChatIF clientUI()
  {
    return myClientUI;
  }

  public String getId()
  {
    return myId;
  }
  
  public String getPassword(){
	  return password;
  }

  //Instance methods ************************************************

  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg)
  {
	  String clientName;
	  if (msg instanceof ArrayList) { // handling the case where want to add to the blockedMe list
		  getBlockedMeList().addAll((ArrayList) msg);
	  }
	  else {		  
		  //finding the indicies of the substring in which there will be there will be the client's name
		  final int BEGINNINGIDIDX = 0;
		  		  
		  int endingIdIdx = ((String) msg).indexOf(">"); //finding the second carrot
		  
		  if (endingIdIdx == -1){
			  endingIdIdx = msg.toString().length() - 1;
		  }
		  
		  clientName = ((String) msg).substring(BEGINNINGIDIDX, endingIdIdx);
		  		  
		  // the message will not show if the client has been blocked
		  if (!(getBlockedList().contains(clientName))){
			  clientUI().display(msg.toString()); 
		  }
	  }
  }
  
  public void blockedMe(String clientId) {
	  blockedMe.add(clientId);
  }
  
  public ArrayList<String> getBlockedMeList() {
	  return blockedMe;
  }
  
  public void block(String clientId){
	  blocked.add(clientId);
  }
  
  public ArrayList<String> getBlockedList() {
	  return blocked;
  }

  /**
   * This method handles all data coming from the UI
   *
   * @param message The message from the UI.
   */
  public void handleMessageFromClientUI(String message)
  {
    if(message.charAt(0) != '#')
    {
      sendMessageToServer(message);
    }
    else
    { // command
      message = message.substring(1); // eliminates first character
      createAndDoCommand(message);
    }

  }

  /**
   * This method handles a simple string message, not a command
   *
   * @param message The message from the UI
   */
  private void sendMessageToServer(String message)
  {
    if(isConnected())
    {
      ServerStringMessageHandler mess = new ServerStringMessageHandler(message);
      try
      {
        sendToServer(mess);
      }
      catch(IOException e)
      {
        clientUI().display("IOException " + e + "\nCould not send message to server.  Terminating client.");
        quit();
      }
    }
    else
    {
      clientUI().display("Not connected to a server. Must login before sending a message.");
    }
  }

  /**
   * This method handles a command message after the '#' has been stripped
   * It uses reflection to create an instance of a subclass of ClientCommand whose name
   * is given by the first token in the message string
   *
   * @param message the command string (after '#' is stripped)
   */
  private void createAndDoCommand(String message)
  {
    String commandStr;
    
    int indexBlank = message.indexOf(' '); // returns -1 if no space, otherwise index
    if(indexBlank == -1) { // no space
      commandStr = "client." + message;
      message = "";
      
    } else {
      // The arguments to the command
      commandStr = "client." + message.substring(0, indexBlank);
      message = message.substring(indexBlank+1);
    }

    try
    {
      Class[] param = {String.class, ChatClient1.class};

      // Looks like reflection! 
      // Where does the type cast happen? MUST be at the end
      ClientCommand cmd = (ClientCommand)Class.forName(commandStr).getConstructor(param).newInstance(message, this); //(string, Chat Client)  
      cmd.doCommand(); // polymorphism. cmd can be of type Login, Exit, etc
   
    }
    catch(Exception ex)
    {
      clientUI().display("\nNo such command " + commandStr + "\nNo action taken.");
    }
  }

  // Exclusively called by AbstractClient.run()
  public void connectionException(Exception ex){
    clientUI().display("Connection exception " + ex + "\nServer shut down. Terminating this client");
    System.exit(0);
  }

  public void connectionClosed()
  {
    clientUI().display("Connection closed.");
  }

  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0); // completely ends the client, does not allow him to come back
  }

}
//End of ChatClient class
