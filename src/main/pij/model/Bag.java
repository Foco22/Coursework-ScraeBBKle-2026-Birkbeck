package main.pij.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a bag of Scrabble tiles.
 */
public class Bag {
    private final List<Tile> tiles;
    private final Map<Character, Integer> values = new HashMap<>();

    public Bag() {
        this.tiles = new ArrayList<>();
        // Inicialization of the bag       
        initializeTiles();
        initializeValues();  
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

    /**
     * Show a tile from the bag.
     */
    public int showBag() {                                                                                                                                 
        System.out.println("Bag contains " + tiles.size() + " tiles:");                                                                                     
        for (Tile tile : tiles) {                                                                                                                           
            System.out.println("  " + tile.getLetter() + " (value: " + tile.getValue() + ")");                                                              
        }
        return tiles.size();                                                                                                                                                  
    } 

    /**
     * Draw tiles from the bag.
     */
    public List<Tile> drawTiles(int count) {
        if (count > tiles.size()) {
            count = tiles.size();
        }

        // create a list the title needed to be drawn
        List<Tile> drawnTiles = new ArrayList<>();
        
        for (int i = 0; i < count; i++) {
            if (!tiles.isEmpty()) {
                // Remove a title in the end of the bag, and adding to the drawntitle

                drawnTiles.add(tiles.remove(tiles.size() - 1));
            }
        }
        return drawnTiles;
    }

    /**
     * Check if the bag is empty.
     */
    public boolean isEmpty() {
            return tiles.isEmpty();
    }
    private void initializeValues() {
        // mismas letras/valores que usas para addTiles
        values.put('A', 1);
        values.put('B', 3);
        values.put('C', 4);
        values.put('D', 2);
        values.put('E', 2);
        values.put('F', 4);
        values.put('G', 3);
        values.put('H', 4);
        values.put('I', 1);
        values.put('J', 11);
        values.put('K', 6);
        values.put('L', 1);
        values.put('M', 3);
        values.put('N', 1);
        values.put('O', 1);
        values.put('P', 3);
        values.put('Q', 12);
        values.put('R', 1);
        values.put('S', 1);
        values.put('T', 1);
        values.put('U', 1);
        values.put('V', 4);
        values.put('W', 4);
        values.put('X', 9);
        values.put('Y', 5);
        values.put('Z', 9);
        values.put('_', 8); // tus blanks
    }

    public int letterValue(char ch) {
        return values.getOrDefault(Character.toUpperCase(ch), 0);
    }

}