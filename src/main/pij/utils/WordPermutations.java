package main.pij.utils;
import java.util.HashSet;
import java.util.Set;

public class WordPermutations {

    //public static void main(String[] args) {
    //    Set<String> result = new HashSet<>();
    //    permuteLenN("abcd", "", result, 2   );
    //    System.out.println("Total: " + result.size());
    //    System.out.println(result);
    //}

    public static void permuteLenN(String remaining, 
                                   String prefix, Set<String> result,
                                   Integer N ) {

        // Save only with large N.
        if (prefix.length() == N) {
            result.add(prefix);
            return;
        }

        for (int i = 0; i < remaining.length(); i++) {
            permuteLenN(
                remaining.substring(0, i) + remaining.substring(i + 1),
                prefix + remaining.charAt(i),
                result, N
            );
        }
    }
}
