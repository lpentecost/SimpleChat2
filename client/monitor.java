package client;

import java.io.IOException;

public class monitor extends ClientCommand {

	public monitor(String str, ChatClient1 client)
	{
		super(str, client);
	}

	public void doCommand()
	{
		try{
			getClient().sendToServer("#monitor " + getStr());
		}catch(IOException ex){
			getClient().clientUI().display("Monitor session with " + getClient().getHost() + " failed.");
		}
	}

}
