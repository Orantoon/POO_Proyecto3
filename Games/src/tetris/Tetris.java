package tetris;

import gamsua.Client;
import gamsua.Server;
import org.json.*;
import java.io.IOException;
import java.util.*;

public class Tetris {
    //Attributes
    private Server server;
    private Client client;
    private int key = 0;

    private int[][] matrix;
    private boolean running = true;
    private Random random = new Random();

    private Vector<Blocks> blocks = new Vector<>();
    private String[] blockNames = new String[]{"i","o","j","l","t","s","z"};

    //Game Loop
    public Tetris() throws IOException {
        server = new Server(935);
        client = new Client(420);

        initMatrix();
        emptyBoard();
        addPiece();
        paintPiece();

        while (server.getClient().isConnected() && running){
            controllerJSON();

            switch (key){
                case 'w', ' ' -> movePiece("rotate");
                case 'a' -> movePiece("left");
                case 's' -> movePiece("down"); //STRAIGHT DOWN
                case 'd' -> movePiece("right");
            }
        }

        System.out.println("You lost!");
        server.close();
        client.close();
        //Close all the sockets and the apps
    }

    public void initMatrix(){
        matrix = new int[][]{
                {0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0}
        };
    }

    public void addPiece(){
        String name = blockNames[random.nextInt(7)];
        Blocks piece = new Blocks(name);
        blocks.add(piece);
        for (int i = 0; i < 4; i++){
            int x = piece.getCoordinates()[i][0];
            int y = piece.getCoordinates()[i][1];

            if (matrix[x][y] != 0){
                running = false;
                return;
            }

            matrix[x][y] = piece.getNum();
        }
    }

    // Starting Board

    public void emptyBoard() throws IOException {
        int[][] upDown = new int[28*4][2];
        int[][] leftRight = new int[196][2];

        for (int i = 0; i < 28; i++){
            upDown[i] = new int[]{0,i};
            upDown[i+28] = new int[]{1,i};
            upDown[i+28*2] = new int[]{48,i};
            upDown[i+28*3] = new int[]{49,i};

            leftRight[i] = new int[]{i,0};
            leftRight[i+21] = new int[]{i+21,0};
            leftRight[i+49] = new int[]{i,1};
            leftRight[i+49+21] = new int[]{i+21,1};
            leftRight[i+49*2] = new int[]{i,26};
            leftRight[i+49*2+21] = new int[]{i+21,26};
            leftRight[i+49*3] = new int[]{i,27};
            leftRight[i+49*3+21] = new int[]{i+21,27};
        }

        send("Gray",upDown);
        send("Gray",leftRight);
    }

    // SCREEN - Coordinates, Clean, Paint

    public int[][] newCoordinates(int[][] normalC){ //Transforms the coordinates of the matrix into the ones in the screen matrix
        int[][] coords = new int[16][2];

        for (int i = 0; i < 4; i++){
            coords[i*4][0]   = normalC[i][0]*2 + 2;
            coords[i*4][1]   = normalC[i][1]*2 + 2;
            coords[i*4+1][0] = normalC[i][0]*2 + 2;
            coords[i*4+1][1] = normalC[i][1]*2 + 3;
            coords[i*4+2][0] = normalC[i][0]*2 + 3;
            coords[i*4+2][1] = normalC[i][1]*2 + 2;
            coords[i*4+3][0] = normalC[i][0]*2 + 3;
            coords[i*4+3][1] = normalC[i][1]*2 + 3;
        }

        return coords;
    }

    public int[][] newCoordinatesMORE(Vector<int[]> vector){
        Vector<int[]> coords = new Vector<>();

        for (int i = 0; i < vector.size(); i++){

            coords.add(new int[]{vector.get(i)[0]*2+2, vector.get(i)[1]*2+2});
            coords.add(new int[]{vector.get(i)[0]*2+2, vector.get(i)[1]*2+3});
            coords.add(new int[]{vector.get(i)[0]*2+3, vector.get(i)[1]*2+2});
            coords.add(new int[]{vector.get(i)[0]*2+3, vector.get(i)[1]*2+3});

        }

        return coords.toArray(new int[coords.size()][2]);
    }

