package com.gameoflife.util;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class to print the board.
 * @see com.gameoflife.model.GameOfLife
 */
public class BoardPrinter {

    /**
     * This constructor is private to prevent instantiation.
     * @throws AssertionError
     *        If this constructor is called
     *        it throws an {@code AssertionError}
     *        because this constructor should never be called.
     *        This class should only be used statically.
     */
    private BoardPrinter() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    /**
     * This method prints the board.
     * @param board
     *        The board to be printed as a {@code boolean[][]}
     * @param size
     *        The size of the board as an {@code int}
     */
    public static void printBoard(boolean[][] board, int size) {
        StringBuilder boardString = new StringBuilder(System.lineSeparator());
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                boardString.append(board[i][j] ? "🟨 " : "⬛ ");
            }
            boardString.append(System.lineSeparator());
        }
        Logger.getLogger(BoardPrinter.class.getName()).log(Level.INFO, boardString.toString());
    }
}
