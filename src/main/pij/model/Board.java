package main.pij.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * Load board configuration from file storage.
     */
    public void loadFromFile(String filename) throws IOException {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }

        // provide the information to stat the board.
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
            } else if (Character.isLetter(c)) {
                // Letter on the board (A-Z)
                board[rowIndex][col] = new Cell(Character.toUpperCase(c), 0, 0);
                col++;
                i++;
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
     * Convert row and column indices to position string like "d7".
     */
    public String indicesToPosition(int row, int col) {
        // convert 0-based indices to position string, it is eaiser way to get the values
        char colLetter = (char) ('a' + col);
        int rowNumber = row + 1;
        return "" + colLetter + rowNumber;
    }

    /**
     * Get start position as string like "d7".
     */
    public String getStartPositionAsString() {
        if (startPosition == null) {
            return null;
        }
        return indicesToPosition(startPosition[0], startPosition[1]);
    }


    /**
     * Show the board in the console.
    */
    public void showBoard() {

        // First : Print column headers
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

    /**
     * Get all words on the board (horizontal and vertical).
     */
    public Map<String, Integer> getAllWordsOnBoard() {
        Map<String, Integer> words = new HashMap<>();

        // Scan horizontal words (left to right)
        for (int row = 0; row < rows; row++) {
            String currentWord = "";
            // current words
            for (int col = 0; col < columns; col++) {
                char letter = board[row][col].getLetter();
                // get the letter of the board in this position
                if (letter != '.') {
                    currentWord += letter;
                } else {
                    if (currentWord.length()  >= 2) {
                        words.put(currentWord, words.getOrDefault(currentWord, 0) + 1);
                    }
                    currentWord = "";
                }
            }
            // Check end of row
            if (currentWord.length() >= 2) {
                words.put(currentWord, words.getOrDefault(currentWord, 0) + 1);
            }
        }

        // Scan the vertical words (top to down)
        for (int column = 0; column < columns; column++) {
            String currentWord = "";
            // current words
            for (int row = 0; row < rows; row++) {
                char letter = board[row][column].getLetter();
                // get the letter of the board in this position
                if (letter != '.') {
                    currentWord += letter;
                } else {
                    if (currentWord.length() >= 2) {
                        words.put(currentWord, words.getOrDefault(currentWord, 0) + 1);
                    }
                    currentWord = "";
                }
            }
            // Check end of row
            if (currentWord.length() >= 2) {
                words.put(currentWord, words.getOrDefault(currentWord, 0) + 1);
            }
        }

        return words;
    }

    /**
     * Place a word on the board.
     */
    public boolean placeWord(String word, String position, 
        int[] StartPosition, 
        Integer countTurns

    ) {
        // Parse position and direction
        // "d7" = column d, row 7, direction down (letter first)
        // "7d" = row 7, column d, direction right (number first)

        int row, col;
        boolean isHorizontal;

        if (Character.isLetter(position.charAt(0))) {
            // Letter first: "d7" = downward (vertical)
            char colChar = Character.toLowerCase(position.charAt(0));
            col = colChar - 'a';
            // allow to get the row, example if d the letter, so it get the 3 in column.
            row = Integer.parseInt(position.substring(1)) - 1;
            isHorizontal = false;
        } else {
            // Number first: "7d" = rightward (horizontal)
            int letterIndex = 0;
            while (letterIndex < position.length() && Character.isDigit(position.charAt(letterIndex))) {
                letterIndex++;
            }
            row = Integer.parseInt(position.substring(0, letterIndex)) - 1;
            char colChar = Character.toLowerCase(position.charAt(letterIndex));
            // allow to get the column of the d, obtian the 3 in the case of d.
            col = colChar - 'a';
            isHorizontal = true;
        }

        // Place each letter - skip cells that already have letters
        int wordIndex = 0;    // Index in the input word
        int boardOffset = 0;  // Offset on the board from starting position
        boolean wordInStartPosition = false;
        int StartPositionRow = startPosition[0];
        int StartPositioncol = startPosition[1];

        while (wordIndex < word.length()) {
            int currentRow = isHorizontal ? row : row + boardOffset;
            int currentCol = isHorizontal ? col + boardOffset : col;

            // Check bounds
            if (currentRow < 0 || currentRow >= rows || currentCol < 0 || currentCol >= columns) {
                return false;
            }

            // Check if cell already has a letter
            if (!board[currentRow][currentCol].isEmpty()) {
                // Cell has a letter - skip this board position, don't consume word letter
                boardOffset++;
                continue;
            }

            if (currentRow == StartPositionRow && currentCol == StartPositioncol) {
                wordInStartPosition = true;
            }

            // Cell is empty - place the letter in the board.
            char letter = Character.toUpperCase(word.charAt(wordIndex));
            board[currentRow][currentCol].setLetter(letter);
            wordIndex++;
            boardOffset++;
        }
        if (countTurns == 0 && !wordInStartPosition) {
            return false;
        }
        return true;
    }

    /**
     * Get the complete word formed at a position in a direction.
     */
    public String getWordAt(String position) {
        int row, col;
        boolean isHorizontal;

        // Parse position (same logic as placeWord). It is a easier way to parse de position and check if a horizontal or vertical.
        if (Character.isLetter(position.charAt(0))) {
            char colChar = Character.toLowerCase(position.charAt(0));
            col = colChar - 'a';
            row = Integer.parseInt(position.substring(1)) - 1;
            isHorizontal = false;
        } else {
            int letterIndex = 0;
            while (letterIndex < position.length() && Character.isDigit(position.charAt(letterIndex))) {
                letterIndex++;
            }
            row = Integer.parseInt(position.substring(0, letterIndex)) - 1;
            char colChar = Character.toLowerCase(position.charAt(letterIndex));
            col = colChar - 'a';
            isHorizontal = true;
        }

        String word = "";
        int currentRow = row;
        int currentCol = col;

        while (currentRow >= 0 && currentRow < rows && currentCol >= 0 && currentCol < columns) {
            char letter = board[currentRow][currentCol].getLetter();
            if (letter == '.') {
                break;  // not count more the word.
            }
            word += letter;

            // Next move to the board, if it is horizontal or vertical
            if (isHorizontal) {
                currentCol++;
            } else {
                currentRow++;
            }
        }
        return word;
    }

    /**
     * Get if this is a validate movemenet after the player game his position.
     */

    public static boolean isValidMove(
        Map<String, Integer> before,
        Map<String, Integer> after) {

    int newWords = 0;
    int increasedWords = 0;

    for (Map.Entry<String, Integer> entry : after.entrySet()) {
        String word = entry.getKey();
        int afterValue = entry.getValue();

        if (!before.containsKey(word)) {
            // add a new value
            newWords++;
        } else {
            int beforeValue = before.get(word);

            if (afterValue == beforeValue + 1) {
                increasedWords++;
            } else if (afterValue != beforeValue) {
                // any other combination as 2,3,4, or more word, are no validated.
                return false;
            }
        }
    }

    // if the size was less, so it not work,
    if (before.size() > after.size()) {
        return false;
    }

    // Onlu a new words can exists or must be a increment of 1 for a exist word. (one occurrence)
    return (newWords == 1 && increasedWords == 0)
        || (newWords == 0 && increasedWords == 1);
}



}
