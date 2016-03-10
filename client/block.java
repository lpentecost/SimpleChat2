package client;
import java.io.IOException;

import ocsf.server.*;
import common.*;


public class block extends ClientCommand {
	String blockClientId;
	
	public block(String str, ChatClient1 client){
		super(str, client);
		blockClientId = str;
	}
	
	@Override
	public void doCommand() {
		
		if(getStr().equals(getClient())){
			getClient().clientUI().display("You cannot block yourself");
			return;
		}
		
		try {
			getClient().sendToServer("#block " + getStr());
		} catch (IOException e) {}
	}
}