package common;

import java.io.IOException;

import ocsf.server.ConnectionToClient;

public class ServerLeaveHandler extends ServerMessageHandler{
	
	public ServerLeaveHandler(String id) {}

	public void handleMessage() {	
		
		System.out.println("-1");
		
		ConnectionToClient connectionToClient = getClient();
						
		System.out.println("0");
		
		// if client is in global channel, don't get them leave
		if (((String)connectionToClient.getInfo("channel")).equals("global")){
			try {
				connectionToClient.sendToClient("You cannot leave the global chat");
			} catch (IOException e) {
				System.out.println("1");
			}
		} else {
			try {
				connectionToClient.setInfo("channel", "global");
				connectionToClient.sendToClient("You have returned to the global chat");
			} catch (IOException e) {
				System.out.println("2");
			}
		}
	}
}