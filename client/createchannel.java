package client;

import java.io.IOException;

import common.ServerCreatechannelHandler;

public class createchannel extends ClientCommand{

	public createchannel(String str, ChatClient1 client) {
		super(str, client);
	}

	@Override
	public void doCommand() {
		//String[] params = getStr().split(" ");
//        ServerChannelHandler s = new ServerChannelHandler(params[0]);
		
		try {
			//getClient().sendToServer(getStr());
			
			getClient().sendToServer("#createchannel " + getStr());
			
		} catch (IOException e) {}
        
//        try {
//			getClient().sendToServer(s);
//		} catch (IOException e) {}
	}
}
