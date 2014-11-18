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
	public Server(){
		 try (ServerSocket serverSocket = new ServerSocket(4444)) { 
	            while (true) {
	                new ServerThread(serverSocket.accept(), "start connection").start();
	            }
	        } catch (IOException e) {
	            System.err.println("Could not listen on port " + 4444);
	        }
	}
	
	 public static void main(String[] args) throws IOException {
	        new Server();
	    }
}
