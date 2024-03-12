package org.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private ObjectOutputStream out;
    private Scanner in;

    public Client() throws IOException {
        socket = new Socket("127.0.0.1", Server.PORT);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new Scanner(System.in);
    }
    private void close() throws IOException {
        socket.close();
        in.close();
        out.close();
    }
    public static void main(String[] args) throws IOException {
        Client client = new Client();
        int number = client.in.nextInt();
        client.in.nextLine();
        while(true){
            String content = client.in.nextLine();
            Message msg = new Message(0 , content);
            client.out.writeObject(msg);
            if(msg.content().equals("END")) break;
        }
//        for (int i = 0 ; i<number; i++){
//            String content = client.in.nextLine();
//            Message msg = new Message(i , content);
//            System.out.println(msg);
//        }
        client.close();
    }


}
