package client;

public class stopmonitor extends ClientCommand {
	
	public stopmonitor(String str, ChatClient1 client) {
		super(str, client);
	}
	
	@Override
	public void doCommand() {
		// TODO Auto-generated method stub
		if (getClient().getMonitor().size() == 0)
			getClient().clientUI().display("No one is monitoring you.");
		else {
			getClient().getMonitor().clear();
			getClient().clientUI().display("You have cleared all monitoring sessions.");
		}
	}

}
