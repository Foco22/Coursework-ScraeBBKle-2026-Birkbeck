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

    public boolean SearchMove(int currentPlayerNum, 
                           Board board, 
                           List<Tile> Tiles,
                           GameManager game,
                           int[] StartPosition,
                           int countTurns,
                           Player currentPlayer,
                           Bag bag
                           ) {


        Cell[][] cells = board.getBoard();    
                                                                                                               
        // First: iterate HORIZONTALLY (each row, left to right)                                                                                                
        //for (int row = 0; row < cells.length; row++) {                                                                                                          
        //    for (int col = 0; col < cells[row].length; col++) {                                                                                                 
        //        Cell cell = cells[row][col];  
        //        char letter = cell.getLetter();                                                                                                                    
        //
        //        if (letter != '.') {                                                                                                                               
        //
        //            // Get the word and the Start and final position. This is the word that i get and now i need to use it to work with my rack                                                                          
        //            boolean isWordStart = (letter != '.') &&                                                                                                           
        //            (col == 0 || cells[row][col - 1].getLetter() == '.');                                                                        
        //                                                                                                                                                
        //            if (isWordStart) {                                                                                                                                 
        //                                                                                                                
        //                String wordBoard = "";                                                                                                      
        //                int endCol = col;                                                                                                                                  
        //                while (endCol < cells[row].length && cells[row][endCol].getLetter() != '.') {                                                                            
        //                    wordBoard = wordBoard + cells[row][endCol].getLetter();                                                                                                
        //                    endCol++;                                                                                                                                       
        //                };  
                                                                                                                                                                   
                        // Skip to end of word                                                                                                                     
        //                endCol--;   
        //                int startCol = col;      
                        
                        // Algorthimo to iterate based on the word
        //                boolean CheckCondition = IterationSearch(wordBoard, row, startCol, endCol, Tiles, board, game, StartPosition, countTurns, currentPlayer, bag);
        //                if (CheckCondition) {                                                                                                                              
        //                    return true;  // Ya encontró, parar                                                                                                   
        //                }   
        //            }                                                                                                                
        //        }                   
        //    }                                                                                                                                                       
        //}
        

        //Second : iterate VERTICAALLY (each column, left to right)                                                                                                
        for (int column = 0; column < cells.length; column++) {                                                                                                          
            for (int row = 0; row < cells[row].length; row++) {                                                                                                 
                Cell cell = cells[row][column];  
                char letter = cell.getLetter();                                                                                                                    
        
                if (letter != '.') {                                                                                                                               
        
                    // Get the word and the Start and final position. This is the word that i get and now i need to use it to work with my rack                                                                          
                    boolean isWordStart = (letter != '.') &&                                                                                                           
                    (row == 0 || cells[row-1][column].getLetter() == '.');                                                                        
                                                                                                                                                        
                    if (isWordStart) {                                                                                                                                 
                                                                                                                        
                        String wordBoard = "";                                                                                                      
                        int endRow = row;                                                                                                                                  
                        while (endRow < cells.length && cells[endRow][column].getLetter() != '.') {                                                                            
                            wordBoard = wordBoard + cells[endRow][column].getLetter();                                                                                                
                            endRow++;                                                                                                                                       
                        };  
                                                                                                                                                                   
                        // Skip to end of word                                                                                                                     
                        endRow--;   
                        int startRow = row;      
                        
                        // Algorthimo to iterate based on the word
                        boolean CheckCondition = IterationSearchVertical(wordBoard, column, startRow, endRow, Tiles, board, game, StartPosition, countTurns, currentPlayer, bag);
                        if (CheckCondition) {                                                                                                                              
                            return true;  // Ya encontró, parar                                                                                                   
                        }   
                    }                                                                                                                
                }                   
            }                                                                                                                                                       
        }
    
        
        return false; 

    }

    // Trying to build it before to get a only one function
    public boolean IterationSearchVertical(String word, 
                                int column, 
                                int startRow, 
                                int endRow , 
                                List<Tile> Tiles,
                                Board board,
                                GameManager game,
                                int[] StartPosition,
                                int countTurns,
                                Player currentPlayer,
                                Bag bag
                                ) {

        // The idea os the algoritmo is that if it detect a word, so it is moving like this:
        // R
        // R_
        // R_ _
        // R _ _ _
        // when it is finish i go to the 
        // _ R_
        // _ R _ _
        // _ R _ _ _
        // etc
        // As it can see, it get the word from start to end, and then i got iterative for the previous empty space to create word based on my tiles
        // it is neccesary to validate if the word create not generate a other currerente here.
        // Convert tiles to string                                                                                                                            
        
        String myTiles = "";                                                                                                                                  
        for (Tile tile : Tiles) {                                                                                                                             
            myTiles += tile.getLetter();                                                                                                                      
        }         
        int MaxTiles = Tiles.size();
        for (int tilesBefore = 0; tilesBefore <= MaxTiles; tilesBefore++) {
            for (int tilesAfter = 0; tilesAfter <= MaxTiles - tilesBefore; tilesAfter++) {

              // Skip if no tiles to place
              if (tilesBefore + tilesAfter == 0) continue;

              // Calcular nueva posición                                                                                                                    
              int newStartRow = startRow - tilesBefore;                                                                                                     
              int newEndRow = endRow + tilesAfter;    

              if (newStartRow < 0 || newEndRow >= board.getBoard()[0].length) {                                                                                 
                continue;  // Se sale del tablero, saltar                                                                                                     
               }                                                                                                                                                 
               
              // Build pattern: "___" + word + "__"                                                                                                         
              String pattern = "_".repeat(tilesBefore) + word + "_".repeat(tilesAfter);                                                                     
              List<String> validWords = new ArrayList<>();  
              System.out.println(pattern);

              tryAllCombinations(pattern, myTiles, 0, "", validWords);                                                                                      
                                                                                                                                                                          
              // Start the validate the word. 
              for (String validWord : validWords) {                                                                                                         
                // Step 0: Get the board before the move.
                Map<String, Integer> MapWordBefore = board.getAllWordsOnBoard();
                boolean EmptyMapWord = game.CheckEmptyMapWord(MapWordBefore);

                // Define start position (Vertical: number+letter like "5e")
                String position = "" + (char)('a' + column) + (newStartRow + 1); 

                System.out.println("DEBUG: Trying word '" + validWord + "' at position '" + position + "'");

                // Step 1: Put the word in the board.
                Set<String> newlyPlacedCells = new HashSet<>();
                char[][] boardBefore = board.snapshotLetters();
                boolean CheckPlaceWord = board.placeWord(validWord, position, StartPosition, countTurns, newlyPlacedCells, EmptyMapWord);
                
                if (!CheckPlaceWord) {
                    // Invalid words - keep same player's turn
                    // Need to store the board as the placeWord method is adding the word to the board
                    board.restoreLetters(boardBefore);
                    //game.showInvalidMoveMessage(validWord, position);
                    continue;
                }

                // 2 Step 2: Get the word generate in the board.
                WordCells WordCellsPlayer = board.getWordAt(position);
                String WordPlayer = WordCellsPlayer.word;
                List<int[]> WordPlayerCells = WordCellsPlayer.cells;

                // 3 Step 3: Check if the word generate is validate in the WordList.
                boolean CheckWordPlayer = WordList.isValidWord(WordPlayer);
                if (!CheckWordPlayer) {
                    board.restoreLetters(boardBefore);
                    continue;
                }

                // 4 Step 4: Getting the new word with the movement of the player.
                Map<String, Integer> MapWordAfter = board.getAllWordsOnBoard();

                // If it was generated more than 1 word based on the new word, i need to cancel the movement and going back to the previous board.
                boolean ValidatedMovement = board.isValidMove(MapWordBefore, MapWordAfter);
                if (!ValidatedMovement) {
                    // First - Restorate the movement 
                    // Solve - It provide a restore the board when the movement is not 100%, as create more currence than it is allowed. 
                    board.restoreLetters(boardBefore);
                    continue;
                }
                
                // 5 Step 5: Count the point if the movement was sucesseed.
                String RowInputPlayerWord = validWord;
                Integer ScorePlayerTurn = game.scoreWord(board, WordCellsPlayer, newlyPlacedCells, RowInputPlayerWord);
                currentPlayer.addScore(ScorePlayerTurn);
                currentPlayer.refillRack(bag);    
                return true;  
                                                                                      
              }  

            }

        }
        return false; 
    }


    public boolean IterationSearch(String word, 
                                int row, 
                                int startCol, 
                                int endCol , 
                                List<Tile> Tiles,
                                Board board,
                                GameManager game,
                                int[] StartPosition,
                                int countTurns,
                                Player currentPlayer,
                                Bag bag
                                ) {

        // The idea os the algoritmo is that if it detect a word, so it is moving like this:
        // R
        // R_
        // R_ _
        // R _ _ _
        // when it is finish i go to the 
        // _ R_
        // _ R _ _
        // _ R _ _ _
        // etc
        // As it can see, it get the word from start to end, and then i got iterative for the previous empty space to create word based on my tiles
        // it is neccesary to validate if the word create not generate a other currerente here.
        // Convert tiles to string                                                                                                                            
        
        String myTiles = "";                                                                                                                                  
        for (Tile tile : Tiles) {                                                                                                                             
            myTiles += tile.getLetter();                                                                                                                      
        }         
        int MaxTiles = Tiles.size();
        for (int tilesBefore = 0; tilesBefore <= MaxTiles; tilesBefore++) {
            for (int tilesAfter = 0; tilesAfter <= MaxTiles - tilesBefore; tilesAfter++) {

              // Skip if no tiles to place
              if (tilesBefore + tilesAfter == 0) continue;

              // Calcular nueva posición                                                                                                                    
              int newStartCol = startCol - tilesBefore;                                                                                                     
              int newEndCol = endCol + tilesAfter;    

              if (newStartCol < 0 || newEndCol >= board.getBoard()[0].length) {                                                                                 
                continue;  // Se sale del tablero, saltar                                                                                                     
               }                                                                                                                                                 
               
              // Build pattern: "___" + word + "__"                                                                                                         
              String pattern = "_".repeat(tilesBefore) + word + "_".repeat(tilesAfter);                                                                     
              List<String> validWords = new ArrayList<>();  
              System.out.println(pattern);

              tryAllCombinations(pattern, myTiles, 0, "", validWords);                                                                                      
                                                                                                                                                                          
              // Start the validate the word. 
              for (String validWord : validWords) {                                                                                                         
                // Step 0: Get the board before the move.
                Map<String, Integer> MapWordBefore = board.getAllWordsOnBoard();
                boolean EmptyMapWord = game.CheckEmptyMapWord(MapWordBefore);

                // Define start position (horizontal: number+letter like "5e")
                String position = "" + (row + 1) + (char)('a' + newStartCol);

                System.out.println("DEBUG: Trying word '" + validWord + "' at position '" + position + "'");

                // Step 1: Put the word in the board.
                Set<String> newlyPlacedCells = new HashSet<>();
                char[][] boardBefore = board.snapshotLetters();
                boolean CheckPlaceWord = board.placeWord(validWord, position, StartPosition, countTurns, newlyPlacedCells, EmptyMapWord);
                
                if (!CheckPlaceWord) {
                    // Invalid words - keep same player's turn
                    // Need to store the board as the placeWord method is adding the word to the board
                    board.restoreLetters(boardBefore);
                    //game.showInvalidMoveMessage(validWord, position);
                    continue;
                }

                // 2 Step 2: Get the word generate in the board.
                WordCells WordCellsPlayer = board.getWordAt(position);
                String WordPlayer = WordCellsPlayer.word;
                List<int[]> WordPlayerCells = WordCellsPlayer.cells;

                // 3 Step 3: Check if the word generate is validate in the WordList.
                boolean CheckWordPlayer = WordList.isValidWord(WordPlayer);
                if (!CheckWordPlayer) {
                    board.restoreLetters(boardBefore);
                    continue;
                }

                // 4 Step 4: Getting the new word with the movement of the player.
                Map<String, Integer> MapWordAfter = board.getAllWordsOnBoard();

                // If it was generated more than 1 word based on the new word, i need to cancel the movement and going back to the previous board.
                boolean ValidatedMovement = board.isValidMove(MapWordBefore, MapWordAfter);
                if (!ValidatedMovement) {
                    // First - Restorate the movement 
                    // Solve - It provide a restore the board when the movement is not 100%, as create more currence than it is allowed. 
                    board.restoreLetters(boardBefore);
                    continue;
                }
                
                // 5 Step 5: Count the point if the movement was sucesseed.
                String RowInputPlayerWord = validWord;
                Integer ScorePlayerTurn = game.scoreWord(board, WordCellsPlayer, newlyPlacedCells, RowInputPlayerWord);
                currentPlayer.addScore(ScorePlayerTurn);
                currentPlayer.refillRack(bag);    
                return true;  
                                                                                      
              }  

            }

        }
        return false; 
    }

    private void tryAllCombinations(String pattern, String availableTiles, int index, String current, List<String> validWords) {                               
 
        if (index == pattern.length()) {                                                                                                                       
            // Check if valid word                                                                                                                             
            if (WordList.isValidWord(current)) {                                                                                                               
                validWords.add(current);                                                                                                                       
            }                                                                                                                                                  
            return;                                                                                                                                            
        }                                                                                                                                                      
                                                                                                                                                               
        char c = pattern.charAt(index);                                                                                                                        
        if (c == '_') {                                                                                                                                        
            // Try each letter                                                                                                                
            for (int i = 0; i < availableTiles.length(); i++) {                                                                                                
                char tile = availableTiles.charAt(i);                                                                                                          
                // Must remove a element of the tile, so it can not count it again.                                                                                                          
                String remaining = availableTiles.substring(0, i) + availableTiles.substring(i + 1);                                                           
                tryAllCombinations(pattern, remaining, index + 1, current + tile, validWords);                                                                 
            }                                                                                                                                                  
        } else {                                                                                                                                               
            // If the letter is from board, i move on it.                                                                                                       
            tryAllCombinations(pattern, availableTiles, index + 1, current + c, validWords);                                                                   
        } 
    }   
}