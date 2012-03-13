
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Heuristics {
     public static int heuristicHorizontal(int playerID, int[][] state, HashMap<String, Integer> patterns) {
        String hash = "";
        int value = 0;
        int y = state[0].length;
        int x = state.length;
        for (int j = 0; j < y; j++) {
            hash = "";
            for (int i = 0; i < x; i++) {
                hash = hash + "" + state[i][j] + "";
            }

            Iterator it = patterns.entrySet().iterator();
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

    public static int heuristics(int playerID, int[][] state, HashMap<String, Integer> patterns) {
        if (state == null) {
            return 0;
        }
        int opponent = 0;
        if (playerID == 1) {
            opponent = 2;
        } else {
            opponent = 1;
        }
        int us = Heuristics.heuristicVertical(playerID, state,patterns) + Heuristics.heuristicHorizontal(playerID, state,patterns) + Heuristics.heuristicsSouthEast(playerID, state,patterns) + Heuristics.heuristicsSouthWest(playerID, state,patterns);
        int them = Heuristics.heuristicVertical(opponent, state,patterns) + Heuristics.heuristicHorizontal(opponent, state,patterns)+ Heuristics.heuristicsSouthEast(opponent, state,patterns) + Heuristics.heuristicsSouthWest(opponent, state,patterns);
        return us + them;
    }

    public static int heuristicVertical(int playerID, int[][] state, HashMap<String, Integer> patterns) {

        String hash = "";
        int value = 0;
        int y = state[0].length;
        int x = state.length;
        for (int j = 0; j < x; j++) {
            hash = "";
            for (int i = 0; i < y; i++) {
                hash = hash + "" + state[j][i] + "";
            }

            Iterator it = patterns.entrySet().iterator();
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

    public static int heuristicsSouthWest(int playerID, int[][] state, HashMap<String, Integer> patterns) {
        int value = 0;
        int thisy = state[0].length;
        int thisx = state.length;
        for (int z = 0; z < thisx; z++) {
            int x = z;
            int y = 0;
            String hash = "";
            do {
                hash += state[x][y];
                x--;
                y++;

            } while (x >= 0 && y < thisy);
//            System.out.println(hash);
            Iterator it = patterns.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry) it.next();

                String key = (String) pairs.getKey();
                key = key.replace("1", "" + playerID + "");
                if (hash.indexOf(key) >= 0) {
                    value += (Integer) pairs.getValue();
                }
            }
        }
        for (int z = 1; z < thisy; z++) {
            int x = thisx - 1;
            int y = z;
            String hash = "";
            do {
                hash += state[x][y];
//                System.out.println("x:" + x + " y: " + y);
                x--;
                y++;

            } while (x >= 0 && y < thisy);
//            System.out.println(hash);
            Iterator it = patterns.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry) it.next();

                String key = (String) pairs.getKey();
                key = key.replace("1", "" + playerID + "");
                if (hash.indexOf(key) >= 0) {
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

    public static int heuristicsSouthEast(int playerID, int[][] state, HashMap<String, Integer> patterns) {
        int value = 0;
        int thisy = state[0].length;
        int thisx = state.length;
        for (int z = thisy - 1; z >= 0; z--) {
            int x = 0;
            int y = z;
            String hash = "";
            do {
                hash += state[x][y];
                x++;
                y++;

            } while (x < thisx && y < thisy);
//            System.out.println(hash);
            Iterator it = patterns.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry) it.next();

                String key = (String) pairs.getKey();
                key = key.replace("1", "" + playerID + "");
                if (hash.indexOf(key) >= 0) {
                    value += (Integer) pairs.getValue();
                }
            }
        }
        for (int z = 1; z < thisx; z++) {
            int x = z;
            int y = 0;
            String hash = "";
            do {
                hash += state[x][y];
                x++;
                y++;

            } while (x < thisx && y < thisy);
//            System.out.println(hash);
            Iterator it = patterns.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry) it.next();

                String key = (String) pairs.getKey();
                key = key.replace("1", "" + playerID + "");
                if (hash.indexOf(key) >= 0) {
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
}
