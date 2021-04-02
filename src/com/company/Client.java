package com.company;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class Client {
    private static final String host_name="localhost";
    public static final int PORT = 8080;
    private static DatagramSocket socket;
//    private static byte[] buffer;
    public static InetAddress address;

    public Client(){
        try {
//            socket = new DatagramSocket(IPADDRESS, PORT);
            try {
                address = InetAddress.getByName(host_name);
//                new Thread(new Receiver()).start();
//                new Thread(new Sender()).start();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }

        } finally {

        }
    }

//    public String sendMessage(String msg) throws IOException {
//        buffer=msg.getBytes();
//        DatagramPackgramPacket(buffer,buffer.length,address,PORT);
//        socket.send(packet);
//        packet=new DatagramPacket(buffer,buffer.length);
//        String receive=new String(packet.getData(),0,packet.getLength());
//        return receive;
//    }

//    public static void main(String[] args) throws IOException {
//
//
//           ServerConnection sc= new ServerConnection(socket);
////            BufferedReader input =new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            BufferedReader keyboard=new BufferedReader(new InputStreamReader(System.in));
//            PrintWriter output=new PrintWriter(socket.getOutputStream(),true);
//
//            new Thread(sc).start();
//            while (true){
//                System.out.println("> ");
//                String command=keyboard.readLine();
//                if(command.equals("quit")){break;}
//                output.println(command);
////                String response=input.readLine();
////                System.out.println("Response "+response);
////                JOptionPane.showMessageDialog(null,response);
//            }
//
////                String response=input.readLine();
////                JOptionPane.showMessageDialog(null,response);
//
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//
////        new Thread(new Receiver()).start();
////        new Thread(new Sender()).start();
//
////        socket.close();
////        System.exit(0);
//    }

//    private static class Receiver implements Runnable {
//        BufferedReader input;
//
//        @Override
//        public void run() {
//            try {
//                input =new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                String response=input.readLine();
//                JOptionPane.showMessageDialog(null,response);
//            } catch (IOException e) {
//                return;
//            }
//        }
//    }
////
//    private static class Sender implements Runnable {
//        PrintWriter output;
//
//        @Override
//        public void run() {
//            Scanner write = new Scanner(System.in);
//            try {
//                output = new PrintWriter(socket.getOutputStream(), true);
//                for (;;) {
//                    if (write.hasNext())
//                        output.println(write.nextLine());
//                }
//            } catch (IOException e) {
//                write.close();
//                return;
//            }
//        }
//    }

}
