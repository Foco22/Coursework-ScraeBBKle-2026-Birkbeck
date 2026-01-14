package main.pij;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import main.pij.model.Tile;
import main.pij.model.Bag;
import main.pij.model.Cell;
import main.pij.model.Board;
import main.pij.model.Player;

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

    @Test
    public void testCellCreation() {
        System.out.println("testCellCreation");
        Cell cell = new Cell('A', 2, 3);
        System.out.println("Created cell with letter: " + cell.getLetter() + ", letter multiplier: " + cell.getLetterMult() + ", word multiplier: " + cell.getWordMult());
        System.out.println("--------------------------------------");
        assertEquals('A', cell.getLetter());
        assertEquals(2, cell.getLetterMult());
        assertEquals(3, cell.getWordMult());
    }   

    @Test
    public void testCelltoString() {
        System.out.println("testCelltoString");
        Cell cell = new Cell('B', 1, 2);
        System.out.println("Cell toString output: " + cell.toString());
        System.out.println("--------------------------------------");
        assertEquals("B", cell.toString());
    }

    @Test
    public void testBoardCreation() {
        System.out.println("testBoardCreation");
        Board board = new Board(15);
        System.out.println("Created board of size: 15x15");
        System.out.println(board);
        System.out.println("--------------------------------------");
        assertNotNull(board);
    }

    @Test
    public void testLoadBoardFromFile() {
        System.out.println("testLoadBoardFromFile");
        Board board = new Board(15);
        try {
            board.loadFromFile("resources/defaultBoard.txt");
            System.out.println("Loaded board configuration:");
            System.out.println(board);
            Cell [][] cells_board = board.getBoard();
            int[] startPos = board.getStartPosition();
            board.showBoard();
            System.out.println("Start position: (" + startPos[0] + ", " + startPos[1] + ")");
        } catch (Exception e) {
            fail("Exception error: " + e.getMessage());
        }
    }

    @Test
    public void testPlayerInitialDraw() {
        System.out.println("testPlayerInitialDraw");
        Bag bag = new Bag();
        Player player = new Player('h'); // 'h' for human player
        player.initialDraw(bag);
        List<Tile> rack = player.getRack();
        int rackSize = player.countTilesInRack();
        System.out.println("Player initial draw completed.");
        System.out.println("Player's rack:");
        player.showRack();
        assertEquals(7, rackSize); // Check board size
        System.out.println("--------------------------------------");
    }


}