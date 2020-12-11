package sockets;

import org.json.*;
import java.net.*;
import java.io.*;

//{FROM, MESSAGE}

public class ServerConsole {
    private ServerSocket serverSocket;
    private Socket client;

    private DataInputStream input;
    private DataOutputStream output;

    private JSONObject send;
    private JSONObject received;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        client = serverSocket.accept();

        input = new DataInputStream(client.getInputStream());
        output = new DataOutputStream(client.getOutputStream());
    }

    public void sendJSON(JSONObject MSG) throws IOException {
        output.writeUTF(JSONObject.valueToString(MSG));
        output.flush();
    }

    public void receiveJSON() throws IOException {
        String str = input.readUTF();
        System.out.println(str);

        received = new JSONObject(str);
        System.out.println(received.getString("Control"));
    }

    public void close(){
        try {
            input.close();
            output.close();
            client.close();
            serverSocket.close();
        } catch (IOException e) {e.printStackTrace();}
    }

    public ServerConsole() throws IOException {
        start(935);
        System.out.println("Server of Console Initialized");
    }

    //Not in the final product
    public static void main(String[] args){
        try {
            ServerConsole server = new ServerConsole();

            server.receiveJSON();
        } catch (IOException e) {e.printStackTrace();}
    }
}
