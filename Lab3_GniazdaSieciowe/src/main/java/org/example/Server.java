package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final String STARTSERVER = "Ready";
    private static final String READY = "Ready for message";
    private static final String END = "Finished";

    public static final int PORT = 1234;
    private ServerSocket serverSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public Server() throws IOException {
        serverSocket = new ServerSocket(PORT);
    }
    private void initConnections() throws IOException {
        Socket clientSocket = serverSocket.accept();
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        in = new ObjectInputStream(clientSocket.getInputStream());
    }

    private void close() throws IOException {
        in.close();
        out.close();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Server server = new Server();
        System.out.println("Server Started.");
        try {
            while (true) {
                server.initConnections();
                server.out.writeObject("Ready");
                server.out.writeObject("Ready for messages");
                var number = server.in.readObject();
                for (int i = 0; i <(int) number; i++) {
                    Message msg = (Message) server.in.readObject();
                    System.out.println(msg);
                }
                server.close();
            }
        } finally {
            server.close();
        }
    }
}
