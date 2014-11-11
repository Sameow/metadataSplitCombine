package lastYearProject;

import java.io.*;
import java.util.*;
import java.net.*;

//import relayUsingStreamsAndThreads.keyExchangeServer.publicKey;

public class ChatServer {
	private Vector<String> users = new Vector<String>();
    private Vector<Manageuser> clients = new Vector<Manageuser>();
    //Manageusers contains all clients
//   private ArrayList<publicKey> otherpplPublicKeys=new ArrayList<publicKey>();
    
    public static void main(String... args) throws Exception {
        new ChatServer();
    } 

    public ChatServer() throws Exception {
        @SuppressWarnings("resource")
		ServerSocket server = new ServerSocket(49152, 10);
        System.out.println("Server Is Running");
        while (true) {
        	System.out.println("Waiting for clients.");
            Socket client = server.accept();
            //keyExchange.awaitConnection();
            Manageuser relayer = new Manageuser(client);
            clients.add(relayer);         
        }
    }

    class Manageuser extends Thread {

        String connectedUser = "";
        BufferedReader input;
        PrintWriter output;

        public Manageuser(Socket client) throws Exception {

            input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            output = new PrintWriter(client.getOutputStream(), true);
            String received = input.readLine();
            //Extract user
            String[] splited = received.split(" connected.");
            connectedUser = splited[0];
            users.add(connectedUser);
            //Tell everyone else user connected
            sendtoall(connectedUser, received);
            System.out.println(connectedUser+" connected.");
            start(); //go to run()
        }

        public void run() {
            String line;
            try {
                while (true) {
                    line = input.readLine();
                    System.out.println("Server received: "+line);
                    if (line.equals("end")) {
                    	System.out.println("Disconnecting client: "+this+" and "+connectedUser);
                        clients.remove(this);
                        users.remove(connectedUser);
                        break;
                    }
                    
                    sendtoall(connectedUser, line); 
                }
            } 
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        } 
        
        public void sendtoall(String user, String message) {

            for (Manageuser c : clients) {
                if (!c.getchatusers().equals(user)) {
                    c.output.println(user + " says:" + message);
                    System.out.println(user + " says:" + message);
                }
            }
        }

        public String getchatusers() {
            return connectedUser;
        }
       

    } 
}
