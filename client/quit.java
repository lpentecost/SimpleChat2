package client;

import java.io.IOException;

/**
 *  Implements client command to quit, after first closing any connection to the current host.
 *
 * @author Chris Nevison
 * @version February 2012
 */
public class quit extends ClientCommand
{
  public quit(String str, ChatClient1 client)
  {
    super(str, client);
  }

  public void doCommand()
  {
    try
    {
      if(getClient().isConnected())
      {
        getClient().closeConnection();
        getClient().clientUI().display("Connection closed, exiting.");
      }
      else
      {
        getClient().clientUI().display("Connection already closed, exiting.");
      }
      System.exit(0);
    }
    catch(IOException ex)
    {
      getClient().clientUI().display("IOException " + ex + ", exiting.");
      System.exit(-1);
    }
  }
}
