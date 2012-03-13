public class Game {
    public static int hasWinner(int[][] state) {
        boolean hasFour = false;
        int y = state[0].length;
        int x = state.length;
        for (int c = 0; c < x; c++) {
            for (int r = 0; r < y; r++) {
                if (Game.hasFourSouth(c, r, state)) {
                    return state[c][r];
                }
                if (Game.hasFourEast(c, r, state)) {
                    return state[c][r];
                }
                if (Game.hasFourSouthEast(c, r, state)) {
                    return state[c][r];
                }
                if (Game.hasFourSouthWest(c, r, state)) {
                    return state[c][r];
                }
            }
        }
        return 0;
    }

    private static boolean hasFourSouthEast(int c, int r, int[][] state) {
        int color = state[c][r];
        if (color == 0) {
            return false;
        }
        int y = c;
        int x = r;
        int thisy = state[0].length;
        int thisx = state.length;
        for (int k = 0; k < 4; k++) {
            if (y > thisy - 1 || x > thisx - 1) {
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

    private static boolean hasFourSouthWest(int c, int r, int[][] state) {
        int color = state[c][r];
        if (color == 0) {
            return false;
        }
        int y = c;
        int x = r;
        int thisy = state[0].length;
        int thisx = state.length;
        for (int k = 0; k < 4; k++) {
            if (y < 0 || x > thisx - 1) {
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

    private static boolean hasFourSouth(int c, int r, int[][] state) {
        int color = state[c][r];
        if (color == 0) {
            return false;
        }
        int thisy = state[0].length;
        int thisx = state.length;
        for (int x = r; x < r + 4; x++) {
            if (x > thisy - 1) {
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

    public static boolean isFull(int[][] state) {
        int y = state[0].length;
        int x = state.length;
        for (int c = 0; c < x; c++) {
            for (int r = 0; r < y; r++) {
                if (state[c][r] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean hasFourEast(int c, int r, int[][] state) {
        int color = state[c][r];
        if (color == 0) {
            return false;
        }
        int thisy = state[0].length;
        int thisx = state.length;
        for (int x = c; x < c + 4; x++) {
            if (x > thisx - 1) {
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
}
