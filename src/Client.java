import java.net.*;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Client {

    public static int PORT;
    public static volatile boolean running;

    public static void end(){
        running=false;
    }

    private static class Receiver implements Runnable { //runnable class to allow user to constantly be able to receive packets
        @Override
        public void run() {
            running = true;
            try {
                Scanner in=new Scanner(System.in);
                while (running) {
                    //Step8: receive the message forwarded form client 1 through the server
                    DatagramSocket sktReceive = new DatagramSocket(8080);
                    byte[] buf = new byte[1024]; //create empty byte buffer
                    DatagramPacket pktReceive = new DatagramPacket(buf, buf.length); //create temporary empty packet
                    sktReceive.receive(pktReceive); //receive packet from socket and store in packet
                    String msg = new String(pktReceive.getData(), 0, pktReceive.getLength()); //convert packet to message string
                    System.out.println("Server says: " + msg); //output message received

                    //Step9: reply message from client1 through server to verify that the message has been received
                    int server_port= pktReceive.getPort();
                    InetAddress server_ip=pktReceive.getAddress();
                    System.out.println("Type the client name in this form [#client_name#] Client IP Address as numerical digits in this form [000.000.000.000] and message you want to send to client:");
                    String message=in.nextLine();
                    pktReceive = new DatagramPacket(message.getBytes(), message.length(), server_ip, server_port);
                    sktReceive.send(pktReceive);

                    sktReceive.close(); //close socket

                    if (msg.equals("end")) {
                        end();
//                   Thread.sleep((long)15);//end loop (and thread) if server says end
                    }
                }
            } catch (SocketException e) {
                System.out.println("Socket error in Client Receiver: ");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("IO error in Client Receiver: ");
                e.printStackTrace();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
            }
            }
        }

        private static class Sender implements Runnable {
            @Override
            public void run() {
                running = true;
                try {
                    DatagramSocket sktSend = new DatagramSocket();
                    while (running) {
                        Scanner in = new Scanner(System.in);
                        System.out.println("Establish connection to Server by typing a your client name in this form [#client_name#] and message:");
                        String message = in.nextLine();
                        try {
                            //Step1: send message to server to establish connection
                            DatagramPacket pktSend = new DatagramPacket(message.getBytes(), message.length(), InetAddress.getLocalHost(), 3000);
                            sktSend.send(pktSend);


                            //Step4: verification message that the server received your message
                            byte[] bytes=new byte[1024];
                            DatagramPacket reply=new DatagramPacket(bytes,bytes.length);
                            sktSend.receive(reply);
                            String feedback=new String(bytes,0,reply.getLength());
                            System.out.println(feedback);

                            //Step5: message to send to Server with the 2nd client name in this form :"#client_name#" and message to 2nd client
                            //System.out.println("Enter client name in this form [#client_name#] and message to send to the 2nd client");
                            String message_clientname=in.nextLine();
                            pktSend = new DatagramPacket(message_clientname.getBytes(), message_clientname.length(),InetAddress.getLocalHost(), 3000);
                            sktSend.send(pktSend);
                           // System.out.println(feedback);

                            //Step12: verification that server delivered your message to the other client and their reply
                            byte[] b=new byte[1024];
                            DatagramPacket delivered=new DatagramPacket(bytes,bytes.length);
                            sktSend.receive(delivered);
                            String received=new String(b,0,delivered.getLength());
                            System.out.println(received);

                        } catch (UnknownHostException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (message.equals("end")) {
                            end();
//                        Thread.sleep((long)15);
                            continue;
                        }
                    }
                } catch (SocketException e) { //|InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }


    public static void main(String[] args){
        new Thread(new Sender()).start();
        //this receiver is for the second client
       new Thread(new Receiver()).start();
    }}
//        public static void main(String[] args) {
////        running = true;
//
//            new Thread(new Sender("Client",Handler.getServerPort())).start();
//            new Thread(new Receiver("Client",Handler.getNewPort())).start();
//        }
//    }

