package common;

import ocsf.server.*;
import server.EchoServer1;
import java.io.IOException;

/**
 *  This class handles a request from a client to login to the server.
 *
 * @author Chris Nevison
 * @version February 2012
 */
public class ServerLoginHandler extends ServerMessageHandler
{
  private String myId;
  private String password;
  
  public ServerLoginHandler(String str, String pass)
  {
    myId = str;
    password = pass;
  }

  /**
   * This method logs the client in by saving its id String and
   * sends a message to all clients that the new client has logged in
   * If already logged in (id String has been set) a message is sent to the
   * client and no other action is taken.
   * Now supports passwords
   */
  public void handleMessage(){
	   	 	  
	// Assume we are connected! And we'll disconnect if there's a mistake
	// It's not particularly graceful. It makes you start another console,
	// and it writes "null has disconnected" to the server console. 
	
	EchoServer1 server = getServer();
	ConnectionToClient connectionToClient = getClient();
	boolean usernameExists = server.usernameExists(myId); // Totally baffled!
		
	if (usernameExists){
		
		// Successful login
		if (server.passwordMatchesUsername(myId, password) && !server.userLoggedIn(myId)){				
			
			server.setUsernameLoggedIn(myId);
			connectionToClient.setInfo("id", myId);				
			connectionToClient.setInfo("channel", "global");	
			server.serverUI().display(myId + " has logged in");
			try { getClient().sendToClient("You have logged in as " + myId); } catch (IOException e) {}	
			server.sendToAllClients("SERVER MSG> " + myId + " has logged in");
			// does NOT close the connection
			
		// Classic wrong password error
		} else if (!server.passwordMatchesUsername(myId, password)){
			try { 
				connectionToClient.sendToClient("Incorrect password...closing client"); 
				connectionToClient.close();
			} catch (IOException e) {}

		// User already logged in somewhere else 
		} else if (server.userLoggedIn(myId)){
			try { 
				connectionToClient.sendToClient("This name is already in use...closing client"); 
				connectionToClient.close();
			} catch (IOException e) {}
		}
				
	// First user to claim this username
	} else {
				
		server.addUsernameWithPassword(myId, password);
		server.setUsernameLoggedIn(myId);
		connectionToClient.setInfo("id", myId);		
		connectionToClient.setInfo("channel", "global");
		server.serverUI().display(myId + " has been created and logged in");
		try { getClient().sendToClient("You have logged in as " + myId); } catch (IOException e) {}
		server.sendToAllClients("SERVER MSG> " + myId + " has logged in");
		
		// does NOT close the connection
	}
  }
}


