package client;

import java.io.IOException;

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
		try{
			if(getClient().isConnected()){
				getClient().closeConnection();
				getClient().clientUI().display("Logged off from the server.");
			} else {
				getClient().clientUI().display("You are already logged off.");
			}
	    } catch(IOException ex) {
	      getClient().clientUI().display("IOException " + ex + ", exiting.");
	      System.exit(-1);
	    }
	}

}
