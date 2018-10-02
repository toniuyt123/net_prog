package org.elsys.netprog.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class EchoServer {
	
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = null;
		try {
			ArrayList<User> connections = new ArrayList<User>();
			serverSocket = new ServerSocket(10001);
		    
			while(true) {
				User u = new User(serverSocket, connections);
				connections.add(u);
				u.start();
			}
			
		} catch (Throwable t) {
			System.out.println(t.getMessage());
		} finally {
			if (serverSocket != null && !serverSocket.isClosed()) {
				serverSocket.close();
			}
			
			System.out.println("Server closed");
		}
	}

}
