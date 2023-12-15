package com.gameoflife.model;

import com.gameoflife.util.BoardPrinter;
import com.gameoflife.util.LogConfiguration;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class represents the Game of Life.
 * @see <a href="https://en.wikipedia.org/w/index.php?title=Conway%27s_Game_of_Life">Conway's Game of Life</a>
 */
public class GameOfLife {

    private final int size;
    private boolean[][] board;
    private boolean[][] nextBoard;
    private static final boolean SLEEP = true; // set to false to disable sleep
    private static final int SLEEP_TIME = 200; // milliseconds

    /**
     * This constructor takes in a {@code int}
     * and creates a board of size {@code size x size}
     * @param size
     *        The size of the board as an {@code int}
     * @see GameOfLife#initializeBoard()
     */
    public GameOfLife(int size) {
        this.size = size;
        board = new boolean[size][size];
        nextBoard = new boolean[size][size];
        initializeBoard();
    }

    /**
     * This is the main method of the program.
     * It creates a {@code GameOfLife} object
     * and starts the game.
     * @see LogConfiguration#configure()
     * @see GameOfLife#GameOfLife(int)
     * @see GameOfLife#startGame()
     * @param args
     *       The command line arguments as a {@code String[]}
     */
    public static void main(String[] args) {
        LogConfiguration.configure();
        GameOfLife game = new GameOfLife(10);
        game.startGame();
    }

    /**
     * This method starts the game.
     * It creates a {@code ExecutorService} with a fixed thread pool of 2 threads.
     * The first thread is responsible for printing the board and updating the board.
     * The second thread is responsible for sleeping for 200 milliseconds.
     * @see BoardPrinter#printBoard(boolean[][], int)
     * @see GameOfLife#updateBoard()
     */
    private void startGame() {
        try (ExecutorService executorService = Executors.newFixedThreadPool(2)) {
            executorService.submit(() -> {
                for (int generation = 1; generation <= 100; generation++) {
                    Logger.getLogger(GameOfLife.class.getName()).log(Level.INFO, "Generation {0}:", generation);
                    printCurrentBoard();
                    updateBoard();
                    if (SLEEP) {
                        try {
                            TimeUnit.MILLISECONDS.sleep(SLEEP_TIME);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            Logger.getLogger(GameOfLife.class.getName()).log(Level.SEVERE, "InterruptedException occurred", e);
                        }
                    }
                }
            });
        }
    }

    /**
     * This method initializes the board.
     * It sets the board to the following pattern:
     * <pre>
     *     ⬛ ⬛ ⬛ ⬛ ⬛ ⬛ ⬛ ⬛ ⬛ ⬛
     *     ⬛ ⬛ 🟨 ⬛ ⬛ ⬛ ⬛ ⬛ ⬛ ⬛
     *     ⬛ ⬛ ⬛ 🟨 ⬛ ⬛ ⬛ ⬛ ⬛ ⬛
     *     ⬛ 🟨 🟨 🟨 ⬛ ⬛ ⬛ ⬛ ⬛ ⬛
     *     ⬛ ⬛ ⬛ ⬛ ⬛ ⬛ ⬛ ⬛ ⬛ ⬛
     *     ⬛ ⬛ ⬛ ⬛ ⬛ ⬛ ⬛ ⬛ ⬛ ⬛
     *     ⬛ ⬛ ⬛ ⬛ ⬛ ⬛ ⬛ ⬛ ⬛ ⬛
     *     ⬛ ⬛ ⬛ ⬛ ⬛ ⬛ ⬛ ⬛ ⬛ ⬛
     *     ⬛ ⬛ ⬛ ⬛ ⬛ ⬛ ⬛ ⬛ ⬛ ⬛
     *     ⬛ ⬛ ⬛ ⬛ ⬛ ⬛ ⬛ ⬛ ⬛ ⬛
     * </pre>
     *
     * This pattern is known as the Glider pattern.
     * @see <a href="https://conwaylife.com/wiki/Glider">Glider Pattern on LifeWiki</a>
     * @see <a href="https://en.wikipedia.org/w/index.php?title=Conway%27s_Game_of_Life&oldid=1010654352#Examples_of_patterns">Examples of patterns</a>
     */
    private void initializeBoard() {
        board[1][2] = true;
        board[2][3] = true;
        board[3][1] = true;
        board[3][2] = true;
        board[3][3] = true;
    }

    /**
     * This method updates the board.
     * It applies the rules of the game to each cell in the board.
     * @see GameOfLife#countLiveNeighbors(int, int)
     * @see GameOfLife#applyRules(int, int, int)
     */
    private void updateBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int liveNeighbors = countLiveNeighbors(i, j);
                applyRules(i, j, liveNeighbors);
            }
        }

        if (Arrays.deepEquals(board, nextBoard)) {
            Logger.getLogger(GameOfLife.class.getName()).log(Level.INFO, "Game over!");
            System.exit(0);
        }

        boolean[][] temp = board;
        board = nextBoard;
        nextBoard = temp;
    }

    /**
     * This method counts the number of live neighbors for a given cell.
     * @param row The row of the cell as an {@code int}
     * @param col The column of the cell as an {@code int}
     * @return The number of live neighbors for the given cell
     * @see GameOfLife#isValidCell(int, int)
     */
    private int countLiveNeighbors(int row, int col) {
        int liveNeighbors = 0;

        int[][] neighbors = {
                {-1, -1}, {-1, 0}, {-1, 1},
                { 0, -1},          { 0, 1},
                { 1, -1}, { 1, 0}, { 1, 1}
        };

        for (int[] neighbor : neighbors) {
            int neighborRow = row + neighbor[0];
            int neighborCol = col + neighbor[1];

            if (isValidCell(neighborRow, neighborCol) && board[neighborRow][neighborCol]) {
                liveNeighbors++;
            }
        }

        return liveNeighbors;
    }

    /**
     * This method checks if the given cell is valid.
     * @param row The row of the cell as an {@code int}
     * @param col The column of the cell as an {@code int}
     * @return {@code true} if the cell is valid, {@code false} otherwise
     */
    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size;
    }

    /**
     * This method applies the rules of the game to the given cell.
     * @param row The row of the cell as an {@code int}
     * @param col The column of the cell as an {@code int}
     * @param liveNeighbors The number of live neighbors for the given cell
     */
    private void applyRules(int row, int col, int liveNeighbors) {
        if (board[row][col]) {
            nextBoard[row][col] = liveNeighbors >= 2 && liveNeighbors <= 3;
        } else {
            nextBoard[row][col] = liveNeighbors == 3;
        }
    }

    /**
     * This method prints the current board.
     * @see BoardPrinter#printBoard(boolean[][], int)
     */
    private void printCurrentBoard() {
        BoardPrinter.printBoard(board, size);
    }
}
