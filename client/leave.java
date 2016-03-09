package client;

import java.io.IOException;
import common.ServerLeaveHandler;

public class leave extends ClientCommand{

	public leave(String str, ChatClient1 client) {
		super(str, client);
	}

	@Override
	public void doCommand() {
		try {
			getClient().sendToServer("#leave " + getStr());
		} catch (IOException e) {}
	}
}