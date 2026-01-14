package main.pij.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the Scrabble game board.
 */
public class Board {
    private final int size;
    private final Cell[][] board;
    private int[] startPosition;


    public Board(int size) {
        this.size = size;
        this.board = new Cell[size][size];

        // Initialize the board with empty cells
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                board[row][col] = new Cell();
            }
        }
    }

}