package common;

import java.io.IOException;

import SimpleChatClient.ClientGUI;

public class ServerJoinchannelHandler extends ServerMessageHandler{

	private String channelName;
	
	public ServerJoinchannelHandler(String str) {
		channelName = str;
	}
	
	public void handleMessage() {
		
		if (getServer().getChannels().contains(channelName)){
			try {
				getClient().setInfo("channel", channelName);
				getClient().sendToClient("You have joined " + channelName);				
			} catch (IOException e) {}
			return;
		}
		
		try {
			getClient().sendToClient(channelName + " doesn't exist! Use #createchannel to create it");
		} catch (IOException e) {}			
	}
}
