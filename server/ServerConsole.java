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
	
	private static ChatIF serverUI;
	
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
		System.out.println("Server chat client, standing by");
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
	
	public static void main(String[] args){
		// When a ServerConsole is created, create an EchoServer!
		
	    int port; //Port to listen on
	    
	    try{
	      port = Integer.parseInt(args[0]); //Get port from command line
	    } catch (Throwable t) {
	      port = DEFAULT_PORT; //Set port to 5555
	    }
	    
	    EchoServer1 sv = new EchoServer1(port);
	    serverUI = new ServerConsole(sv, port);
	    sv.setServerUI(serverUI);
	    
	    try {
		    sv.listen(); //Start listening for connections
		} catch (Exception ex) {
		    serverUI.display("ERROR - Could not listen for clients!");
		}
	    
	    // Creates a new server console
	    try {
	        ((ServerConsole)serverUI).accept();
	    } catch (Exception e) {
	    	serverUI.display("Something happened");
	    }
	    
	}
}
