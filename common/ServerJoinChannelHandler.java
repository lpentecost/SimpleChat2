package common;

import java.io.IOException;

import SimpleChatClient.ClientGUI;

public class ServerJoinchannelHandler extends ServerMessageHandler{

	private String channelName;
	
	public ServerJoinchannelHandler(String str) {
		channelName = str;
	}
	
	public void handleMessage() {		
		try {
			getClient().setInfo("channel", channelName);
			getClient().sendToClient("You have joined " + channelName);				
		} catch (IOException e) {}
	}
}
