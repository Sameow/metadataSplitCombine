package metadataSecurity;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketImpl;

public class Server {
	public Server(){
		int portNumber = 4444;
		try ( 
	            ServerSocket serverSocket = new ServerSocket(portNumber);
	            Socket clientSocket = serverSocket.accept();
	            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
	            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	        ) {
	            String inputLine, outputLine;
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
	                + portNumber + " or listening for a connection");
	            System.out.println(e.getMessage());
	        }
	}
	public void splitFile(String fileName, int fileSize, Socket clientSocket) throws IOException{
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
	}
	
	public void combineFile(){
		
	}
	 public static void main(String[] args) throws IOException {
	        new Server();
	    }
}
