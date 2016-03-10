package client;
import java.io.IOException;

public class whoblocksme extends ClientCommand {

	public whoblocksme(String str, ChatClient1 client) {
		super(str, client);
	}
	
	@Override
	public void doCommand() {
		try{
			getClient().sendToServer("#whoblocksme " + getStr());
		}catch(IOException ex){}
	}

}
