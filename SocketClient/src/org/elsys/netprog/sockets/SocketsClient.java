package org.elsys.netprog.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class SocketsClient {

	public static void main(String[] args) throws IOException {
		Socket echoSocket = null;
		try {
			    echoSocket = new Socket("localhost", 10001);
			    PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
			    BufferedReader in = new BufferedReader(
			            new InputStreamReader(echoSocket.getInputStream()));
			    BufferedReader stdIn = new BufferedReader(
			            new InputStreamReader(System.in));
			    
			    String userInput;
			    listen(in);
			    while ((userInput = stdIn.readLine()) != null) {
			        out.println(userInput);
			    }
		} catch (Throwable t) {
			System.out.println(t.getMessage());
		} finally {
			if (echoSocket != null && !echoSocket.isClosed()) {
				echoSocket.close();
			}
		}
	}
	
	public static void listen(BufferedReader in) throws IOException {
		new Thread(new Runnable() {
			public void run() {
				while(true) {
					try {
						System.out.println("response: " + in.readLine());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

}
