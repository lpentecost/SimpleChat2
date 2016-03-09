package common;

import java.io.IOException;

import SimpleChatClient.ClientGUI;

public class ServerJoinchannelHandler extends ServerMessageHandler{

	private String channelName;
	
	public ServerJoinchannelHandler(String str) {
		channelName = str;
	}
	
	public void handleMessage() {		
		String clientUsername = getClient().getInfo("id").toString();
		if (getServer().channelExists(channelName)){
			getServer().changeUserChannel(clientUsername, channelName);
			try {
				getClient().sendToClient("You have joined " + channelName);				
			} catch (IOException e) {}
		} else {
			try {
				getClient().sendToClient("That channel does not exist");
			} catch (IOException e) {}
		}
	}
}
