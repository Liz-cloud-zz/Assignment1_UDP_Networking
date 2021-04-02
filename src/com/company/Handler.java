package com.company;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
//import java.net.Socket;
//import java.util.ArrayList;
//
//public class Handler implements Runnable{
//    private Socket client;
//    private BufferedReader input_message;
//    private PrintWriter output_message;
//    private ArrayList<Handler> clients;
//
//    public Handler(Socket client, ArrayList<Handler> clients) throws IOException {
//        this.client=client;
//        this.clients=clients;
//        input_message=new BufferedReader(new InputStreamReader(client.getInputStream()));
//        output_message=new PrintWriter(client.getOutputStream(),true);
//
//    }
//    @Override
//    public void run() {
//        try{
//            while (true){
//                String request=input_message.readLine();
//                if(request.contains("name")){
//                    output_message.println(Server.getRandomName());
//                }else if(request.startsWith("say")){
//                    int first_space=request.indexOf(" ");
//                    if(first_space!=-1){outToAll(request.substring(first_space+1));}
//                }
//                else {
//                    output_message.println("Type a name to get a random name");
//                }
//            }
//        } catch (IOException e) {
//            System.err.println(e.getStackTrace());
//        } finally {
//            output_message.close();
//            try {
//                input_message.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private void outToAll(String msg) {
//        for(Handler client:clients){
//            client.output_message.println(msg);
//        }
//    }
//}
