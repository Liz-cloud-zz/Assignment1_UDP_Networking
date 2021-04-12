import java.io.IOException;
import java.net.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class Server {
    public static volatile boolean running;
    public static HashMap<String, Connector > hashMap =new HashMap<>();//Hash Map to store the different clients to  be contacted
    public static ArrayList<String> client_names=new ArrayList<>();//arraylist for the names of the clients
    public static void end(){
        running=false;
    }//to terminate thread for connection connection


//    private static class Sender implements Runnable {
//        @Override
//        public void run() {
//             running=true;
//            try {
//                DatagramSocket server_sender=new DatagramSocket();
//                while (running){
//                    Scanner in=new Scanner(System.in);
//                    String message=in.nextLine();
//                    try {
//
//                        DatagramPacket packet=new DatagramPacket(message.getBytes(), message.length(), InetAddress.getLocalHost(),8080);
//                        server_sender.send(packet);
//                    } catch (UnknownHostException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    if(message.equals("end")){
//                        end();
////                        Thread.sleep((long)15);
//                        continue;
//                    }
//                }
//            } catch (SocketException e) {//| InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        }
//    }
    private static class Receiver implements Runnable { //runnable class to allow user to constantly be able to receive packets
        @Override
        public void run() {
            int start=0;
            int end=0;
            String client_name="";
            String to_send="";
            String message="";
            String ipAddress="";
            running=true;
            try {
                while (running) {
                    //Step2: receive the first message sent by client to establish connection
                    DatagramSocket sktReceive = new DatagramSocket(3000);
                    byte[] buf = new byte[1024]; //create empty byte buffer
                    DatagramPacket pktReceive = new DatagramPacket(buf, buf.length); //create temporary empty packet
                    sktReceive.receive(pktReceive); //receive packet from socket and store in packet
                    message = new String(pktReceive.getData(), 0, pktReceive.getLength()); //convert packet to message string
                    String []strings=message.split(",");
                    client_name=strings[0];//get client name
                    to_send=strings[1];//message  to send to client2
                    System.out.println("Connection established! The client name is: " + client_name+" message :"+to_send); //output message received

                    //Step3: verify to 1st client that you have seen the message by sending a message
                    InetAddress clientAddress = pktReceive.getAddress();//client1 ip
                    int clientPort = pktReceive.getPort();//client1 port number
                    Connector c1=new Connector(clientPort,clientAddress.getHostAddress());//make a Connector Object
                    hashMap.put(client_name,c1);//add the client to the client list
                    client_names.add(client_name);//add the client name to client name list
                    String quote = "Message Received.Please send the name of the destination client,Client IP Address,message this form:[#client_name#,000.000.000.000,message] you want to send to client: ";
                    byte[] buffer = quote.getBytes();
                    if(client_names.contains(client_name)) {
                        Connector to_connect = hashMap.get(client_name);
                        DatagramPacket response = new DatagramPacket(buffer, buffer.length, to_connect.getAddress(), to_connect.getPort_number());
                        sktReceive.send(response);
                    }else {
                        System.out.println("unknown client name!");
                    }

                    //Step6: get the client name from user and the message to be sent
                    // add the client to the hash map
                    sktReceive.receive(pktReceive); //receive packet from socket and store in packet
                    message= new String(pktReceive.getData(), 0, pktReceive.getLength()); //convert packet to message string
                    String []strings3=message.split(",");
                    client_name=strings3[0];//get client name
                    ipAddress=strings3[1];
                    to_send=strings3[2];//message  to send to client2
                    System.out.println("Client name is " + client_name+" "+"message to be send is:"+to_send); //output message received
                    Connector c=new Connector(clientPort,ipAddress);
                    hashMap.put(client_name,c);
                    client_names.add(client_name);

                    //Step7: forward the message to the 2nd client
                    if(client_names.contains(client_name)){
                        Connector to_connect=hashMap.get(client_name);
                        byte[] b=to_send.getBytes();
                        DatagramPacket forward = new DatagramPacket(b, b.length, to_connect.getAddress(),to_connect.getPort_number() );
                        sktReceive.send(forward);

                        //Step10: 2nd client replies to the message you send to send to client 1
                        sktReceive.receive(pktReceive); //receive packet from socket and store in packet
                        String delivered = new String(pktReceive.getData(), 0, pktReceive.getLength()); //convert packet to message string
                        System.out.println("Client2 says: " + delivered); //output message received

                        //Step11: forward the message from second client to 1st client
                        // If second client doesnt exist send broadcast message to all users
                        String reply="Reply from 2nd client: "+delivered;
                        byte[] buffe = reply.getBytes();
                        String []strings2=message.split(",");
                        client_name=strings2[0];//get client name
                        ipAddress=strings2[1];//get ip address
                        to_send=strings2[2];//message  to send to client2
                        if(client_names.contains(client_name)){
                            Connector c2=hashMap.get(client_name);
                            DatagramPacket response1 = new DatagramPacket(buffe, buffe.length, c2.getAddress(), c2.getPort_number());
                            sktReceive.send(response1);
                        }else {//if its not an existing client add it to the client list then send message to it
                            Connector c3=new Connector(clientPort,ipAddress);
                            hashMap.put(client_name,c3);
                            client_names.add(client_name);
                            if(client_names.contains(client_name)) {
                                Connector connector1 = hashMap.get(client_name);
                                byte[] feedback=message.getBytes();
                                DatagramPacket response1 = new DatagramPacket(feedback, feedback.length, connector1.getAddress(), connector1.getPort_number());
                                sktReceive.send(response1);
                            }
                        }
                    }
                    else {
                        //if client doesnt exist tell the all users that it doesn't exist
                        message="Unknown client name :"+client_name+"client is probably off line";
                        byte[] feedback=message.getBytes();
                        for (Connector connector1: hashMap.values()){
                            DatagramPacket response1 = new DatagramPacket(feedback, feedback.length, connector1.getAddress(), connector1.getPort_number());
                            sktReceive.send(response1);
                        }
                    }
                    sktReceive.close();
                    if (message.equals("end"))
                        end();
//                    Thread.sleep((long)15);//end loop (and thread) if server says end
                }
            } catch (SocketException e){ // | InterruptedException e) {
                System.out.println("Socket error in Client Receiver: ");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("IO error in Client Receiver: ");
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args){
        //new Thread(new Server.Sender()).start();
        // we should work using one thread for both sending and receiving otherwise the above changes wont be possible
        new Thread(new Server.Receiver()).start();
     }
}



