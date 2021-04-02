package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerConnection implements Runnable{
    private Socket server;
    private BufferedReader input_message;


    public ServerConnection(Socket server) throws IOException {
        this.server=server;
        input_message=new BufferedReader(new InputStreamReader(server.getInputStream()));

    }

    @Override
    public void run() {
            try {
                while (true) {
                    String response = input_message.readLine();
                    if(response==null){break;}
                    System.out.println("Server says: " + response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    input_message.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

    }

}

