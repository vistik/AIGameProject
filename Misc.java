public class Misc {
    
    /**
     * Print the given state
     * @param state 
     */

    public static void printBoard(int[][] state) {
        if (state == null) {
            System.out.println("nUL!LL!L");
            return;
        }
        int y = state[0].length;
        int x = state.length;
        for (int j = 0; j < y; j++) {
            for (int i = 0; i < x; i++) {
                System.out.print(state[i][j] + " ");
            }
            System.out.print("\n");
        }

    }
    
    /**
     * generate hash of the given state
     * @param state
     * @return 
     */

    public static String getHash(int[][] state) {
        String hash = "";
        int y = state[0].length;
        int x = state.length;
        for (int j = 0; j < y; j++) {
            for (int i = 0; i < x; i++) {
                hash = hash + "" + state[i][j] + "";
            }
        }
        return hash;
    }
    
    /**
     * copy a int[][] and return the copy
     * @param org
     * @return 
     */

    public static int[][] copyDoblAr(int[][] org) {
        int[][] copy = new int[org.length][org[0].length];
        for (int y = 0; y < org.length; y++) {
            for (int x = 0; x < org[y].length; x++) {
                copy[y][x] = org[y][x];
            }

        }
        return copy;
    }
}
