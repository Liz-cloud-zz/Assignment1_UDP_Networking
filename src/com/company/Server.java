package com.company;
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

public class Server {
    private static final int PORT=8080;
    private static String[] names ={"Linda","Seo Joon", "Matt","Aran"};
    private static String[] adjs ={"the gentle ","the un over wrought ", "the urbane"};
    private static ArrayList<Handler>clients=new ArrayList<>();
    private static ExecutorService pool= Executors.newFixedThreadPool(4);

    public static String getRandomName(){
        String name=names[(int) (Math.random() * names.length)];
        String adj=adjs[(int) (Math.random() * adjs.length)];
        return name+" "+adj;
    }
    public static void main(String[] args)throws IOException {
        ServerSocket client_lister =new ServerSocket(PORT);
        while (true) {
            System.out.println("Waiting for connection");
            Socket client = client_lister.accept();
            System.out.println("Server connected!");
            Handler thread=new Handler(client,clients);
            clients.add(thread);
            pool.execute(thread);
        }

//    }{output.println("Hey");
//        System.out.println("Closing connection");
//        client.close();
//        client.close();


    }
}
