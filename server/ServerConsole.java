package server;

import common.*;
import ocsf.server.AbstractServer;
import server.EchoServer1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import SimpleChatClient.ClientConsole;
import client.*;

public class ServerConsole implements ChatIF{

	//Class variables *************************************************
	final public static int DEFAULT_PORT = 5555;
	
	private EchoServer1 server;
	
	private int port;
	
	public ServerConsole(EchoServer1 sv, int p){
		server = sv;
		port = p;
	}
	
	@Override
	public void display(String message){
		System.out.println(message);
	}
	
	public void accept(){
	  try {
		System.out.println("Server chat client, standing by\n");
	    BufferedReader fromConsole = new BufferedReader(new InputStreamReader(System.in));
	    String message;
	
	    while (true){
	      message = fromConsole.readLine();
	      server.handleMessageFromServerConsole(message);
	    }
	  } catch (Exception ex) {
	    System.out.println("Unexpected error while reading from console!");
	  }
	}
	
//	public static void main(String[] args) {
//		String host = "";
//		String id = "";
//		int port = 0;
//		
//		try{
//			port = Integer.valueOf(args[0]);
//		}catch(NumberFormatException e){
//			port = DEFAULT_PORT;
//		}
//		
//	    try {
//	      id = args[0];
//	    } catch(ArrayIndexOutOfBoundsException e) {
//	      System.out.println("No id provided, can't login.");
//	      System.exit(-1);
//	    }
//	    System.out.println("Loggging in as " + id);
//	    
//	    try {
//	      host = args[1];
//	    } catch(ArrayIndexOutOfBoundsException e) {
//	      host = "localhost";
//	    }
//	    
//	    ServerConsole chat = new ServerConsole(server, port);
//	    chat.accept();
//	}
	
}
