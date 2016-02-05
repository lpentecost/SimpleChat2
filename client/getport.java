package client;

/**
 *  Implements client command to retrieve the port
 *
 * @author Michael Dunnegan
 * @version February 2016
 */

public class getport extends ClientCommand{

	public getport(String str, ChatClient1 client) {
		super(str, client);
	}

	@Override
	public void doCommand() {
		System.out.println("Your port is " + getClient().getPort());
	}

}
