package common;
import java.io.IOException;

import client.*;
import ocsf.server.*;

//Alex Prugh

public class ServerForwardMessageHandler extends ServerMessageFindHandler {
	private String client2id;
	private String message;
	private ConnectionToClient connect;
	
	public ServerForwardMessageHandler(String id) { //no message from client forwarded
		client2id = id;
		System.out.println(client2id);
	}
	
	public ServerForwardMessageHandler(String str, String id) {
		message = str;
		client2id = id;
	}
	
	@Override
	public void handleMessage() {
		// TODO Auto-generated method stub
		connect = findClient(client2id);
		try {
			connect.sendToClient(message);
		}
		catch (IOException ex) {
			getServer().sendToAllClients("FORWARD DID NOT WORK"); //delete later
		}
	}

}
