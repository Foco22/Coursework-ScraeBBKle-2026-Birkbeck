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
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Manages the Scrabble game flow.
 */
public class ComputerMove {
    private final Board board;
    private final Player player1;
    private final Player player2;
    private final Bag bag;
    private final GameManager gameManager;


    public ComputerMove(Board board, Player player1, Player player2, Bag bag, GameManager gameManager) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        this.bag = bag;
        this.gameManager = gameManager;

    }

    public void FirstMove(int currentPlayerNum) {

        if (currentPlayerNum == 1) {
            List<Tile> PlayerRack = player1.getRack();
            System.out.println("Player rack:");
            for (Tile tile : PlayerRack) {
                System.out.println(tile);
            }
        } 
        else {
            List<Tile> PlayerRack = player1.getRack();
            System.out.println("Player rack:");
            for (Tile tile : PlayerRack) {
                System.out.println(tile);
            }
        }
    }
    

}