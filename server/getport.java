package server;

public class getport extends ServerCommand{

	public getport(String str, EchoServer1 server) {
		super(str, server);
	}

	@Override
	public void doCommand() {
		System.out.println("Connected on port " + getServer().getPort());
	}
}
