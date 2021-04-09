package com.company;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

public class Handler {
    public static final int MIN_PORT_NUMBER = 1100;
    public static final int MAX_PORT_NUMBER = 65535;
    private static AtomicInteger currentMinPort = new AtomicInteger(MIN_PORT_NUMBER);
    public static int newPort;
    public static int serverPort;

    public static synchronized int findAvailLocalPort() {
        int next = findFreeLocalPort(currentMinPort.get(), null);
        currentMinPort.set(next + 1);
        return next;
    }

    public static synchronized int findFreeLocalPort(int fromPort, InetAddress bindAddress) {
        if (fromPort < currentMinPort.get() || fromPort > MAX_PORT_NUMBER) {
            throw new IllegalArgumentException("From port number not in valid range: " + fromPort);
        }

        for (int i = fromPort; i <= MAX_PORT_NUMBER; i++) {
            if (available(i, bindAddress)) {
                return i;
            }
        }

        throw new NoSuchElementException("Could not find an available port above " + fromPort);
    }

    public static boolean available(int port, InetAddress bindAddress) throws IllegalArgumentException {
        if (port < currentMinPort.get() || port > MAX_PORT_NUMBER) {
            throw new IllegalArgumentException("Invalid start currentMinPort: " + port);
        }

        DatagramSocket ds = null;
        try {
            ds = (bindAddress != null) ? new DatagramSocket(port, bindAddress) : new DatagramSocket(port);
            ds.setReuseAddress(true);
            return true;
        } catch (IOException e) {
            // Do nothing
        } finally {
            if (ds != null) {
                ds.close();
            }
        }

        return false;

    }

//    private int getRandomPort() {
//        int port = findAvailLocalPort();
//        currentPort = port;
//        return port;
//    }

    public static int setServerPort() {
        int port = 3000;//findAvailLocalPort();
        serverPort = port;
        return port;
    }

    public static int getServerPort() {
        return serverPort;
    }

    public static int setNewPort() {
        int port = 8080;//findAvailLocalPort();
        newPort = port;
        return port;
    }

    public static int getNewPort() {
        return newPort;
    }
}
