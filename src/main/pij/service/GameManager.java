package main.pij.service;

import main.pij.model.Board;
import main.pij.model.Bag;
import main.pij.model.Player;
import main.pij.model.WordList;
import main.pij.model.WordCells;
import main.pij.model.Cell;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Manages the Scrabble game flow.
 */
public class GameManager {
    private final Board board;
    private final Player player1;
    private final Player player2;
    private final Bag bag;
    private final String gameType; // "o" open or "c" closed

    private int turn;        // 0 = player1, 1 = player2
    private int countTurns;  // Total turns played

    public GameManager(Board board, Player player1, Player player2, Bag bag, String gameType) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        this.bag = bag;
        this.gameType = gameType;
        this.turn = 0;        // Player 1 starts
        this.countTurns = 0;
    }

    public int GentCountTurn() {
        return countTurns;
    }

    // Get current player object
    public Player getCurrentPlayer() {
        return turn == 0 ? player1 : player2;
    }

    // Get current player number (1 or 2)
    public int getCurrentPlayerNumber() {
        return turn + 1;
    }

    // Get other player
    public Player getOtherPlayer() {
        return turn == 0 ? player2 : player1;
    }

    /**
     * Show turn information at the turn
     */
    public void showTurnInfo() {
        Player current = getCurrentPlayer();
        Player other = getOtherPlayer();

        // If open game, show opponent's tiles
        if (gameType.equals("o")) {
            System.out.println("OPEN GAME: Player " + other.getPlayerNumber() + "'s tiles:");
            System.out.println("OPEN GAME: " + other.getRackFormatted());
        }

       // Show current player's turn and tiles
       System.out.println("It's your turn, Player " + current.getPlayerNumber() + "! Your tiles:");
       System.out.println(current.getRackFormatted());

       // Show move instructions
       System.out.println("Please enter your move in the format: \"word,square\" (without the quotes)");
       System.out.println("For example, for suitable tile rack and board configuration, a downward move");
       System.out.println("could be \"HI,f4\" and a rightward move could be \"HI,4f\".");
       System.out.println();
       System.out.println("In the word, upper-case letters are standard tiles and lower-case letters");
       System.out.println("are wildcards.");
       System.out.println("Entering \",\" passes the turn.");

    }

    /**
     * Get player move input from console.
     */
    public String[] getPlayerInput(Scanner scanner) {
        System.out.print("> ");
        String input = scanner.nextLine().trim();
    
        // Pass turn
        if (input.equals(",")) {
            return new String[]{null, null};
        }
    
        String[] parts = input.split(",");
        if (parts.length != 2) {
            System.out.println("Invalid format. Use: WORD,POSITION (e.g., RUN,d7)");
            return new String[]{"", ""};
        }
    
        String word = parts[0].trim();
        String position = parts[1].trim().toLowerCase();
    
        // Validate WORD (letters only)
        if (!word.matches("[A-Za-z]+")) {
            System.out.println("Invalid word. Only letters are allowed.");
            return new String[]{"", ""};
        }
    
        // Validate POSITION (letter+number OR number+letter). It prevent mistake in the inputs.
        if (!position.matches("([a-z][0-9]+)|([0-9]+[a-z])")) {
            System.out.println("Invalid position. Use letter+number or number+letter (e.g., d7 or 7d).");
            return new String[]{"", ""};
        }
    
        return new String[]{word, position};
    }    

    /**
     * Checks if all words on the board are valid.
     */
    public boolean areAllWordsValid(Map<String, Integer> wordsOnBoard) {
        for (String word : wordsOnBoard.keySet()) {
            if (!WordList.isValidWord(word)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Shows error message when a move is invalid.
     */
    public void showInvalidMoveMessage(String word, String position) {
        System.out.println("The board does not permit word " + word + " at position " + position + ". Please try again.");
        System.out.println("Please enter your move in the format: \"word,square\" (without the quotes)");
        System.out.println("For example, for suitable tile rack and board configuration, a downward move");
        System.out.println("could be \"HI,f4\" and a rightward move could be \"HI,4f\".");
        System.out.println();
        System.out.println("In the word, upper-case letters are standard tiles and lower-case letters");
        System.out.println("are wildcards.");
        System.out.println("Entering \",\" passes the turn.");
    }


    public int scoreWord(Board board, WordCells wordCellsPlayer, Set<String> newlyPlacedCells, String rawInputWord) {
        int sum = 0;
        int wordFactor = 1;
    
        Cell[][] boardGame = board.getBoard();
    
        // Safety: if mismatch, avoid crash
        int n = Math.min(wordCellsPlayer.cells.size(), rawInputWord.length());
    
        for (int i = 0; i < n; i++) {
            int[] pos = wordCellsPlayer.cells.get(i);
            int r = pos[0];
            int c = pos[1];
    
            Cell cell = boardGame[r][c];
    
            boolean isNew = newlyPlacedCells.contains(r + "," + c);
    
            // Use RAW input to detect a wildcard
            char rawCh = rawInputWord.charAt(i);
            boolean isWildcard = Character.isLowerCase(rawCh);
    
            // Use board letter (uppercase) for normal tiles
            char boardCh = cell.getLetter();
            int base = isWildcard ? 8 : bag.letterValue(boardCh);
    
            if (isNew) {
                int letterMult = cell.getLetterMult();
                int wordMult = cell.getWordMult();
    
                int letterFactor = (letterMult == 0 ? 1 : letterMult);
                int cellWordFactor = (wordMult == 0 ? 1 : wordMult);
    
                sum += base * letterFactor;
                wordFactor *= cellWordFactor;
            } else {
                sum += base;
            }
        }
    
        return sum * wordFactor;
    }
    

    public void nextTurn() {
        turn = 1 - turn;     // Change the turnn to 0 a 1
        countTurns++;        // Add Count the turn
    }
    public void showMoveResult(String word, String position, int points) {
        System.out.println();
        System.out.println("The move is: Letters: " + word.toUpperCase() + " at position " + position);
        System.out.println("Points this turn: " + points);
        System.out.println("Player 1 score: " + player1.getScore());
        System.out.println("Player 2 score: " + player2.getScore());
        System.out.println();
    }

    // Get the values of the total unplayed title values.
    public int unplayedTilesValue(Player player) {
        int sum = 0;
        for (var tile : player.getRack()) {
            sum += tile.getValue();
        }
        return sum;
    }

    // Show the final results to the game.
    public void showFinalResult() {
        int score1 = player1.getScore();
        int score2 = player2.getScore();
    
        System.out.println("Game Over!");
        System.out.println("Player 1 scored " + score1 + " points.");
        System.out.println("Player 2 scored " + score2 + " points.");
    
        if (score1 > score2) {
            System.out.println("Player 1 wins!");
        } else if (score2 > score1) {
            System.out.println("Player 2 wins!");
        } else {
            System.out.println("It's a draw!");
        }
    }

    
    

}