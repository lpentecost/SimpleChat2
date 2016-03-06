/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

package SimpleChatClient;

/* TextDemo.java requires no other files. */

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

import common.ChatIF;
import server.Channel;
import client.ChatClient1;

public class ClientGUI extends JPanel implements ActionListener, ChatIF {
    protected JTextField textField;
    protected JTextArea textArea;
    private final static String newline = "\n";
        
    /**
     * The default port to connect on.
     */
    final public static int DEFAULT_PORT = 5555;
    
    /**
     * The instance of the client created by this ClientGUI.
     */
    ChatClient1 client;

    public ClientGUI(String host, int port, String channel) {
        super(new GridBagLayout());

        textField = new JTextField(20);
        textField.addActionListener(this);

        textArea = new JTextArea(5, 20);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        //Add Components to this panel.
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;

        c.fill = GridBagConstraints.HORIZONTAL;
        add(textField, c);

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(scrollPane, c);
        
        try
        {
          client = new ChatClient1(host, port, this, channel);
        }
        catch(IOException exception)
        {
          display("Error: Can't setup connection!\n"
                    + " Terminating client.");
          System.exit(1);
        }
        //added from prugh
        display("connected to " + host + "-" + port);
        //-------
    }

    public void actionPerformed(ActionEvent evt) {
        String message = textField.getText();
        //added
        if(message.contains("#monitor") ){
            textArea.setForeground(Color.red);
        }
        else if (message.contains("#stopmonitor")){
         textArea.setForeground(Color.BLACK);
        }//-----------
        
        client.handleMessageFromClientUI(message);
        textField.setText("");
    }
    
    public void display(String message)
    {
        //added:
        System.out.println(message);//unsure if this is a leftover debugging thing from prugh
        //-----
        textArea.append("> " + message + newline);    	
        //Make sure the new text is visible, even if there
        //was a selection in the text area.
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI(String host, int port, String channel) {
        //Create and set up the window.
        JFrame frame = new JFrame("Chat");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add contents to the window.
        frame.add(new ClientGUI(host, port, channel));

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
    	final String host = "localhost";//changed to be final (unsure if this necessary or has purpose or breaks things)
        int port = DEFAULT_PORT;//changed from hard-coded 5555 to DEFAULT_PORT        
        
        
        //added
        String idIn = "";
        final String id;
        try{//this part might have to work differently in general depending on how mike's channel implementation works.
            idIn = args[0];
        }
        catch(ArrayIndexOutOfBoundsException e){
            System.out.println("No id provided, can't login.");
            System.exit(-1);
        }
        
        try{
            port = Integer.parseInt(args[1]);
        } catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Default port used.");
            port = DEFAULT_PORT;
        } catch(NumberFormatException e){
            System.out.println("Port not a number, exiting");
            System.exit(-1);
        }
        final int port_final = port;//moved down here for prugh's code
        id = idIn;
        //------
       
    	//Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(host, port_final, Channel.DEFAULT_CHANNEL);
            }
        });
    }
    
    public static void createAndRun(String host, int port, String channelName){
    	javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI("localhost", 5555, channelName);
            }
        });
    }
}

