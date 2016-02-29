package server;

import java.io.IOException;
import java.util.HashSet;

import ocsf.server.ConnectionToClient;

public class Channel {

	String name;
	HashSet<String> usernames;
	
	public Channel(String channelName){
		name = channelName;
		usernames = new HashSet<String>();
	}
	
	public ConnectionToClient getConnectionToClientByUsername(String username, EchoServer1 server){
		Thread[] clientThreadList = server.getClientConnections();

	    for (int i=0; i<clientThreadList.length; i++)
	    {
	      try
	      {
	    	  if ( username.equals( ((ConnectionToClient)clientThreadList[i]).getInfo("id"))) {
	    		  return (ConnectionToClient)clientThreadList[i]; 
	    	  }
	      }catch (Exception ex) {}
	    }
	    return null;
	}
		
	public void addUser(String username){
		usernames.add(username);
	}

	public void sendToMembers(Object msg, EchoServer1 server) {
		HashSet<String> recipients = usernames;
		for (String name : recipients){
			try {
				getConnectionToClientByUsername(name, server).sendToClient(msg);
			} catch (IOException e) {}
		}		
	}
}
