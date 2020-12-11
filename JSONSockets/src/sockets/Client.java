package sockets;

import org.json.JSONObject;
import java.net.*;
import java.io.*;

//{FROM, MESSAGE}

public class Client {
    private Socket socket;

    private DataInputStream input;
    private DataOutputStream output;

    private JSONObject send;
    private JSONObject received;

    public void start(int port) throws IOException {
        socket = new Socket("localhost",935);
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());
    }

    public void sendJSON(JSONObject MSG) throws IOException {
        output.writeUTF(JSONObject.valueToString(MSG));
        output.flush();
    }

    public void receiveJSON() throws IOException {
        received = (JSONObject) JSONObject.stringToValue(input.readUTF());
    }

    public void close(){
        try {
            input.close();
            output.close();
            socket.close();
        } catch (IOException e) {e.printStackTrace();}
    }

    public Client() throws IOException {
        start(935);
        System.out.println("Client Connected!");
    }

    public static void main(String[] args){
        try {
            Client client = new Client();

            //while (client.socket.isConnected()){
            //    System.out.println("Yay");
            //}
        } catch (IOException e) {e.printStackTrace();}
    }
}
