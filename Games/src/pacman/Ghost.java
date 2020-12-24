package pacman;

import java.util.Arrays;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Vector;

public class Ghost extends Thread{
    private PacMan pacMan;

    private String color;
    private int[] currentPos;
    private int[] lastPos;
    private int code;
    private boolean inCage;
    private int changeCode;


    public Ghost (PacMan pm, String _color) throws IOException {
        pacMan = pm;
        color = _color;
        code = 0;   // code: 0 = NONE, 1 = UP, 2 = RIGHT, 3 = DOWN, 4 = LEFT
        currentPos = new int[] {0,0};
        lastPos = null;
        inCage = false;
        changeCode = 30;
    }


    public void send(String color, int[][] coords) throws IOException {
        JSONObject Obj = new JSONObject();
        JSONArray array = new JSONArray();

        for (int[] c: coords)
            array.put(c);

        Obj.put(color,array);
        PacMan.getClient().sendJSON(Obj);

        // this.send("Black",new int[][]{lastPos});
    }   // ++++++++++++++++++


    public int[] getCurrentPos() {
        return currentPos;
    }
    public boolean getInCage() {return inCage;}


    public void cleanGhost() throws IOException {
        if (lastPos == null){
            return;
        }

        Vector<int []> blacks = new Vector<>();
        Vector<int []> whites = new Vector<>();

        if (lastPos[1]+1 <= 49){
            if (pacMan.getDotMatrix()[lastPos[0]][lastPos[1]+1] == 0){
                blacks.add(new int[]{lastPos[0],lastPos[1]+1});
            }else{
                whites.add(new int[] {lastPos[0],lastPos[1]+1});
            }

            if (pacMan.getDotMatrix()[lastPos[0]+1][lastPos[1]+1] == 0){
                blacks.add(new int[]{lastPos[0]+1,lastPos[1]+1});
            }else{
                whites.add(new int[]{lastPos[0]+1,lastPos[1]+1});
            }

            if (pacMan.getDotMatrix()[lastPos[0]+2][lastPos[1]+1] == 0){
                blacks.add(new int[]{lastPos[0]+2,lastPos[1]+1});
            }else{
                whites.add(new int[]{lastPos[0]+2,lastPos[1]+1});
            }
        }
        if (lastPos[1]+2 <= 49){
            if (pacMan.getDotMatrix()[lastPos[0]+1][lastPos[1]+2] == 0){
                blacks.add(new int[]{lastPos[0]+1,lastPos[1]+2});
            }else{
                whites.add(new int[]{lastPos[0]+1,lastPos[1]+2});
            }

            if (pacMan.getDotMatrix()[lastPos[0]+2][lastPos[1]+2] == 0){
                blacks.add(new int[]{lastPos[0]+2,lastPos[1]+2});
            }else{
                whites.add(new int[]{lastPos[0]+2,lastPos[1]+2});
            }

            if (pacMan.getDotMatrix()[lastPos[0]+3][lastPos[1]+2] == 0){
                blacks.add(new int[]{lastPos[0]+3,lastPos[1]+2});
            }else{
                whites.add(new int[]{lastPos[0]+3,lastPos[1]+2});
            }
        }
        if (lastPos[1]-1 >= 0){
            if (pacMan.getDotMatrix()[lastPos[0]+1][lastPos[1]-1] == 0){
                blacks.add(new int[]{lastPos[0]+1,lastPos[1]-1});
            }else{
                whites.add(new int[]{lastPos[0]+1,lastPos[1]-1});
            }

            if (pacMan.getDotMatrix()[lastPos[0]+2][lastPos[1]-1] == 0){
                blacks.add(new int[]{lastPos[0]+2,lastPos[1]-1});
            }else{
                whites.add(new int[]{lastPos[0]+2,lastPos[1]-1});
            }

            if (pacMan.getDotMatrix()[lastPos[0]+3][lastPos[1]-1] == 0){
                blacks.add(new int[]{lastPos[0]+3,lastPos[1]-1});
            }else{
                whites.add(new int[]{lastPos[0]+3,lastPos[1]-1});
            }
        }
        if (pacMan.getDotMatrix()[lastPos[0]][lastPos[1]] == 0){
            blacks.add(new int[]{lastPos[0],lastPos[1]});
        }else{
            whites.add(new int[]{lastPos[0],lastPos[1]});
        }

        if (pacMan.getDotMatrix()[lastPos[0]+1][lastPos[1]] == 0){
            blacks.add(new int[]{lastPos[0]+1,lastPos[1]});
        }else{
            whites.add(new int[]{lastPos[0]+1,lastPos[1]});
        }

        if (pacMan.getDotMatrix()[lastPos[0]+2][lastPos[1]] == 0){
            blacks.add(new int[]{lastPos[0]+2,lastPos[1]});
        }else{
            whites.add(new int[]{lastPos[0]+2,lastPos[1]});
        }

        //Send Functions
        if (!blacks.isEmpty())
            this.send("Black",blacks.toArray(new int[blacks.size()][2]));

        if (!whites.isEmpty())
            this.send("White",whites.toArray(new int[whites.size()][2]));


        //System.out.println("=========");
        //System.out.println("r: " + lastPos[0]+2);
        //System.out.println("c: " + lastPos[1]);
        //System.out.println(pacMan.getDotMatrix()[lastPos[0]+2][lastPos[1]]);
    }

