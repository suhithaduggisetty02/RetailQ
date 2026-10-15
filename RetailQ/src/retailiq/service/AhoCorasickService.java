package retailiq.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import retailiq.model.Product;
import retailiq.model.Review;
import retailiq.util.DataStore;
import retailiq.util.MainInput;
import retailiq.util.ResultFormatter;

public class AhoCorasickService {

    static class TrieNode {
        Map<Character, TrieNode> next = new HashMap<>();
        TrieNode fail = null;
        List<String> matchedPatterns = new ArrayList<>();
    }

    private static TrieNode root;

    public static void buildAutomaton(String[] patterns) {
        root = new TrieNode();

        for (String pattern : patterns) {
            String pat = pattern.trim().toLowerCase();
            if (pat.isEmpty()) continue;
            TrieNode current = root;
            for (int i = 0; i < pat.length(); i++) {
                char c = pat.charAt(i);
                current.next.putIfAbsent(c, new TrieNode());
                current = current.next.get(c);
            }
            current.matchedPatterns.add(pat);
        }

        Queue<TrieNode> queue = new LinkedList<>();
        for (TrieNode child : root.next.values()) {
            child.fail = root;
            queue.add(child);
        }

        while (!queue.isEmpty()) {
            TrieNode current = queue.poll();

            for (Map.Entry<Character, TrieNode> entry : current.next.entrySet()) {
                char c = entry.getKey();
                TrieNode child = entry.getValue();

                TrieNode fallback = current.fail;
                while (fallback != null && !fallback.next.containsKey(c)) {
                    fallback = fallback.fail;
                }

                if (fallback != null && fallback.next.containsKey(c)) {
                    child.fail = fallback.next.get(c);
                } else {
                    child.fail = root;
                }

                if (child.fail != null) {
                    child.matchedPatterns.addAll(child.fail.matchedPatterns);
                }

                queue.add(child);
            }
        }
    }

    public static List<String> matchText(String text) {
        List<String> found = new ArrayList<>();
        if (root == null || text == null) return found;

        String t = text.toLowerCase();
        TrieNode current = root;

        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            while (current != root && !current.next.containsKey(c)) {
                current = current.fail;
            }

            if (current.next.containsKey(c)) {
                current = current.next.get(c);
            } else {
                current = root;
            }

            if (!current.matchedPatterns.isEmpty()) {
                for (String p : current.matchedPatterns) {
                    if (!found.contains(p)) {
                        found.add(p);
                    }
                }
            }
        }
        return found;
    }

    public static void analyzeReviewKeywords() {
        System.out.println("\n----------------------------------------");
        System.out.println("     ANALYZE REVIEW KEYWORDS (AHO-CORASICK)");
        System.out.println("----------------------------------------");
        System.out.println("Default Retail Keywords: battery, noise, sound, comfortable, keyboard, quality, mouse, fast");
        String input = MainInput.readString("Enter custom keywords (comma-separated, or press Enter for default): ");
        if (input.trim().isEmpty()) {
            input = "battery, noise, sound, comfortable, keyboard, quality, mouse, fast";
        }

        String[] rawKeywords = input.split(",");
        List<String> cleaned = new ArrayList<>();
        for (String k : rawKeywords) {
            String s = k.trim().toLowerCase();
            if (!s.isEmpty()) cleaned.add(s);
        }
        String[] keywords = cleaned.toArray(new String[0]);

        long startTime = System.nanoTime();
        buildAutomaton(keywords);

        Map<String, Integer> keywordCounts = new HashMap<>();
        for (String k : keywords) keywordCounts.put(k, 0);

        List<String> matchedReviewSummaries = new ArrayList<>();

        for (Review review : DataStore.reviews) {
            List<String> matches = matchText(review.getReviewText());
            if (!matches.isEmpty()) {
                for (String m : matches) {
                    keywordCounts.put(m, keywordCounts.getOrDefault(m, 0) + 1);
                }
                matchedReviewSummaries.add(String.format("Review for Product #%d by %s [%s] -> Keywords: %s",
                    review.getProductId(), review.getCustomerName(), review.getReviewText(), matches));
            }
        }
        long elapsed = System.nanoTime() - startTime;

        ResultFormatter.printHeader(
            "Aho-Corasick Multi-Pattern Automaton",
            "CO1 - String Algorithms",
            "analyzeReviewKeywords()",
            String.join(", ", keywords)
        );

        System.out.println("Keyword Frequency Distribution Across Reviews:");
        System.out.println("----------------------------------------------");
        for (String k : keywords) {
            int count = keywordCounts.getOrDefault(k, 0);
            System.out.printf("• '%-14s': %2d occurrence(s)%n", k, count);
        }

        System.out.println("\nMatching Review Details (" + matchedReviewSummaries.size() + " reviews):");
        System.out.println("----------------------------------------------");
        for (String summary : matchedReviewSummaries) {
            System.out.println(summary);
        }

        ResultFormatter.printFooter(
            elapsed,
            "O(N + sum(M_i) + Z) where N is text length, M_i pattern lengths, Z occurrences",
            "O(sum(M_i) * |Sigma|) for Trie and failure links",
            "Manager Menu"
        );
    }

    public static void searchMultipleKeywords() {
        System.out.println("\n----------------------------------------");
        System.out.println("     SEARCH MULTIPLE KEYWORDS (AHO-CORASICK)");
        System.out.println("----------------------------------------");
        String input = MainInput.readString("Enter comma-separated keywords (e.g. bluetooth, gaming, watch): ");
        if (input.trim().isEmpty()) {
            System.out.println("Keywords cannot be empty.");
            return;
        }

        String[] raw = input.split(",");
        List<String> cleaned = new ArrayList<>();
        for (String k : raw) {
            String s = k.trim().toLowerCase();
            if (!s.isEmpty()) cleaned.add(s);
        }
        String[] keywords = cleaned.toArray(new String[0]);

        long startTime = System.nanoTime();
        buildAutomaton(keywords);

        List<Product> matchedProducts = new ArrayList<>();
        Map<Integer, List<String>> productKeywordMap = new HashMap<>();

        for (Product p : DataStore.products) {
            String combinedText = p.getName() + " " + p.getDescription() + " " + p.getCategory();
            List<String> found = matchText(combinedText);
            if (!found.isEmpty()) {
                matchedProducts.add(p);
                productKeywordMap.put(p.getId(), found);
            }
        }
        long elapsed = System.nanoTime() - startTime;

        ResultFormatter.printHeader(
            "Aho-Corasick Multi-Pattern Search",
            "CO1 - String Algorithms",
            "searchMultipleKeywords()",
            String.join(", ", keywords)
        );

        System.out.println("Matching Products in Catalog (" + matchedProducts.size() + " found):");
        if (matchedProducts.isEmpty()) {
            System.out.println("No products matched the given keywords.");
        } else {
            for (Product p : matchedProducts) {
                System.out.printf("• [%d] %-30s | Matches: %s%n", p.getId(), p.getName(), productKeywordMap.get(p.getId()));
            }
        }

        ResultFormatter.printFooter(
            elapsed,
            "O(N + sum(M_i) + Z)",
            "O(sum(M_i) * |Sigma|)",
            "Customer Menu"
        );
    }
}