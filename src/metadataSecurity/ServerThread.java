package metadataSecurity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerThread extends Thread{
	PrintWriter output;
	BufferedReader input;
	public ServerThread(Socket socket) {
		try {
			output = new PrintWriter(socket.getOutputStream(), true);
			input = new BufferedReader(
		            new InputStreamReader(socket.getInputStream()));
		} 
	 	catch (UnknownHostException e) {
            System.err.println("Don't know about host " + socket.getInetAddress());
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                server);
            System.exit(1);
        }
	}

}
