package tetris;

import gamsua.Client;
import gamsua.Server;
import org.json.*;
import java.io.IOException;

public class Tetris {
    private Server server;
    private Client client;
    private int key = 0;

    public Tetris() throws IOException {
        server = new Server(935);
        client = new Client(420);

        while (server.getClient().isConnected()){
            controllerJSON();

            //To test the screen
            switch (key){
                case 'w'->send("Yellow",new int[]{0,0});
                case 'a'->send("Red",new int[]{1,1});
                case 's'->send("Green",new int[]{2,2});
                case 'd'->send("Purple",new int[]{3,3});
                case ' '->send("Blue",new int[]{4,4});
            }
        }
    }

    public void controllerJSON() throws IOException {
        server.receiveJSON();
        System.out.println(server.getReceived().toString());
        key = server.getReceived().getInt("Key");
        System.out.println(key);
    }

    public void send(String color, int[] coord) throws IOException {
        JSONObject Obj = new JSONObject();
        JSONArray array = new JSONArray();
        array.put(coord);
        Obj.put(color,array);
        client.sendJSON(Obj);
    }

    public static void main(String[] args) throws IOException{
        new Tetris();
    }
}
