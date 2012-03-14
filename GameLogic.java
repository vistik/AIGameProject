
import java.util.*;

// http://www.overclockers.com/forums/showthread.php?t=554653
public class GameLogic implements IGameLogic {

    private int x = 0;
    private int y = 0;
    private int playerID;
    private int[][] board;
    private HashMap<String, Integer> memory;
    private HashMap<String, Integer> patterns;
    private int time;
    private int moves;
    private int depth;
    private Heuristics h;

    public GameLogic() {
    }

    public void initializeGame(int x, int y, int playerID) {
        this.x = x;
        this.y = y;
        this.playerID = playerID;
        this.board = new int[x][y];
        this.depth = 5;
        this.memory = new HashMap<String, Integer>();
        this.patterns = new HashMap<String, Integer>();;

        this.patterns.put("1111", 1000);
        this.patterns.put("1110", 9);
        this.patterns.put("0111", 9);
        this.patterns.put("1100", 3);
        this.patterns.put("0110", 3);
        this.patterns.put("0011", 3);
        this.patterns.put("1000", 1);
        this.patterns.put("0100", 1);
        this.patterns.put("0010", 1);
        this.patterns.put("0001", 1);
        
        Heuristics h = new Heuristics(); 
        this.h = h;

    }

    public Winner gameFinished() {

        int winner = Game.hasWinner(this.board);
        switch (winner) {
            case 1:
                return Winner.PLAYER1;
            case 2:
                return Winner.PLAYER2;
        }

        if (Game.isFull(this.board)) {
            return Winner.TIE;
        }
        return Winner.NOT_FINISHED;
    }

    public Winner gameFinished(int[][] state) {
        int winner = Game.hasWinner(state);
        switch (winner) {
            case 1:
                return Winner.PLAYER1;
            case 2:
                return Winner.PLAYER2;
        }

        if (Game.isFull(state)) {
            return Winner.TIE;
        }
        return Winner.NOT_FINISHED;
    }

    public void insertCoin(int column, int playerID) {
        if (this.board[column][0] != 0) {
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

    public int decideNextMove() {
        System.out.println("Player:" + playerID);
        System.out.println("Memory:" + this.memory.size());
        this.moves++;
        System.out.println("Turn:" + this.moves);
        System.out.println("Depth:" + this.depth);
        int oldmem = this.memory.size();
//        this.memory = new HashMap<String, Integer>();
        System.out.println("Starts on finding move");
        Stopwatch st = new Stopwatch();
        st.start();
        int nextMov = this.minmaxdecision(this.depth, this.playerID);
        st.stop();
        this.time += st.getElapsedTimeSecs();
        System.out.println("secs: " + st.getElapsedTimeSecs());
        System.out.println("secs /avg: " + this.time / this.moves);
        System.out.println("Memory:" + this.memory.size());
        System.out.println("New Memory:" + (this.memory.size() - oldmem));
        System.out.println("********************");
        return nextMov;
    }

    public ArrayList<int[][]> getPossibleActions(int playerID, int[][] state) {
        ArrayList<int[][]> actions = new ArrayList<int[][]>();
        ArrayList<int[][]> niceactions = new ArrayList<int[][]>();
        int[][] newState = null;
        for (int i = 0; i < this.x; i++) {
            int[][] s = Misc.copyDoblAr(state);
            newState = insertCoin(i, playerID, s);
            int h = this.h.heuristics(playerID, newState,this.patterns);
            actions.add(newState);


        }
        return actions;
    }

    public int minmaxdecision(int depth, int playerID) {
        ArrayList<int[][]> actions = null;
        actions = this.getPossibleActions(playerID, this.board);
        int mValue = 0;
        if (this.playerID == 1) {
            mValue = Integer.MIN_VALUE;
        } else {
            mValue = Integer.MAX_VALUE;
        }
        int mV;
        int i = 0;
        int primI = 0;
        int[][] mAction = null;
        for (int[][] a : actions) {
            if (a != null) {
                if (this.playerID == 1) {
                    mV = minValue(a, depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    if (mValue < mV) {
                        mValue = mV;
                        mAction = a;
                        primI = i;
                    }
                } else {
                    mV = maxValue(a, depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    if (mValue > mV) {
                        mValue = mV;
                        mAction = a;
                        primI = i;
                    }
                }
            }
            i++;
        }
        return primI;
    }

    public int maxValue(int[][] state, int level, int max, int min) {
        try {
            int v = ((Integer) memory.get(Misc.getHash(state))).intValue();
            return v;
        } catch (Exception e) {
        }
        int mValue;
        switch (this.gameFinished(state)) {
            case TIE:
                return 0;
            case PLAYER1:
                return 1000;
            case PLAYER2:
                return -1000;
        }
        if (level == 0) {
            return this.h.heuristics(1, state,this.patterns);
        } else {
            level--;
        }
        mValue = Integer.MIN_VALUE;
        ArrayList<int[][]> actions = null;
        actions = this.getPossibleActions(1, state);
        int[][] primI = null;
        for (int[][] a : actions) {
            if (a != null) {
                int mV = minValue(a, level, max, min);

                if (mValue < mV) {
                    mValue = mV;
                    primI = a;
                }

                if (mValue >= min) {
                    this.memory.put(Misc.getHash(a), mValue);
                    return mValue;
                }
                max = Math.max(max, mValue);



            }
        }
        if (primI != null) {
            this.memory.put(Misc.getHash(primI), mValue);
        }
        return mValue;
    }

    public int minValue(int[][] state, int level, int max, int min) {
        try {
            int v = ((Integer) memory.get(Misc.getHash(state))).intValue();
            return v;
        } catch (Exception e) {
        }
        int mValue;
        switch (this.gameFinished(state)) {
            case TIE:
                return 0;
            case PLAYER1:
                return 1000;
            case PLAYER2:
                return -1000;
        }
        if (level == 0) {
            return this.h.heuristics(2, state,this.patterns);
        } else {
            level--;
        }

        mValue = Integer.MAX_VALUE;

        ArrayList<int[][]> actions = null;
        actions = this.getPossibleActions(2, state);
        int[][] primI = null;
        for (int[][] a : actions) {
            if (a != null) {
                int mV = maxValue(a, level, max, min);
                if (mValue > mV) {
                    mValue = mV;
                    primI = a;
                }
                if (mValue <= max) {
                    this.memory.put(Misc.getHash(a), mValue);
                    return mValue;
                }
                min = Math.min(min, mValue);
            }

        }
        if (primI != null) {
            this.memory.put(Misc.getHash(primI), mValue);
        }
        return mValue;
    }

}
