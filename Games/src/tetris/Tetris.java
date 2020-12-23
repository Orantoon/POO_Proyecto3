package tetris;

import gamsua.Client;
import gamsua.Server;
import org.json.*;
import java.io.IOException;
import java.util.*;

public class Tetris implements Runnable{ ////////////////////////////////////////
    //Attributes
    private Server server;
    private Client client;
    private int key = 0;

    private int[][] matrix;
    private boolean running = true;
    private Random random = new Random();
    private Thread thread = new Thread(this); ////////////////////////////////////////

    private Vector<Blocks> blocks = new Vector<>();
    private String[] blockNames = new String[]{"i","o","j","l","t","s","z"};

    //Game Loop
    public Tetris() throws IOException, InterruptedException {
        server = new Server(935);
        client = new Client(420);

        initMatrix();
        emptyBoard();
        addPieceToBlocks();
        nextPiece();
        paintPiece();
        thread.start(); ////////////////////////////////////////

        while (server.getClient().isConnected() && running){
            Thread.sleep(500);
            movePiece("down");
        }

        System.out.println("You lost!");
        //cleanScreen();
        //gameOver();

        while (server.getClient().isConnected()){
            Thread.sleep(Integer.MAX_VALUE);
        }

        //Close all the sockets and the apps
    }

    @Override
    public void run() { ////////////////////////////////////////
        try {
            while (server.getClient().isConnected() && running){
                controllerJSON();

                switch (key){
                    case 'w', ' ' -> movePiece("rotate");
                    case 'a' -> movePiece("left");
                    case 's' -> movePiece("down"); //STRAIGHT DOWN
                    case 'd' -> movePiece("right");
                }
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

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

    //Adding Pieces
    public void addPieceToBlocks(){
        String name = blockNames[random.nextInt(7)];
        Blocks piece = new Blocks(name);
        blocks.add(piece);
    }

    public void addPieceToMatrix() throws IOException {
        Blocks piece = blocks.elementAt(blocks.size()-2);
        for (int i = 0; i < 4; i++){
            int x = piece.getCoordinates()[i][0];
            int y = piece.getCoordinates()[i][1];

            if (matrix[x][y] != 0 && matrix[x][y] != piece.getNum()){
                running = false;
                return;
            }

            matrix[x][y] = piece.getNum();
        }
    }

    public void nextPiece() throws IOException {
        addPieceToBlocks();
        visNext();
        addPieceToMatrix();
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

        int[][] nextText = new int[][]{{9,31},{10,31},{11,31},{12,31},{9,32},{9,33},{10,34},{11,34},{12,34},{9,37},
                {9,38},{10,36},{11,36},{12,36},{11,37},{12,37},{12,38},{9,40},{9,42},{10,41},{11,41},{12,40},{12,42},{9,44},{9,45},{9,46},
                {10,45},{11,45},{12,45}};

        int[][] visualB = new int[128][2];

        for (int i = 0; i < 16; i++){
            visualB[i]    = new int[]{14,i+31};
            visualB[i+16] = new int[]{15,i+31};
            visualB[i+32] = new int[]{28,i+31};
            visualB[i+48] = new int[]{29,i+31};
            visualB[i+64] = new int[]{14+i,31};
            visualB[i+80] = new int[]{14+i,32};
            visualB[i+96] = new int[]{14+i,45};
            visualB[i+112] = new int[]{14+i,46};
        }

        send("Gray",nextText);
        send("Gray",visualB);
    }

    public void clearVis() throws IOException {
        int[][] square = new int[12*4][2];
        for (int i = 0; i < 12; i++){
            square[i] = new int[]{21,i+33};
            square[i+12] = new int[]{22,i+33};
            square[i+12*2] = new int[]{23,i+33};
            square[i+12*3] = new int[]{24,i+33};
        }

        send("Black",square);
    }

    public void visNext() throws IOException {
        clearVis();
        int[][] coords = newCoordinates(blocks.lastElement().getCoordinates());
        String color = "None";

        for (int[] coord: coords){
            coord[0] += 19;
            coord[1] += 24;
        }

        switch (blocks.lastElement().getNum()){
            case 1 -> color = "Light Blue";
            case 2 -> color = "Yellow";
            case 3 -> color = "Blue";
            case 4 -> color = "Orange";
            case 5 -> color = "Purple";
            case 6 -> color = "Red";
            case 7 -> color = "Green";
        }

        send(color,coords);
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
        int[][] coords = newCoordinates(blocks.elementAt(blocks.size()-2).getOldCoord());
        send("Black",coords);
    }

    public void paintPiece() throws IOException { //Called after, new coordinates.
        Blocks piece = blocks.elementAt(blocks.size()-2);

        int num = piece.getNum();
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

        int[][] blockC = piece.getCoordinates();
        send(color,newCoordinates(blockC));
    }


    //Move Piece

    public void movePiece(String direction) throws IOException {
        Blocks piece = blocks.elementAt(blocks.size()-2);

        switch (direction){
            case "left"  -> piece.moveLeft(matrix);
            case "right" -> piece.moveRight(matrix);
            case "down"  -> piece.moveDown();
            case "rotate" -> piece.rotate(matrix);
        }

        for (int[] old: piece.getOldCoord())
            matrix[old[0]][old[1]] = 0;
        for (int[] newC: piece.getCoordinates())
            matrix[newC[0]][newC[1]] = piece.getNum();

        if (piece.pieceUnder(matrix)){
            deleteCompletedLines();
            nextPiece();
            blocks.remove(piece);
        }

        cleanPiece();
        paintPiece();
    }


    //Completed Lines
    public Vector<Integer> linesCompleted(){
        Vector<Integer> res = new Vector<>();

        for (int row = 0; row < 24 ; row++){

            if (Arrays.stream(matrix[row]).noneMatch(i -> i ==0))
                res.add(row);

        } return res;
    }

    public void deleteCompletedLines(){
        Vector<Integer> lines = linesCompleted();

        if (lines.isEmpty())
            return;

        for (int line: lines){
            for (int i = line; i > 0; i--){
                if (matrix[i].equals(new int[]{0,0,0,0,0,0,0,0,0,0,0,0}))
                    break;

                matrix[i] = matrix[i-1].clone();
            }
        } matrix[0] = new int[]{0,0,0,0,0,0,0,0,0,0,0,0};

        try {
            sendMatrix();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public Vector<int[]> findAllCoordsOfNum(int num){
        Vector<int[]> res = new Vector<>();

        for (int i = 0; i < 24; i++){
            for (int j = 0; j < 12; j++){

                if (matrix[i][j] == num)
                    res.add(new int[]{i-1,j});

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
    public static void main(String[] args) throws IOException, InterruptedException {
        new Tetris();
    }
}
