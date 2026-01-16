package main.pij.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordCells {
    public final String word;
    public final List<int[]> cells; // cada int[] = {row, col}

    public WordCells(String word, List<int[]> cells) {
        this.word = word;
        this.cells = cells;
    }
}
