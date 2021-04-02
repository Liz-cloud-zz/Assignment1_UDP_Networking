package com.company;

import java.net.SocketException;
/// class to test if the network is working
public class UDPController {
    private static Client client;

    public void setClient() throws SocketException {
        new Server().start();
        client=new Client();
    }
    public static void main(String[] args){
        String message=client.sendMessage("hey");
        //assertEquals("hey",message);
        message=client.sendMessage("server is busy");
        //assertFalse(message.equals("hey"));
    }
    //termination method of connection
    public void end(){
        client.sendMessage("end");
        client.close;

    }
}
