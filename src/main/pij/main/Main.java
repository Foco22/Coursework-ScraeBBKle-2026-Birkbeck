package main.pij.main;

import main.pij.model.Board;
import main.pij.model.Bag;
import main.pij.model.Player;
import main.pij.service.ComputerMove;
import main.pij.service.GameManager;
import main.pij.model.WordList;
import main.pij.model.WordCells;

import java.util.ArrayList;
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
        char player1Type = 'c';
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
        board.showBoard(bag);
        
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
            int countTurns = game.GentCountTurn();
            Player otherPlayer = (currentPlayerNum == 1) ? player2 : player1;

            // Logic to a HUMAN player - I prefer to divide as to know destroy what it has been build to the human action
            if (currentPlayer.isHuman()){

                if (countTurns != 0){
                    board.showBoard(bag);
                }
                game.showTurnInfo();
    

                // 0 Step 0: Get and Validate the input of the user.                
                String[] PlayerInput = game.getPlayerInput(scanner);
                if (PlayerInput[0] == null && PlayerInput[1] == null) {
                    // Pass turn
                    if (currentPlayerNum == 1) {
                        skipTurnPlayer1++;                        
                    } else {
                        skipTurnPlayer2++;
                    }
                    if (skipTurnPlayer1 >= 2 && skipTurnPlayer2 >= 2) {
                        System.out.println("Game over: both players passed twice.");
                        break;
                    }
                    game.nextTurn();
                    continue;

                } else if (PlayerInput[0].isEmpty() || PlayerInput[1].isEmpty()) {
                    // Invalid input
                    game.showInvalidMoveMessage(PlayerInput[0], PlayerInput[1]);
                    continue;
                } else {
                    // Valid input ✅
                    String RowInputPlayerWord = PlayerInput[0];
                    String position = PlayerInput[1];
                }
                
                // 1- Step 1: Check the words in the board before the movement of the player. 
                // I need to compare the begining state of tha board with the last state.
                Map<String, Integer> MapWordBefore = board.getAllWordsOnBoard();
                boolean EmptyMapWord = game.CheckEmptyMapWord(MapWordBefore);

                // 2- Step 2: Validate if the words on the board are validated.
                boolean CheckWords = game.areAllWordsValid(MapWordBefore);
    
                if (!CheckWords) {
                    // Invalid words - keep same player's turn
                    game.showInvalidMoveMessage(PlayerInput[0], PlayerInput[1]);
                    continue;
                }
                
                // 3 Step 3: Put the word in the board and get if the word is in the start position for the turn 0
                int[] StartPosition = board.getStartPosition();
                String word = PlayerInput[0];
                String position = PlayerInput[1];
    
                // It was created this method to copy the information of the cell that really were changed in this movement, as example: 
                // IF the movement was SNOWS, but the WORD SNOW was before, i need to get only the S.
                Set<String> newlyPlacedCells = new HashSet<>();
                char[][] boardBefore = board.snapshotLetters();
                boolean CheckPlaceWord = board.placeWord(word, position, StartPosition, countTurns, newlyPlacedCells, EmptyMapWord);
    
                if (!CheckPlaceWord) {
                    // Invalid words - keep same player's turn
                    // Need to store the board as the placeWord method is adding the word to the board
                    board.restoreLetters(boardBefore);
                    game.showInvalidMoveMessage(PlayerInput[0], PlayerInput[1]);
                    continue;
                }

                // 4 Step 4: Get the word generate in the board.
                WordCells WordCellsPlayer = board.getWordAt(position);
                String WordPlayer = WordCellsPlayer.word;
                List<int[]> WordPlayerCells = WordCellsPlayer.cells;
            
                // 5 Step 5: Check if the word generate is validate in the WordList.
                boolean CheckWordPlayer = WordList.isValidWord(WordPlayer);
                if (!CheckWordPlayer) {
                    // Invalid words - keep same player's turn
                    game.showInvalidMoveMessage(PlayerInput[0], PlayerInput[1]);
                    continue;
                }
    
                // 6 Step 6: Getting the new word with the movement of the player.
                Map<String, Integer> MapWordAfter = board.getAllWordsOnBoard();

                System.out.println(MapWordAfter);
                // If it was generated more than 1 word based on the new word, i need to cancel the movement and going back to the previous board.
                boolean ValidatedMovement = board.isValidMove(MapWordBefore, MapWordAfter);
                if (!ValidatedMovement) {
                    // First - Restorate the movement 
                    // Solve - It provide a restore the board when the movement is not 100%, as create more currence than it is allowed. 
                    board.restoreLetters(boardBefore);
                    game.showInvalidMoveMessage(PlayerInput[0], PlayerInput[1]);
                    continue;
                }
    
                // 7 Step 7: Count the point if the movement was sucesseed.
                String RowInputPlayerWord = PlayerInput[0];
                Integer ScorePlayerTurn = game.scoreWord(board, WordCellsPlayer, newlyPlacedCells, RowInputPlayerWord);
                currentPlayer.addScore(ScorePlayerTurn);
                currentPlayer.refillRack(bag);      
                
                // 8 Step 8: Show the results and the movement.
                game.showMoveResult(WordPlayer, position, ScorePlayerTurn);
    
                // 9 Step 9: Condition of finishing the game or going to the next player.
                // If the bag is empty and one of the player has a empty rack.
                if (bag.isEmpty() && (player1.countTilesInRack() == 0 || player2.countTilesInRack() == 0)) {
                    System.out.println("Game over: bag is empty and a player has an empty rack.");
                    break;
                }
                // If SkipTurn Player 2 and 1 is more than 2 for both.
                if (skipTurnPlayer2 >= 2 && skipTurnPlayer1 >= 2) {
                    break;
                }

                // Valid move - continue to next turn
                game.nextTurn();            
            }   

            // Logic to a COMPUTER player - 
            else {
                
                // Step 0: Get the first word if board is empty
                Map<String, Integer> MapWordBefore = board.getAllWordsOnBoard();
                boolean EmptyMapWord = game.CheckEmptyMapWord(MapWordBefore);
                if (EmptyMapWord) {
                    ComputerMove computerMove = new ComputerMove(player1, player2);                                                                  
                    String PlayerWord = computerMove.FirstMoveWord(currentPlayerNum);  
                    // if the element is null, so it pass the turn.    
                    if (PlayerWord == null ) {
                        // Pass the turn
                        game.nextTurn();
                        continue;
                    }    
                    else {
                        int[] StartPosition = board.getStartPosition();
                        String word = PlayerWord;
                        String position = board.getStartPositionAsString();
                        System.out.println(PlayerWord);
                        System.out.println(position);

                        // Step 1: Put the eord, using the Start postion as the position of the default
                        Set<String> newlyPlacedCells = new HashSet<>();
                        char[][] boardBefore = board.snapshotLetters();
                        boolean CheckPlaceWord = board.placeWord(word, position, StartPosition, countTurns, newlyPlacedCells, EmptyMapWord);
        
                        if (!CheckPlaceWord) {
                            // Invalid words - keep same player's turn
                            // Need to store the board as the placeWord method is adding the word to the board
                            board.restoreLetters(boardBefore);
                            game.showInvalidMoveMessage(PlayerWord, position);
                            continue;
                        }

                        // 4 Step 4: Get the word generate in the board.
                        WordCells WordCellsPlayer = board.getWordAt(position);
                        String WordPlayer = WordCellsPlayer.word;
                        List<int[]> WordPlayerCells = WordCellsPlayer.cells;


                    }
                }



                gameRunning = false;
                
                
            }
        
        
        }

        // 10 Step 10: Game Over.
        Integer UnPlayedTitlePlayer1 = game.unplayedTilesValue(player1);
        Integer UnPlayedTitlePlayer2 = game.unplayedTilesValue(player2);
        player1.addScore(-UnPlayedTitlePlayer1);
        player2.addScore(-UnPlayedTitlePlayer2);        
        game.showFinalResult();
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }
}