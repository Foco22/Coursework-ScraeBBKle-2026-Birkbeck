package main.pij.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
 

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
     * Set dummy rack for testing (RUN tiles).
     */
    public void setDummyRack() {
        rack.clear();
        rack.add(new Tile('O', 1));
        rack.add(new Tile('_', 8));

    }

    public void setDummyRack2() {
        rack.clear();
        rack.add(new Tile('_', 8));

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

    /**
     * Get rack formatted as [L#], [L#], ... like [E2], [N1]
     */
    public String getRackFormatted() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rack.size(); i++) {
            Tile tile = rack.get(i);
            sb.append("[").append(tile.getLetter()).append(tile.getValue()).append("]");
            if (i < rack.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        String typeStr = (type == 'h') ? "Human" : "Computer";
        return "Player " + playerNumber + " (" + typeStr + ")";
    }

}

