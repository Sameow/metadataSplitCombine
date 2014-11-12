package metadataSecurity;

import java.io.*;
import java.net.*;

public class Client {
	private InetAddress server;  
	private PrintWriter output;
	private BufferedReader input;
	private boolean fileSplited;
	private Socket socket;
	
	 public InetAddress getServer() {
		return server;
	}
	public void setServer(InetAddress server) {
		this.server = server;
	}
	public PrintWriter getOutput() {
		return output;
	}
	public void setOutput(PrintWriter output) {
		this.output = output;
	}
	public BufferedReader getInput() {
		return input;
	}
	public void setInput(BufferedReader input) {
		this.input = input;
	}
	public boolean isFileSplited() {
		return fileSplited;
	}
	public void setFileSplited(boolean fileSplited) {
		this.fileSplited = fileSplited;
	}
	
	public Client() throws IOException {
		 try {
				server = InetAddress.getLocalHost();
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
	 }
	
	public void sendFile(File file) throws IOException{
		FileInputStream userInput = new FileInputStream(file);
        if (userInput != null) {
            output.println(userInput);
        }      
        String serverResult;
        while ((serverResult = input.readLine()) != null) {
            if (serverResult.equals("File splited")) {
            	fileSplited=true;   	
                break;	
            }
        }
	}
	
	public File getFile() throws IOException {
		output.println("Combine file.");
		String fileName = input.readLine();
		File combinedFile = new File(fileName);
		FileOutputStream fos = new FileOutputStream(combinedFile,true);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		byte[] mybytearray = new byte[1024];
        InputStream is = socket.getInputStream();
        int bytesRead = is.read(mybytearray, 0, mybytearray.length);
        bos.write(mybytearray, 0, bytesRead);
	    fos.flush();
	    bos.close();
	    fos.close();
	    return combinedFile;   
        }
	
	 public static void main(String[] args) throws IOException {
//	       new Client(new File("TextFile.txt"));
	    }
	 
}
