package tetris;

public class Blocks {
    //Attributes
    private int num;
    private int rot = 0;
    private int[][] oldCoord = new int[][]{{-1,-1},{-1,-1},{-1,-1},{-1,-1}};
    private int[][] coordinates = new int[][]{{-1,-1},{-1,-1},{-1,-1},{-1,-1}};

    public Blocks(String name){
        switch (name){
            case "i" -> {
                num = 1;
                coordinates[0] = new int[]{0, 3};
                coordinates[1] = new int[]{0, 4};
                coordinates[2] = new int[]{0, 5};
                coordinates[3] = new int[]{0, 6};
            }
            case "o" -> {
                num = 2;
                coordinates[0] = new int[]{0, 5};
                coordinates[1] = new int[]{0, 6};
                coordinates[2] = new int[]{1, 5};
                coordinates[3] = new int[]{1, 6};
            }
            case "j" -> {
                num = 3;
                coordinates[0] = new int[]{0, 5};
                coordinates[1] = new int[]{1, 5};
                coordinates[2] = new int[]{1, 6};
                coordinates[3] = new int[]{1, 7};
            }
            case "l" -> {
                num = 4;
                coordinates[0] = new int[]{0, 7};
                coordinates[1] = new int[]{1, 7};
                coordinates[2] = new int[]{1, 6};
                coordinates[3] = new int[]{1, 5};
            }
            case "t" -> {
                num = 5;
                coordinates[0] = new int[]{0, 6};
                coordinates[1] = new int[]{1, 5};
                coordinates[2] = new int[]{1, 6};
                coordinates[3] = new int[]{1, 7};
            }
            case "s" -> {
                num = 6;
                coordinates[0] = new int[]{0, 6};
                coordinates[1] = new int[]{0, 7};
                coordinates[2] = new int[]{1, 6};
                coordinates[3] = new int[]{1, 5};
            }
            case "z" ->{
                num = 7;
                coordinates[0] = new int[]{0, 5};
                coordinates[1] = new int[]{0, 6};
                coordinates[2] = new int[]{1, 6};
                coordinates[3] = new int[]{1, 7};
            }
        }
    }

    public void moveLeft(int[][] matrix){
        for (int[] coord: coordinates){
            if (coord[1] == 0)
                return;
        }

        for (int i = 0; i < 4; i++){
            oldCoord[i][0] = coordinates[i][0];
            oldCoord[i][1] = coordinates[i][1];

            coordinates[i][1] = coordinates[i][1]-1;
        }
    }

    public void moveRight(int[][] matrix){
        for (int[] coord: coordinates){
            if (coord[1] == 11)
                return;
        }

        for (int i = 0; i < 4; i++){
            oldCoord[i][0] = coordinates[i][0];
            oldCoord[i][1] = coordinates[i][1];

            coordinates[i][1] = coordinates[i][1]+1;
        }
    }

    public void moveDown(int[][] matrix){
        for (int[] coord: coordinates){
            if (coord[0] == 23)
                return;
        }

        for (int i = 0; i < 4; i++){
            oldCoord[i][0] = coordinates[i][0];
            oldCoord[i][1] = coordinates[i][1];

            coordinates[i][0] = coordinates[i][0]+1;
        }
    }

