package server;

import java.io.IOException;

public class start extends ServerCommand{

	public start(String str, EchoServer1 server) {
		super(str, server);
	}

	@Override
	public void doCommand() {
		
		if(getServer().isListening()){
			serverUI().display("The server is already running");
			return;
		}
		
		try {
			getServer().listen();
		} catch (IOException e) {
			serverUI().display("Server could not listen");
		}
	}
}
