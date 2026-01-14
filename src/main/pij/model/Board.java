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

        // Line 0: columns, Line 1: rows, Line 2: start position (e.g., d7), make the transformation to cell coordinates
        if (lines.size() >= 3) {
            startPosition = parseStartPosition(lines.get(2).trim());
            System.out.println("Start position parsed:" + startPosition[0] + "," + startPosition[1]);
        }
        
        return lines;
    }

     /**
     * Get the start position of the "d7".
     */
    private int[] parseStartPosition(String position) {
        if (position == null || position.isEmpty()) {
            return null;
        }
        char colChar = Character.toLowerCase(position.charAt(0));
        int col = colChar - 'a';
        int row = Integer.parseInt(position.substring(1)) - 1;
        return new int[]{row, col};
    }

}