package common;

import java.io.IOException;
import java.util.ArrayList;

import ocsf.server.*;
// This class helps with the #whoblockme command

public class ServerBlockHandler extends ServerMessageHandler{

	private String idToBlock;

	public ServerBlockHandler(String id) {
		idToBlock = id;
	}
	
	@Override
	public void handleMessage() {
		
		getClient().addToBlockedList(idToBlock);
		
		try {
			getClient().sendToClient("You have blocked " + idToBlock);
		} catch (IOException e) {}
	}
}

