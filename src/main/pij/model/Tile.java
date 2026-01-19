package main.pij.model;

/**
 * Represents a single Scrabble tile with a letter and point value.
 */
public class Tile {
    private final char letter;
    private final int value;
    public Tile(char letter, int value) {
        this.letter = letter;
        this.value = value;
    }

    public char getLetter() {
        return letter;
    }

    public int getValue() {
        return value;
    }

    /**
     * Tite as comodding for blank tiles.
     */
    @Override
    public String toString() {
        return letter == '_' ? "_" : String.valueOf(letter);
    }
    
}
