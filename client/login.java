package client;

import common.*;
import java.io.IOException;

/**
 *  Implements client command to log in to the current host.
 *
 * @author Chris Nevison
 * @version February 2012
 */
public class login extends NotConnectedClientCommand
{
  public login(String str, ChatClient1 client)
  {
	// apparently str is the message
    super(str, client);
  }

  public void doCmd()
  {
    try
    {
    	    	
      // This connection will be terminated if there's a login error
      getClient().openConnection();
      
      // Not necessarily true
      //getClient().clientUI().display("Connection to " + getClient().getHost() + " opened.");
      
      String[] params = getStr().split(" ");

      ServerLoginHandler s = new ServerLoginHandler(params[0], params[1]);
      getClient().sendToServer(s);
      
    }
    catch(IOException ex)
    {
      getClient().clientUI().display("Connection to " + getClient().getHost() + " failed.");
    }
  }
}

