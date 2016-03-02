package common;

import java.io.IOException;

import SimpleChatClient.ClientGUI;

public class ServerJoinChannelHandler extends ServerMessageHandler{

	private String channelName;
	
	public ServerJoinChannelHandler(String str) {
		channelName = str;
	}
	
	public void handleMessage() {		
		String clientUsername = getClient().getInfo("id").toString();
		if (getServer().channelExists(channelName)){
			getServer().addUserToChannel(clientUsername, channelName);
			try {
				getClient().sendToClient("You have joined " + channelName);
				
				
				// Hardcoding default host and port...
				
				// Going to need to get rid of uniqueness...
				ClientGUI.createAndRun("localhost", 5555, channelName);
				
				getClient().sendToClient("ServerJoinChannel getClient().sendToClient test");
				
				// Then it needs to log you in
				
			} catch (IOException e) {}
		} else {
			try {
				getClient().sendToClient("That channel does not exist");
			} catch (IOException e) {}
		}
	}
}
