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
    // Getter the letters, wordMult and letterMult
    public char getLetter() {
        return letter;
    }
    public int getLetterMult() {
        return letterMult;
    }
    public int getWordMult() {
        return wordMult;    
    }

    // Setter the letters, wordMult and letterMult
    public void setLetter(char letter) {
        this.letter = letter;
    }
    public void setLetterMult(int letterMult) {
        this.letterMult = letterMult;
    }
    public void setWordMult(int wordMult) { 
        this.wordMult = wordMult;
    }
    /**
     * Review if the cell is empty.
     */
    public boolean isEmpty() {
        return letter == '.';
    }

    /**
     * Method to override the toString default method for String. It can be useful for printing the board. It will be testing in the UnitTest
     * Only show the letter in the board, if it is neccesary to show the multipliers, it can be shown use the other methods.
     */
    @Override
    public String toString() {
        if (letter != '.') {
            return String.valueOf(letter);
        } else if (wordMult != 0) {
            return wordMult + "!";
        } else if (letterMult != 0) {
            return letterMult + ".";
        } else {
            return ".";
        }
    }
}