package sockets;

import org.json.*;
import java.net.*;
import java.io.*;

//{FROM, MESSAGE}

public class ServerConsole {
    private ServerSocket serverSocket;
    private Socket client;

    public Socket getClient() {return client;}

    private DataInputStream input;
    private DataOutputStream output;

    private JSONObject send;
    private JSONObject received;

    public DataInputStream getInput() {return input;}
    public void setInput(DataInputStream input) {this.input = input;}

    public DataOutputStream getOutput() {return output;}
    public void setOutput(DataOutputStream output) {this.output = output;}

    public JSONObject getSend() {return send;}
    public void setSend(JSONObject send) {this.send = send;}

    public JSONObject getReceived() {return received;}
    public void setReceived(JSONObject received) {this.received = received;}

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
        //System.out.println(str);

        received = new JSONObject(str);
        //System.out.println(received.getString("Control"));
        System.out.println(received.toString());
    }

    public void close(){
        try {
            input.close();
            output.close();
            client.close();
            serverSocket.close();
        } catch (IOException e) {e.printStackTrace();}
    }

    public ServerConsole() throws IOException, InterruptedException {
        start(935);
        System.out.println("Server of Console Initialized");
        //Thread.sleep(Integer.MAX_VALUE);
    }

    //Not in the final product
    /*public static void main(String[] args){
        try {
            ServerConsole server = new ServerConsole();

            server.receiveJSON();
        } catch (IOException e) {e.printStackTrace();}
    }*/
}
