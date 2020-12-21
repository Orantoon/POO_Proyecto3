package pacman;

import org.json.JSONArray;
import org.json.JSONObject;

import gamsua.Client;
import gamsua.Server;
import java.io.IOException;
import java.util.Arrays;

public class PacMan {
    private final Server server;
    private final Client client;
    private int key;

    // Game
    private final int[][] map;

    private int[] currentPos;
    private int[] lastPos;
    private int code;   // code: 0 = NONE, 1 = UP, 2 = RIGHT, 3 = DOWN, 4 = LEFT
    private boolean open;


    // Constructor
    public PacMan() throws IOException {
        server = new Server(935);
        client = new Client(420);
        key = 0;

        // Game
        map = new int[][] {{0,0},{1,0},{2,0},{3,0},{4,0},{5,0},{6,0},{7,0},{8,0},{9,0},{10,0},{11,0},{12,0},{13,0},{14,0},{15,0},{16,0},{17,0},{18,0},{19,0},{20,0},{21,0},{22,0},{23,0},{24,0},{25,0},{26,0},{27,0},{28,0},{29,0},{30,0},{31,0},{32,0},{33,0},{34,0},{35,0},{36,0},{37,0},{38,0},{39,0},{40,0},{41,0},{42,0},{43,0},{44,0},{45,0},{46,0},{47,0},{48,0},{49,0},
                {0,1},{24,1},{25,1},{49,1},
                {0,2},{24,2},{25,2},{49,2},
                {0,3},{24,3},{25,3},{49,3},
                {0,4},{24,4},{25,4},{49,4},
                {0,5},{5,5},{6,5},{7,5},{8,5},{13,5},{14,5},{15,5},{16,5},{17,5},{18,5},{19,5},{24,5},{25,5},{30,5},{31,5},{32,5},{33,5},{34,5},{35,5},{36,5},{41,5},{42,5},{43,5},{44,5},{49,5},
                {0,6},{5,6},{6,6},{7,6},{8,6},{13,6},{14,6},{15,6},{16,6},{17,6},{18,6},{19,6},{24,6},{25,6},{30,6},{31,6},{32,6},{33,6},{34,6},{35,6},{36,6},{41,6},{42,6},{43,6},{44,6},{49,6},
                {0,7},{5,7},{6,7},{7,7},{8,7},{13,7},{14,7},{15,7},{16,7},{17,7},{18,7},{19,7},{24,7},{25,7},{30,7},{31,7},{32,7},{33,7},{34,7},{35,7},{36,7},{41,7},{42,7},{43,7},{44,7},{49,7},
                {0,8},{49,8},
                {0,9},{49,9},
                {0,10},{49,10},
                {0,11},{49,11},
                {0,12},{5,12},{6,12},{7,12},{8,12},{13,12},{14,12},{19,12},{20,12},{21,12},{22,12},{23,12},{24,12},{25,12},{26,12},{27,12},{28,12},{29,12},{30,12},{35,12},{36,12},{41,12},{42,12},{43,12},{44,12},{49,12},
                {0,13},{13,13},{14,13},{24,13},{25,13},{35,13},{36,13},{49,13},
                {0,14},{13,14},{14,14},{24,14},{25,14},{35,14},{36,14},{49,14},
                {0,15},{13,15},{14,15},{24,15},{25,15},{35,15},{36,15},{49,15},
                {0,16},{13,16},{14,16},{24,16},{25,16},{35,16},{36,16},{49,16},
                {0,17},{1,17},{2,17},{3,17},{4,17},{5,17},{6,17},{7,17},{8,17},{13,17},{14,17},{15,17},{16,17},{17,17},{18,17},{19,17},{24,17},{25,17},{30,17},{31,17},{32,17},{33,17},{34,17},{35,17},{36,17},{41,17},{42,17},{43,17},{44,17},{45,17},{46,17},{47,17},{48,17},{49,17},
                {8,18},{13,18},{14,18},{35,18},{36,18},{41,18},
                {8,19},{13,19},{14,19},{35,19},{36,19},{41,19},
                {8,20},{13,20},{14,20},{35,20},{36,20},{41,20},
                {8,21},{13,21},{14,21},{35,21},{36,21},{41,21},
                {0,22},{1,22},{2,22},{3,22},{4,22},{5,22},{6,22},{7,22},{8,22},{13,22},{14,22},{19,22},{20,22},{21,22},{22,22},{23,22},{24,22},{25,22},{26,22},{27,22},{28,22},{29,22},{30,22},{35,22},{36,22},{41,22},{42,22},{43,22},{44,22},{45,22},{46,22},{47,22},{48,22},{49,22},
                {19,23},{30,23},
                {19,24},{30,24},
                {19,25},{30,25},
                {19,26},{30,26},
                {0,27},{1,27},{2,27},{3,27},{4,27},{5,27},{6,27},{7,27},{8,27},{13,27},{14,27},{19,27},{20,27},{21,27},{22,27},{23,27},{24,27},{25,27},{26,27},{27,27},{28,27},{29,27},{30,27},{35,27},{36,27},{41,27},{42,27},{43,27},{44,27},{45,27},{46,27},{47,27},{48,27},{49,27},
                {8,28},{13,28},{14,28},{35,28},{36,28},{41,28},
                {8,29},{13,29},{14,29},{35,29},{36,29},{41,29},
                {8,30},{13,30},{14,30},{35,30},{36,30},{41,30},
                {8,31},{13,31},{14,31},{35,31},{36,31},{41,31},
                {0,32},{1,32},{2,32},{3,32},{4,32},{5,32},{6,32},{7,32},{8,32},{13,32},{14,32},{19,32},{20,32},{21,32},{22,32},{23,32},{24,32},{25,32},{26,32},{27,32},{28,32},{29,32},{30,32},{35,32},{36,32},{41,32},{42,32},{43,32},{44,32},{45,32},{46,32},{47,32},{48,32},{49,32},
                {0,33},{49,33},
                {0,34},{49,34},
                {0,35},{49,35},
                {0,36},{49,36},
                {0,37},{5,37},{6,37},{7,37},{8,37},{13,37},{14,37},{19,37},{20,37},{21,37},{22,37},{23,37},{24,37},{25,37},{26,37},{27,37},{28,37},{29,37},{30,37},{35,37},{36,37},{41,37},{42,37},{43,37},{44,37},{49,37},
                {0,38},{5,38},{6,38},{7,38},{8,38},{13,38},{14,38},{19,38},{20,38},{21,38},{22,38},{23,38},{24,38},{25,38},{26,38},{27,38},{28,38},{29,38},{30,38},{35,38},{36,38},{41,38},{42,38},{43,38},{44,38},{49,38},
                {0,39},{5,39},{6,39},{7,39},{8,39},{13,39},{14,39},{19,39},{20,39},{21,39},{22,39},{23,39},{24,39},{25,39},{26,39},{27,39},{28,39},{29,39},{30,39},{35,39},{36,39},{41,39},{42,39},{43,39},{44,39},{49,39},
                {0,40},{13,40},{14,40},{24,40},{25,40},{35,40},{36,40},{49,40},
                {0,41},{13,41},{14,41},{24,41},{25,41},{35,41},{36,41},{49,41},
                {0,42},{13,42},{14,42},{24,42},{25,42},{35,42},{36,42},{49,42},
                {0,43},{13,43},{14,43},{24,43},{25,43},{35,43},{36,43},{49,43},
                {0,44},{5,44},{6,44},{7,44},{8,44},{9,44},{10,44},{11,44},{12,44},{13,44},{14,44},{15,44},{16,44},{17,44},{18,44},{19,44},{24,44},{25,44},{30,44},{31,44},{32,44},{33,44},{34,44},{35,44},{36,44},{37,44},{38,44},{39,44},{40,44},{41,44},{42,44},{43,44},{44,44},{49,44},
                {0,45},{49,45},
                {0,46},{49,46},
                {0,47},{49,47},
                {0,48},{49,48},
                {0,49},{1,49},{2,49},{3,49},{4,49},{5,49},{6,49},{7,49},{8,49},{9,49},{10,49},{11,49},{12,49},{13,49},{14,49},{15,49},{16,49},{17,49},{18,49},{19,49},{20,49},{21,49},{22,49},{23,49},{24,49},{25,49},{26,49},{27,49},{28,49},{29,49},{30,49},{31,49},{32,49},{33,49},{34,49},{35,49},{36,49},{37,49},{38,49},{39,49},{40,49},{41,49},{42,49},{43,49},{44,49},{45,49},{46,49},{47,49},{48,49},{49,49}
        };

        code = 0;
        open = false;
    }


