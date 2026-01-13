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

    @Test void TestBagInitialization() {
        System.out.println("TestBagInitialization");
        main.pij.model.Bag bag = new main.pij.model.Bag();
        System.out.println("Total tiles in bag: " + bag.tilesRemaining());
        System.out.println("--------------------------------------");
        assertEquals(100, bag.tilesRemaining()); // Standard Scrabble bag has 100 tiles
    }


}