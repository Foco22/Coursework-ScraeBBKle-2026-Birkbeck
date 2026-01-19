package main.pij.service;

import main.pij.model.Board;
import main.pij.model.Bag;
import main.pij.model.Player;
import main.pij.model.Tile;
import main.pij.model.WordList;
import main.pij.model.WordCells;
import main.pij.model.Cell;
import main.pij.utils.WordPermutations;
import main.pij.service.GameManager;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Manages the Scrabble game flow.
 */
public class ComputerMove {
    private final Player player1;
    private final Player player2;


    public ComputerMove(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public void FirstMove(int currentPlayerNum) {

        if (currentPlayerNum == 1) {
            String playerWords = "";
            List<Tile> PlayerRack = player1.getRack();
            System.out.println("Player rack:");
            for (Tile tile : PlayerRack) {
                System.out.println(tile);
                playerWords += tile.getLetter();
            }
            System.out.println("------------------------------------------");
            System.out.println(playerWords);
            Set<String> result = new HashSet<>();                                                                                                                     
            WordPermutations.permuteLenN(playerWords, "", result, 2); 
            System.out.println(result);
            

        } 
        else {

            String playerWords = "";
            List<Tile> PlayerRack = player2.getRack();
            System.out.println("Player rack:");
            for (Tile tile : PlayerRack) {
                System.out.println(tile);
                playerWords += tile.getLetter();
            }
            System.out.println(playerWords);
            Set<String> result = new HashSet<>();                                                                                                                     
            WordPermutations.permuteLenN(playerWords, "", result, 2); 
            System.out.println(result);
        }
    }
    

}