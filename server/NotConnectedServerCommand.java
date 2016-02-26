package server;

import java.io.IOException;

/**
 *  This abstract class provides should be extended by any client command class for
 *  a command that can only be executed when the client is not connected to a server
 *
 * @author Chris Nevison
 * @version February 2012
 */
public abstract class NotConnectedServerCommand extends ServerCommand
{
  public NotConnectedServerCommand(String str, EchoServer1 server)
  {
    super(str, server);
  }

  /**
   * This method checks whether the client is connected. If it is, then no action
   * is taken. If it is not connected, then the command is executed by a call
   * to the abstract doCmd method.
   */

  public void doCommand()
  {
    if(!getServer().isListening() && getServer().getNumberOfClients() == 0)
    {
      doCmd();
    }
    else
    {
      System.out.println("Currently listeing for clients. Cannot execute command. Type #stop to stop listening for clients");
    }
  }

  /**
   * This method provides the slot that any command from the client UI to the client must fill by
   * implementing this method in the subclass that defines the command. This method will not be called
   * if the client is already connected to a server.
   */
  public abstract void doCmd();


}