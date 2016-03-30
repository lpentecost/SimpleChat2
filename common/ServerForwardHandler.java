package common;
import java.io.IOException;
import java.util.HashSet;

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
		String receivingName = (String) receivingClientConnection.getInfo("id");
		
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
			HashSet<String> monitorList = receivingClientConnection.getMonitorList();
			if (monitorList.size()!= 0) {
				for (String receivingClient: monitorList) {
					ConnectionToClient receiving = getServer().getConnectionToClientByName(receivingClient);
					try {
						receiving.sendToClient("(Monitor message to " + receivingName + ": FORWARDED MESSAGE (" + cName + ")> " + forwardedMessage);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		else {
			try {
				c.sendToClient("ERROR: did not forward message.");
			}
			catch (IOException ex) {}
		}
	}
}
