package server;

import java.io.IOException;

public class setport extends NotConnectedServerCommand{

	public setport(String str, EchoServer1 server) {
		super(str, server);
	}
	
	@Override
	public void doCmd() {
		String host = getStr();
		
		if (host != ""){
			getServer().setPort(host);
			System.out.println("Port set to " + host);
		} else {
			System.out.println("Port number cannot be empty. Host not set");
		}
		
	}

}
