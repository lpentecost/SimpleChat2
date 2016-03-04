package client;

import java.io.IOException;
import common.ServerLeaveChannelHandler;

public class leave extends ClientCommand{

	public leave(String str, ChatClient1 client) {
		super(str, client);
	}

	@Override
	public void doCommand() {
				
        ServerLeaveChannelHandler s = new ServerLeaveChannelHandler();
        
        try {
			getClient().sendToServer(s);
		} catch (IOException e) {}
	}
}