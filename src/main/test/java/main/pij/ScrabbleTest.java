package main.pij;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import main.pij.model.Tile;
import main.pij.model.Bag;
import main.pij.model.Cell;
import main.pij.model.Board;
import main.pij.model.Player;
import main.pij.model.WordList;
import main.pij.utils.WordPermutations;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
            System.out.println("Start position: (" + startPos[0] + ", " + startPos[1] + ")");
            System.out.println("--------------------------------------");
        } catch (Exception e) {
            fail("Exception error: " + e.getMessage());
            System.out.println("--------------------------------------");
        }
    }

    @Test
    public void testPlayerInitialDraw() {
        System.out.println("testPlayerInitialDraw");
        Bag bag = new Bag();
        Player player = new Player(1,   'h'); // 'h' for human player
        player.initialDraw(bag);
        List<Tile> rack = player.getRack();
        int rackSize = player.countTilesInRack();
        System.out.println("Player initial draw completed.");
        System.out.println("Player's rack:");
        player.showRack();
        assertEquals(7, rackSize); // Check board size
        System.out.println("--------------------------------------");
    }

    @Test
    public void testWordList() {
        System.out.println("testLoadWordList");
        WordList.loadWords();
        boolean wordIsList = WordList.isValidWord("RUN");
        System.out.println(wordIsList);
        assertEquals(true, wordIsList); // Check board size
        System.out.println("--------------------------------------");
    }

    @Test 
    public void testPermutationWord() {
        System.out.println("testPermutationWord");
        Set<String> result = new HashSet<>();
        WordPermutations.permuteLenN("abcd", "", result, 2   );
        System.out.println("Total: " + result.size());
        System.out.println(result);
        assertEquals(12, result.size()); // Check board size
        System.out.println("--------------------------------------");

    
    }

    @Test 
    public void testDummyTiles() {
        System.out.println("testDummyTiles");
        char player1Type = 'h';
        Player player1 = new Player(1, player1Type);
        player1.setDummyRack();
        int CountTiles = player1.countTilesInRack();
        System.out.println(CountTiles);

        assertEquals(2, CountTiles); // Check board size
        System.out.println("--------------------------------------");

    
    }

}