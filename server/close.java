package server;

import java.io.IOException;

public class close extends ServerCommand
{
  public close(String str, EchoServer1 server)
  {
    super(str, server);
  }

  public void doCommand(){
    try{
      getServer().close();
      getServer().sendToAllClients("Server Closed");
    }
    catch(IOException ex)
    {
      getServer().sendToAllClients("Something bad happened. Shutting down");
      System.exit(-1);
    }
  }
}
