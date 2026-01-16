package main.pij.model;                                                                                                                                                                                                                                                                                               
import java.io.*;                                                                                                                                         
import java.util.HashSet;                                                                                                                                 
import java.util.Set;

public class WordList {
    private static final String DEFAULT_WORDLIST = "resources/wordlist.txt";
    private static Set<String> words;

    // Static method to load words.
    public static void loadWords() {
        loadWords(DEFAULT_WORDLIST);
    }

    public static void loadWords(String filename) {
        words = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line.trim().toLowerCase());
            }
            System.out.println("Loaded " + words.size() + " words from " + filename);
        } catch (IOException e) {
            System.out.println("Error loading wordlist: " + e.getMessage());
        }
    }
    public static boolean isValidWord(String word) {
        return words.contains(word.toLowerCase());
    }   
}    