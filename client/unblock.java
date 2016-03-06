package client;
import java.util.ArrayList;

public class unblock extends ClientCommand {
	private String unblockClientId;
	
	public unblock(String str, ChatClient1 client){
		super(str.split(" ")[0], client);
		unblockClientId = str;
	}
	
	@Override
	public void doCommand() {
		// TODO Auto-generated method stub
		ArrayList blockedList = getClient().getBlockedList();
		if (unblockClientId.length() > 0 && blockedList.contains(unblockClientId)) {
			blockedList.remove(unblockClientId);
			getClient().clientUI().display("Messages from " + unblockClientId + " will now be displayed.");
		}
		else if (unblockClientId.length() == 0 && blockedList.size() > 0) {
			for (int i = 0; i < blockedList.size(); i++) {
				getClient().clientUI().display("Messages from " + blockedList.get(i) + " will now be displayed.");
			}
			blockedList.clear();
		}
		else if (blockedList.size()==0)
			getClient().clientUI().display("No blocking is in effect.");
		else {
			getClient().clientUI().display("Messages from " + unblockClientId + " were not blocked");
		}
}
}
