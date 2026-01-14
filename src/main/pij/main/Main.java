package main.pij.main;

import main.pij.model.Board;
import main.pij.model.Bag;
import main.pij.model.Player;

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
        String boardChoice = scanner.nextLine().trim().toLowerCase();

        Board board;
        if (boardChoice.equals("l")) {
            System.out.println("Enter the filename to load the board from (in resources/):");
            String filename = scanner.nextLine().trim();

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
        board.showBoard();
    }



    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }
}