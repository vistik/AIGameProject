
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
        this.board = new int[x][y];
//        ArrayList<int[][]> actions = null;
//        actions = this.getPossibleActions(2, this.board);
//        for (int[][] ises : actions) {
//            this.printBoard(ises);
//            System.out.println("");
//        
//        this.minmaxdecision(1);
//        System.out.println(this.getRandomMove());
        insertCoin(1, 1);
        insertCoin(2, 2);
        insertCoin(3, 1);
        insertCoin(0, 2);
        insertCoin(1, 1);
        insertCoin(2, 2);
        insertCoin(3, 1);
        insertCoin(0, 2);
        insertCoin(1, 1);
        insertCoin(2, 2);
        insertCoin(3, 1);
        insertCoin(0, 2);
        insertCoin(1, 1);
        insertCoin(2, 2);
        insertCoin(3, 1);
//        
//        for (int i = 0; i < 42; i++) {
//            (i ^ 7);
//            System.out.println(Math.pow(16,7));
//        }
        this.printBoard(board);
        this.hasWinner(this.board);
        System.exit(0);

        System.out.println("winner:" + this.hasWinner(this.board));
    }

    public Winner gameFinished() {

        int winner = this.hasWinner(this.board);
        switch (winner) {
            case 1:
                return Winner.PLAYER1;

            case 2:
                return Winner.PLAYER2;

        }

        if (this.isFull(this.board)) {
            return Winner.TIE;
        }
        return Winner.NOT_FINISHED;
    }

    public Winner gameFinished(int[][] state) {

        int winner = this.hasWinner(state);
        switch (winner) {
            case 1:
                return Winner.PLAYER1;

            case 2:
                return Winner.PLAYER2;

        }

        if (this.isFull(state)) {
            return Winner.TIE;
        }
        return Winner.NOT_FINISHED;
    }

    public void insertCoin(int column, int playerID) {
        if (this.board[column][0] != 0){
            return;
        }
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
            }
        }
    }

    public int[][] insertCoin(int column, int playerID, int[][] state) {
        if (state[column][0] != 0) {
            return null;
        }
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

    public ArrayList<int[][]> getPossibleActions(int playerID, int[][] state) {
        ArrayList<int[][]> actions = new ArrayList<int[][]>();
        int[][] newState = null;
        for (int i = 0; i < this.x; i++) {
            int[][] s = this.copyDoblAr(state);
            newState = insertCoin(i, playerID, s);
            if (newState != null) {
                actions.add(newState);
            }
        }
        return actions;
    }

    public int decideNextMove() {

        return 0;
    }
    
    public void minmaxdecision(int playerID){
        ArrayList<int[][]> actions = null;
        actions = this.getPossibleActions(playerID, this.board);
        int mValue = Integer.MIN_VALUE;
        int mV;
        int[][] mAction = null;
        for (int[][] a : actions) {
            mV = minValue(a);
            if (mValue < mV) {
                mValue = mV;
                mAction = a;
            }
            this.printBoard(mAction);
            System.out.println(mValue);

        }
        this.printBoard(mAction);
    }

    public int maxValue(int[][] state) {
//        System.out.println("Max turn:");
//        this.printBoard(this.board);
        int mValue;
        switch (this.gameFinished(state)) {
            case TIE:
                return 0;
            case PLAYER1:
                return 1;
            case PLAYER2:
                return -1;
        }
        mValue = Integer.MIN_VALUE;
//        for (int i = 0; i < state.length - 1; i++) {
//            if (state[i][0] == 0) {
//                int mV = minValue(insertCoin(i, 1, state));
//                if (mValue < mV) {
//                    mValue = mV;
//                }
//                //mValue = Max(mValue, minValue(insertCoin(i, 2, state))
//                //Result skal s�tte en coin i den p�g�ldende columne i
//            }
//        }
        ArrayList<int[][]> actions = null;
        actions = this.getPossibleActions(1, state);
        for (int[][] a : actions) {
            int mV = minValue(a);
            if (mValue < mV) {
                mValue = mV;
            }

        }
//        System.out.println("Choice:" + mValue);
        return mValue;
    }

    public int minValue(int[][] state) {
//        System.out.println("Min turn:");
//        this.printBoard(this.board);
        int mValue;
        switch (this.gameFinished(state)) {
            case TIE:
                return 0;
            case PLAYER1:
                return 1;
            case PLAYER2:
                return -1;
        }

        mValue = Integer.MAX_VALUE;
//        for (int i = 0; i < state.length - 1; i++) {
//            if (state[i][0] == 0) {
//                int mV = maxValue(insertCoin(i, 2, state));
//                if (mValue > mV) {
//                    mValue = mV;
//                }
//                //mValue = MIN(mValue, maxValue(insertCoin(i, 2, state))
//                //Result skal s�tte en coin i den p�g�ldende columne i
//            }
//        }
        ArrayList<int[][]> actions = null;
        actions = this.getPossibleActions(2, state);
        for (int[][] a : actions) {
            int mV = maxValue(a);
            if (mValue > mV) {
                mValue = mV;
            }

        }
//        System.out.println("Choice:" + mValue);
        return mValue;
    }

    public void printBoard(int[][] state) {
        for (int j = 0; j < this.y; j++) {
            for (int i = 0; i < this.x; i++) {
                System.out.print(state[i][j] + " ");
            }
            System.out.print("\n");
        }

    }

    private boolean hasFourEast(int c, int r, int[][] state) {
        int color = state[c][r];
        if (color == 0) {
            return false;
        }
        for (int x = c; x < c + 4; x++) {
            if (x > this.x -1){
                return false;
            }
            if (x > 6) {
                return false;
            } else if (state[x][r] != color) {
                return false;
            }
        }
        return true;
    }

    private boolean hasFourSouthEast(int c, int r, int[][] state) {
        int color = state[c][r];
        if (color == 0) {
            return false;
        }
        int y = c;
        int x = r;
        for (int k = 0; k < 4; k++) {
            if (y > this.y -1 || x > this.x -1){
                return false;
            }
            if ((y > 6) || (x > 5)) {
                return false;
            }
            if (state[y][x] != color) {
                return false;
            }
            y++;
            x++;
        }
        return true;
    }

    private boolean hasFourSouthWest(int c, int r, int[][] state) {
        int color = state[c][r];
        if (color == 0) {
            return false;
        }
        int y = c;
        int x = r;
        for (int k = 0; k < 4; k++) {
            if (y < 0 || x > this.x -1){
                return false;
            }
            if ((y < 0) || (x > 5)) {
                return false;
            }
            if (state[y][x] != color) {
                return false;
            }
            y--;
            x++;
        }
        return true;
    }

    private boolean hasFourSouth(int c, int r, int[][] state) {
        int color = state[c][r];
        if (color == 0) {
            return false;
        }
        for (int x = r; x < r + 4; x++) {
            if (x > this.y -1){
                return false;
            }
            if (x > 5) {
                return false;
            } else if (state[c][x] != color) {
                return false;
            }
        }
        return true;
    }

    public int hasWinner(int[][] state) {
        boolean hasFour = false;
        for (int c = 0; c < this.x; c++) {
            for (int r = 0; r < this.y; r++) {
                if (hasFourSouth(c, r, state)) {
                    return state[c][r];
                }
                if (hasFourEast(c, r, state)) {
                    return state[c][r];
                }
                if (hasFourSouthEast(c, r, state)) {
                    return state[c][r];
                }
                if (hasFourSouthWest(c, r, state)) {
                    return state[c][r];
                }
            }
        }
        return 0;
    }

    public boolean isFull(int[][] state) {
        for (int c = 0; c < this.x; c++) {
            for (int r = 0; r < this.y; r++) {
                if (state[c][r] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public int[][] copyDoblAr(int[][] org) {
        int[][] copy = new int[org.length][org[0].length];
        for (int y = 0; y < org.length; y++) {
            for (int x = 0; x < org[y].length; x++) {
                copy[y][x] = org[y][x];
            }

        }
        return copy;
    }
}