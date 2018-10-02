package org.elsys.netprog.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class User extends Thread {
	Socket clientSocket;
	PrintWriter out;
	BufferedReader in;
	ArrayList<User> otherUsers;
	
	public User(ServerSocket serverSocket, ArrayList<User> otherUsers) throws IOException {
		this.clientSocket = serverSocket.accept();
	    System.out.println("client connected from " + clientSocket.getInetAddress());
		
	    this.otherUsers = otherUsers;
		this.out = new PrintWriter(this.clientSocket.getOutputStream(), true);
		this.in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
	}
	
	public void run() {
		String inputLine;
		try {
			while ((inputLine = in.readLine()) != null) {
			    for(int i = 0;i <= otherUsers.size();i++) {
			    	otherUsers.get(i).getPrintWriter().println(inputLine);
			    }
				
			    if (inputLine.equals("exit"))
			        break;
			}
		} catch(Throwable t) {
			System.out.println(t.getMessage());
		}
	}
	
	public PrintWriter getPrintWriter() {
		return out;
	}
}