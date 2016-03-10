package common;

import java.io.IOException;
import ocsf.server.ConnectionToClient;
import server.EchoServer1;

public class ServerLogoffHandler extends ServerMessageHandler{
	
	public ServerLogoffHandler(String id){}
	
	@Override
	public void handleMessage() {
		
		EchoServer1 server = getServer();
		ConnectionToClient connectionToClient = getClient();
		server.serverUI().display(connectionToClient.getInfo("id") + " logged out.");
		server.setUsernameLoggedOut((String)connectionToClient.getInfo("id"));
		
		try {
			connectionToClient.close();
		} catch (IOException e) {}
	}
}
