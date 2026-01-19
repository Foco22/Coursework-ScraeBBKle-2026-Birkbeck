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

    public String FirstMoveWord(int currentPlayerNum) {

        System.out.println("currentPlayerNum");
        String playerWords = "";                                                                                                                              
        List<Tile> PlayerRack;                                                                                                                                
                                  
        if (currentPlayerNum == 1) {
            PlayerRack = player1.getRack();
        }
        else {
            PlayerRack = player2.getRack();
        }

        System.out.println("Player rack:");
        for (Tile tile : PlayerRack) {
            System.out.println(tile);
            playerWords += tile.getLetter();
        }

        // Max to min size of the player Rack   
        for (int i = PlayerRack.size(); i >= 0; i--) {
            Set<String> PermutationWords = new HashSet<>();                                                                                                                     
            WordPermutations.permuteLenN(playerWords, "", PermutationWords, i);
            
            // for each permutation is calculate the possible word.
            for (String word : PermutationWords) {
                if (word.contains("_")) {
                    for (char c = 'a'; c <= 'z'; c++) {
                        String expanded = word.replace('_', c);
                        boolean checkWord = WordList.isValidWord(expanded);
                        if (checkWord) {
                            return expanded;
                        }
                    }
                } else {
                    boolean checkWord = WordList.isValidWord(word);
                    if (checkWord) {
                        return word;
                    }
                }
            }
        }
        return null;  // No valid word found
    }

    public void SearchMove(int currentPlayerNum, Board board, List<Tile> Tiles) {

        Cell[][] cells = board.getBoard();    
                                                                                                               
        // First: iterate HORIZONTALLY (each row, left to right)                                                                                                
        for (int row = 0; row < cells.length; row++) {                                                                                                          
            for (int col = 0; col < cells[row].length; col++) {                                                                                                 
                Cell cell = cells[row][col];  
                char letter = cell.getLetter();                                                                                                                    

                if (letter != '.') {                                                                                                                               

                    // Get the word and the Start and final position. This is the word that i get and now i need to use it to work with my rack                                                                          
                    boolean isWordStart = (letter != '.') &&                                                                                                           
                    (col == 0 || cells[row][col - 1].getLetter() == '.');                                                                        
                                                                                                                                                        
                    if (isWordStart) {                                                                                                                                 
                                                                                                                        
                        String wordBoard = "";                                                                                                      
                        int endCol = col;                                                                                                                                  
                        while (endCol < cells[row].length && cells[row][endCol].getLetter() != '.') {                                                                            
                            wordBoard = wordBoard + cells[row][endCol].getLetter();                                                                                                
                            endCol++;                                                                                                                                       
                        };  
                        System.out.println("Word: " + wordBoard);                                                                                                  
                        System.out.println("Row: " + row + ", Start Col: " + col);                                                                                 
                                                                                                                                                                   
                        // Skip to end of word                                                                                                                     
                        endCol--;   
                        int startCol = col;      
                        
                        // Algorthimo to iterate based on the word
                        IterationSearch(wordBoard, row, startCol, endCol, Tiles);
                    }                                                                                                                
                }                   
            }                                                                                                                                                       
        
        }
    }

    public void IterationSearch(String word, int row, int startCol, int endCol , List<Tile> Tiles) {

        // The idea os the algoritmo is that if it detect a word, so it is moving like this:
        // _R
        // _R_
        // _ _ R _
        // _ _ R _ _
        // _ _ _ R _ _
        // As it can see, it get the word from start to end, and then i got iterative for the previous empty space to create word based on my tiles
        // it is neccesary to validate if the word create not generate a other currerente here.
        // Convert tiles to string                                                                                                                            
        
        String myTiles = "";                                                                                                                                  
        for (Tile tile : Tiles) {                                                                                                                             
            myTiles += tile.getLetter();                                                                                                                      
        }         
        System.out.println(myTiles);
        int MaxTiles = Tiles.size();
        for (int tilesBefore = 0; tilesBefore <= MaxTiles; tilesBefore++) {                                                                                   
            for (int tilesAfter = 0; tilesAfter <= MaxTiles - tilesBefore; tilesAfter++) {              

              // Build pattern: "___" + word + "__"                                                                                                         
              String pattern = "_".repeat(tilesBefore) + word + "_".repeat(tilesAfter);                                                                     
              List<String> validWords = new ArrayList<>();  

              tryAllCombinations(pattern, myTiles, 0, "", validWords);                                                                                      
                                                                                                                                                            
              for (String validWord : validWords) {                                                                                                         
                  System.out.println("Valid word found: " + validWord);                                                                                     
              }                                                                                                                                             
            }                                                                                                                                       
        }
    }

    private void tryAllCombinations(String pattern, String availableTiles, int index, String current, List<String> validWords) {                               
                                                                                                                                                              
    }   
}