    public void cleanPiece() throws IOException{ //Called before, so always cleans the old coordinates.
        int[][] coords = newCoordinates(blocks.lastElement().getOldCoord());
        send("Black",coords);
    }

    public void paintPiece() throws IOException { //Called after, new coordinates.
        int num = blocks.lastElement().getNum();
        String color = "None";

        switch (num){
            case 1 -> color = "Light Blue";
            case 2 -> color = "Yellow";
            case 3 -> color = "Blue";
            case 4 -> color = "Orange";
            case 5 -> color = "Purple";
            case 6 -> color = "Red";
            case 7 -> color = "Green";
        }

        int[][] blockC = blocks.lastElement().getCoordinates();
        send(color,newCoordinates(blockC));
    }


    //Move Piece

    public void movePiece(String direction) throws IOException {
        switch (direction){
            case "left"  -> blocks.lastElement().moveLeft();
            case "right" -> blocks.lastElement().moveRight();
            case "down"  -> blocks.lastElement().moveDown();
            case "rotate" -> blocks.lastElement().rotate(matrix);
        }

        for (int[] old: blocks.lastElement().getOldCoord())
            matrix[old[0]][old[1]] = 0;
        for (int[] newC: blocks.lastElement().getCoordinates())
            matrix[newC[0]][newC[1]] = blocks.lastElement().getNum();

        //deleteCompletedLines();

        if (blocks.lastElement().pieceUnder(matrix)){
            addPiece();
            blocks.remove(blocks.elementAt(blocks.size()-2)); //delete piece
        }

        cleanPiece();
        paintPiece();
    }


    //Completed Lines

    public Vector<Integer> lineCompleted(){
        Vector<Integer> res = new Vector<>();

        for (int row = 23; row >= 0; row--){

            if (!Arrays.stream(matrix[row]).anyMatch(i -> i == 0)){
                res.add(row);
                System.out.println("Line completed: " + row);
            }

        } return res;
    }

    public void deleteCompletedLines(){ //Error of blank spaces
        Vector<Integer> lines = lineCompleted();

        if (lines.isEmpty())
            return;

        for (int line: lines){
            for (int i = line; i>0;--i){
                matrix[i] = matrix[i-1].clone();
            } matrix[0] = new int[]{0,0,0,0,0,0,0,0,0,0,0,0};
        }

        try {sendMatrix();}
        catch (IOException ioException) {ioException.printStackTrace();}
    }

    public Vector<int[]> findAllCoordsOfNum(int num){
        Vector<int[]> res = new Vector<>();

        for (int i = 0; i < 24; i++){
            for (int j = 0; j < 12; j++){

                if (matrix[i][j] == num)
                    res.add(new int[]{i,j});

            }
        }

        return res;
    }

    public void sendMatrix() throws IOException {
        String[] colors = {"Black","Light Blue", "Yellow", "Blue", "Orange", "Purple", "Red", "Green"};

        for (int i = 0; i < 8; i++){
            Vector<int[]> coords = findAllCoordsOfNum(i);

            if (coords.isEmpty())
                continue;

            send(colors[i],newCoordinatesMORE(coords));
        }
    }

    // / / / / / Socket Communication / / / / / //

    public void controllerJSON() throws IOException {
        server.receiveJSON();
        System.out.println(server.getReceived().toString());
        key = server.getReceived().getInt("Key");
        System.out.println(key);
    }

    public void send(String color, int[][] coord) throws IOException {
        JSONObject Obj = new JSONObject();
        JSONArray array = new JSONArray();

        for (int[] c: coord)
            array.put(c);

        Obj.put(color,array);
        client.sendJSON(Obj);
    }

    // Main
    public static void main(String[] args) throws IOException{
        new Tetris();
    }
}
