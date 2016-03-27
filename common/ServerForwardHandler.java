package common;
import java.io.IOException;

import client.*;
import ocsf.server.*;
import server.EchoServer1;

//Alex Prugh

public class ServerForwardHandler extends ServerMessageHandler {
	private String receivingClient;
	private String forwardedMessage;
	private ConnectionToClient receivingClientConnection;

	public ServerForwardHandler(String client, String message) {		
		receivingClient = client;
		forwardedMessage = message;
	}
	
	@Override
	public void handleMessage() {	
		
		EchoServer1 server = getServer();
		ConnectionToClient c = getClient();
		String cName = (String) c.getInfo("id"); 
		
		receivingClientConnection = server.getConnectionToClientByName(receivingClient);
		if (!server.getUsernamePasswords().containsKey(receivingClient)){
			try {
				getClient().sendToClient("The username " + receivingClient + " does not exist");
			} catch (IOException e) {}
			return;
		}
		
		else if(!receivingClientConnection.getBlockedList().contains(getClient().getInfo("id"))) {
		
			try {
				receivingClientConnection.sendToClient("FORWARDED MESSAGE (" + cName + ")> " + forwardedMessage);
			}
			catch (IOException ex) {}
		}
		else {
			try {
				c.sendToClient("ERROR: did not forward message.");
			}
			catch (IOException ex) {}
		}
	}
}
