package common;

import server.EchoServer1;

public class ServerLogoffHandler extends ServerMessageHandler{

	private String myId;
	
	public ServerLogoffHandler(){}
	
	@Override
	public void handleMessage() {
		
		EchoServer1 server = getServer();		
		server.serverUI().display(getClient().getInfo("id") + " logged out."); // This isn't getting printed
		//server.setUsernameLoggedOut(getClient().getInfo("id").toString());
		
	}
}
