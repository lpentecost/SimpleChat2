package client;

import java.io.IOException;
import common.ServerJoinchannelHandler;

public class joinchannel extends ClientCommand{

	public joinchannel(String str, ChatClient1 client) {
		super(str, client);
	}

	@Override
	public void doCommand() {
		String[] params = getStr().split(" ");
				
        //ServerJoinChannelHandler s = new ServerJoinChannelHandler(params[0]);
		try {
			getClient().sendToServer("#joinchannel " + getStr());
		} catch (IOException e1) {}
	}
}