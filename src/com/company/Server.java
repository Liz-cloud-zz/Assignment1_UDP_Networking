package com.company;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Server {
//    public static int PORT=8080;
//    public static volatile boolean running;
//
//    public static void end(){
//        running=false;
//    }
//
//
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
//    private static class Receiver implements Runnable { //runnable class to allow user to constantly be able to receive packets
//        @Override
//        public void run() {
//            running=true;
//            try {
//                while (running) {
//                    DatagramSocket sktReceive = new DatagramSocket(3000);
//                    byte[] buf = new byte[1024]; //create empty byte buffer
//                    DatagramPacket pktReceive = new DatagramPacket(buf, buf.length); //create temporary empty packet
//                    sktReceive.receive(pktReceive); //receive packet from socket and store in packet
//                    String msg = new String(pktReceive.getData(), 0, pktReceive.getLength()); //convert packet to message string
//                    System.out.println("Client says: " + msg); //output message received
//                    sktReceive.close(); //close socket
//
//                    if (msg.equals("end"))
//                        end();
////                    Thread.sleep((long)15);//end loop (and thread) if server says end
//                }
//            } catch (SocketException e){ // | InterruptedException e) {
//                System.out.println("Socket error in Client Receiver: ");
//                e.printStackTrace();
//            } catch (IOException e) {
//                System.out.println("IO error in Client Receiver: ");
//                e.printStackTrace();
//            }
//        }
//    }

    public static void main(String[] args){
        new Thread(new Sender("Server",Handler.setNewPort())).start();
        new Thread(new Receiver("Server",Handler.setServerPort())).start();
    }

}
