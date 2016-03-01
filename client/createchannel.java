package client;

import java.io.IOException;

import common.ServerChannelHandler;

public class createchannel extends ClientCommand{

	public createchannel(String str, ChatClient1 client) {
		super(str, client);
	}

	@Override
	public void doCommand() {
		String[] params = getStr().split(" ");
        ServerChannelHandler s = new ServerChannelHandler(params[0]);
        
        try {
			getClient().sendToServer(s);
		} catch (IOException e) {}
	}
}
