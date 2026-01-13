package main.pij;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import main.pij.model.Tile;
import main.pij.model.Bag;

import java.util.List;

public class ScrabbleTest {

    // ==================== MODEL TESTS ====================

    // --- Tile Tests ---
    @Test
    public void testTileCreation() {
        System.out.println("testTileCreation");
        Tile tile = new Tile('A', 1);
        System.out.println("Created tile: " + tile.getLetter() + " with value " + tile.getValue());
        System.out.println("--------------------------------------");
        assertEquals('A', tile.getLetter());
        assertEquals(1, tile.getValue());
    }

    @Test
    public void testWildcardTile() {
        System.out.println("testWildcardTile");
        Tile wildcard = new Tile('_', 8);
        System.out.println("Created wildcard tile: " + wildcard.getLetter() + " with value " + wildcard.getValue());
        System.out.println("--------------------------------------");
        assertEquals('_', wildcard.getLetter());
        assertEquals(8, wildcard.getValue());
    }

    @Test 
    public void TestBagInitialization() {
        System.out.println("TestBagInitialization");
        main.pij.model.Bag bag = new main.pij.model.Bag();
        System.out.println("Total tiles in bag: " + bag.tilesRemaining());
        System.out.println("--------------------------------------");
        assertEquals(100, bag.tilesRemaining()); // Standard Scrabble bag has 100 tiles
    }

    @Test
    public void TestShowbag() {
        System.out.println("testDrawTiles");
        main.pij.model.Bag bag = new main.pij.model.Bag();
        int tilessize = bag.showBag();
        System.out.println("Tiles in bag: " + tilessize);
        System.out.println("--------------------------------------");
        assertEquals(100, tilessize); // Standard Scrabble bag has 100 tiles

    }

    @Test
    public void testDrawTiles() {
        System.out.println("testDrawTiles");
        main.pij.model.Bag bag = new main.pij.model.Bag();
        List<Tile> drawnTiles = bag.drawTiles(7);
        System.out.println("Drawn tiles:");
        for (Tile tile : drawnTiles) {
            System.out.println("  " + tile.getLetter() + " (value: " + tile.getValue() + ")");
        }
        System.out.println("Remaining tiles in bag: " + bag.tilesRemaining());
        System.out.println("--------------------------------------");
        assertEquals(7, drawnTiles.size());
        assertEquals(93, bag.tilesRemaining());
    }
}