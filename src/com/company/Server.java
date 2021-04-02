package com.company;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A single message is send in a DataGramPacket through a DataGramSocket
 */
public class Server extends Thread{
    private boolean is_running;//status variable
    private DatagramSocket socket;//global datagram socket to send packets
    private byte[] buffer=new byte[256];//byte array to wrapp our messages

    //constructor for server
    public Server() throws SocketException {
        socket=new DatagramSocket(8080);
    }

    //create threads
    public void run(){
        is_running=true;
        //while loop that runs until running is changed to false
        while (is_running){
            DatagramPacket packet=new DatagramPacket(buffer,buffer.length);//instantiate a datagrampacket to receive incoming messages
            try {
                socket.receive(packet);//call receive method on the socket.Blocks until a message arrives it stores the message inside the byte array od the datagram that passed it
            } catch (IOException e) {
                e.printStackTrace();
            }
            // retrieve the address and port of client since we are going to respond
            InetAddress address=packet.getAddress();
            int port=packet.getPort();
            //create datagram to reply message from client
            packet=new DatagramPacket(buffer,buffer.length,address,port);
            String received=new String(packet.getData(),0,packet.getLength());
            if(received.equals("end")){
                is_running=false;
                continue;
            }
            try {
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        socket.close();
    }

//    private static String[] names ={"Linda","Seo Joon", "Matt","Marcus"};
//    private static String[] adjs ={"the gentle ","the un over wrought ", "the urbane"};
//    private static ArrayList<Handler>clients=new ArrayList<>();
//    private static ExecutorService pool= Executors.newFixedThreadPool(4);
//
//    public static String getRandomName(){
//        String name=names[(int) (Math.random() * names.length)];
//        String adj=adjs[(int) (Math.random() * adjs.length)];
//        return name+" "+adj;
//    }
//    public static void main(String[] args)throws IOException {
//        ServerSocket client_lister =new ServerSocket(PORT);
//        while (true) {
//            System.out.println("Waiting for connection");
//            Socket client = client_lister.accept();
//            System.out.println("Server connected!");
//            Handler thread=new Handler(client,clients);
//            clients.add(thread);
//            pool.execute(thread);
//        }
//
////    }{output.println("Hey");
////        System.out.println("Closing connection");
////        client.close();
////        client.close();
//
//
//    }
}
