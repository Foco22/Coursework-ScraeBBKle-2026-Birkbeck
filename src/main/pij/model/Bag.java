package main.pij.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a bag of Scrabble tiles.
 */
public class Bag {
    private final List<Tile> tiles;

    public Bag() {
        this.tiles = new ArrayList<>();
        // Inicialization of the bag       
        initializeTiles();
    }
    /**
     * Initialize the bag in the time 0.
     */
    private void initializeTiles() {
        // Way to Inicialization without repeating code
        addTiles('A', 8, 1);
        addTiles('B', 2, 3);
        addTiles('C', 2, 4);
        addTiles('D', 4, 2);
        addTiles('E', 9, 2);
        addTiles('F', 3, 4);
        addTiles('G', 4, 3);
        addTiles('H', 3, 4);
        addTiles('I', 9, 1);
        addTiles('J', 1, 11);
        addTiles('K', 2, 6);
        addTiles('L', 4, 1);
        addTiles('M', 2, 3);
        addTiles('N', 7, 1);
        addTiles('O', 7, 1);
        addTiles('P', 2, 3);
        addTiles('Q', 1, 12);
        addTiles('R', 6, 1);
        addTiles('S', 4, 1);
        addTiles('T', 5, 1);
        addTiles('U', 5, 1);
        addTiles('V', 2, 4);
        addTiles('W', 2, 4);
        addTiles('X', 1, 9);
        addTiles('Y', 2, 5);
        addTiles('Z', 1, 9);

        for (int i = 0; i < 2; i++) {
            tiles.add(new Tile('_', 8));
        }

        // Random combination of the tiles
        Collections.shuffle(tiles);
    }

    /**
     * Method to add easier the tile to the bag using when the letter is repeated.
     */
    private void addTiles(char letter, int count, int value) {
        for (int i = 0; i < count; i++) {
            tiles.add(new Tile(letter, value));
        }
    }

    /**
     * Return the remaining number of tiles in the bag.
     */
    public int tilesRemaining() {
        return tiles.size();
    }
}