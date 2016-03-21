package client;

import java.io.IOException;

public class stopmonitor extends ClientCommand {
	
	public stopmonitor(String str, ChatClient1 client) {
		super(str, client);
	}
	
	@Override
	public void doCommand() {
		try {
			getClient().sendToServer("#stopmonitor " + getStr());
		} catch (IOException e) {}
	}
}
