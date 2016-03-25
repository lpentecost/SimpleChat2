package client;
import java.io.IOException;
import java.util.ArrayList;

import ocsf.server.ConnectionToClient;

public class unblock extends ClientCommand {
	
	public unblock(String str, ChatClient1 client){
		super(str.split(" ")[0], client);
	}
	
	@Override
	public void doCommand() {
		try {
			getClient().sendToServer("#unblock " + getStr());
		} catch (IOException e) {}
	}
}
