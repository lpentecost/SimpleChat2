package client;

import java.io.IOException;

public class createchannel extends ClientCommand{

	public createchannel(String str, ChatClient1 client) {
		super(str, client);
	}

	@Override
	public void doCommand() {
		try {			
			getClient().sendToServer("#createchannel " + getStr());
		} catch (IOException e) {}
	}
}
