package streamsAndThreads;

import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static java.lang.System.out;

@SuppressWarnings("serial")
public class ChatUsers extends JFrame implements ActionListener {
    String username;
    PrintWriter useThisToSend;
    BufferedReader useThisToReceive;
    JTextArea  chatmsg;
    JTextField typedMessage;
    JButton send,exit;
    Socket chatusers;
    
    public ChatUsers(String uname,String servername) throws Exception {
        super(uname); 
        this.username = uname;
        chatusers  = new Socket(servername,49152);
        System.out.println("Connected to "+servername);
        useThisToReceive = new BufferedReader( new InputStreamReader( chatusers.getInputStream()) ) ;
        useThisToSend = new PrintWriter(chatusers.getOutputStream(),true);
        useThisToSend.println(uname+" connected.");
        buildInterface(servername);
     // new keyExchangeServer(servername, pubk, uname);
        new MessagesThread().start();
    }
    
    class  MessagesThread extends Thread {
        @Override
        public void run() {
          //  String line;
            try {
                while(true) {	
                    	String line = useThisToReceive.readLine();
                        chatmsg.append(line + "\n");
                }
            } catch(Exception ex) {}
        }
    }
    
    public void buildInterface(String servername) {
        send = new JButton("Send");
        exit = new JButton("Exit");
        chatmsg = new JTextArea();
        chatmsg.setRows(30);
        chatmsg.setColumns(50);
        chatmsg.setEditable(false);
        chatmsg.append("Connected to "+servername+"\n");
        typedMessage  = new JTextField(50);
        JScrollPane sp = new JScrollPane(chatmsg, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(sp,"Center");
        JPanel bp = new JPanel( new FlowLayout());
        bp.add(typedMessage);
        bp.add(send);
        bp.add(exit);
        bp.setBackground(Color.LIGHT_GRAY);
        bp.setName("Java Relay");
        add(bp,"North");
        send.addActionListener(this);
        exit.addActionListener(this);
        setSize(500,300);
        setVisible(true);
        pack();
    }
    
    @Override
    public void actionPerformed(ActionEvent evt) {
        if ( evt.getSource() == exit ) {
            useThisToSend.println("end"); 
            System.exit(0);
        } else {
        	useThisToSend.println(typedMessage.getText()); 
            chatmsg.append("You say: "+typedMessage.getText() + "\n");
            typedMessage.setText(null);
        }
    }
    
    public static void main(String ... args) {
    	String SetUserName="";
    	while (SetUserName.isEmpty()){
        SetUserName = JOptionPane.showInputDialog(null,"Please enter your name :", "Java Relay",
             JOptionPane.PLAIN_MESSAGE);
        }
        String servername = "localhost";  
        try {
            new ChatUsers( SetUserName ,servername);
        } catch(Exception ex) {
            out.println( "Error:" + ex.getMessage());
        }
        
    } 
}
