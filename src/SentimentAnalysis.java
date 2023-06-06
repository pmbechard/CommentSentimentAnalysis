import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SentimentAnalysis {
    private HashMap<String, Double> map;
    private double minScore;
    private double maxScore;

    /**
     * Initializes map with key-value pairs representing words and their sentiment scores
     * Precondition: A dataset must be available in root directory in a file called "data.csv"
     * with each line consisting of a word (String) and its sentiment score (Double) separated
     * by commas.
     */
    public SentimentAnalysis() {
        this.map = new HashMap<>();
        try {
            Scanner sc = new Scanner(new File("data.csv"));
            while (sc.hasNextLine()) {
                String[] line = sc.nextLine().split(",");
                map.put(line[0], Double.valueOf(line[1]));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Data file not found.");
        } catch (InputMismatchException e) {
            System.out.println("Data file improperly formatted.");
        }

        minScore = Double.MAX_VALUE;
        maxScore = Double.MIN_VALUE;
        for (Double val : map.values()) {
            if (val < minScore) minScore = val;
            if (val > maxScore) maxScore = val;
        }
    }

    /**
     * Analyses an array of words and finds the average sentiment score of the collection.
     * @param text - A text passage.
     * @return score - The total sentiment score divided by the number of scored words.
     */
    public Double getRating(String text) {
        if (text == null) return 0.0;
        double sum = 0;
        int wordCount = 0;
        for (String word : text.split(" ")) {
            if (word == null) continue;
            word = cleanWord(word);
            if (map.containsKey(word)) {
                double score = ((map.get(word) + Math.abs(minScore)) / (maxScore + Math.abs(minScore))) * 5;
                sum += score;
                wordCount++;
            }
        }
        if (wordCount == 0) return 0.0;
        return Math.round((sum / wordCount) * 100) / 100.0;
    }

    /**
     * Removes all non-alphabetic characters from a word and changes the remaining characters to lowercase.
     * @param word - A String representing any word in a passage.
     * @return cleanedWord - The same word with non-alphabetic characters removed and in lowercase letters.
     */
    private String cleanWord(String word) {
        StringBuilder cleanedWord = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            char letter = word.charAt(i);
            if (Character.isAlphabetic(letter)) {
                cleanedWord.append(letter);
            }
        }
        return cleanedWord.toString().toLowerCase();
    }
}
