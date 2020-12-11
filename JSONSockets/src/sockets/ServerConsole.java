package sockets;

import java.net.*;
import java.io.*;

//{FROM, MESSAGE}

public class ServerConsole extends BasicSocket{
    private ServerSocket serverSocket;
    private Socket client;

    public Socket getClient() {return client;}

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        client = serverSocket.accept();

        this.setInput(new DataInputStream(client.getInputStream()));
        this.setOutput(new DataOutputStream(client.getOutputStream()));
    }

    public void close(){
        try {
            this.getInput().close();
            this.getOutput().close();
            client.close();
            serverSocket.close();
        } catch (IOException e) {e.printStackTrace();}
    }

    public ServerConsole() throws IOException, InterruptedException {
        super();
        start(935);
        System.out.println("Server of Console Initialized");
    }
}
