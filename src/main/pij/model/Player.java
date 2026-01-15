package main.pij.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the Scrabble game.It can be Human or Computer
 */
public class Player {
    private final int playerNumber; // 1 or 2
    private final char type; // 'h' for human, 'c' for computer
    private int score;
    private List<Tile> rack;

    public Player(int playerNumber, char type) {
        this.playerNumber = playerNumber;
        this.type = type;
        this.score = 0;
        this.rack = new ArrayList<>();
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    /**
     * Draw initial 7 tiles from the bag.
     */
    public void initialDraw(Bag bag) {
        List<Tile> drawn = bag.drawTiles(7);
        rack.addAll(drawn);
    }

    /**
     * Draw tiles to refill rack to 7 tiles.
     */
    public void refillRack(Bag bag) {
        int needed = 7 - rack.size();
        if (needed > 0) {
            List<Tile> drawn = bag.drawTiles(needed);
            rack.addAll(drawn);
        }
    }

    public List<Tile> getRack() {
        return rack;
    }

    public int countTilesInRack() {
        return rack.size();
    }

    public boolean isHuman() {
        return type == 'h';
    }

    public boolean isComputer() {
        return type == 'c';
    }

    public void addScore(int points) {
        this.score += points;
    }

    public int getScore() {
        return score;
    }

    /**
     * Method to display the player's rack.
     */
    public void showRack() {
        System.out.print("Rack: ");
        for (Tile tile : rack) {
            System.out.print(tile.getLetter() + " ");
        }
        System.out.println();
    }

    @Override
    public String toString() {
        String typeStr = (type == 'h') ? "Human" : "Computer";
        return "Player " + playerNumber + " (" + typeStr + ")";
    }
}