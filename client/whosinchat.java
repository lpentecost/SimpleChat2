package client;
import java.io.IOException;

import common.ServerGetClientListMessageHandler;

public class whosinchat extends ClientCommand {
	public whosinchat(String str, ChatClient1 client)
	{
		super(str, client);
	}
	
	@Override
	public void doCommand() {
		// TODO Auto-generated method stub
		try {
			getClient().sendToServer(new ServerGetClientListMessageHandler());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
