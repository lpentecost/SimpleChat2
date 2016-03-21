package client;

import java.io.IOException;

public class breakmonitor extends ClientCommand{
	
	public breakmonitor(String str, ChatClient1 client){
		super(str, client);
	}
	
	@Override
	public void doCommand() {
		try {
			getClient().sendToServer("#breakmonitor " + getStr());
		} catch (IOException e) {}
	}
}
