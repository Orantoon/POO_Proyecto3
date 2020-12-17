package gamsua;

import org.json.*;
import java.io.*;
import java.net.Socket;

public class Client {
    //Attributes
    private Socket socket;
    private DataOutputStream output;

    public Socket getSocket() {return socket;}

    public void sendJSON(JSONObject MSG) throws IOException{
        output.writeUTF(MSG.toString());
        output.flush();
    }

    public void start(int port) throws IOException {
        socket = new Socket("localhost",port);
        output = new DataOutputStream(socket.getOutputStream());
    }

    public void close(){
        try {
            output.close();
            socket.close();
        } catch (IOException e) {e.printStackTrace();}
    }

    public Client(int port) throws IOException{
        start(port);
    }
}
