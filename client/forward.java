package client;
import java.io.IOException;

import common.*;

//Alex Prugh

public class forward extends ClientCommand {
	private String toClient;
	private String message;
	
	public forward(String str, ChatClient1 client) {
		super(str.split(" ")[0], client);
		toClient = str.substring(0, str.indexOf(" "));
		str = str.substring(str.indexOf(" ") + 1);
		message = client.getId() + "> forwarded: " + str;
	}
	
	@Override
	public void doCommand() {
		// TODO Auto-generated method stud
		try {
			//getClient().sendToServer(new ServerForwardMessageHandler(message, toClient));
			
			getClient().sendToServer("#forward " + getStr());

			getClient().clientUI().display("Message forwarded.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// Do I add anything here?
		}
	}
	
}
