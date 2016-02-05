package server;

import java.io.IOException;

public class start extends NotConnectedServerCommand{

	public start(String str, EchoServer1 server) {
		super(str, server);
	}

	@Override
	public void doCmd() {
		if (getServer().isListening()){
			System.out.println("The server is already running");
		} else {
			try {
				getServer().listen();
			} catch (IOException e) {
				System.out.println("Server couldn't start.");
			}
		}
	}
}
