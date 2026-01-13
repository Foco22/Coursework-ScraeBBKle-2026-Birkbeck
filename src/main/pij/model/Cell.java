package main.pij.model;

/**
 * Represents a single cell on the Scrabble board.
 */
public class Cell {
    private char letter;
    private int letterMult;
    private int wordMult;

    /**
     * Create a new cell with specified values.
     */
    public Cell(char letter, int letterMult, int wordMult) {
        this.letter = letter;
        this.letterMult = letterMult;
        this.wordMult = wordMult;
    }

    /**
     * Create a new cell with default values.
     */
    public Cell() {
        this.letter = '.';
        this.letterMult = 0;
        this.wordMult = 0;
    }

    public char getLetter() {
        return letter;
    }
    public int getLetterMult() {
        return letterMult;
    }
    public int getWordMult() {
        return wordMult;    
    }

}