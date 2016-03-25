package common;

import java.io.IOException;
import ocsf.server.ConnectionToClient;
import server.EchoServer1;

public class ServerWhoblocksmeHandler extends ServerMessageHandler{

	public ServerWhoblocksmeHandler(String id){}
	
	@Override
	public void handleMessage() {
				
		EchoServer1 server = getServer();
		ConnectionToClient connectionToClient = getClient();
		String actorName = (String) connectionToClient.getInfo("id");
		
		String myBlockers = "Who blocks me: ";
		int initialLength = myBlockers.length();
		
		Thread[] clientThreadList = server.getClientConnections();
		
		ConnectionToClient c;
		
		for (int i=0; i < clientThreadList.length; i++){
			c = (ConnectionToClient)clientThreadList[i];
			if (c.getBlockedList().contains(actorName) && c != connectionToClient){
				myBlockers += c.getInfo("id") + ", ";
			}
	    }
		
		if (myBlockers.length() == initialLength){
			myBlockers += "No one!   ";
		}
				
		myBlockers = myBlockers.substring(0, myBlockers.length() - 2);
		
		try {
			getClient().sendToClient(myBlockers);
		} catch (IOException e) {}
	}
}