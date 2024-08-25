package camilo.opertationquasarfire.Utils;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class UtilRebuilMessage {

    public static String rebuilMessage(List<List<String>> messages) {
        int maxLength = 0;
        for (List<String> message : messages) {
            maxLength = Math.max(maxLength, message.size());
        }
        List<List<String>> wordList = new ArrayList<>();
        for (int i = 0; i < maxLength; i++) {
            wordList.add(new ArrayList<>());
        }
        for (List<String> message : messages) {
            for (int i = 0; i < message.size(); i++) {
                if (!message.get(i).isEmpty()) {
                    wordList.get(i).add(message.get(i));
                    // wordList.get(i).add(message.get(i));
                    /*
                     * if(i>0 && !message.get(i).isEmpty())
                     * wordList.get(i-1).add(message.get(i));
                     * if(i<message.size()-1 && !message.get(i).isEmpty())
                     * wordList.get(i+1).add(message.get(i));
                     */

                }
            }
        }
        String mostCommonWord = "";
        List<String> reconstructedMessage = new ArrayList<>();
        for (List<String> words : wordList) {
            // while(words.contains(mostCommonWord)) words.remove(mostCommonWord);
            words.remove(mostCommonWord);
            // words.remove(mostCommonWord);
            mostCommonWord = findMostCommonWord(words);
            if (!mostCommonWord.isEmpty())
                reconstructedMessage.add(mostCommonWord);
        }
        return String.join(" ", reconstructedMessage);

    }

    private static String findMostCommonWord(List<String> words) {
        List<String> uniqueWords = new ArrayList<>(words);
        uniqueWords.removeIf(String::isEmpty);
        uniqueWords = new ArrayList<>(new LinkedHashSet<>(uniqueWords));
        int maxFrequency = 0;
        String mostCommonWord = "";
        for (String word : uniqueWords) {
            int frequency = 0;
            for (String w : words) {
                if (word.equals(w)) {
                    frequency++;
                }
            }
            if (frequency > maxFrequency) {
                maxFrequency = frequency;
                mostCommonWord = word;
            }
        }
        return mostCommonWord;
    }
}
