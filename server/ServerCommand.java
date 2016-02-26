package server;

import common.ChatIF;
import server.EchoServer1;

/**
 *  This abstract class specifies a framework for any command from the client
 *  user interface to the client. Any such command must be implemented with
 *  a subclass of this class with a classname identical to the command
 *  (stripped of the '#'). An instance of the command class will be formed
 *  from the name using reflection, then its doCommand method will be
 *  executed.
 *
 * @author Chris Nevison
 * @version February 2012
 */
public abstract class ServerCommand
{
  private String myString;
  private EchoServer1 myServer;
  private ChatIF serverUI;

  public ServerCommand(String str, EchoServer1 server)
  {
    myString = str;
    myServer = server;
    serverUI = server.serverUI();
  }
  
  
  /**
   * This method provides the command access to the server's user interface
   *
   * @return the server chat interface
   */
  protected ChatIF serverUI(){
	  return serverUI;
  }

  /**
   * This method provides the command access to the client
   *
   * @return the client
   */
  protected EchoServer1 getServer()
  {
    return myServer;
  }

  /**
   * This method provides the command access to the command String (stripped of the command)
   *
   * @return command String
   */
  protected String getStr()
  {
    return myString;
  }

  /**
   * This method provides the slot that any command from the client UI to the client must fill by
   * implementing this method in the subclass that defines the command.
   */
  abstract public void doCommand();
}
