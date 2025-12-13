package gol;

public class GameOfLife implements Board {

    // Integers: 0 or 1 for alive or dead
    private int[][] board;

    // Constructor
    public GameOfLife(int x, int y)
    {
        // Construct a 2D array of the given size
        board = new int[x][y];
    }

    // Set values on the board
    public void set(int x, int y, int[][] data) {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                board[i + x][j + y] = data[i][j];
            }
        }
    }

    // Run the simulation for a number of turns
    public void run(int turns) {
        for (int i = 0; i < turns; i++) {
            step();
        }
    }

    // Step the simulation forward one turn
    public void step()
    {
        print();

        int xLimit = board.length;
        int yLimit = board[0].length;

        // Temporary board for simultaneous updates
        int[][] nextBoard = new int[xLimit][yLimit];

        for (int x = 0; x < xLimit; x++) {
            for (int y = 0; y < yLimit; y++) {

                int neighbors = countNeighbors(x, y);

                if (board[x][y] == 1) {
                    // Live cell rules
                    if (neighbors < 2 || neighbors > 3) {
                        nextBoard[x][y] = 0;
                    } else {
                        nextBoard[x][y] = 1;
                    }
                } else {
                    // Dead cell rule
                    if (neighbors == 3) {
                        nextBoard[x][y] = 1;
                    } else {
                        nextBoard[x][y] = 0;
                    }
                }
            }
        }

        board = nextBoard;
    }

    // Count neighbors
    public int countNeighbors(int x, int y) {
        int count = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {

                // Skip the cell itself
                if (i == 0 && j == 0) {
                    continue;
                }

                count += get(x + i, y + j);
            }
        }

        return count;
    }

    // Get a value from the board with wrap-around
    public int get(int x, int y) {
        int xLimit = board.length;
        int yLimit = board[0].length;
        return board[(x + xLimit) % xLimit][(y + yLimit) % yLimit];
    }

    // Test helper
    public int[][] get()
    {
        return board;
    }

    // Print the board
    public void print(){
        System.out.print("\n ");
        for (int y = 0; y < board[0].length; y++) {
            System.out.print(y % 10 + " ");
        }

        for (int x = 0; x < board.length; x++) {
            System.out.print("\n" + x % 10);
            for (int y = 0; y < board[x].length; y++)
            {
                if (board[x][y] == 1)
                {
                    System.out.print("⬛");
                }
                else
                {
                    System.out.print("⬜");
                }
            }
        }
        System.out.println();
    }
}
