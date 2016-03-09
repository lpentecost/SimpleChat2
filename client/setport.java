package client;

/**
 *  Implements client command to set the port. Available only when disconnected from server
 *
 * @author Michael Dunnegan
 * @version February 2016
 */

public class setport extends NotConnectedClientCommand {

	public setport(String str, ChatClient1 client) {
		super(str, client);
	}

	@Override
	public void doCmd() {
		String port = getStr();
		
		try {
			int portNumber = Integer.valueOf(port);
		} catch (NumberFormatException e){
			System.out.println("Port must be a number. Port not set.");
			return;
		}
		
		if (port != ""){
			getClient().setPort(Integer.valueOf(port));
			getClient().clientUI().display("Port set to " + port);
		} else {
			getClient().clientUI().display("Port number cannot be empty. Port not set.");
		}
	}
}
