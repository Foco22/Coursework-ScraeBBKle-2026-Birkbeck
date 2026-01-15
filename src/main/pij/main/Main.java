package main.pij.main;

import main.pij.model.Board;
import main.pij.model.Bag;
import main.pij.model.Player;
import main.pij.service.GameManager;

import java.util.Map;
import java.util.Scanner;

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
        Player player1 = new Player(player1Type);
        
        System.out.print("Player 2 - Enter 'h' for human or 'c' for computer: ");
        //char player2Type = scanner.nextLine().trim().toLowerCase().charAt(0);
        char player2Type = 'h';
        Player player2 = new Player(player2Type);

        // 3. Game type (it is still not used the class, but we prepare the input)
        System.out.print("Would you like to play an _o_pen or a _c_losed game? ");
        // String gameType = scanner.nextLine().trim().toLowerCase();
        String gameType = String.valueOf('l');

        // 4. Initialize bag and draw tiles
        Bag bag = new Bag();
        player1.initialDraw(bag);
        player2.initialDraw(bag);

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

        while (gameRunning) {



        }



    }



    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }
}