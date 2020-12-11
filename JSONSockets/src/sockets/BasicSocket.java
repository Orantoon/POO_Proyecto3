package sockets;

import org.json.*;
import java.io.*;
import java.io.IOException;

public class BasicSocket {
    //Attributes
    private DataInputStream input;
    private DataOutputStream output;
    private JSONObject received;

    //Getters and Setter
    public DataInputStream getInput() {return input;}
    public void setInput(DataInputStream input) {this.input = input;}

    public DataOutputStream getOutput() {return output;}
    public void setOutput(DataOutputStream output) {this.output = output;}

    //Methods
    public void sendJSON(JSONObject MSG) throws IOException {
        output.writeUTF(JSONObject.valueToString(MSG));
        output.flush();
    }

    public void receiveJSON() throws IOException {
        String str = input.readUTF();

        received = new JSONObject(str);
        System.out.println(received.toString());
    }
}