    public void controllerJSON() throws IOException {
        server.receiveJSON();
        System.out.println(server.getReceived().toString());
        key = server.getReceived().getInt("Key");
        System.out.println(key);
    }

    public void send(String color, int[][] coords) throws IOException {
        JSONObject Obj = new JSONObject();
        JSONArray array = new JSONArray();

        for (int[] c: coords)
            array.put(c);

        Obj.put(color,array);
        client.sendJSON(Obj);

        // this.send("Black",new int[][]{lastPos});
    }

    /*public void send2 (String color, int[] coords) throws IOException {
        JSONObject Obj = new JSONObject();
        JSONArray array = new JSONArray();
        array.put(coords);
        Obj.put(color,array);
        client.sendJSON(Obj);
    }*/ // SEND 2


    // Game Methods
    public void drawMap() throws IOException {
        for (int[] coords : map){
            this.send("Blue",new int[][]{{coords[1],coords[0]}});
        }
        // White ghost door
        this.send("White",new int[][]{{22,23}});
        this.send("White",new int[][]{{22,24}});
        this.send("White",new int[][]{{22,25}});
        this.send("White",new int[][]{{22,26}});
    }

    public void cleanPacMan() throws IOException {
        if (lastPos == null){
            return;
        }
        if (lastPos[1]+1 <= 49){
            this.send("Black",new int[][]{{lastPos[0],lastPos[1]+1}});
            this.send("Black",new int[][]{{lastPos[0]+1,lastPos[1]+1}});
            this.send("Black",new int[][]{{lastPos[0]+2,lastPos[1]+1}});
            this.send("Black",new int[][]{{lastPos[0]+3,lastPos[1]+1}});
        }
        if (lastPos[1]+2 <= 49){
            this.send("Black",new int[][]{{lastPos[0]+1,lastPos[1]+2}});
            this.send("Black",new int[][]{{lastPos[0]+2,lastPos[1]+2}});
        }
        if (lastPos[1]-1 >= 0){
            this.send("Black",new int[][]{{lastPos[0]+1,lastPos[1]-1}});
            this.send("Black",new int[][]{{lastPos[0]+2,lastPos[1]-1}});
        }
        this.send("Black",new int[][]{lastPos});
        this.send("Black",new int[][]{{lastPos[0]+1,lastPos[1]}});
        this.send("Black",new int[][]{{lastPos[0]+2,lastPos[1]}});
        this.send("Black",new int[][]{{lastPos[0]+3,lastPos[1]}});
    }

