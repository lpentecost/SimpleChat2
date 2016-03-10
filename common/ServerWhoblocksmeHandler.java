package common;

import java.io.IOException;
import ocsf.server.ConnectionToClient;
import server.EchoServer1;

public class ServerWhoblocksmeHandler extends ServerMessageHandler{

	public ServerWhoblocksmeHandler(String id){
		System.out.println("In ServerWhoblocksmeHandler constructor");
	}
	
	@Override
	public void handleMessage() {
				
		System.out.println("1");
		EchoServer1 server = getServer();
		System.out.println("2");
		ConnectionToClient connectionToClient = getClient();
		System.out.println("3");
		String actorName = (String) connectionToClient.getInfo("id");
		System.out.println("4");
		
		String myBlockers = "Who blocks me: ";
		
		Thread[] clientThreadList = server.getClientConnections();
		
		ConnectionToClient c;
		
		for (int i=0; i < clientThreadList.length; i++){
			c = (ConnectionToClient)clientThreadList[i];
			if (c.getBlockedList().contains(actorName) && c != connectionToClient){
				myBlockers += c.getInfo("id") + ", ";
			}
	    }
		
		System.out.println("5");
		
		myBlockers = myBlockers.substring(0, myBlockers.length() - 2);
		
		try {
			getClient().sendToClient(myBlockers);
		} catch (IOException e) {}
	}
}
