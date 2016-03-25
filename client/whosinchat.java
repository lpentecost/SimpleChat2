package client;
import java.io.IOException;

//import common.ServerGetClientListMessageHandler;

public class whosinchat extends ClientCommand {
	public whosinchat(String str, ChatClient1 client)
	{
		super(str, client);
	}
	
	@Override
	public void doCommand() {
		try {			
			getClient().sendToServer("#whosinchat " + getStr());
		} catch (IOException e) {}
	}

}