    public void drawPacMan() throws IOException {   // Coords for top left square
        cleanPacMan();

        if (!open){
            if (currentPos[1]+1 <= 49){
                this.send("Yellow",new int[][]{{currentPos[0],currentPos[1]+1}});
                this.send("Yellow",new int[][]{{currentPos[0]+1,currentPos[1]+1}});
                this.send("Yellow",new int[][]{{currentPos[0]+2,currentPos[1]+1}});
                this.send("Yellow",new int[][]{{currentPos[0]+3,currentPos[1]+1}});
            }
            if (currentPos[1]+2 <= 49){
                this.send("Yellow",new int[][]{{currentPos[0]+1,currentPos[1]+2}});
                this.send("Yellow",new int[][]{{currentPos[0]+2,currentPos[1]+2}});
            }
            if (currentPos[1]-1 >= 0){
                this.send("Yellow",new int[][]{{currentPos[0]+1,currentPos[1]-1}});
                this.send("Yellow",new int[][]{{currentPos[0]+2,currentPos[1]-1}});
            }
            this.send("Yellow",new int[][]{currentPos});
            this.send("Yellow",new int[][]{{currentPos[0]+1,currentPos[1]}});
            this.send("Yellow",new int[][]{{currentPos[0]+2,currentPos[1]}});
            this.send("Yellow",new int[][]{{currentPos[0]+3,currentPos[1]}});
            return;
        }
        switch (code){
            case 1:
                if (currentPos[1]+1 <= 49){
                    this.send("Yellow",new int[][]{{currentPos[0]+2,currentPos[1]+1}});
                    this.send("Yellow",new int[][]{{currentPos[0]+3,currentPos[1]+1}});
                }
                if (currentPos[1]+2 <= 49){
                    this.send("Yellow",new int[][]{{currentPos[0]+1,currentPos[1]+2}});
                    this.send("Yellow",new int[][]{{currentPos[0]+2,currentPos[1]+2}});
                }
                if (currentPos[1]-1 >= 0){
                    this.send("Yellow",new int[][]{{currentPos[0]+1,currentPos[1]-1}});
                    this.send("Yellow",new int[][]{{currentPos[0]+2,currentPos[1]-1}});
                }
                this.send("Yellow",new int[][]{{currentPos[0]+2,currentPos[1]}});
                this.send("Yellow",new int[][]{{currentPos[0]+3,currentPos[1]}});
                break;
            case 2:
                if (currentPos[1]+1 <= 49){
                    this.send("Yellow",new int[][]{{currentPos[0],currentPos[1]+1}});
                    this.send("Yellow",new int[][]{{currentPos[0]+3,currentPos[1]+1}});
                }
                if (currentPos[1]-1 >= 0){
                    this.send("Yellow",new int[][]{{currentPos[0]+1,currentPos[1]-1}});
                    this.send("Yellow",new int[][]{{currentPos[0]+2,currentPos[1]-1}});
                }
                this.send("Yellow",new int[][]{currentPos});
                this.send("Yellow",new int[][]{{currentPos[0]+1,currentPos[1]}});
                this.send("Yellow",new int[][]{{currentPos[0]+2,currentPos[1]}});
                this.send("Yellow",new int[][]{{currentPos[0]+3,currentPos[1]}});
                break;
            case 3:
                if (currentPos[1]+1 <= 49){
                    this.send("Yellow",new int[][]{{currentPos[0],currentPos[1]+1}});
                    this.send("Yellow",new int[][]{{currentPos[0]+1,currentPos[1]+1}});
                }
                if (currentPos[1]+2 <= 49){
                    this.send("Yellow",new int[][]{{currentPos[0]+1,currentPos[1]+2}});
                    this.send("Yellow",new int[][]{{currentPos[0]+2,currentPos[1]+2}});
                }
                if (currentPos[1]-1 >= 0){
                    this.send("Yellow",new int[][]{{currentPos[0]+1,currentPos[1]-1}});
                    this.send("Yellow",new int[][]{{currentPos[0]+2,currentPos[1]-1}});
                }
                this.send("Yellow",new int[][]{currentPos});
                this.send("Yellow",new int[][]{{currentPos[0]+1,currentPos[1]}});
                break;
            case 4:
                if (currentPos[1]+1 <= 49){
                    this.send("Yellow",new int[][]{{currentPos[0],currentPos[1]+1}});
                    this.send("Yellow",new int[][]{{currentPos[0]+1,currentPos[1]+1}});
                    this.send("Yellow",new int[][]{{currentPos[0]+2,currentPos[1]+1}});
                    this.send("Yellow",new int[][]{{currentPos[0]+3,currentPos[1]+1}});
                }
                if (currentPos[1]+2 <= 49){
                    this.send("Yellow",new int[][]{{currentPos[0]+1,currentPos[1]+2}});
                    this.send("Yellow",new int[][]{{currentPos[0]+2,currentPos[1]+2}});
                }
                this.send("Yellow",new int[][]{currentPos});
                this.send("Yellow",new int[][]{{currentPos[0]+3,currentPos[1]}});
                break;
        }
    }

