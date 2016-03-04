package common;

import java.io.IOException;

public class ServerLeaveChannelHandler extends ServerMessageHandler{
	
	public ServerLeaveChannelHandler() {}
	
	public void handleMessage() {		
		String clientUsername = getClient().getInfo("id").toString();
				
		// if client is in global channel, don't get them leave
		if (getServer().getUsernameChannels().get(clientUsername) == "global"){
			try {
				getClient().sendToClient("You cannot leave the global chat");
			} catch (IOException e) {}
		} 
		else 
		{
			getServer().changeUserChannel(clientUsername, "global");
			try {
				getClient().sendToClient("You have returned to the global chat");
			} catch (IOException e) {}
		}
	}
}