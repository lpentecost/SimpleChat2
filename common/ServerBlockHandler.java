package common;

import java.io.IOException;
import java.util.ArrayList;

import ocsf.server.*;
import server.EchoServer1;

public class ServerBlockHandler extends ServerMessageHandler{

	private String idToBlock;

	public ServerBlockHandler(String id) {
		idToBlock = id;
	}
	
	@Override
	public void handleMessage() {
		
		ConnectionToClient myConnection = getClient();
		EchoServer1 server = getServer();
		
		if (myConnection.getInfo("id").equals(idToBlock)){
			try {
				getClient().sendToClient("You cannot block yourself");
			} catch (IOException e) {}
			return;
		}
		
		if(myConnection.getBlockedList().contains(idToBlock)){
			try {
				getClient().sendToClient("You have already blocked " + idToBlock);
			} catch (IOException e) {}
			return;
		}
		
		if (!server.getUsernamePasswords().containsKey(idToBlock)){
			try {
				getClient().sendToClient("The username " + idToBlock + " does not exist");
			} catch (IOException e) {}
			return;
		}
		
		getClient().addToBlockedList(idToBlock);
		
		try {
			getClient().sendToClient("You have blocked " + idToBlock);
		} catch (IOException e) {}
	}
}

