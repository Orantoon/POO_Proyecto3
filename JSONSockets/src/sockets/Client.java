package sockets;

import java.net.*;
import java.io.*;

//{FROM, MESSAGE}

public class Client extends BasicSocket{
    private Socket socket;
    public Socket getClient() {return socket;}

    public void start(int port) throws IOException {
        socket = new Socket("localhost",port);
        this.setInput(new DataInputStream(socket.getInputStream()));
        this.setOutput(new DataOutputStream(socket.getOutputStream()));
    }

    public void close(){
        try {
            this.getInput().close();
            this.getOutput().close();
            socket.close();
        } catch (IOException e) {e.printStackTrace();}
    }

    public Client() throws IOException {
        super();
        start(935);
        System.out.println("Client Connected!");
    }
}
