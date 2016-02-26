package server;

public class stop extends ServerCommand
{
  public stop(String str, EchoServer1 server)
  {
    super(str, server);
  }

  public void doCommand(){
	  if(!getServer().isListening()){
		  serverUI().display("Server is already stopped, and not listening.");
		  return;
	  }
      getServer().stopListening();
  }
}
