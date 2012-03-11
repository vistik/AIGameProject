
import java.util.ArrayList;
import java.util.Arrays;

// http://www.overclockers.com/forums/showthread.php?t=554653
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
////        int[][] board = ;
//        System.out.println(x);
//        System.out.println(y);

        this.board = new int[x][y];

//        for (int i = 0; i < x; i++) {
//            for (int j = 0; j < y; j++) {
//                this.board[i][j] = 0;
//            }
//        }
//        this.board = insertCoin(1, 1, this.board);
//        this.board = new int[x][y];
//        this.board = insertCoin(2, 2, this.board);
//        this.board = insertCoin(1, 1, this.board);
//        this.board = insertCoin(4, 2, this.board);
//        this.board = insertCoin(3, 1, this.board);
//        this.board = insertCoin(6, 2, this.board);
//        this.board = insertCoin(1, 1, this.board);
//        this.printBoard();
        this.getPossibleActions(2,this.board);
//        this.printBoard(insertCoin(0, 1,this.board));
//        this.printBoard(insertCoin(5, 1,this.board));
//        for (int[][] ises : states) {
//            System.out.println("");
//            this.printBoard(ises);
//        }
//        this.printBoard();
        System.exit(0);
                
        System.out.println("winner:" + this.hasWinner());
//        System.exit(0);
        //TODO Write your implementation for this method
    }

    public Winner gameFinished() {
        int winner = this.hasWinner();
        switch (winner) {
            case 1:
                return Winner.PLAYER1;

            case 2:
                return Winner.PLAYER2;

        }

        if (this.isFull()) {
            return Winner.TIE;
        }
        return Winner.NOT_FINISHED;
    }

    public void insertCoin(int column, int playerID) {
        for (int i = 0; i < this.y; i++) {
            if (this.board[column][i] != 0) {
                this.board[column][i - 1] = playerID;
                return;
            }

            if (this.y - 1 == i) {
                if (this.board[column][i] == 0) {
                    this.board[column][i] = playerID;
                    return;
                }
//                else {
//                    System.err.println("Move not allowed 1");
//                    System.exit(1);
//                }
            }
        }
    }

    public int[][] insertCoin(int column, int playerID, int[][] s) {
        System.out.println("insertCoin, called with:");
        int[][] state = null;
        state = Arrays.copyOf(s, s.length);
        this.printBoard(state);
        System.out.println("");
        for (int i = 0; i < this.y; i++) {
            if (state[column][i] != 0) {
                state[column][i - 1] = playerID;
                return state;
            }
            if (this.y - 1 == i) {
                if (state[column][i] == 0) {
                    state[column][i] = playerID;
                    return state;
                }    
            }
        }
        return state;
    }

    public void getPossibleActions(int playerID, int[][] s) {
    	
        for (int i = 0; i < this.x; i++) {
        	int[][] nyS = this.copyDoblAr(s);
            System.out.println("i:" + i);
            System.out.println("");
            this.printBoard(insertCoin(i, playerID, nyS));
            System.out.println("");
        }
    }

    public int decideNextMove() {

        return 0;
    }

    public int maxValue(int[][] state) {
        System.out.println("Max turn:");
        this.printBoard();
        int mValue;
        switch (this.gameFinished()) {
            case TIE:
                return 0;
            case PLAYER1:
                return 1;
            case PLAYER2:
                return -1;
        }
        mValue = Integer.MIN_VALUE;
        for (int i = 0; i < state.length - 1; i++) {
            if (state[i][0] == 0) {
                int mV = minValue(insertCoin(i, 1, state));
                if (mValue < mV) {
                    mValue = mV;
                }
                //mValue = Max(mValue, minValue(insertCoin(i, 2, state))
                //Result skal s�tte en coin i den p�g�ldende columne i
            }
        }
        System.out.println("Choice:" + mValue);
        return mValue;
    }

    public int minValue(int[][] state) {
        System.out.println("Min turn:");
        this.printBoard();
        int mValue;
        switch (this.gameFinished()) {
            case TIE:
                return 0;
            case PLAYER1:
                return 1;
            case PLAYER2:
                return -1;
        }

        mValue = Integer.MAX_VALUE;
        for (int i = 0; i < state.length - 1; i++) {
            if (state[i][0] == 0) {
                int mV = maxValue(insertCoin(i, 2, state));
                if (mValue > mV) {
                    mValue = mV;
                }
                //mValue = MIN(mValue, maxValue(insertCoin(i, 2, state))
                //Result skal s�tte en coin i den p�g�ldende columne i
            }
        }
        System.out.println("Choice:" + mValue);
        return mValue;
    }

    public void printBoard() {
        for (int j = 0; j < this.y; j++) {
            for (int i = 0; i < this.x; i++) {
                System.out.print(this.board[i][j] + " ");
            }
            System.out.print("\n");
        }

    }

    public void printBoard(int[][] state) {
        for (int j = 0; j < this.y; j++) {
            for (int i = 0; i < this.x; i++) {
                System.out.print(state[i][j] + " ");
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
        if (color == 0) {
            return false;
        }
        int y = c;
        int x = r;
        for (int k = 0; k < 4; k++) {
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
        if (color == 0) {
            return false;
        }
        int y = c;
        int x = r;
        for (int k = 0; k < 4; k++) {
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
        if (color == 0) {
            return false;
        }
        for (int x = r; x < r + 4; x++) {
            if (x > 5) {
                return false;
            } else if (this.board[c][x] != color) {
                return false;
            }
        }
        return true;
    }

    public int hasWinner() {
        boolean hasFour = false;
        for (int c = 0; c < this.x; c++) {
            for (int r = 0; r < this.y; r++) {
                if (hasFourSouth(c, r)) {
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

    public boolean isFull() {
        for (int c = 0; c < 7; c++) {
            for (int r = 0; r < 6; r++) {
                if (this.board[c][r] == 0) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public int[][] copyDoblAr(int[][] org){
    	int[][] copy = new int[org.length][org[0].length];
    		for(int y=0;y<org.length;y++){
    			for(int x=0;x<org[y].length;x++){
    				copy[y][x] = org[y][x];
    			}
    			
    		}
    	return copy;
    }
    
}