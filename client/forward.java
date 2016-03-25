package client;
import java.io.IOException;

import common.*;

//Alex Prugh

public class forward extends ClientCommand {
	
	public forward(String str, ChatClient1 client) {
		super(str, client);
	}
	
	@Override
	public void doCommand() {
		try {			
			getClient().sendToServer("#forward " + getStr());
		} catch (IOException e) {}
	}
	
}
