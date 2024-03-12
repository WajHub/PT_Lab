package org.example;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ClientHandler(Socket socket, ObjectOutputStream out, ObjectInputStream in) {
        this.socket = socket;
        this.out = out;
        this.in = in;
    }

    @Override
    public void run() {
        try {
            out.writeObject("Ready");
            out.writeObject("Ready for messages");
            var number = in.readObject();
            for (int i = 0; i <(int) number; i++) {
                Message msg = (Message) in.readObject();
                System.out.println(msg);
            }
            out.writeObject("Finished");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void close() throws IOException {
        in.close();
        out.close();
    }

}
