package main.pij.main;

import main.pij.model.Board;
import main.pij.model.Bag;
import main.pij.model.Player;
import main.pij.service.GameManager;
import main.pij.model.WordList;
import main.pij.model.WordCells;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Main class to start the Scrabble game.
 */
public class Main {

    public void run() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("============                     ============");
        System.out.println("============ S c r a b b l e     ============");
        System.out.println("============                     ============");
        System.out.println("--------------------------------");

        // 1. Board choice
        System.out.println("Would you like to _l_oad a board or use the _d_efault board?");
        // String boardChoice = scanner.nextLine().trim().toLowerCase();
        String boardChoice = String.valueOf('l');

        Board board;
        if (boardChoice.equals("l")) {
            System.out.println("Enter the filename to load the board from (in resources/):");
            // String filename = scanner.nextLine().trim();
            String filename = "defaultBoard";

            // Add .txt extension if not provided
            if (!filename.endsWith(".txt")) {
                filename = filename + ".txt";
            }
            // Always look in resources/ folder
            if (!filename.startsWith("resources/")) {
                filename = "resources/" + filename;
            }

            try {
                board = new Board(0);
                board.loadFromFile(filename);
            } catch (Exception e) {
                System.out.println("Error loading board from file: " + e.getMessage());
                System.out.println("Using default board instead.");
                board = new Board(0);
                try {
                    board.loadFromFile("resources/defaultBoard.txt");
                } catch (Exception ex) {
                    System.out.println("Error loading default board: " + ex.getMessage());
                }
            }
        } else {
            // Default board
            board = new Board(0);
            try {
                board.loadFromFile("resources/defaultBoard.txt");
            } catch (Exception e) {
                System.out.println("Error loading default board: " + e.getMessage());
            }
        }

        // Show the board
        // board.showBoard();
        
        //  Players
        System.out.print("Player 1 - Enter 'h' for human or 'c' for computer: ");
        // char player1Type = scanner.nextLine().trim().toLowerCase().charAt(0);
        char player1Type = 'h';
        Player player1 = new Player(1, player1Type);

        System.out.print("Player 2 - Enter 'h' for human or 'c' for computer: ");
        //char player2Type = scanner.nextLine().trim().toLowerCase().charAt(0);
        char player2Type = 'h';
        Player player2 = new Player(2, player2Type);

        // 3. Game type (it is still not used the class, but we prepare the input)
        System.out.print("Would you like to play an _o_pen or a _c_losed game? ");
        // String gameType = scanner.nextLine().trim().toLowerCase();
        String gameType = String.valueOf('o');

        // 4. Initialize bag and draw tiles
        Bag bag = new Bag();
        //player1.initialDraw(bag);
        //player2.initialDraw(bag);
        player1.setDummyRack();
        player2.setDummyRack();


        // Create game manager
        GameManager game = new GameManager(board, player1, player2, bag, gameType);

        // Show the board
        board.showBoard();
        
        // Show the start position
        System.out.println("Start position: " + board.getStartPositionAsString());

        // let set the condiction to stop the game
        int skipTurnPlayer1 = 0;
        int skipTurnPlayer2 = 0;
        boolean gameRunning = true;

        // load the wordlist;
        WordList.loadWords();
    
        while (gameRunning) {
            int currentPlayerNum = game.getCurrentPlayerNumber();
            Player currentPlayer = game.getCurrentPlayer();
            Player otherPlayer = (currentPlayerNum == 1) ? player2 : player1;

            game.showTurnInfo();

            String[] input = game.getPlayerInput(scanner);
            System.out.println(input);

            // 1- Step 1: Check the words in the board before the movement of the player. 
            // I need to compare the begining state of tha board with the last state.
            Map<String, Integer> MapWordBefore = board.getAllWordsOnBoard();

            // 2- Step 2: Validate if the words on the board are validated.
            boolean CheckWords = game.areAllWordsValid(MapWordBefore);

            if (!CheckWords) {
                // Invalid words - keep same player's turn
                game.showInvalidMoveMessage(input[0], input[1]);
                continue;
            }

            // 3 Step 3: Put the word in the board and get if the word is in the start position for the turn 0
            int countTurns = game.GentCountTurn();
            int[] StartPosition = board.getStartPosition();

            String word = input[0];
            String position = input[1];

            // It was created this method to copy the information of the cell that really were changed in this movement, as example: 
            // IF the movement was SNOWS, but the WORD SNOW was before, i need to get only the S.
            Set<String> newlyPlacedCells = new HashSet<>();
            System.out.println(newlyPlacedCells);
            boolean CheckPlaceWord = board.placeWord(word, position, StartPosition, countTurns,newlyPlacedCells);
            System.out.println(newlyPlacedCells);
            System.out.println("------------------------------------------------------------");
            System.out.println("------------------------------------------------------------");

            if (!CheckPlaceWord) {
                // Invalid words - keep same player's turn
                game.showInvalidMoveMessage(input[0], input[1]);
                continue;
            }
            // 4 Step 4: Get the word generate in the board.
            WordCells WordCellsPlayer = board.getWordAt(position);
            String WordPlayer = WordCellsPlayer.word;
            List<int[]> WordPlayerCells = WordCellsPlayer.cells;
            System.out.println("Word Player" + WordPlayer);
            System.out.println("WordPlayerCells" + WordPlayerCells);
        
            for (int[] cell : WordPlayerCells) {
                System.out.println("(" + cell[0] + ", " + cell[1] + ")");
            }

            // 5 Step 5: Check if the word generate is validate in the WordList.
            boolean CheckWordPlayer = WordList.isValidWord(WordPlayer);
            System.out.println(CheckWordPlayer);
            if (!CheckWordPlayer) {
                // Invalid words - keep same player's turn
                game.showInvalidMoveMessage(input[0], input[1]);
                continue;
            }

            // 6 Step 6: Getting the new with the movement of the player.
            Map<String, Integer> MapWordAfter = board.getAllWordsOnBoard();

            System.out.println(MapWordBefore);
            System.out.println(MapWordAfter);

            boolean ValidatedMovement = board.isValidMove(MapWordBefore, MapWordAfter);
            if (!ValidatedMovement) {
                // First - Restorate the movement 
                // this must be done, it is still in process. 
                
                // Second - Show the information 
                game.showInvalidMoveMessage(input[0], input[1]);
                continue;
            }

            // 7 Step 7: Count the point if the movement was sucesseed.
            Integer ScorePlayerTurn = game.scoreWord(board, WordCellsPlayer, newlyPlacedCells);
            System.out.println(ScorePlayerTurn);
            System.out.println(ScorePlayerTurn);
            System.out.println(ScorePlayerTurn);
            currentPlayer.addScore(ScorePlayerTurn);


            // Valid move - continue to next turn
            gameRunning = false;
            
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }
}