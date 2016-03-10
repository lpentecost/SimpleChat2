package client;
import java.io.IOException;

public class whoiblock extends ClientCommand {
	
	public whoiblock(String str, ChatClient1 client) {
		super(str, client);
    }
	
	@Override
	public void doCommand() {
		try{
	        getClient().sendToServer("#whoiblock " + getStr());
	    }catch(IOException ex){}
	}
}
