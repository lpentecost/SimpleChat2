package client;

/**
 *  Implements client command to set the host. Available only when disconnected from server
 *
 * @author Michael Dunnegan
 * @version February 2016
 */

public class sethost extends NotConnectedClientCommand {

	public sethost(String str, ChatClient1 client) {
		super(str, client);
	}

	@Override
	public void doCmd() {
		
		String host = getStr();
		ChatClient1 client = getClient();
		
		if (host != ""){
			client.setHost(host);
			client.clientUI().display("Host set to " + host);
		} else {
			client.clientUI().display("Host name cannot be empty. Host not set");
		}
	}
}
