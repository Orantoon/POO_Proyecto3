package gamsua;

import org.json.*;
import java.io.*;
import java.net.*;

public class Server {
    //Attributes
    private ServerSocket serverS;
    private Socket client;
    private DataInputStream input;
    private JSONObject received;

    public Socket getClient() {return client;}

    public JSONObject getReceived() {return received;}

    public void receiveJSON() throws IOException{
        received = new JSONObject(input.readUTF());
    }

    public void start(int port) throws IOException {
        serverS = new ServerSocket(port);
        client = serverS.accept();

        input = new DataInputStream(client.getInputStream());
    }

    public void close(){
        try {
            input.close();
            client.close();
            serverS.close();
        } catch (IOException e) {e.printStackTrace();}
    }

    public Server(int port) throws IOException {
        start(port);
    }
}
