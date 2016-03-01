package client;

import java.io.IOException;

import common.ServerLogoffHandler;
import ocsf.server.ConnectionToClient;

/**
 *  Implements client command to log off, but not quit
 *
 * @author Michael Dunnegan
 * @version February 2016
 */

public class logoff extends ClientCommand{

	public logoff(String str, ChatClient1 client) {
		super(str, client);
	}

	@Override
	public void doCommand() {
		
		// need to get the connectiontoclient
		
		
		try{
			if(getClient().isConnected()){
			    getClient().sendToServer(new ServerLogoffHandler());
				getClient().clientUI().display("Logged off from the server.");
				getClient().closeConnection();
			} else {
				getClient().clientUI().display("You are already logged off.");
			}
	    } catch(IOException ex) {
	      getClient().clientUI().display("IOException " + ex + ", exiting.");
	      System.exit(-1);
	    }
	}

}
