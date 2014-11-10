package metadataSecurity;
import java.io.*;
import java.net.*;
import java.util.*;
 
public class sendToServer extends Thread {
 
    protected DatagramSocket socket = null;
    protected BufferedReader in = null;
    protected boolean moreQuotes = true;

    public sendToServer(File file) throws IOException {
        FileInputStream inputStream = new FileInputStream(file);
        byte[] fileInBytes = new byte[(int) file.length()];
        inputStream.read(fileInBytes, 0, (int) file.length());
        // get a datagram socket
        DatagramSocket socket = new DatagramSocket();
        // send request
        InetAddress address = InetAddress.getLocalHost();
        DatagramPacket packet = new DatagramPacket(fileInBytes, fileInBytes.length, address, 4445);
        socket.send(packet);
     
//        // get response
//        packet = new DatagramPacket(buf, buf.length);
//        socket.receive(packet);
// 
//        // display response
//        String received = new String(packet.getData(), 0, packet.getLength());
//        System.out.println("Quote of the Moment: " + received);
//     
//        socket.close();
        
    }
}