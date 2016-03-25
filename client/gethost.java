package client;

/**
 *  Implements client command to retrieve host
 *
 * @author Michael Dunnegan
 * @version February 2016
 */

public class gethost extends ClientCommand{

	public gethost(String str, ChatClient1 client) {
		super(str, client);
	}

	@Override
	public void doCommand() {
		System.out.println("Your host is " + getClient().getHost());
	}
}
