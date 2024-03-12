package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final String startServer = "Ready";
    private static final String ready = "Ready for message";
    private static final String end = "Finished";

    public static final int PORT = 1234;
    private ServerSocket serverSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public Server() throws IOException {
        serverSocket = new ServerSocket(PORT);
        initConnections();
    }
    private void initConnections() throws IOException {
        Socket clientSocket = serverSocket.accept();
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        in = new ObjectInputStream(clientSocket.getInputStream());
    }

    private void close() throws IOException {
        in.close();
        out.close();
        serverSocket.close();;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Server server = new Server();
        System.out.println("Server Started");
        while (true) {
            Message msg = (Message) server.in.readObject();
            System.out.println(msg);
            if(msg.content().equals("END")) break;
        }
        server.close();
    }
}