    public void movePlayer() throws IOException {
        switch (code){
            case 0:
                lastPos = null;
                currentPos = new int[] {28,24};
                code = 4;
                break;
            case 1:
                lastPos = currentPos;
                currentPos = new int[] {currentPos[0]-1,currentPos[1]};
                break;
            case 2:
                if (currentPos[1]+1 > 49){
                    lastPos = currentPos;
                    currentPos = new int[] {currentPos[0],0};
                }else{
                    lastPos = currentPos;
                    currentPos = new int[] {currentPos[0],currentPos[1]+1};
                }
                break;
            case 3:
                lastPos = currentPos;
                currentPos = new int[] {currentPos[0]+1,currentPos[1]};
                break;
            case 4:
                if (currentPos[1]-1 < 0){
                    lastPos = currentPos;
                    currentPos = new int[] {currentPos[0],49};
                }else{
                    lastPos = currentPos;
                    currentPos = new int[] {currentPos[0],currentPos[1]-1};
                }
                break;
        }

        drawPacMan();

        open = !open;
    }

    public boolean isInMap(int[] pos) {
        for (int[] coords : map){
            if (Arrays.equals(new int[]{coords[1],coords[0]},pos)){
                return true;
            }
        }
        return false;
    }

    public boolean canMove(int num) throws IOException {

        switch (num){
            case 1:
                if (isInMap(new int[] {currentPos[0]-1,currentPos[1]-1}) || isInMap(new int[] {currentPos[0]-1,currentPos[1]}) || isInMap(new int[] {currentPos[0]-1,currentPos[1]+1}) || isInMap(new int[] {currentPos[0]-1,currentPos[1]+2})){
                    return false;
                }
                break;
            case 2:
                if (isInMap(new int[] {currentPos[0],currentPos[1]+3}) || isInMap(new int[] {currentPos[0]+1,currentPos[1]+3}) || isInMap(new int[] {currentPos[0]+2,currentPos[1]+3}) || isInMap(new int[] {currentPos[0]+3,currentPos[1]+3})){
                    return false;
                }
                break;
            case 3:
                if (isInMap(new int[] {currentPos[0]+4,currentPos[1]-1}) || isInMap(new int[] {currentPos[0]+4,currentPos[1]}) || isInMap(new int[] {currentPos[0]+4,currentPos[1]+1}) || isInMap(new int[] {currentPos[0]+4,currentPos[1]+2})){
                    return false;
                }
                break;
            case 4:
                if (isInMap(new int[] {currentPos[0],currentPos[1]-2}) || isInMap(new int[] {currentPos[0]+1,currentPos[1]-2}) || isInMap(new int[] {currentPos[0]+2,currentPos[1]-2}) || isInMap(new int[] {currentPos[0]+3,currentPos[1]-2})){
                    return false;
                }
                break;
        }
        return true;
    }


    public static void main(String[] args) throws IOException{
        PacMan main = new PacMan();

        main.drawMap();
        main.movePlayer();   // Initializes Player

        while (main.server.getClient().isConnected()){
            main.controllerJSON();

            if (main.canMove(main.code)){
                main.movePlayer();
            }

            switch (main.key){  // A button is pressed
                case 'w':
                    if (main.canMove(1)){
                        main.code = 1;
                    }
                    break;
                case 'a':
                    if (main.canMove(4)){
                        main.code = 4;
                    }
                    break;
                case 's':
                    if (main.canMove(3)){
                        main.code = 3;
                    }
                    break;
                case 'd':
                    if (main.canMove(2)){
                        main.code = 2;
                    }
                    break;
            }

            //To test the screen
            /*switch (main.key){
                case 'w'->main.send("Yellow",new int[]{0,0});
                case 'a'->main.send("Red",new int[]{1,1});
                case 's'->main.send("Green",new int[]{2,2});
                case 'd'->main.send("Purple",new int[]{3,3});
                case ' '->main.send("Blue",new int[]{4,4});
            }*/
        }
    }
}