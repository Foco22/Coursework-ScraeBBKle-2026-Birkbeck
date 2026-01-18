package main.pij.service;

import main.pij.model.Board;
import main.pij.model.Bag;
import main.pij.model.Player;
import main.pij.model.WordList;
import main.pij.model.WordCells;
import main.pij.model.Cell;


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


    public ComputerMove(Board board, Player player1, Player player2, Bag bag) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        this.bag = bag;
    }
    

    

}