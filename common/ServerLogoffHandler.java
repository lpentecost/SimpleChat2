package common;

import server.EchoServer1;

public class ServerLogoffHandler extends ServerMessageHandler{

	private String myId;
	
	public ServerLogoffHandler(String str){
		myId = str;
	}
	
	@Override
	public void handleMessage() {
		EchoServer1 server = getServer();
		server.setUsernameLoggedOut(myId);
		server.serverUI().display(myId + " logged out.");
	}
}
