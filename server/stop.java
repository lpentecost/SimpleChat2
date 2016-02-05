package server;

public class stop extends ServerCommand
{
  public stop(String str, EchoServer1 server)
  {
    super(str, server);
  }

  public void doCommand(){
	System.out.println("Server stopped listening for clients.");
    getServer().stopListening();
  }
}
