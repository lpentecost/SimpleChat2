package common;

import java.io.IOException;
import java.util.ArrayList;

import ocsf.server.*;
// This class helps with the #whoblockme command

public class ServerUnblockHandler extends ServerMessageHandler{

	private String idToUnblock;

	public ServerUnblockHandler(String id) {
		idToUnblock = id;
	}
	
	@Override
	public void handleMessage() {
		
		ConnectionToClient connectionToClient = getClient();
		
		if (!connectionToClient.getBlockedList().contains(idToUnblock)){
			try {
				getClient().sendToClient(idToUnblock + " was not blocked");
			} catch (IOException e) {}
			return;
		}
		
		try {
			getClient().removeFromBlockedList(idToUnblock);
			getClient().sendToClient("You have unblocked " + idToUnblock);
		} catch (IOException e) {}
	}
}