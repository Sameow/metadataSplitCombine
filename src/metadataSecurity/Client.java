package metadataSecurity;

import java.io.*;
import java.net.*;

public class Client {
	private InetAddress server;  
	private PrintWriter output;
	private BufferedReader input;
	
	 public Client() {
		 try {
				server = InetAddress.getLocalHost();
	            Socket socket = new Socket(server, 4444);
	            output = new PrintWriter(socket.getOutputStream(), true);
	            input = new BufferedReader(
	                new InputStreamReader(socket.getInputStream()));
	        } catch (UnknownHostException e) {
	            System.err.println("Don't know about host " + server);
	            System.exit(1);
	        } catch (IOException e) {
	            System.err.println("Couldn't get I/O for the connection to " +
	                server);
	            System.exit(1);
	        }
	            BufferedReader userInput =
	                new BufferedReader(new InputStreamReader(System.in));
	            String fromServer;
	            String fromUser;

	            while ((fromServer = input.readLine()) != null) {
	                System.out.println("Server: " + fromServer);
	                if (fromServer.equals("Bye."))
	                    break;
	                
	                fromUser = userInput.readLine();
	                if (fromUser != null) {
	                    System.out.println("Client: " + fromUser);
	                    output.println(fromUser);
	                }
	            }
	 }
	 public static void main(String[] args) throws IOException {
	       new Client();
	    }
	 
}
