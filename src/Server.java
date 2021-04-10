import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class Server {
    public static int PORT=8080;
    public static volatile boolean running;
    public static HashMap<String, Connector > hashMap =new HashMap<>();//Hash Map to store the different clients to  be contacted
    public static ArrayList<String> client_names=new ArrayList<>();//aaraylist for the names of the clients
    public static void end(){
        running=false;
    }


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
            running=true;
            try {
                while (running) {
                    //Step2: receive the first message sent by client to establish connection
                    DatagramSocket sktReceive = new DatagramSocket(3000);
                    byte[] buf = new byte[1024]; //create empty byte buffer
                    DatagramPacket pktReceive = new DatagramPacket(buf, buf.length); //create temporary empty packet
                    sktReceive.receive(pktReceive); //receive packet from socket and store in packet
                    String msg = new String(pktReceive.getData(), 0, pktReceive.getLength()); //convert packet to message string
                    System.out.println("Connection established! The client said: " + msg); //output message received

                    //Step3: verify to 1st client that you have seen the message by sending a message
                    InetAddress clientAddress = pktReceive.getAddress();//client1 ip
                    int clientPort = pktReceive.getPort();//client1 port number
                    String quote = "Message Received.Please send the name of the client you want your message send to: ";
                    byte[] buffer = quote.getBytes();
                    DatagramPacket response = new DatagramPacket(buffer, buffer.length, clientAddress, clientPort);
                    sktReceive.send(response);

                    //Step6: get the client name from user and the message to be sent
                    // add the client to the hash map
                    sktReceive.receive(pktReceive); //receive packet from socket and store in packet
                    String message= new String(pktReceive.getData(), 0, pktReceive.getLength()); //convert packet to message string
                    int start=message.indexOf('#');
                    int end=message.lastIndexOf('#');
                    String client_name=message.substring(start,end+1);
                    String to_send=message.substring(end+1);
                    System.out.println("Client name is " + client_name+" "+"message to be send is:"+to_send); //output message received

                    String ip="192.168.1.52";//ip address of 2nd client to receive message
                    int port=8090;//port of 2nd client to receive message
                    Connector c=new Connector(port,ip);
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
//                        String  x = "received received";
                        String reply="Reply from 2nd client: "+delivered;
                        byte[] buffe = reply.getBytes();
                        DatagramPacket response1 = new DatagramPacket(buffe, buffe.length, InetAddress.getByName("192.168.1.1"), clientPort);
                        sktReceive.send(response1);

                    }
                    else {
                        //if client doesnt extist tell the user that it doesn't exist
                        String  x = "client does not exist";
                        byte[] buffe = x.getBytes();
                        DatagramPacket response1 = new DatagramPacket(buffer, buffer.length, clientAddress, clientPort);
                        sktReceive.send(response1);
                    }
                    sktReceive.close();
                    if (msg.equals("end"))
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
//    public static void main(String[] args){
//        new Thread(new Sender("Server",Handler.setNewPort())).start();
//        new Thread(new Receiver("Server",Handler.setServerPort())).start();
//    }


