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
		
		if (c.getInfo("id").equals(idToMonitor)){
			try {
				getClient().sendToClient("You cannot monitor yourself");
			} catch (IOException e) {}
			return;
		}
		
		if(c.getMonitorList().contains(idToMonitor)){
			try {
				getClient().sendToClient("You are already being monitored by " + idToMonitor);
			} catch (IOException e) {}
			return;
		}
		
		if (!server.getUsernamePasswords().containsKey(idToMonitor)){
			try {
				getClient().sendToClient("The username " + idToMonitor + " does not exist");
			} catch (IOException e) {}
			return;
		}
		
		ConnectionToClient monitoringUser = server.getConnectionToClientByName(idToMonitor);
		if (monitoringUser.getBlockedList().contains(getClient().getInfo("id"))) { //preventing monitor to someone you blocked
			try {
				getClient().sendToClient("Client cannot monitor you.");
			} catch (IOException e) {}
			return;
		}
		c.addToMonitorList((String)monitoringUser.getInfo("id"));
		try {
			c.sendToClient("You are now being monitored by " + idToMonitor);
		} catch (IOException e) {}
		
		try {
			monitoringUser.sendToClient("You are now monitoring " + c.getInfo("id") + ". Use #break <username> to stop monitoring this user");
		} catch (IOException e) {}
	}
}
