package common;

import java.io.IOException;
import java.util.HashSet;

import ocsf.server.ConnectionToClient;
import server.EchoServer1;

public class ServerBreakmonitorHandler extends ServerMessageHandler{

	public ServerBreakmonitorHandler(String s) {}

	@Override
	public void handleMessage() {
		EchoServer1 server = getServer();
		ConnectionToClient me = getClient();
		
		Thread[] threads = server.getClientConnections();
		if (threads.length == 0){
			try {
				me.sendToClient("You were not monitoring anyone");
			} catch (IOException e) {}
		}
				
		for (Thread t : server.getClientConnections()){
			ConnectionToClient c = (ConnectionToClient)t;
			
			if (c.getMonitorList().contains(me.getInfo("id"))){
				c.removeFromMonitorList((String)me.getInfo("id"));
				try {
					c.sendToClient(c.getInfo("id") + " broke the monitoring connection and is no longer monitoring you");
				} catch (IOException e) {}
			}
		}
	}
}