    public void drawGhost() throws IOException {
        cleanGhost();
        String bColor;
        if (pacMan.getPower()){
            bColor = "Gray";
        }else{
            bColor = color;
        }
        switch (code) {
            case 1 -> {
                if (currentPos[1] + 1 <= 49) {
                    this.send(bColor, new int[][]{{currentPos[0], currentPos[1]+1}});
                    this.send(bColor, new int[][]{{currentPos[0]+1, currentPos[1]+1}});
                    this.send(bColor, new int[][]{{currentPos[0]+2, currentPos[1]+1}});
                }
                if (currentPos[1] + 2 <= 49) {
                    this.send("White", new int[][]{{currentPos[0], currentPos[1]+2}});
                    this.send(bColor, new int[][]{{currentPos[0]+1, currentPos[1]+2}});
                    this.send(bColor, new int[][]{{currentPos[0]+2, currentPos[1]+2}});
                    this.send(bColor, new int[][]{{currentPos[0]+3, currentPos[1]+2}});
                }
                if (currentPos[1] - 1 >= 0) {
                    this.send("White", new int[][]{{currentPos[0], currentPos[1]-1}});
                    this.send(bColor, new int[][]{{currentPos[0]+1, currentPos[1]-1}});
                    this.send(bColor, new int[][]{{currentPos[0]+2, currentPos[1]-1}});
                    this.send(bColor, new int[][]{{currentPos[0]+3, currentPos[1]-1}});
                }
                this.send(bColor, new int[][]{currentPos});
                this.send(bColor, new int[][]{{currentPos[0]+1, currentPos[1]}});
                this.send(bColor, new int[][]{{currentPos[0]+2, currentPos[1]}});
            }
            case 2 -> {
                if (currentPos[1] + 1 <= 49) {
                    this.send(bColor, new int[][]{{currentPos[0], currentPos[1]+1}});
                    this.send(bColor, new int[][]{{currentPos[0]+1, currentPos[1]+1}});
                    this.send(bColor, new int[][]{{currentPos[0]+2, currentPos[1]+1}});
                }
                if (currentPos[1] + 2 <= 49) {
                    this.send("White", new int[][]{{currentPos[0]+1, currentPos[1]+2}});
                    this.send(bColor, new int[][]{{currentPos[0]+2, currentPos[1]+2}});
                    this.send(bColor, new int[][]{{currentPos[0]+3, currentPos[1]+2}});
                }
                if (currentPos[1] - 1 >= 0) {
                    this.send(bColor, new int[][]{{currentPos[0]+1, currentPos[1]-1}});
                    this.send(bColor, new int[][]{{currentPos[0]+2, currentPos[1]-1}});
                    this.send(bColor, new int[][]{{currentPos[0]+3, currentPos[1]-1}});
                }
                this.send(bColor, new int[][]{currentPos});
                this.send("White", new int[][]{{currentPos[0]+1, currentPos[1]}});
                this.send(bColor, new int[][]{{currentPos[0]+2, currentPos[1]}});
            }
            case 3 -> {
                if (currentPos[1] + 1 <= 49) {
                    this.send(bColor, new int[][]{{currentPos[0], currentPos[1]+1}});
                    this.send(bColor, new int[][]{{currentPos[0]+1, currentPos[1]+1}});
                    this.send(bColor, new int[][]{{currentPos[0]+2, currentPos[1]+1}});
                }
                if (currentPos[1] + 2 <= 49) {
                    this.send(bColor, new int[][]{{currentPos[0]+1, currentPos[1]+2}});
                    this.send("White", new int[][]{{currentPos[0]+2, currentPos[1]+2}});
                    this.send(bColor, new int[][]{{currentPos[0]+3, currentPos[1]+2}});
                }
                if (currentPos[1] - 1 >= 0) {
                    this.send(bColor, new int[][]{{currentPos[0]+1, currentPos[1]-1}});
                    this.send("White", new int[][]{{currentPos[0]+2, currentPos[1]-1}});
                    this.send(bColor, new int[][]{{currentPos[0]+3, currentPos[1]-1}});
                }
                this.send(bColor, new int[][]{currentPos});
                this.send(bColor, new int[][]{{currentPos[0]+1, currentPos[1]}});
                this.send(bColor, new int[][]{{currentPos[0]+2, currentPos[1]}});
            }
            case 4 -> {
                if (currentPos[1] + 1 <= 49) {
                    this.send(bColor, new int[][]{{currentPos[0], currentPos[1]+1}});
                    this.send("White", new int[][]{{currentPos[0]+1, currentPos[1]+1}});
                    this.send(bColor, new int[][]{{currentPos[0]+2, currentPos[1]+1}});
                }
                if (currentPos[1] + 2 <= 49) {
                    this.send(bColor, new int[][]{{currentPos[0]+1, currentPos[1]+2}});
                    this.send(bColor, new int[][]{{currentPos[0]+2, currentPos[1]+2}});
                    this.send(bColor, new int[][]{{currentPos[0]+3, currentPos[1]+2}});
                }
                if (currentPos[1] - 1 >= 0) {
                    this.send("White", new int[][]{{currentPos[0]+1, currentPos[1]-1}});
                    this.send(bColor, new int[][]{{currentPos[0]+2, currentPos[1]-1}});
                    this.send(bColor, new int[][]{{currentPos[0]+3, currentPos[1]-1}});
                }
                this.send(bColor, new int[][]{currentPos});
                this.send(bColor, new int[][]{{currentPos[0]+1, currentPos[1]}});
                this.send(bColor, new int[][]{{currentPos[0]+2, currentPos[1]}});
            }
        }
    }

