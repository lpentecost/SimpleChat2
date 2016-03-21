//package common;
//
//import ocsf.server.ConnectionToClient;
//
//public abstract class ServerMessageFindHandler extends ServerMessageHandler {
//
//	@Override
//	public abstract void handleMessage();
//	
//	public ConnectionToClient findClient(String clientId){
//	    Thread[] clientThreadList = getServer().getClientConnections();
//	    for (int i=0; i<clientThreadList.length; i++)
//	    {
//	    	if(((ConnectionToClient)clientThreadList[i]).getInfo("id").equals(clientId)) {
//	    		return (ConnectionToClient) clientThreadList[i];
//	    	}
//	    }
//	    return null;
//	}
//
//}
