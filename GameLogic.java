
import java.util.*;

// http://www.overclockers.com/forums/showthread.php?t=554653
public class GameLogic implements IGameLogic {

    private int x = 0;
    private int y = 0;
    private int playerID;
    private int[][] board;
    private HashMap<String, Integer> memory;
    private HashMap<String, Integer> patterns;

    public GameLogic() {
        // TODO Write your implementation for this method
    }

    public void initializeGame(int x, int y, int playerID) {
        this.x = x;
        this.y = y;
        this.playerID = playerID;
        this.board = new int[x][y];
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

        // System.out.println(playerID);
        //
//        insertCoin(0, 1);
//        insertCoin(0, 1);
//        insertCoin(0, 1);
//        insertCoin(0, 2);
//
//
//        insertCoin(1, 2);
//        insertCoin(1, 2);
//        insertCoin(1, 1);
//        insertCoin(1, 1);
//
//        insertCoin(2, 1);
//        insertCoin(2, 2);
//        insertCoin(2, 2);
//
//
//        insertCoin(3, 2);
        //
        // insertCoin(this.minmaxdecision(-1,2), 2);
        // // insertCoin(this.minmaxdecision(-1,1), 1);
        // // insertCoin(this.minmaxdecision(-1,2), 2);
        // // insertCoin(this.minmaxdecision(-1,1), 1);
        // this.printBoard(this.board);
        // System.out.println("winner:" + this.hasWinner(this.board));
        // //
        // System.exit(0);
        // );
//        this.printBoard(board);
//        System.out.println("this.heuristicHorizontal(1, this.board)");
//        System.out.println(this.heuristicHorizontal(1, this.board));
//
//        System.out.println("this.heuristicHorizontal(2, this.board)");
//        System.out.println(this.heuristicHorizontal(2, this.board));
////
//        System.out.println("this.heuristicVertical(1, this.board)");
//        System.out.println(this.heuristicVertical(1, this.board));
//
//        System.out.println("this.heuristicVertical(2, this.board)");
//        System.out.println(this.heuristicVertical(2, this.board));

//        System.out.println(this.heuristics(1, this.board));
//        System.out.println("Unsort Map......");
//        Map<String, Integer> unsortMap = new HashMap<String, Integer>();
//        unsortMap.put("1", 1);
//        unsortMap.put("2", 44);
//        unsortMap.put("3", 2);
//        unsortMap.put("4", 1);
//        unsortMap.put("5", 188);
//        unsortMap.put("6", 6);
//        unsortMap.put("7", 4);
//        unsortMap.put("8", 2);
//
//        Iterator iterator = unsortMap.entrySet().iterator();
//
//        for (Map.Entry entry : unsortMap.entrySet()) {
//            System.out.println("Key : " + entry.getKey()
//                    + " Value : " + entry.getValue());
//        }
//
//        System.out.println("Sorted Map......");
//        Map<String, String> sortedMap = sortByComparator(unsortMap);
//
//        for (Map.Entry entry : sortedMap.entrySet()) {
//            System.out.println("Key : " + entry.getKey()
//                    + " Value : " + entry.getValue());
//        }
//        System.exit(0);
    }

    public String getHash(int[][] state) {
        if (state == null) {
            System.out.println("Det fejer");
        }
        String hash = "";
        for (int j = 0; j < this.y; j++) {
            for (int i = 0; i < this.x; i++) {
                hash = hash + "" + state[i][j] + "";
            }
        }
        return hash;
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
        System.out.println("Memory:" + this.memory.size());
        int oldmem = this.memory.size();
//        this.memory = new HashMap<String, Integer>();
        System.out.println("Starts on finding move");
        Stopwatch st = new Stopwatch();
        st.start();
        int nextMov = this.minmaxdecision(6, this.playerID);
        st.stop();
        System.out.println("secs: " + st.getElapsedTimeSecs());
        System.out.println("Memory:" + this.memory.size());
        System.out.println("New Memory:" + (this.memory.size() - oldmem));
        
        // System.out.println();
        return nextMov;
    }

    private static Map sortByComparator(Map unsortMap) {

        List list = new LinkedList(unsortMap.entrySet());

        //sort list based on comparator
        Collections.sort(list, new Comparator() {

            public int compare(Object o2, Object o1) {
                return ((Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry) (o2)).getValue());
            }
        });

