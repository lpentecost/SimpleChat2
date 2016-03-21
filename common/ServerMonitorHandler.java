package common;

import java.io.IOException;
import java.util.HashSet;

import ocsf.server.ConnectionToClient;
import server.EchoServer1;

public class ServerMonitorHandler extends ServerMessageHandler{
	
	private String idToMonitor;

	public ServerMonitorHandler(String toMonitor) {
		System.out.println("(ServerMonitorHandler constructor) id: " + toMonitor);
		idToMonitor = toMonitor;
	}
	
	@Override
	public void handleMessage() {
		
		EchoServer1 server = getServer();
		ConnectionToClient c = getClient();
		
		ConnectionToClient monitoringUser = server.getConnectionToClientByName(idToMonitor);
		c.addToMonitorList((String)monitoringUser.getInfo("id"));
		
		try {
			c.sendToClient("You are now being monitored by " + idToMonitor);
		} catch (IOException e) {}
		
		try {
			monitoringUser.sendToClient("You are now monitoring " + c.getInfo("id") + ". Use #break <username> to stop monitoring this user");
		} catch (IOException e) {}
	}
}