    public void rotate(int[][] matrix){
        for (int i = 0; i < 4; i++) {
            oldCoord[i][0] = coordinates[i][0];
            oldCoord[i][1] = coordinates[i][1];
        }

        switch (num){
            case 1 -> { //i
                if (rot%2==0){
                    coordinates[0][1] = coordinates[2][1] = coordinates[3][1] = coordinates[1][1];
                    coordinates[1][0]++;
                    coordinates[2][0]+= 2;
                    coordinates[3][0]+= 3;
                } else {
                    coordinates[1][0] = coordinates[2][0] = coordinates[3][0] = coordinates[0][0];
                    coordinates[0][1]--;
                    coordinates[2][1]++;
                    coordinates[3][1]+= 2;
                }
            }

            case 3 -> { //j
                switch (rot){
                    case 0 -> {
                        coordinates[0][1] = coordinates[1][1] = coordinates[3][1] = coordinates[2][1];
                        coordinates[0][1]++;
                        coordinates[1][0]--;
                        coordinates[3][0]++;
                    } case 1 -> {
                        coordinates[0][0] = coordinates[1][0] = coordinates[3][0] = coordinates[2][0];
                        coordinates[0][1] = coordinates[1][1] = coordinates[3][1] = coordinates[2][1];
                        coordinates[0][0]++;
                        coordinates[0][1]++;
                        coordinates[1][1]++;
                        coordinates[3][1]--;
                    } case 2 -> {
                        coordinates[0][0] = coordinates[1][0] = coordinates[3][0] = coordinates[2][0];
                        coordinates[0][1] = coordinates[1][1] = coordinates[3][1] = coordinates[2][1];
                        coordinates[0][0]--;
                        coordinates[1][0]++;
                        coordinates[3][0]++;
                        coordinates[3][1]--;
                    } case 3 -> {
                        coordinates[0][0] = coordinates[1][0] = coordinates[3][0] = coordinates[2][0];
                        coordinates[0][1] = coordinates[1][1] = coordinates[3][1] = coordinates[2][1];
                        coordinates[0][0]--;
                        coordinates[0][1]--;
                        coordinates[1][1]--;
                        coordinates[3][1]++;
                    }
                }
            }

            case 4 -> { //l
                switch (rot) {
                    case 0 -> {
                        coordinates[0][0] = coordinates[1][0] = coordinates[3][0] = coordinates[2][0];
                        coordinates[0][1] = coordinates[1][1] = coordinates[3][1] = coordinates[2][1];
                        coordinates[0][0]--;
                        coordinates[1][0]++;
                        coordinates[3][0]++;
                        coordinates[3][1]++;
                    } case 1 -> {
                        coordinates[0][0] = coordinates[1][0] = coordinates[3][0] = coordinates[2][0];
                        coordinates[0][1] = coordinates[1][1] = coordinates[3][1] = coordinates[2][1];
                        coordinates[0][1]--;
                        coordinates[0][0]++;
                        coordinates[1][1]--;
                        coordinates[3][1]++;
                    } case 2 -> {
                        coordinates[0][0] = coordinates[1][0] = coordinates[3][0] = coordinates[2][0];
                        coordinates[0][1] = coordinates[1][1] = coordinates[3][1] = coordinates[2][1];
                        coordinates[0][0]--;
                        coordinates[0][1]--;
                        coordinates[1][0]--;
                        coordinates[3][0]++;
                    } case 3 -> {
                        coordinates[0][0] = coordinates[1][0] = coordinates[3][0] = coordinates[2][0];
                        coordinates[0][1] = coordinates[1][1] = coordinates[3][1] = coordinates[2][1];
                        coordinates[0][1]--;
                        coordinates[1][1]++;
                        coordinates[3][0]--;
                        coordinates[3][1]++;
                    }
                }
            }

            case 5 -> {//t
                switch (rot){
                    case 0 -> {
                        coordinates[0][0] = coordinates[1][0] = coordinates[3][0] = coordinates[2][0];
                        coordinates[0][1] = coordinates[1][1] = coordinates[3][1] = coordinates[2][1];
                        coordinates[0][0]--;
                        coordinates[1][0]++;
                        coordinates[3][1]++;
                    } case 1 -> {
                        coordinates[0][0] = coordinates[1][0] = coordinates[3][0] = coordinates[2][0];
                        coordinates[0][1] = coordinates[1][1] = coordinates[3][1] = coordinates[2][1];
                        coordinates[0][1]--;
                        coordinates[1][1]++;
                        coordinates[3][0]++;
                    } case 2 -> {
                        coordinates[0][0] = coordinates[1][0] = coordinates[3][0] = coordinates[2][0];
                        coordinates[0][1] = coordinates[1][1] = coordinates[3][1] = coordinates[2][1];
                        coordinates[0][0]--;
                        coordinates[1][1]--;
                        coordinates[3][0]++;
                    } case 3 -> {
                        coordinates[0][0] = coordinates[1][0] = coordinates[3][0] = coordinates[2][0];
                        coordinates[0][1] = coordinates[1][1] = coordinates[3][1] = coordinates[2][1];
                        coordinates[0][1]--;
                        coordinates[1][0]--;
                        coordinates[3][1]++;
                    }
                }
            }

            case 6 -> {
                if (rot%2 == 0){
                    coordinates[0][0] = coordinates[1][0] = coordinates[3][0] = coordinates[2][0];
                    coordinates[0][1] = coordinates[1][1] = coordinates[3][1] = coordinates[2][1];
                    coordinates[0][0]--;
                    coordinates[1][1]++;
                    coordinates[3][0]++;
                    coordinates[3][1]++;
                } else {
                    coordinates[0][0] = coordinates[1][0] = coordinates[3][0] = coordinates[2][0];
                    coordinates[0][1] = coordinates[1][1] = coordinates[3][1] = coordinates[2][1];
                    coordinates[0][1]--;
                    coordinates[1][0]--;
                    coordinates[3][0]--;
                    coordinates[3][1]++;
                }
            }

            case 7 -> {
                if (rot%2 == 0){
                    coordinates[0][0] = coordinates[1][0] = coordinates[3][0] = coordinates[2][0];
                    coordinates[0][1] = coordinates[1][1] = coordinates[3][1] = coordinates[2][1];
                    coordinates[0][0]++;
                    coordinates[1][1]++;
                    coordinates[3][0]--;
                    coordinates[3][1]++;
                } else {
                    coordinates[0][0] = coordinates[1][0] = coordinates[3][0] = coordinates[2][0];
                    coordinates[0][1] = coordinates[1][1] = coordinates[3][1] = coordinates[2][1];
                    coordinates[0][0]--;
                    coordinates[0][1]--;
                    coordinates[1][0]--;
                    coordinates[3][1]++;
                }
            }
        }

        boolean notEnoughSpace = false;
        boolean outOfBoundaries = false;

        //Boundaries
        if (lowestRow() < 0 || highestRow() > 23 || lowestCol() < 0 || highestCol() > 11)
            outOfBoundaries = true;


        //Not enough space
        if (!outOfBoundaries){
            for (int i = 0; i < 4; i++){
                int x = coordinates[i][0];
                int y = coordinates[i][1];

                if (matrix[x][y] != 0 && !isCoord(new int[]{x,y})){
                    notEnoughSpace = true;
                    break;
                }
            }
        }

        //Cancel rotation
        if (outOfBoundaries || notEnoughSpace){
            for (int i = 0; i < 4; i++){
                coordinates[i][0] = oldCoord[i][0];
                coordinates[i][1] = oldCoord[i][1];
                rot--;
            }
        }

        rot++;
        if (rot > 3)
            rot = 0;
    }

