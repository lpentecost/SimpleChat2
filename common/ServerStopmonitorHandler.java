package common;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;

import ocsf.server.ConnectionToClient;
import server.EchoServer1;

public class ServerStopmonitorHandler extends ServerMessageHandler{
	
	public ServerStopmonitorHandler(String c) {}
	
	@Override
	public void handleMessage() {
		
		EchoServer1 server = getServer();
		ConnectionToClient c = getClient();
				
		Iterator<String> i = c.getMonitorList().iterator();
		
		if (!i.hasNext()){
			try {
				c.sendToClient("No one is monitoring you");
			} catch (IOException e) {}
			return;
		}
		
		while (i.hasNext()) {
		   String s = i.next();
		   server.getConnectionToClientByName(s).removeFromMonitorList((String)c.getInfo("id"));
		   i.remove();
		}
		
		try {
			c.sendToClient("You are no longer being monitored");
		} catch (IOException e) {}
	}
}