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
    private int columns; // M: 7-26
    private int rows;    // N: 10-99
        private Cell[][] board;
    private int[] startPosition;

    // Validation constants
    private static final int MIN_COLUMNS = 7;
    private static final int MAX_COLUMNS = 26;
    private static final int MIN_ROWS = 10;
    private static final int MAX_ROWS = 99;
    private static final int MIN_SQUARES = 192;
    
    public Board(int size) {
        this.columns = 0;
        this.rows = 0;
        this.board = null;
        this.startPosition = null;
    }

    /**
     * Create a board with specified dimensions.
     */
    public Board(int columns, int rows) {
        this.columns = columns;
        this.rows = rows;
        this.board = new Cell[rows][columns];
        initializeBoard();
    }

    /**
     * Initialize all cells with default values.
     */
    private void initializeBoard() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
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
            this.columns = Integer.parseInt(lines.get(0).trim());
            this.rows = Integer.parseInt(lines.get(1).trim());
            validateDimensions(columns, rows);  // Validar dimensiones
            this.startPosition = parseStartPosition(lines.get(2).trim());
            System.out.println("Start position parsed:" + startPosition[0] + "," + startPosition[1]);

            this.board = new Cell[rows][columns];
            initializeBoard();
        }   
    
        // Parse board data starting from line 3
        int rowIndex = 0;
        for (int i = 3; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) {
                continue;
            }
            if (rowIndex >= rows) {
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

        while (i < line.length() && col < columns) {
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

    /**
     * Validate the size of the board.
     */
    private void validateDimensions(int cols, int rowCount) {
            if (cols < MIN_COLUMNS || cols > MAX_COLUMNS) {
                throw new IllegalArgumentException(
                    "Columns must be between " + MIN_COLUMNS + " and " + MAX_COLUMNS + ", got: " + cols);
            }
            if (rowCount < MIN_ROWS || rowCount > MAX_ROWS) {
                throw new IllegalArgumentException(
                    "Rows must be between " + MIN_ROWS + " and " + MAX_ROWS + ", got: " + rowCount);
            }
            if (cols * rowCount < MIN_SQUARES) {
                throw new IllegalArgumentException(
                    "Board must have at least " + MIN_SQUARES + " squares, got: " + (cols * rowCount));
            }
    }

        
    // Get the board
    public Cell[][] getBoard() {
        return board;
    }

    // Get the start position
    public int[] getStartPosition() {
        return startPosition;
    }


    /**
     * Show the board in the console.
    */
    public void showBoard() {

        // First : Print column headers (a, b, c, ...) 
        System.out.print("   ");
        for (int col = 0; col < columns; col++) {
            System.out.printf("%4c", (char)('a' + col));
        }
        System.out.println();

        // Second : Print each row with row number
        for (int row = 0; row < rows; row++) {
            System.out.printf("%2d ", row + 1);
            for (int col = 0; col < columns; col++) {
                System.out.printf("%4s", board[row][col].toString());
            }
            System.out.println();
        }
    }
}