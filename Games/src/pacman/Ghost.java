package pacman;

import gamsua.Client;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class Ghost extends Thread{
    private PacMan pacMan;

    private String color;
    private int[] currentPos;
    private int[] lastPos;
    private int code;
    private boolean inCage;
    private boolean vulnerable; // PacMan can eat them


    public Ghost (PacMan pm, String _color, int[] _currentPos, boolean _inCage) throws IOException {
        pacMan = pm;
        color = _color;
        code = 0;   // code: 0 = NONE, 1 = UP, 2 = RIGHT, 3 = DOWN, 4 = LEFT
        currentPos = _currentPos;
        lastPos = null;
        inCage = _inCage;
        vulnerable = false;
    }


    public void send(String color, int[][] coords) throws IOException {
        JSONObject Obj = new JSONObject();
        JSONArray array = new JSONArray();

        for (int[] c: coords)
            array.put(c);

        Obj.put(color,array);
        pacMan.getClient().sendJSON(Obj);

        // this.send("Black",new int[][]{lastPos});
    }   // ++++++++++++++++++


    public void cleanGhost() throws IOException {
        if (lastPos == null){
            return;
        }
        if (lastPos[1]+1 <= 49){
            if (pacMan.getDotMatrix()[lastPos[0]][lastPos[1]+1] == 0){
                this.send("Black",new int[][]{{lastPos[0],lastPos[1]+1}});
            }else{
                this.send("White",new int[][]{{lastPos[0],lastPos[1]+1}});
            }

            if (pacMan.getDotMatrix()[lastPos[0]+1][lastPos[1]+1] == 0){
                this.send("Black",new int[][]{{lastPos[0]+1,lastPos[1]+1}});
            }else{
                this.send("White",new int[][]{{lastPos[0]+1,lastPos[1]+1}});
            }

            if (pacMan.getDotMatrix()[lastPos[0]+2][lastPos[1]+1] == 0){
                this.send("Black",new int[][]{{lastPos[0]+2,lastPos[1]+1}});
            }else{
                this.send("White",new int[][]{{lastPos[0]+2,lastPos[1]+1}});
            }
        }
        if (lastPos[1]+2 <= 49){
            if (pacMan.getDotMatrix()[lastPos[0]+1][lastPos[1]+2] == 0){
                this.send("Black",new int[][]{{lastPos[0]+1,lastPos[1]+2}});
            }else{
                this.send("White",new int[][]{{lastPos[0]+1,lastPos[1]+2}});
            }

            if (pacMan.getDotMatrix()[lastPos[0]+2][lastPos[1]+2] == 0){
                this.send("Black",new int[][]{{lastPos[0]+2,lastPos[1]+2}});
            }else{
                this.send("White",new int[][]{{lastPos[0]+2,lastPos[1]+2}});
            }

            if (pacMan.getDotMatrix()[lastPos[0]+3][lastPos[1]+2] == 0){
                this.send("Black",new int[][]{{lastPos[0]+3,lastPos[1]+2}});
            }else{
                this.send("White",new int[][]{{lastPos[0]+3,lastPos[1]+2}});
            }
        }
        if (lastPos[1]-1 >= 0){
            if (pacMan.getDotMatrix()[lastPos[0]+1][lastPos[1]-1] == 0){
                this.send("Black",new int[][]{{lastPos[0]+1,lastPos[1]-1}});
            }else{
                this.send("White",new int[][]{{lastPos[0]+1,lastPos[1]-1}});
            }

            if (pacMan.getDotMatrix()[lastPos[0]+2][lastPos[1]-1] == 0){
                this.send("Black",new int[][]{{lastPos[0]+2,lastPos[1]-1}});
            }else{
                this.send("White",new int[][]{{lastPos[0]+2,lastPos[1]-1}});
            }

            if (pacMan.getDotMatrix()[lastPos[0]+3][lastPos[1]-1] == 0){
                this.send("Black",new int[][]{{lastPos[0]+3,lastPos[1]-1}});
            }else{
                this.send("White",new int[][]{{lastPos[0]+3,lastPos[1]-1}});
            }
        }
        if (pacMan.getDotMatrix()[lastPos[0]][lastPos[1]] == 0){
            this.send("Black",new int[][]{{lastPos[0],lastPos[1]}});
        }else{
            this.send("White",new int[][]{{lastPos[0],lastPos[1]}});
        }

        if (pacMan.getDotMatrix()[lastPos[0]+1][lastPos[1]] == 0){
            this.send("Black",new int[][]{{lastPos[0]+1,lastPos[1]}});
        }else{
            this.send("White",new int[][]{{lastPos[0]+1,lastPos[1]}});
        }

        if (pacMan.getDotMatrix()[lastPos[0]+2][lastPos[1]] == 0){
            this.send("Black",new int[][]{{lastPos[0]+2,lastPos[1]}});
        }else{
            this.send("White",new int[][]{{lastPos[0]+2,lastPos[1]}});
        }
    }

    public void drawGhost() throws IOException {
        switch (code) {
            case 1 -> {
                if (currentPos[1] + 1 <= 49) {
                    this.send(color, new int[][]{{currentPos[0], currentPos[1]+1}});
                    this.send(color, new int[][]{{currentPos[0]+1, currentPos[1]+1}});
                    this.send(color, new int[][]{{currentPos[0]+2, currentPos[1]+1}});
                }
                if (currentPos[1] + 2 <= 49) {
                    this.send("White", new int[][]{{currentPos[0], currentPos[1]+2}});
                    this.send(color, new int[][]{{currentPos[0]+1, currentPos[1]+2}});
                    this.send(color, new int[][]{{currentPos[0]+2, currentPos[1]+2}});
                    this.send(color, new int[][]{{currentPos[0]+3, currentPos[1]+2}});
                }
                if (currentPos[1] - 1 >= 0) {
                    this.send("White", new int[][]{{currentPos[0], currentPos[1]-1}});
                    this.send(color, new int[][]{{currentPos[0]+1, currentPos[1]-1}});
                    this.send(color, new int[][]{{currentPos[0]+2, currentPos[1]-1}});
                    this.send(color, new int[][]{{currentPos[0]+3, currentPos[1]-1}});
                }
                this.send(color, new int[][]{currentPos});
                this.send(color, new int[][]{{currentPos[0]+1, currentPos[1]}});
                this.send(color, new int[][]{{currentPos[0]+2, currentPos[1]}});
            }
            case 2 -> {
                if (currentPos[1] + 1 <= 49) {
                    this.send(color, new int[][]{{currentPos[0], currentPos[1]+1}});
                    this.send(color, new int[][]{{currentPos[0]+1, currentPos[1]+1}});
                    this.send(color, new int[][]{{currentPos[0]+2, currentPos[1]+1}});
                }
                if (currentPos[1] + 2 <= 49) {
                    this.send("White", new int[][]{{currentPos[0]+1, currentPos[1]+2}});
                    this.send(color, new int[][]{{currentPos[0]+2, currentPos[1]+2}});
                    this.send(color, new int[][]{{currentPos[0]+3, currentPos[1]+2}});
                }
                if (currentPos[1] - 1 >= 0) {
                    this.send(color, new int[][]{{currentPos[0]+1, currentPos[1]-1}});
                    this.send(color, new int[][]{{currentPos[0]+2, currentPos[1]-1}});
                    this.send(color, new int[][]{{currentPos[0]+3, currentPos[1]-1}});
                }
                this.send(color, new int[][]{currentPos});
                this.send("White", new int[][]{{currentPos[0]+1, currentPos[1]}});
                this.send(color, new int[][]{{currentPos[0]+2, currentPos[1]}});
            }
            case 3 -> {
                if (currentPos[1] + 1 <= 49) {
                    this.send(color, new int[][]{{currentPos[0], currentPos[1]+1}});
                    this.send(color, new int[][]{{currentPos[0]+1, currentPos[1]+1}});
                    this.send(color, new int[][]{{currentPos[0]+2, currentPos[1]+1}});
                }
                if (currentPos[1] + 2 <= 49) {
                    this.send(color, new int[][]{{currentPos[0]+1, currentPos[1]+2}});
                    this.send("White", new int[][]{{currentPos[0]+2, currentPos[1]+2}});
                    this.send(color, new int[][]{{currentPos[0]+3, currentPos[1]+2}});
                }
                if (currentPos[1] - 1 >= 0) {
                    this.send(color, new int[][]{{currentPos[0]+1, currentPos[1]-1}});
                    this.send("White", new int[][]{{currentPos[0]+2, currentPos[1]-1}});
                    this.send(color, new int[][]{{currentPos[0]+3, currentPos[1]-1}});
                }
                this.send(color, new int[][]{currentPos});
                this.send(color, new int[][]{{currentPos[0]+1, currentPos[1]}});
                this.send(color, new int[][]{{currentPos[0]+2, currentPos[1]}});
            }
            case 4 -> {
                if (currentPos[1] + 1 <= 49) {
                    this.send(color, new int[][]{{currentPos[0], currentPos[1]+1}});
                    this.send("White", new int[][]{{currentPos[0]+1, currentPos[1]+1}});
                    this.send(color, new int[][]{{currentPos[0]+2, currentPos[1]+1}});
                }
                if (currentPos[1] + 2 <= 49) {
                    this.send(color, new int[][]{{currentPos[0]+1, currentPos[1]+2}});
                    this.send(color, new int[][]{{currentPos[0]+2, currentPos[1]+2}});
                    this.send(color, new int[][]{{currentPos[0]+3, currentPos[1]+2}});
                }
                if (currentPos[1] - 1 >= 0) {
                    this.send("White", new int[][]{{currentPos[0]+1, currentPos[1]-1}});
                    this.send(color, new int[][]{{currentPos[0]+2, currentPos[1]-1}});
                    this.send(color, new int[][]{{currentPos[0]+3, currentPos[1]-1}});
                }
                this.send(color, new int[][]{currentPos});
                this.send(color, new int[][]{{currentPos[0]+1, currentPos[1]}});
                this.send(color, new int[][]{{currentPos[0]+2, currentPos[1]}});
            }
        }
    }

    public void moveGhost(){

    }

    public boolean canMove(){

    }

    public void freeFromCage(){

    }

    public void goToCage(){}


    @Override
    public void run() {
        super.run();

    }
}
