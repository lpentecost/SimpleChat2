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
      getServer().sendToAllClients("Server Closed");
      getServer().close();
    }
    catch(IOException ex)
    {
      getServer().sendToAllClients("Something bad happened. Shutting down");
      System.exit(-1);
    }
  }
}
