import java.net.*;
import java.util.Scanner;

// class that stores the ip address and port number for the client the messages are being forwarded to:
public class Connector {
    InetAddress address;
    int port_number;

    public Connector(int port ,String address){
        try {
            this.address=InetAddress.getByName(address);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        this.port_number=port;
    }

    public void setAddress(String address){
        try {
            this.address=InetAddress.getByName(address);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
    public InetAddress getAddress(){
        return this.address;
    }

    public void setPort_number(int port){
        this.port_number=port;
    }
    public int getPort_number(){
        return this.port_number;
    }
}
