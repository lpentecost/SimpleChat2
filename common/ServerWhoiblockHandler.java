package common;

import java.io.IOException;

import ocsf.server.ConnectionToClient;

public class ServerWhoiblockHandler extends ServerMessageHandler{

	public ServerWhoiblockHandler(String id) {}
	
	public void handleMessage() {
		
		ConnectionToClient connectionToClient = getClient();
	
		String stringToSend = "You have blocked: ";
		
		for (String s: connectionToClient.getBlockedList()){
			stringToSend += s + ", ";
		}
		
		if (connectionToClient.getBlockedList().isEmpty()) {
			stringToSend += "No one!   ";
		}
		
		stringToSend = stringToSend.substring(0, stringToSend.length() - 2);
		
		try {
			getClient().sendToClient(stringToSend);
		} catch (IOException e) {}
	}
}