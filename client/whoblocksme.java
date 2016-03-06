package client;

import java.util.ArrayList;

public class whoblocksme extends ClientCommand {

	public whoblocksme(String str, ChatClient1 client) {
		super(str, client);
	}
	
	@Override
	public void doCommand() {
		// TODO Auto-generated method stub
		ArrayList blockedMe = getClient().getBlockedMeList();
		if (blockedMe.size() == 0) {
			getClient().clientUI().display("No one is blocking you.");
		}
		else {
			for(int i = 0; i < blockedMe.size(); i++) {
				getClient().clientUI().display("Messages to " + blockedMe.get(i) + " are being blocked.");
			}
		}
	}

}
