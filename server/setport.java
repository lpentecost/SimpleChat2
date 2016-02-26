package server;

import java.io.IOException;

public class setport extends NotConnectedServerCommand{

	public setport(String str, EchoServer1 server) {
		super(str, server);
	}
	
	@Override
	public void doCmd() {
		
		int port = 0;
		try{
			port = Integer.valueOf(getStr());
		} catch (NumberFormatException e){
			System.out.println("Port must be a number!");
		}
		
		if (port < 100 || port > 9999){
			System.out.println("Port not set. Port must be between 100 and 9999");
			return;
		}
		
		getServer().setPort(port);
		System.out.println("Port set to " + port);
	}
}