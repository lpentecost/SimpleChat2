package server;

import java.io.IOException;

public class quit extends ServerCommand
{
  public quit(String str, EchoServer1 server)
  {
    super(str, server);
  }

  public void doCommand(){
    try{
      getServer().close();
      System.out.println("Server Shut Down");
      getServer().sendToAllClients("Server Shut Down");
      System.exit(0);
    }
    catch(IOException ex)
    {
      getServer().sendToAllClients("Something bad happened. Shutting down");
      System.exit(-1);
    }
  }
}
