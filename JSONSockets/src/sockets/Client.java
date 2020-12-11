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
        String str = input.readUTF();
        System.out.println(str);

        received = new JSONObject(str);
        System.out.println(received.getString("Consola"));
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

            JSONObject OBJ = new JSONObject();
            OBJ.put("Control","arriba");
            client.sendJSON(OBJ);

        } catch (IOException e) {e.printStackTrace();}
    }
}
