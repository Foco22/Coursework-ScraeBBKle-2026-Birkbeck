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
    }
    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }
}