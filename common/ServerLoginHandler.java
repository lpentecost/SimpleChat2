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
	
	boolean usernameExists = server.usernameExists(myId);
		
	if (usernameExists){
		
		if (server.passwordMatchesUsername(myId, password) /*&& !server.userLoggedIn(myId)*/){
			// Successful login
			
			server.setUsernameLoggedIn(myId);
			connectionToClient.setInfo("id", myId);			
			server.changeUserChannel(myId, "global");
			server.serverUI().display(myId + " has logged in");
			try { getClient().sendToClient("You have logged in as " + myId); } catch (IOException e) {}	
			server.sendToAllClients("SERVER MSG> " + myId + " has logged in");

			// does NOT close the connection
			
		// Password matches, but the user is logged in. Bad case
		} else if (server.passwordMatchesUsername(myId, password) /*&& server.userLoggedIn(myId)*/){
						
			server.serverUI().display("Someone attempted to log in as " + myId + " while " + myId + " was logged in.");
			try { connectionToClient.sendToClient("This account is in use."); } catch (IOException e) {}
			
			try {
				connectionToClient.close();
			} catch (IOException e) {}

			
		// Classic wrong password error
		} else if (!server.passwordMatchesUsername(myId, password) /*&& !server.userLoggedIn(myId)*/){
			try { connectionToClient.sendToClient("Incorrect password"); } catch (IOException e) {}
			
			try {
				connectionToClient.close();
			} catch (IOException e) {}
			
		// Wrong password. Someone else is logged in as this name. Potential break in attempt
		} else if (!server.passwordMatchesUsername(myId, password) /*&& server.userLoggedIn(myId)*/){
			
			try { connectionToClient.sendToClient("Incorrect password"); } catch (IOException e) {}
			
			try {
				connectionToClient.close();
			} catch (IOException e) {}
		}
		
	// First user to claim this username
	} else {
		
		server.addUsernameWithPassword(myId, password);
		server.setUsernameLoggedIn(myId);
		connectionToClient.setInfo("id", myId);	
		server.changeUserChannel(myId, "global");
		server.serverUI().display(myId + " has been created and logged in");
		try { getClient().sendToClient("You have logged in as " + myId); } catch (IOException e) {}
		server.sendToAllClients("SERVER MSG> " + myId + " has logged in");
	}
  }
}


