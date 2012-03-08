
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
        this.insertCoin(1, 2);
        this.insertCoin(2, 1);
        this.insertCoin(1, 2);
        this.insertCoin(3, 1);
        this.insertCoin(1, 2);
        this.insertCoin(4, 1);
        this.printBoard();
        System.exit(0);
        //TODO Write your implementation for this method
    }

    public Winner gameFinished() {
        //TODO Write your implementation for this method
        return Winner.NOT_FINISHED;
    }

    public void insertCoin(int column, int playerID) {
        for (int i = 0; i < this.y; i++) {
//            System.out.print(column + "," + i + "\n");
//            System.out.println(this.board[column][i]);
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
        //TODO Write your implementation for this method	
    }

    public int decideNextMove() {
        //TODO Write your implementation for this method
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
}
