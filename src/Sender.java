import java.io.IOException;
import java.net.*;
import java.util.Scanner;


//public class Sender implements Runnable {
//    private String name;
//    private int port;
//    private volatile boolean running;
//
//    public Sender(String myName, int portNum/*, boolean shouldRun*/) {
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
//            DatagramSocket sktSend=new DatagramSocket();
//            while (running){
//                Scanner in=new Scanner(System.in);
//                String message=in.nextLine();
//                try {
//                    DatagramPacket pktSend=new DatagramPacket(message.getBytes(), message.length(), InetAddress.getLocalHost(),port);
//                    sktSend.send(pktSend);
//                } catch (UnknownHostException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                if(message.equals("end")){
//                    setRunning(false);
//                    continue;
//                }
//            }
//        } catch (SocketException e) {
//            e.printStackTrace();
//        }
//    }
//}
