package common;

import java.io.IOException;

import server.EchoServer1;

public class ServerCreatechannelHandler extends ServerMessageHandler{

	private String channelName;
	
	public ServerCreatechannelHandler(String str) {
		channelName = str;
	}
	
	@Override
	public void handleMessage() {
		EchoServer1 server = getServer();
		//server.createChannel(channelName);
		
		if (!server.getChannels().contains(channelName)){
			try {
				server.addToListOfChannels(channelName);
				getClient().sendToClient("You have created channel " + channelName);
			} catch (IOException e) {}
			server.serverUI().display("Channel " + channelName + " created");
			return;
		}
		
		try {
			getClient().sendToClient(channelName + " already exists!");
		} catch (IOException e) {}
	}
}