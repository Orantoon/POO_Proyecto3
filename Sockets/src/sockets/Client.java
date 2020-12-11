package sockets;

import java.net.*;
import java.io.*;

class Client {
    public static void main(String[] args)throws Exception{
        Socket socket = new Socket("127.0.0.1",935);
        System.out.println("Connected to Server!");
        System.out.println("Start Typing (first client): ");

        //What you receive (Input)
        DataInputStream in = new DataInputStream(socket.getInputStream());

        //What you write (Your output)
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        //What you write on the terminal
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String clientMSG, serverMSG;

        do {
            clientMSG = br.readLine();

            out.writeUTF(clientMSG);
            out.flush();

            serverMSG = in.readUTF();
            System.out.println("Server says: "+ serverMSG);
        }
        while(!clientMSG.equals("stop"));

        out.close();
        socket.close();
    }
}