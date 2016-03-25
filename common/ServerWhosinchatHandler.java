package common;

import java.io.IOException;

import ocsf.server.ConnectionToClient;
import server.EchoServer1;

public class ServerWhosinchatHandler extends ServerMessageHandler{

	public ServerWhosinchatHandler(String id) {}
	
	@Override
	public void handleMessage() {
		EchoServer1 server = getServer();
		ConnectionToClient myClient = getClient();
		Thread[] clientThreadList = server.getClientConnections();
		
		String names = "";
		for (int i = 0; i < clientThreadList.length; i++){
			names += ((ConnectionToClient)clientThreadList[i]).getInfo("id") + "\n";
		}
		
		try {
			myClient.sendToClient((String)names);
		} catch (IOException e) {}
	}
}
