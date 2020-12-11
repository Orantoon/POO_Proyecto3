package sockets;

import java.net.*;
import java.io.*;

class Server {
    public static void main(String[] args)throws Exception{
        ServerSocket serverSocket = new ServerSocket(935);
        System.out.println("Server Initialized!\nWaiting for client...");
        Socket client = serverSocket.accept();
        System.out.println("Client connected!");
        System.out.println("Start Typing: ");

        //What you receive (Input)
        DataInputStream in = new DataInputStream(client.getInputStream());

        //What you write (Your output)
        DataOutputStream out = new DataOutputStream(client.getOutputStream());

        //What you write on the terminal
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String clientMSG, serverSMG;

        do {
            clientMSG = in.readUTF();
            System.out.println("client says: "+clientMSG);

            serverSMG = br.readLine();

            out.writeUTF(serverSMG);
            out.flush();
        } while(!clientMSG.equals("stop"));

        in.close();
        client.close();
        serverSocket.close();
    }
}