package org.example;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Scanner scanner;

    public Client() throws IOException {
        socket = new Socket("127.0.0.1", Server.PORT);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        scanner = new Scanner(System.in);
    }
    private void close() throws IOException {
        socket.close();
        in.close();
        out.close();
    }
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Client client = new Client();
        System.out.println("Server: "+client.in.readObject());
        System.out.println("Give a number of messages");
        int number = client.scanner.nextInt();
        client.scanner.nextLine();
        client.out.writeObject(number);
        System.out.println("Server: "+client.in.readObject());
        for (int i = 0 ; i<number; i++){
            String content = client.scanner.nextLine();
            Message msg = new Message(i , content);
            client.out.writeObject(msg);
        }
        client.close();
    }


}
