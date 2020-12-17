package gamsua;

import java.io.IOException;

public class Tetris {
    private Server server;

    public void controllerJSON() throws IOException {
        server.receiveJSON();
        System.out.println(server.getReceived().toString());
    }

    public Tetris() throws IOException {
        server = new Server(935);
    }

    public static void main(String[] args) throws IOException{
        Tetris main = new Tetris();
        while (main.server.getClient().isConnected()){
            main.controllerJSON();
        }
    }
}
