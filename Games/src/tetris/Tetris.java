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
        cleanScreen();
        gameOver();

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

    public void cleanScreen() throws IOException {
        send("Clean",new int[][]{{0,0}});
    }

    public void gameOver() throws IOException{
        int[][] gameText = new int[][]{
                {13,2},{13,3},{14,2},{14,3},{15,2},{15,3},{16,2},{16,3},{17,2},{17,3},{18,2},{18,3},{19,2},{19,3},{20,2},{20,3},
                {11,4},{11,5},{11,6},{11,7},{11,8},{11,9},{11,10},{12,4},{12,5},{12,6},{12,7},{12,8},{12,9},{12,10},
                {21,4},{21,5},{21,6},{21,7},{21,8},{21,9},{21,10},{21,11},{21,12},{22,4},{22,5},{22,6},{22,7},{22,8},{22,9},{22,10},{22,11},{22,12},
                {17,9},{17,10},{17,11},{17,12},{18,9},{18,10},{18,11},{18,12},{19,11},{19,12},{20,11},{20,12},

                {13,15},{13,16},{14,15},{14,16},{15,15},{15,16},{16,15},{16,16},{17,15},{17,16},{18,15},{18,16},{19,15},{19,16},{20,15},{20,16},{21,15},{21,16},{22,15},{22,16},
                {11,17},{11,18},{11,19},{11,20},{11,21},{11,22},{12,17},{12,18},{12,19},{12,20},{12,21},{12,22},
                {13,23},{13,24},{14,23},{14,24},{15,23},{15,24},{16,23},{16,24},{17,23},{17,24},{18,23},{18,24},{19,23},{19,24},{20,23},{20,24},{21,23},{21,24},{22,23},{22,24},
                {17,17},{17,18},{17,19},{17,20},{17,21},{17,22},{18,17},{18,18},{18,19},{18,20},{18,21},{18,22},

                {11,27},{11,28},{11,29},{11,30},{11,33},{11,34},{12,27},{12,28},{12,29},{12,30},{12,33},{12,34},
                {13,27},{13,28},{14,27},{14,28},{15,27},{15,28},{16,27},{16,28},{17,27},{17,28},{18,27},{18,28},{19,27},{19,28},{20,27},{20,28},{21,27},{21,28},{22,27},{22,28},
                {13,31},{13,32},{14,31},{14,32},{15,31},{15,32},{16,31},{16,32},{17,31},{17,32},{18,31},{18,32},{19,31},{19,32},{20,31},{20,32},{21,31},{21,32},{22,31},{22,32},
                {13,35},{13,36},{14,35},{14,36},{15,35},{15,36},{16,35},{16,36},{17,35},{17,36},{18,35},{18,36},{19,35},{19,36},{20,35},{20,36},{21,35},{21,36},{22,35},{22,36},

                {11,41},{11,42},{11,43},{11,44},{11,45},{11,46},{12,41},{12,42},{12,43},{12,44},{12,45},{12,46},
                {13,39},{13,40},{14,39},{14,40},{15,39},{15,40},{16,39},{16,40},{17,39},{17,40},{18,39},{18,40},{19,39},{19,40},{20,39},{20,40},{21,39},{21,40},{22,39},{22,40},
                {16,41},{16,42},{16,43},{16,44},{17,41},{17,42},{17,43},{17,44},
                {21,41},{21,42},{21,43},{21,44},{21,45},{21,46},{22,41},{22,42},{22,43},{22,44},{22,45},{22,46}
        };

        int[][] overText = new int[][]{
                {29,2},{29,3},{30,2},{30,3},{31,2},{31,3},{32,2},{32,3},{33,2},{33,3},{34,2},{34,3},{35,2},{35,3},{36,2},{36,3},
                {29,10},{29,11},{30,10},{30,11},{31,10},{31,11},{32,10},{32,11},{33,10},{33,11},{34,10},{34,11},{35,10},{35,11},{36,10},{36,11},
                {27,4},{27,5},{27,6},{27,7},{27,8},{27,9},{28,4},{28,5},{28,6},{28,7},{28,8},{28,9},
                {37,4},{37,5},{37,6},{37,7},{37,8},{37,9},{38,4},{38,5},{38,6},{38,7},{38,8},{38,9},

                {27,14},{27,15},{28,14},{28,15},{29,14},{29,15},{30,14},{30,15},{31,14},{31,15},{32,14},{32,15},{33,14},{33,15},{34,14},{34,15},
                {27,22},{27,23},{28,22},{28,23},{29,22},{29,23},{30,22},{30,23},{31,22},{31,23},{32,22},{32,23},{33,22},{33,23},{34,22},{34,23},
                {35,16},{35,17},{36,16},{36,17},{35,20},{35,21},{36,20},{36,21},{37,18},{37,19},{38,18},{38,19},

                {27,28},{27,29},{27,30},{27,31},{27,32},{27,33},{28,28},{28,29},{28,30},{28,31},{28,32},{28,33},
                {29,26},{29,27},{30,26},{30,27},{31,26},{31,27},{32,26},{32,27},{33,26},{33,27},{34,26},{34,27},{35,26},{35,27},{36,26},{36,27},{37,26},{37,27},{38,26},{38,27},
                {32,28},{32,29},{32,30},{32,31},{33,28},{33,29},{33,30},{33,31},
                {37,28},{37,29},{37,30},{37,31},{37,32},{37,33},{38,28},{38,29},{38,30},{38,31},{38,32},{38,33},

                {27,36},{27,37},{27,38},{27,39},{27,40},{27,41},{28,36},{28,37},{28,38},{28,39},{28,40},{28,41},
                {33,36},{33,37},{33,38},{33,39},{33,40},{33,41},{34,36},{34,37},{34,38},{34,39},{34,40},{34,41},
                {29,36},{29,37},{30,36},{30,37},{31,36},{31,37},{32,36},{32,37},{35,36},{35,37},{36,36},{36,37},{37,36},{37,37},{38,36},{38,37},
                {29,42},{29,43},{30,42},{30,43},{31,42},{31,43},{32,42},{32,43},
                {35,42},{35,43},{36,42},{36,43},{37,42},{37,43},{38,42},{38,43}
        };

        send("Red",gameText);
        send("Gray",overText);

    }

    public void victoryScreen() throws IOException {
        int[][] youWinText = new int[][]{
                {14,13},{14,14},{15,13},{15,14},{16,13},{16,14},{17,13},{17,14},
                {14,17},{14,18},{15,17},{15,18},{16,17},{16,18},{17,17},{17,18},
                {18,15},{18,16},{19,15},{19,16},{20,15},{20,16},{21,15},{21,16},

                {14,23},{14,24},{15,23},{15,24},{20,23},{20,24},{21,23},{21,24},
                {16,21},{16,22},{17,21},{17,22},{18,21},{18,22},{19,21},{19,22},
                {16,25},{16,26},{17,25},{17,26},{18,25},{18,26},{19,25},{19,26},

                {14,29},{14,30},{15,29},{15,30},{16,29},{16,30},{17,29},{17,30},{18,29},{18,30},{19,29},{19,30},
                {14,33},{14,34},{15,33},{15,34},{16,33},{16,34},{17,33},{17,34},{18,33},{18,34},{19,33},{19,34},
                {20,31},{20,32},{21,31},{21,32},


                {26,9},{26,10},{27,9},{27,10},{28,9},{28,10},{29,9},{29,10},{30,9},{30,10},{31,9},{31,10},
                {26,17},{26,18},{27,17},{27,18},{28,17},{28,18},{29,17},{29,18},{30,17},{30,18},{31,17},{31,18},
                {32,11},{32,12},{33,11},{33,12},{32,15},{32,16},{33,15},{33,16},{30,13},{30,14},{31,13},{31,14},

                {26,21},{26,22},{26,23},{26,24},{26,25},{26,26},{27,21},{27,22},{27,23},{27,24},{27,25},{27,26},
                {32,21},{32,22},{32,23},{32,24},{32,25},{32,26},{33,21},{33,22},{33,23},{33,24},{33,25},{33,26},
                {28,23},{28,24},{29,23},{29,24},{30,23},{30,24},{31,23},{31,24},

                {26,29},{26,30},{26,31},{26,32},{26,33},{26,34},{27,29},{27,30},{27,31},{27,32},{27,33},{27,34},
                {28,29},{28,30},{29,29},{29,30},{30,29},{30,30},{31,29},{31,30},{32,29},{32,30},{33,29},{33,30},
                {28,35},{28,36},{29,35},{29,36},{30,35},{30,36},{31,35},{31,36},{32,35},{32,36},{33,35},{33,36},

                {26,39},{26,40},{27,39},{27,40},{28,39},{28,40},{29,39},{29,40},{30,39},{30,40},{32,39},{32,40},{33,39},{33,40}
        };

        send("Blue",youWinText);
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