    public void moveGhost() throws InterruptedException, IOException {
        Thread.sleep(100);
        switch (code){
            case 0:
                lastPos = null;
                goToCage();
                /*if (color == "green"){
                    currentPos = new int[] {23,24};
                    inCage = true;
                }
                if (color == "Light Blue"){
                    currentPos = new int[] {18,24};
                }*/
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
        drawGhost();
    }

    public void directMove(int[] newPos) throws InterruptedException, IOException {   // One direction at once
        int tmpCode = code;
        if (newPos[0] > currentPos[0]){
            code = 3;
            while (currentPos[0] != newPos[0]){
                moveGhost();
            }
            code = tmpCode;
            return;
        }
        if (newPos[0] < currentPos[0]){
            code = 1;
            while (currentPos[0] != newPos[0]){
                moveGhost();
            }
            code = tmpCode;
            return;
        }
        if (newPos[1] > currentPos[1]){
            code = 2;
            while (currentPos[1] != newPos[1]){
                moveGhost();
            }
            code = tmpCode;
            return;
        }
        if (newPos[1] < currentPos[1]){
            code = 4;
            while (currentPos[1] != newPos[1]){
                moveGhost();
            }
            code = tmpCode;
        }
    }

    public boolean canMove(int num){
        if (inCage){
            return false;
        }
        switch (num){
            case 1:
                if (pacMan.isInMap(new int[] {currentPos[0]-1,currentPos[1]-1}) || pacMan.isInMap(new int[] {currentPos[0]-1,currentPos[1]}) || pacMan.isInMap(new int[] {currentPos[0]-1,currentPos[1]+1}) || pacMan.isInMap(new int[] {currentPos[0]-1,currentPos[1]+2})){
                    return false;
                }
                break;
            case 2:
                if (pacMan.isInMap(new int[] {currentPos[0],currentPos[1]+3}) || pacMan.isInMap(new int[] {currentPos[0]+1,currentPos[1]+3}) || pacMan.isInMap(new int[] {currentPos[0]+2,currentPos[1]+3}) || pacMan.isInMap(new int[] {currentPos[0]+3,currentPos[1]+3})){
                    return false;
                }
                break;
            case 3:
                if (pacMan.isInMap(new int[] {currentPos[0]+4,currentPos[1]-1}) || pacMan.isInMap(new int[] {currentPos[0]+4,currentPos[1]}) || pacMan.isInMap(new int[] {currentPos[0]+4,currentPos[1]+1}) || pacMan.isInMap(new int[] {currentPos[0]+4,currentPos[1]+2})){
                    return false;
                }
                break;
            case 4:
                if (pacMan.isInMap(new int[] {currentPos[0],currentPos[1]-2}) || pacMan.isInMap(new int[] {currentPos[0]+1,currentPos[1]-2}) || pacMan.isInMap(new int[] {currentPos[0]+2,currentPos[1]-2}) || pacMan.isInMap(new int[] {currentPos[0]+3,currentPos[1]-2})){
                    return false;
                }
                break;
        }
        return true;
    }

    public void freeFromCage() throws InterruptedException, IOException {
        if (color.equals("Purple")){
            while (pacMan.getGhostInCage() == 2){
                //sleep(1);
            }
            sleep(3000);
            directMove(new int[] {23,24});
            directMove(new int[] {18,24});
        }
        if (color.equals("Red")){
            sleep(5000);
            directMove(new int[] {23,24});
            directMove(new int[] {18,24});
        }
        inCage = false;
    }

    public void goToCage(){
        if (color.equals("Purple")){
            currentPos = new int[] {23,27};
            inCage = true;
            code = 2;
        }
        if (color.equals("Red")){
            currentPos = new int[] {23,21};
            inCage = true;
            code = 4;
        }
    }

    public void pacManCollision(){
        if (Arrays.equals(currentPos,pacMan.getCurrentPos())){
            if (pacMan.getPower()){
                goToCage();
            }else{
                pacMan.setLose(true);
            }
        }
    }

    public void randomCode(){
        Random random = new Random();
        code = random.nextInt(4);
        code++;
    }


    @Override
    public void run(){
        super.run();
        try {
            moveGhost();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

        while (pacMan.getServer().getClient().isConnected()){
            if (inCage){
                try {
                    freeFromCage();
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }

            if (canMove(code)){
                try {
                    moveGhost();
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }

            // Changes direction randomly
            if (changeCode <= 0 || !canMove(code)){
                changeCode = 30;
                randomCode();
            }else{
                changeCode--;
            }

            pacManCollision();
        }
    }
}
