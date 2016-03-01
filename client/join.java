package client;

import java.io.IOException;
import common.ServerJoinChannelHandler;

public class join extends ClientCommand{

	public join(String str, ChatClient1 client) {
		super(str, client);
	}

	@Override
	public void doCommand() {
		String[] params = getStr().split(" ");
				
        ServerJoinChannelHandler s = new ServerJoinChannelHandler(params[0]);
        
        try {
			getClient().sendToServer(s);
		} catch (IOException e) {}
	}
}