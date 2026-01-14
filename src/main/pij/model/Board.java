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
    public void loadFromFile(String filename) throws IOException {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }

        // Line 0: columns, Line 1: rows, Line 2: start position (e.g., d7), make the transformation to cell coordinates
        if (lines.size() >= 3) {
            this.startPosition = parseStartPosition(lines.get(2).trim());
            System.out.println("Start position parsed:" + startPosition[0] + "," + startPosition[1]);
        }
        
        // Parse board data starting from line 3
        int rowIndex = 0;
        for (int i = 3; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) {
                continue;
            }
            if (rowIndex >= size) {
                break;
            }
            parseRow(line, rowIndex);
            rowIndex++;
        }
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

    /**
     * Obtain the information for the given board.
     * Format: [N] = word multiplier, <N> = letter multiplier, . = empty
     */
    private void parseRow(String line, int rowIndex) {
        int col = 0;
        int i = 0;

        while (i < line.length() && col < size) {
            char c = line.charAt(i);

            if (c == '.') {
                // Empty cell
                board[rowIndex][col] = new Cell();
                col++;
                i++;
            } else if (c == '[') {
                // Word multiplier [N]
                int endIndex = line.indexOf(']', i);
                int mult = Integer.parseInt(line.substring(i + 1, endIndex));
                board[rowIndex][col] = new Cell('.', 0, mult);
                col++;
                i = endIndex + 1;
            } else if (c == '<') {
                // Letter multiplier <N>
                int endIndex = line.indexOf('>', i);
                int mult = Integer.parseInt(line.substring(i + 1, endIndex));
                board[rowIndex][col] = new Cell('.', mult, 0);
                col++;
                i = endIndex + 1;
            } else {
                i++;
            }
        }
    }

    // Get the board
    public Cell[][] getBoard() {
        return board;
    }

}