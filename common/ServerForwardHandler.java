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
		try {
			receivingClientConnection.sendToClient("FORWARDED MESSAGE (" + cName + ")> " + forwardedMessage);
		}
		catch (IOException ex) {}
	}
}
