package common;

import java.io.IOException;

import ocsf.server.ConnectionToClient;

public class ServerLeaveHandler extends ServerMessageHandler{
	
	public ServerLeaveHandler() {}

	public void handleMessage() {	
		
		ConnectionToClient connectionToClient = getClient();
				
		// if client is in global channel, don't get them leave
		if (connectionToClient.getInfo("channel") == "global"){
			try {
				connectionToClient.sendToClient("You cannot leave the global chat");
			} catch (IOException e) {}
		} else {
			try {
				connectionToClient.setInfo("channel", "global");
				connectionToClient.sendToClient("You have returned to the global chat");
			} catch (IOException e) {}
		}
		
	}
}