        //put sorted list into map again
        Map sortedMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    public ArrayList<int[][]> getPossibleActions(int playerID, int[][] state) {
        ArrayList<int[][]> actions = new ArrayList<int[][]>();
//        Map<int[][], Integer> actions = new HashMap<int[][], Integer>();
        ArrayList<int[][]> niceactions = new ArrayList<int[][]>();
        int[][] newState = null;
        int hiscore = 0;
        if (playerID == 2) {
            hiscore = Integer.MIN_VALUE;
        } else {
            hiscore = Integer.MAX_VALUE;
        }

        for (int i = 0; i < this.x; i++) {
            int[][] s = this.copyDoblAr(state);
            newState = insertCoin(i, playerID, s);
            int h = this.heuristics(playerID, newState);
//            actions.put(newState, h);
            actions.add(newState);
//            if (playerID == 2) {
//                if (h > hiscore) {
//                    hiscore = h;
//                }
//            } else {
//                if (h < hiscore) {
//                    hiscore = h;
//                }
//            }


        }
//        Map<int[][], Integer> sortedMap = sortByComparator(actions);
//        int max = this.x - 2;
//        int i = 0;
//        for (Map.Entry entry : sortedMap.entrySet()) {
//            if (max < i){
//                break;
//            }
//            System.out.println("Key : " + entry.getKey()
//                    + " Value : " + entry.getValue());
//            niceactions.add((int[][])entry.getKey());
//            i++;
//        }

//        for (int[][] action : actions) {
//            int h = this.heuristics(playerID, action);
//            if (h == hiscore) {
//                niceactions.add(action);
//            }
//
//        }

//        System.out.println("nice:" + actions.size());
        return actions;
    }

    public int minmaxdecision(int depth, int playerID) {
        ArrayList<int[][]> actions = null;
        actions = this.getPossibleActions(playerID, this.board);
        // for (int[][] a : actions) {
        // this.printBoard(a);
        // }
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
                // this.printBoard(a);
                // System.out.println(mV);
            }
            i++;
        }
//        System.out.println(primI);
//        System.out.println("ngoeg");
//        this.printBoard(mAction);
        return primI;
    }

    public int maxValue(int[][] state, int level, int max, int min) {
//        System.out.println(memory.size());
        try {
            int v = ((Integer) memory.get(this.getHash(state))).intValue();
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
            return this.heuristics(1, state);
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
                    this.memory.put(this.getHash(a), mValue);
                    return mValue;
                }
                max = Math.max(max, mValue);



            }
        }
        if (primI != null) {
            this.memory.put(this.getHash(primI), mValue);
        }
        return mValue;
    }

    public int minValue(int[][] state, int level, int max, int min) {
        try {
            int v = ((Integer) memory.get(this.getHash(state))).intValue();
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
            return this.heuristics(2, state);
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
                    this.memory.put(this.getHash(a), mValue);
                    return mValue;
                }
                min = Math.min(min, mValue);
            }

        }
        if (primI != null) {
            this.memory.put(this.getHash(primI), mValue);
        }
        return mValue;
    }

    public int heuristicHorizontal(int playerID, int[][] state) {
        String hash = "";
        int value = 0;
        for (int j = 0; j < this.y; j++) {
            hash = "";
            for (int i = 0; i < this.x; i++) {
                hash = hash + "" + state[i][j] + "";
            }

            Iterator it = this.patterns.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry) it.next();

                String key = (String) pairs.getKey();
                key = key.replace("1", "" + playerID + "");
                if (hash.indexOf(key) >= 0) {
//                    System.out.println(key);
                    value += (Integer) pairs.getValue();
                }
            }
        }
        int factor;
        if (playerID == 2) {
            factor = -1;
        } else {
            factor = 1;
        }
        return value * factor;

    }

    public int heuristics(int playerID, int[][] state) {
        if (state == null) {
            return 0;
        }
        int opponent = 0;
        if (playerID == 1) {
            opponent = 2;
        } else {
            opponent = 1;
        }
        int us = this.heuristicVertical(playerID, state) + this.heuristicHorizontal(playerID, state);
        int them = this.heuristicVertical(opponent, state) + this.heuristicHorizontal(opponent, state);
        return us + them;
    }

    public int heuristicVertical(int playerID, int[][] state) {

        String hash = "";
        int value = 0;
        for (int j = 0; j < this.x; j++) {
            hash = "";
            for (int i = 0; i < this.y; i++) {
                hash = hash + "" + state[j][i] + "";
            }

            Iterator it = this.patterns.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry) it.next();

                String key = (String) pairs.getKey();
                key = key.replace("1", "" + playerID + "");
                if (hash.indexOf(key) >= 0) {
//                    System.out.println(key);
                    value += (Integer) pairs.getValue();
                }
            }
        }
        int factor;
        if (playerID == 2) {
            factor = -1;
        } else {
            factor = 1;
        }

        return value * factor;
    }
    
    public void heuristicsSouthEast(int playerID, int[][] state){
        for(int y = this.y; y >= 0; y--){
            
        }
    }

    public void printBoard(int[][] state) {
        if (state == null) {
            System.out.println("nUL!LL!L");
            return;
        }

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
            if (x > this.x - 1) {
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
            if (y > this.y - 1 || x > this.x - 1) {
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
            if (y < 0 || x > this.x - 1) {
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
            if (x > this.y - 1) {
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
