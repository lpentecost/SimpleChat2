package server;

import java.util.HashSet;

public class Channel {

	String name;
	HashSet<String> usernames;
	
	public Channel(String channelName){
		name = channelName;
		usernames = new HashSet<String>();
	}
	
	public void addUser(String username){
		usernames.add(username);
	}
	
}