    public int highestRow(){
        return Math.max(Math.max(coordinates[0][0],coordinates[1][0]),Math.max(coordinates[2][0],coordinates[3][0]));
    }

    public int lowestRow(){
        return Math.min(Math.min(coordinates[0][0],coordinates[1][0]),Math.min(coordinates[2][0],coordinates[3][0]));
    }

    public int highestCol(){
        return Math.max(Math.max(coordinates[0][1],coordinates[1][1]),Math.max(coordinates[2][1],coordinates[3][1]));
    }

    public int lowestCol(){
        return Math.min(Math.min(coordinates[0][1],coordinates[1][1]),Math.min(coordinates[2][1],coordinates[3][1]));
    }

    public boolean isCoord(int[] newC){
        for (int i = 0; i < 4; i++){
            if (coordinates[i][0] == newC[0] && coordinates[i][1] == newC[1])
                return true;
        } return false;
    }

    public boolean pieceUnder(int[][] matrix){
        for (int i = 0; i < 4; i++){
            int x = coordinates[i][0]+1;
            int y = coordinates[i][1];

            if (x == 24)
                return true;

            if (matrix[x][y] != 0 && !isCoord(new int[]{x,y})){
                return true;
            }
        } return false;
    }

    public int getNum() {return num;}
    public int[][] getCoordinates() {return coordinates;}
    public int[][] getOldCoord() {return oldCoord;}

}
