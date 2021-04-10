//package com.company;
//
//import java.io.IOException;
//import java.net.DatagramPacket;
//import java.net.DatagramSocket;
//import java.net.SocketException;
//
//public class Receiver implements Runnable {
//    private int port;
//    private volatile boolean running;
//    private String name;
//
//    public Receiver(String myName, int portNum/*, boolean shouldRun*/) {
//        name = myName;
//        port = portNum;
////        running = shouldRun;
//    }
//
//    public void setRunning(boolean run) {
//        running = run;
//    }
//
//    @Override
//    public void run() {
//        setRunning(true);
//        try {
//            while (running) {
//                DatagramSocket sktReceive = new DatagramSocket(port);
//                byte[] buf = new byte[1024]; //create empty byte buffer
//                DatagramPacket pktReceive = new DatagramPacket(buf, buf.length); //create temporary empty packet
//                sktReceive.receive(pktReceive); //receive packet from socket and store in packet
//                String msg = new String(pktReceive.getData(), 0, pktReceive.getLength()); //convert packet to message string
//                System.out.println(name + " says: " + msg); //output message received
//                sktReceive.close(); //close socket
//
//                if (msg.equals("end"))
//                    setRunning(false); //end loop (and thread) if server says end
//            }
//        } catch (SocketException e) {
//            System.out.println("Socket error in Client Receiver: ");
//            e.printStackTrace();
//        } catch (IOException e) {
//            System.out.println("IO error in Client Receiver: ");
//            e.printStackTrace();
//        }
//    }
//}
