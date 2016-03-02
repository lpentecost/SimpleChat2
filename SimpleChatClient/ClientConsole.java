package SimpleChatClient;

// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com

import java.io.*;
import java.util.Scanner;

import common.*;
import server.Channel;
import client.*;

/**
 * This class constructs the UI for a chat client.  It implements the
 * chat interface in order to activate the display() method.
 * Warning: Some of the code here is cloned in ServerConsole
 *
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @version July 2000
 */
public class ClientConsole implements ChatIF
{
  //Class variables *************************************************

  /**
   * The default port to connect on.
   */
	
  final public static int DEFAULT_PORT = 5555;

  //Instance variables **********************************************

  /**
   * The instance of the client that created this ConsoleChat.
   */
  ChatClient1 client;

  //Constructors ****************************************************

  /**
   * Constructs an instance of the ClientConsole UI...by creating a ChatClient...
   *
   * @param host The host to connect to.
   * @param port The port to connect on.
   */
  public ClientConsole(String host, int port, String channelName)
  {
    try{
      client = new ChatClient1(host, port, this, channelName);
    } catch(IOException exception) {
      System.out.println("Error: Can't setup connection!" + " Terminating client.");
      System.exit(1);
    }
  }


  //Instance methods ************************************************

  /**
   * This method waits for input from the console.  Once it is
   * received, it sends it to the client's message handler.
   */
  public void accept(){
    try {
      BufferedReader fromConsole = new BufferedReader(new InputStreamReader(System.in));
      String message;

      while (true){
        message = fromConsole.readLine();
        client.handleMessageFromClientUI(message);
      }
    } catch (Exception ex) {
      System.out.println("Unexpected error while reading from console!");
    }
  }

  /**
   * This method overrides the method in the ChatIF interface.  It
   * displays a message onto the screen.
   *
   * @param message The string to be displayed.
   */
  public void display(String message){
    System.out.println(message);
  }


  //Class methods ***************************************************

  /**
   * This method is responsible for the creation of the Client UI.
   *
   * @param args[0] The login ID
   * @param args[1] Password
   * @param args[2] The port to connect to. Must be a number.
   * @param args[3] The host to connect to.
   */
  public static void main(String[] args) {
    
    String host = "localhost";
    int port = 5555;

    ClientConsole chat = new ClientConsole(host, port, Channel.DEFAULT_CHANNEL);
    chat.accept();
  }

}
//End of ConsoleChat class
