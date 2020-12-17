package gamsua;

import java.io.IOException;

public class PacMan {
    private final Server server;

    public void controllerJSON() throws IOException {
        server.receiveJSON();
        System.out.println(server.getReceived().toString());
    }

    public PacMan() throws IOException {
        server = new Server(935);
    }

    public static void main(String[] args) throws IOException{
        PacMan main = new PacMan();
        while (main.server.getClient().isConnected()){
            main.controllerJSON();
        }
    }
}
