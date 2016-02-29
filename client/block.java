package client;
import java.io.IOException;

import ocsf.server.*;
import common.*;


public class block extends ClientCommand {
	String blockClientId;
	
	public block(String str, ChatClient1 client){
		super(str.split(" ")[0], client);
		blockClientId = str;
	}
	@Override
	public void doCommand() {
//		ServerForwardMessageHandler msgHandler = new ServerForwardMessageHandler(blockClientId);
		// TODO Auto-generated method stub
		if (getClient().getBlockedList().indexOf(blockClientId) > -1) { // blocked client has already been blocked
			getClient().clientUI().display(blockClientId + " has already been blocked.");
		}
		else if(getClient().getId().equals(blockClientId)) { // tries to block themselves
			getClient().clientUI().display("You cannot block the sending of messages to yourself.");
		}
//		else if(msgHandler.findClient() == null) { //blocked client doesn't exist
//			getClient().clientUI().display("User " + blockClientId + " does not exist.");
//			System.out.println("Here3");
//		}
		else {
			getClient().block(blockClientId);
			try {
				getClient().sendToServer(new ServerBlockingMessageHandler(getClient().getId(), blockClientId));
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
			getClient().clientUI().display("Messages from " + blockClientId + " will be blocked.");
		}
	}

}