package main.pij.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the Scrabble game.It can be Human or Computer
 */
public class Player {
    private final char type; // 'h' for human, 'c' for computer
    private int score;
    private List<Tile> rack;

    public Player(char type) {
        this.type = type;
        this.score = 0;
        this.rack = new ArrayList<>();
    }

}