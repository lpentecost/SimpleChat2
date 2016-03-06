package client;
import java.util.ArrayList;

public class whoiblock extends ClientCommand {
	
	public whoiblock(String str, ChatClient1 client) {
		super(str, client);
  }
	@Override
	public void doCommand() {
		// TODO Auto-generated method stub
		ArrayList blockedList = getClient().getBlockedList();
		if (blockedList.size() == 0) {
			getClient().clientUI().display("No blocking in effect.");
		}
		else {
			for (int i = 0; i < blockedList.size(); i++) {
				getClient().clientUI().display("Messages from " + blockedList.get(i) + " are blocked.");
			}
		}
	}

}
