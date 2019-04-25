public class Board {
    private final int GRID_SIZE = 8;
    private int[][] grid = new int[GRID_SIZE][GRID_SIZE];
    private int numberOfTokens = 0;
    private int winningPlayer = 0;

    public int getSize() {
        return GRID_SIZE;
    }

    public boolean isFull() {
        return numberOfTokens >= GRID_SIZE * GRID_SIZE;
    }

    public boolean isColumnFull(int x) {
        return grid[x][0] != 0;
    }

    public void dropToken(int player, int x) {
        // starting from the bottom look for an empty spot in the column
        for (int y = GRID_SIZE - 1; y >= 0; y--) {
            if (grid[x][y] == 0) {
                // place the user's token in this empty spot
                grid[x][y] = player;
                numberOfTokens++;

                // check if this is a winning move and remember it
                checkForWin(player, x, y);
                return;
            }
        }
    }

    public int getWinningPlayer() {
        return winningPlayer;
    }

    public String getToken(int player) {
        if (player == 1) {
            return "\uD83D\uDD34"; // 🔴
        } else if (player == 2) {
            return "\uD83D\uDD35"; // 🔵
        } else {
            return "  ";
        }
    }

    public String toString() {
        // 🔴 🔵 ┌ ┬ ┐ ├ ┼ ┤ └ ┴ ┘ │ ─

        String result = "\n";

        // column numbers
        for (int x = 0; x < GRID_SIZE; x++) {
            result += "  " + (x + 1);
        }
        result += "\n";

        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                if (y == 0) {
                    if (x == 0) {
                        result += "┌──";
                    } else {
                        result += "┬──";
                    }
                } else {
                    if (x == 0) {
                        result += "├──";
                    } else {
                        result += "┼──";
                    }
                }
            }

            if (y == 0) {
                result += "┐\n";
            } else {
                result += "┤\n";
            }

            for (int x = 0; x < GRID_SIZE; x++) {
                result += "│" + getToken(grid[x][y]);
            }
            result += "│\n";
        }

        for (int x = 0; x < GRID_SIZE; x++) {
            if (x == 0) {
                result += "└──";
            } else {
                result += "┴──";
            }
        }
        result += "┘\n";

        return result;
    }

    private int consecutiveTokensInDirection(int player, int x, int y, int dx, int dy) {
        int consecutiveTokens = 0;

        // check for up to three more same tokens in the specified direction
        for (int i = 1; i < 4; i++) {
            int checkX = x + i * dx;
            int checkY = y + i * dy;

            if (checkX < 0 || checkX >= GRID_SIZE || checkY < 0 || checkY >= GRID_SIZE) {
                // passed the edge of the grid
                break;
            }

            if (grid[checkX][checkY] != player) {
                // not the same token
                break;
            }

            // found same token - keep going
            consecutiveTokens++;
        }

        return consecutiveTokens;
    }

    private void checkForWin(int player, int x, int y) {
        if (winningPlayer != 0) {
            // a player has already won - don't check again
            return;
        }

        // starting with the specified token, check for three more tokens in a line

        // check to the left and to the right
        if (consecutiveTokensInDirection(player, x, y, 1, 0)
                + consecutiveTokensInDirection(player, x, y, -1, 0)
                >= 3) {
            winningPlayer = player;
            return;
        }

        // check diagonal /
        if (consecutiveTokensInDirection(player, x, y, 1, -1)
                + consecutiveTokensInDirection(player, x, y, -1, 1)
                >= 3) {
            winningPlayer = player;
            return;
        }

        // check diagonal \
        if (consecutiveTokensInDirection(player, x, y, -1, -1)
                + consecutiveTokensInDirection(player, x, y, 1, 1)
                >= 3) {
            winningPlayer = player;
            return;
        }

        // check below
        if (consecutiveTokensInDirection(player, x, y, 0, 1)
                >= 3) {
            winningPlayer = player;
            return;
        }
    }
}