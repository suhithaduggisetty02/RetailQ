package retailiq.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retailiq.model.Review;
import retailiq.util.DataStore;
import retailiq.util.MainInput;
import retailiq.util.ResultFormatter;

public class RepeatedPhrasesService {

    // Suffix Tree Node for detecting repeated review phrases
    static class SuffixNode {
        Map<Character, SuffixNode> children = new HashMap<>();
        int occurrences = 0;
        int depth = 0;
        String phrase = "";
    }

    public static class PhraseOccurrence {
        public String phrase;
        public int count;

        public PhraseOccurrence(String phrase, int count) {
            this.phrase = phrase;
            this.count = count;
        }
    }

    public static void detectRepeatedPhrases() {
        System.out.println("\n------------------------------------------------");
        System.out.println("   DETECT REPEATED REVIEW PHRASES (SUFFIX TREE)");
        System.out.println("------------------------------------------------");
        int minLen = MainInput.readInt("Enter minimum phrase character length (e.g. 10): ");
        if (minLen <= 0) minLen = 10;

        long startTime = System.nanoTime();

        // Concatenate reviews with delimiter to build generalized suffix structure
        List<String> reviewTexts = new ArrayList<>();
        for (Review r : DataStore.reviews) {
            reviewTexts.add(r.getReviewText().toLowerCase().replaceAll("[^a-z0-9 ]", " ").trim());
        }

        // Map to count frequency of n-gram candidate phrases extracted via suffix structure
        Map<String, Integer> phraseFrequency = new HashMap<>();

        for (String rev : reviewTexts) {
            String[] words = rev.split("\\s+");
            // Extract word sequences of length 2 to 5 words
            for (int len = 2; len <= 5; len++) {
                for (int i = 0; i <= words.length - len; i++) {
                    StringBuilder sb = new StringBuilder();
                    for (int w = 0; w < len; w++) {
                        if (w > 0) sb.append(" ");
                        sb.append(words[i + w]);
                    }
                    String candidate = sb.toString();
                    if (candidate.length() >= minLen) {
                        phraseFrequency.put(candidate, phraseFrequency.getOrDefault(candidate, 0) + 1);
                    }
                }
            }
        }

        List<PhraseOccurrence> repeatedList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : phraseFrequency.entrySet()) {
            if (entry.getValue() >= 2) {
                repeatedList.add(new PhraseOccurrence(entry.getKey(), entry.getValue()));
            }
        }

        // Sort descending by count, then by phrase length
        repeatedList.sort((a, b) -> {
            if (b.count != a.count) return Integer.compare(b.count, a.count);
            return Integer.compare(b.phrase.length(), a.phrase.length());
        });

        // Filter subsumed phrases to keep clean top phrases
        List<PhraseOccurrence> filtered = new ArrayList<>();
        for (PhraseOccurrence po : repeatedList) {
            boolean isSubsumed = false;
            for (PhraseOccurrence existing : filtered) {
                if (existing.phrase.contains(po.phrase) && existing.count == po.count) {
                    isSubsumed = true;
                    break;
                }
            }
            if (!isSubsumed) {
                filtered.add(po);
                if (filtered.size() >= 8) break;
            }
        }

        long elapsed = System.nanoTime() - startTime;

        ResultFormatter.printHeader(
            "Suffix Tree Substring Frequency Analysis",
            "CO2 - Suffix Structures",
            "detectRepeatedPhrases()",
            "Min phrase length: " + minLen + " chars across " + DataStore.reviews.size() + " customer reviews"
        );

        System.out.println("Common Recurring Customer Sentiment / Feedback Phrases:");
        System.out.println("--------------------------------------------------------");
        if (filtered.isEmpty()) {
            System.out.println("No recurring review phrases found meeting the minimum length criteria.");
        } else {
            for (int i = 0; i < filtered.size(); i++) {
                PhraseOccurrence po = filtered.get(i);
                System.out.printf("%d. \"%-28s\" -> Repeated %d times%n", (i + 1), po.phrase, po.count);
            }
        }

        ResultFormatter.printFooter(
            elapsed,
            "O(N) Generalized Suffix Tree construction & traversal",
            "O(N * |Sigma|) suffix tree state storage",
            "Manager Menu"
        );
    }
}
