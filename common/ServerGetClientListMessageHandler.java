package common;
import java.io.IOException;
import java.util.ArrayList;

import ocsf.server.ConnectionToClient;

public class ServerGetClientListMessageHandler extends ServerMessageFindHandler {

	ArrayList<ConnectionToClient> clients;
	String allClientsString;
	
	public ServerGetClientListMessageHandler() {
		clients = new ArrayList<ConnectionToClient>();
		allClientsString = "";
	}
	
	@Override
	public void handleMessage() {
		// TODO Auto-generated method stub
		clients = getServer().getAllClients();
		ConnectionToClient askingClient = findClient((String) getClient().getInfo("id"));
		for (int i = 0; i < clients.size(); i++) {
			allClientsString += clients.get(i).getInfo("id") + " ";
		}
		if (allClientsString.length() == 0) {
			try {
				askingClient.sendToClient("There are no clients in this server.");
			} catch (IOException e) {
				}
		}
		else {
			try {
				askingClient.sendToClient(allClientsString);
			} catch (IOException e) {
			}
		}
	}

}
