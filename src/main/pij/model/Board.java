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

    /**
     * Load board configuration from file.
     */
    public List<String> loadFromFile(String filename) throws IOException {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

}