package uk.me.paulriley.railwaychildren;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class FrequencyScanner {

    public Map<String, Integer> wordList = new TreeMap<>();

    private String[] invalidCharacters = {")", "*", "'", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "_", "-"};

    public void scanLine(String readLine) {
        readLine = readLine.replace("--", " ");
        String[] words = readLine.split("[.,\\s!;()?:\"]+");

        for (String word: words) {
            if (validWord(word)) {
                String sanitisedWord = word.toLowerCase(Locale.getDefault());

                if (wordList.containsKey(sanitisedWord)) {
                    Integer count = wordList.get(sanitisedWord);
                    wordList.replace(sanitisedWord, count, count + 1);
                } else {
                    wordList.put(sanitisedWord, 1);
                }
            }
        }
    }

    public boolean validWord(String word) {
        if (word.length() == 0) return false;
        if (Arrays.asList(invalidCharacters).contains(word)) return false;
        return true;
    }

    public String displayFrequencyList() {
        StringBuilder output = new StringBuilder();

        for (Map.Entry<String, Integer> entry: wordList.entrySet()) {
            output.append(String.format(Locale.getDefault(), "%20s\t%5d\t%s\n", entry.getKey(), entry.getValue(), displayPrime(entry)));
        }

        return output.toString();
    }

    public String displayPrime(Map.Entry<String, Integer> entry) {
        if (entry.getValue() < 2) return "";

        for (int i = 2; i <= entry.getValue() / 2; i++) {
            if (entry.getValue() % i == 0) {
                return "";
            }
        }

        return "prime";
    }
}
