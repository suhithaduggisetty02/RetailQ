package retailiq.service;

import java.util.ArrayList;
import java.util.List;

import retailiq.model.Product;
import retailiq.util.DataStore;
import retailiq.util.MainInput;
import retailiq.util.ResultFormatter;

public class SearchSuggestionsService {

    public static class Suggestion {
        public String candidate;
        public int distance;
        public String category;

        public Suggestion(String candidate, int distance, String category) {
            this.candidate = candidate;
            this.distance = distance;
            this.category = category;
        }
    }

    public static void generateSearchSuggestions(String returnMenu) {
        System.out.println("\n------------------------------------------------");
        System.out.println("   VIEW SEARCH SUGGESTIONS & AUTOCORRECT (CO3)");
        System.out.println("------------------------------------------------");
        System.out.println("Select Edit Distance Algorithm:");
        System.out.println("1. Levenshtein Distance (Insert, Delete, Substitute)");
        System.out.println("2. Damerau-Levenshtein Distance (Includes Transpositions)");
        System.out.println("3. Back");
        int algoChoice = MainInput.readInt("Enter choice: ");
        if (algoChoice == 3) return;

        String algoName = (algoChoice == 2) ? "Damerau-Levenshtein" : "Levenshtein";

        String query = MainInput.readString("Enter potentially misspelled search query (e.g. headfone, mouce, keybaord): ");
        if (query.isEmpty()) {
            System.out.println("Query cannot be empty.");
            return;
        }

        long startTime = System.nanoTime();
        List<Suggestion> suggestions = new ArrayList<>();

        for (Product p : DataStore.products) {
            int distName = (algoChoice == 2)
                ? DamerauLevenshteinService.distance(query, p.getName())
                : LevenshteinService.distance(query, p.getName());

            suggestions.add(new Suggestion(p.getName(), distName, p.getCategory()));

            // Also check individual tokens of the product name
            String[] tokens = p.getName().split("\\s+");
            for (String tok : tokens) {
                if (tok.length() >= 3) {
                    int distTok = (algoChoice == 2)
                        ? DamerauLevenshteinService.distance(query, tok)
                        : LevenshteinService.distance(query, tok);
                    suggestions.add(new Suggestion(tok, distTok, p.getCategory()));
                }
            }
        }

        // Sort ascending by edit distance
        suggestions.sort((a, b) -> Integer.compare(a.distance, b.distance));

        // Deduplicate suggestions
        List<Suggestion> top = new ArrayList<>();
        List<String> seen = new java.util.ArrayList<>();
        for (Suggestion s : suggestions) {
            String lower = s.candidate.toLowerCase();
            if (!seen.contains(lower)) {
                seen.add(lower);
                top.add(s);
                if (top.size() >= 5) break;
            }
        }

        long elapsed = System.nanoTime() - startTime;

        ResultFormatter.printHeader(
            algoName + " Distance DP",
            "CO3 - Dynamic Programming",
            "generateSearchSuggestions()",
            query
        );

        System.out.println("Fuzzy Query Autocorrect Candidates (\"Did you mean...?\"):");
        System.out.println("---------------------------------------------------------");
        for (int i = 0; i < top.size(); i++) {
            Suggestion s = top.get(i);
            System.out.printf("%d. \"%-32s\" [Edit Distance: %d] (%s)%n", (i + 1), s.candidate, s.distance, s.category);
        }

        ResultFormatter.printFooter(
            elapsed,
            (algoChoice == 2) ? "O(|Query| * |Target|) Damerau DP" : "O(|Query| * |Target|) Levenshtein DP",
            "O(|Query| * |Target|) DP matrix",
            returnMenu
        );
    }
}
