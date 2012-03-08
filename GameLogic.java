
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameLogic implements IGameLogic {

    private int x = 0;
    private int y = 0;
    private int playerID;
    private int[][] board;

    public GameLogic() {
        //TODO Write your implementation for this method
    }

    public void initializeGame(int x, int y, int playerID) {
        this.x = x;
        this.y = y;
        this.playerID = playerID;
//        int[][] board = ;
        System.out.println(x);
        System.out.println(y);

        this.board = new int[x][y];

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                this.board[i][j] = 0;
            }
        }
//        this.board[1][2] = 1;
        this.insertCoin(1, 1);
        this.insertCoin(2, 1);
        this.insertCoin(1, 1);
        this.insertCoin(3, 1);
        this.insertCoin(1, 1);
        this.insertCoin(4, 1);
        this.insertCoin(1, 1);
        this.printBoard();
        System.out.println("winner:" + this.hasWinner());
        System.exit(0);
        //TODO Write your implementation for this method
    }

    public Winner gameFinished() {
        //TODO Write your implementation for this method
        return Winner.NOT_FINISHED;
    }

    public void insertCoin(int column, int playerID) {
        for (int i = 0; i < this.y; i++) {
            if (this.board[column][i] != 0) {
                this.board[column][i - 1] = playerID;
                break;
            }

            if (this.y - 1 == i) {
                if (this.board[column][i] == 0) {
                    this.board[column][i] = playerID;
                } else {
                    System.err.println("Move not allowed");
                    System.exit(1);
                }
            }
        }
    }


    public int decideNextMove() {
        //TODO Write your implementation for this method
    	//HEr skal ske noget!!!
        return 0;
    }

    public void printBoard() {
        for (int j = 0; j < this.y; j++) {
            for (int i = 0; i < this.x; i++) {
                System.out.print(this.board[i][j] + " ");
            }
            System.out.print("\n");
        }

    }

    private boolean hasFourEast(int c, int r) {
        int color = this.board[c][r];
        if (color == 0) {
            return false;
        }
        for (int x = c; x < c + 4; x++) {
            if (x > 6) {
                return false;
            } else if (this.board[x][r] != color) {
                return false;
            }
        }
        return true;
    }

    private boolean hasFourSouthEast(int c, int r) {
        int color = this.board[c][r];
        //System.out.printf("SOUTHEAST: matt[%d][%d] is %d\n",c,r,color);
        if (color == 0) {
            return false;
        }
        int y = c;
        int x = r;
        for (int k = 0; k < 4; k++) {
            //System.out.printf("\tSOUTHEAST Checking: matt[%d][%d]\n",y,x);
            if ((y > 6) || (x > 5)) {
                return false;
            }
            if (this.board[y][x] != color) {
                return false;
            }
            y++;
            x++;
        }
        return true;
    }

    private boolean hasFourSouthWest(int c, int r) {
        int color = this.board[c][r];
        //System.out.printf("SOUTHWEST: matt[%d][%d] is %d\n",c,r,color);
        if (color == 0) {
            return false;
        }
        int y = c;
        int x = r;
        for (int k = 0; k < 4; k++) {
            //System.out.printf("\tSOUTHWEST Checking: matt[%d][%d]\n",y,x);
            if ((y < 0) || (x > 5)) {
                return false;
            }
            if (this.board[y][x] != color) {
                return false;
            }
            y--;
            x++;
        }
        return true;
    }

    private boolean hasFourSouth(int c, int r) {
        int color = this.board[c][r];
        //System.out.printf("SOUTH: matt[%d][%d] is %d\n",c,r,color);
        if (color == 0) {
            return false;
        }
        for (int x = r; x < r + 4; x++) {
            ///	System.out.printf("\tSOUTH Checking: matt[%d][%d]\n",c,x);
            if (x > 5) {
                return false;
            } else if (this.board[c][x] != color) {
                return false;
            }
        }
        //	System.out.printf("FOURINAROW_SOUTH: %d\n",color);
        return true;
    }

    public int hasWinner() {
        boolean hasFour = false;
        for (int c = 0; c < this.x; c++) {
            for (int r = 0; r < this.y; r++) {
                if (hasFourSouth(c, r)) {
                    //System.out.printf("hasWinner is TRUE at mat[%d][%d] color: %d\n",c,r,matt[c][r]);
                    return this.board[c][r];
                }
                if (hasFourEast(c, r)) {
                    return this.board[c][r];
                }
                if (hasFourSouthEast(c, r)) {
                    return this.board[c][r];
                }
                if (hasFourSouthWest(c, r)) {
                    return this.board[c][r];
                }
            }
        }
        return 0;
    }
}
