package com.company;
import java.net.*;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Client {

//    public static int PORT;
//    public static volatile boolean running;
//
//    public static void end(){
//        running=false;
//    }
//
//    private static class Receiver implements Runnable { //runnable class to allow user to constantly be able to receive packets
//        @Override
//        public void run() {
//            running = true;
//            try {
//                while (running) {
//                    DatagramSocket sktReceive = new DatagramSocket(8080);
//                    byte[] buf = new byte[1024]; //create empty byte buffer
//                    DatagramPacket pktReceive = new DatagramPacket(buf, buf.length); //create temporary empty packet
//                    sktReceive.receive(pktReceive); //receive packet from socket and store in packet
//                    String msg = new String(pktReceive.getData(), 0, pktReceive.getLength()); //convert packet to message string
//                    System.out.println("Server says: " + msg); //output message received
//                    sktReceive.close(); //close socket
//
//                    if (msg.equals("end")) {
//                        end();
////                   Thread.sleep((long)15);//end loop (and thread) if server says end
//                    }
//                }
//            } catch (SocketException e) {
//                System.out.println("Socket error in Client Receiver: ");
//                e.printStackTrace();
//            } catch (IOException e) {
//                System.out.println("IO error in Client Receiver: ");
//                e.printStackTrace();
////            } catch (InterruptedException e) {
////                e.printStackTrace();
//            }
//            }
//        }
//
//        private static class Sender implements Runnable {
//            @Override
//            public void run() {
//                running = true;
//                try {
//                    DatagramSocket sktSend = new DatagramSocket();
//                    while (running) {
//                        Scanner in = new Scanner(System.in);
//                        String message = in.nextLine();
//                        try {
//                            DatagramPacket pktSend = new DatagramPacket(message.getBytes(), message.length(), InetAddress.getLocalHost(), 3000);
//                            sktSend.send(pktSend);
//                        } catch (UnknownHostException e) {
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        if (message.equals("end")) {
//                            end();
////                        Thread.sleep((long)15);
//                            continue;
//                        }
//                    }
//                } catch (SocketException e) { //|InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }

//    public boolean getRunning() {
//        return running;
//    }
//
//    public void setRunning(boolean run) {
//        running = run;
//    }

        public static void main(String[] args) {
//        running = true;

            new Thread(new Sender("Client",Handler.getServerPort())).start();
            new Thread(new Receiver("Client",Handler.getNewPort())).start();
        }
    }

