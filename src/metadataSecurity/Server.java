package metadataSecurity;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketImpl;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Server {
	private ArrayList<InetAddress> otherServerIP;
	
	public Server(){
		try ( 
	            ServerSocket serverSocket = new ServerSocket(4444);
	            Socket clientSocket = serverSocket.accept();
	            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
	            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	        ) {
	            String inputLine, outputLine;
	            output.println("Connected");
	            while ((inputLine = input.readLine()) != null) {
	            	if (inputLine.equals("Combine file.")){
	            		combineFile();
	            	}
	            	if (inputLine.equals("Split file.")){ 
	            		String fileName = input.readLine();
	            		int fileSize = Integer.parseInt(input.readLine());
	            		splitFile(fileName, fileSize, clientSocket);
	            	}
	            }
	        } catch (IOException e) {
	            System.out.println("Exception caught when trying to listen on port "
	                + 4444 + " or listening for a connection");
	            System.out.println(e.getMessage());
	        }
	}
	private void splitFile(String fileName, int fileSize, Socket clientSocket) throws IOException{
		File receivedFile = new File(fileName);
		FileOutputStream fos = new FileOutputStream(receivedFile,true);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		byte[] mybytearray = new byte[fileSize];
        int bytesRead = clientSocket.getInputStream().read(mybytearray, 0, mybytearray.length);
        bos.write(mybytearray, 0, bytesRead);
	    fos.flush();
	    bos.close();
	    fos.close();
	  
	   //fileSplit(receivedFile);
	    sendSharesToOthers();
	    
	}
	
	private void sendSharesToOthers() {
		otherServerIP = getOtherServerIP();
		Socket socket;
		PrintWriter output;
		BufferedReader input;
		 try {
			 	socket = new Socket(server, 4444);
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
		output.println("Sending shares.");
//		output.println(shares n relevant info);
	}
	private ArrayList<InetAddress> getOtherServerIP() throws UnknownHostException {
		//get all the server IP
		ArrayList<InetAddress> serverIPs =new ArrayList<InetAddress>();
		for (int i=0; i<serverIPs.size(); i++){
			if (serverIPs.get(i).equals(InetAddress.getLocalHost())){
				serverIPs.remove(i);
				break;
			}
		}
	
		
		return otherServerIP;	
	}
	private void combineFile(){
		
	}
	 public static void main(String[] args) throws IOException {
	        new Server();
	    }
}
