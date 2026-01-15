package main.pij.service;

import main.pij.model.Board;
import main.pij.model.Bag;
import main.pij.model.Player;

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
     * Show turn information at the start of each turn.
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

}