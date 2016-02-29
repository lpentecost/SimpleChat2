package common;

import java.io.IOException;
import java.util.ArrayList;

import ocsf.server.*;
// This class helps with the #whoblockme command

public class ServerBlockingMessageHandler extends ServerMessageHandler{
	
	private String client1id; // client1id is blocking client2id
	private String client2id; // client2id must add client1id to its blockedMe ArrayList
	private ConnectionToClient connect;
	
	public ServerBlockingMessageHandler(String id1, String id2) {
		client1id = id1;
		client2id = id2;
	}
	
	@Override
	public void handleMessage() {
		// TODO Auto-generated method stub
		connect = findClient();
		ArrayList blockedMeAddition = new ArrayList();
		try {
			blockedMeAddition.add(client1id);
			connect.sendToClient(blockedMeAddition);
		}
		catch (IOException ex) {
			getServer().sendToAllClients("Couldn't add to blockedMeList"); //delete later
		}
	}
	
	
	public ConnectionToClient findClient(){
	    Thread[] clientThreadList = getServer().getClientConnections();
	    for (int i=0; i<clientThreadList.length; i++)
	    {
	    	if(((ConnectionToClient)clientThreadList[i]).getInfo("id").equals(client2id)) {
	    		return (ConnectionToClient) clientThreadList[i];
	    	}
	    }
	    return null;
	}
}

