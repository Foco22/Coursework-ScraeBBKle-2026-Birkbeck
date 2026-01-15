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

    // Get current player (0 or 1)
    public int getTurn() {
        return turn;
    } 
}