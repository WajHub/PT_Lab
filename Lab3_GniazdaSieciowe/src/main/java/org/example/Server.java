package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int PORT = 1234;
    private ServerSocket serverSocket;

    public Server() throws IOException {
        serverSocket = new ServerSocket(PORT);
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        System.out.println("Server Started.");
        while (true) {
            Socket clientSocket = server.serverSocket.accept();
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

            ClientHandler clientHandler = new ClientHandler(clientSocket, out, in);
            Thread thread = new Thread(clientHandler);
            thread.start();
        }

    }
}
