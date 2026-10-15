package retailiq.service;

import java.util.ArrayList;
import java.util.List;

import retailiq.model.Product;
import retailiq.util.DataStore;
import retailiq.util.MainInput;
import retailiq.util.ResultFormatter;

public class ProductSimilarityService {

    // Hand-built Suffix Array construction using prefix doubling / ranking
    public static int[] buildSuffixArray(String text) {
        int n = text.length();
        int[] sa = new int[n];
        int[] rank = new int[n];
        int[] tempRank = new int[n];

        for (int i = 0; i < n; i++) {
            sa[i] = i;
            rank[i] = text.charAt(i);
        }

        for (int k = 1; k < n; k *= 2) {
            // Hand-built counting/insertion sort on pairs (rank[i], rank[i+k])
            sortSuffixes(sa, rank, tempRank, n, k);
            tempRank[sa[0]] = 0;
            for (int i = 1; i < n; i++) {
                int prev = sa[i - 1];
                int curr = sa[i];
                int prevSecond = (prev + k < n) ? rank[prev + k] : -1;
                int currSecond = (curr + k < n) ? rank[curr + k] : -1;

                if (rank[prev] == rank[curr] && prevSecond == currSecond) {
                    tempRank[curr] = tempRank[prev];
                } else {
                    tempRank[curr] = tempRank[prev] + 1;
                }
            }
            System.arraycopy(tempRank, 0, rank, 0, n);
            if (rank[sa[n - 1]] == n - 1) break;
        }

        return sa;
    }

    private static void sortSuffixes(int[] sa, int[] rank, int[] temp, int n, int k) {
        // Simple insertion sort for clean, dependency-free sorting of suffixes
        for (int i = 1; i < n; i++) {
            int key = sa[i];
            int j = i - 1;
            while (j >= 0 && compareSuffix(sa[j], key, rank, n, k) > 0) {
                sa[j + 1] = sa[j];
                j--;
            }
            sa[j + 1] = key;
        }
    }

    private static int compareSuffix(int a, int b, int[] rank, int n, int k) {
        if (rank[a] != rank[b]) {
            return Integer.compare(rank[a], rank[b]);
        }
        int rankA2 = (a + k < n) ? rank[a + k] : -1;
        int rankB2 = (b + k < n) ? rank[b + k] : -1;
        return Integer.compare(rankA2, rankB2);
    }

    // Kasai's algorithm for LCP Array in O(N)
    public static int[] buildKasaiLCP(String text, int[] sa) {
        int n = text.length();
        int[] rank = new int[n];
        for (int i = 0; i < n; i++) {
            rank[sa[i]] = i;
        }

        int[] lcp = new int[n];
        int h = 0;

        for (int i = 0; i < n; i++) {
            if (rank[i] > 0) {
                int j = sa[rank[i] - 1];
                while (i + h < n && j + h < n && text.charAt(i + h) == text.charAt(j + h)) {
                    h++;
                }
                lcp[rank[i]] = h;
                if (h > 0) h--;
            }
        }
        return lcp;
    }

    public static class LCSResult {
        public String longestCommonSubstring;
        public int length;
        public double similarityScore;

        public LCSResult(String lcs, int len, double score) {
            this.longestCommonSubstring = lcs;
            this.length = len;
            this.similarityScore = score;
        }
    }

    public static LCSResult computeLCS(String desc1, String desc2) {
        String s1 = desc1.toLowerCase();
        String s2 = desc2.toLowerCase();
        int len1 = s1.length();

        String combined = s1 + "#" + s2 + "$";
        int[] sa = buildSuffixArray(combined);
        int[] lcp = buildKasaiLCP(combined, sa);

        int maxLcp = 0;
        int maxIndex = -1;

        for (int i = 1; i < combined.length(); i++) {
            int suf1 = sa[i - 1];
            int suf2 = sa[i];

            boolean isSuf1InFirst = suf1 < len1;
            boolean isSuf2InFirst = suf2 < len1;

            if (isSuf1InFirst != isSuf2InFirst) {
                if (lcp[i] > maxLcp) {
                    maxLcp = lcp[i];
                    maxIndex = suf1;
                }
            }
        }

        String lcs = (maxIndex != -1 && maxLcp > 0) ? combined.substring(maxIndex, maxIndex + maxLcp) : "";
        double score = (len1 + s2.length() > 0) ? (2.0 * maxLcp) / (len1 + s2.length()) * 100.0 : 0.0;
        return new LCSResult(lcs, maxLcp, score);
    }

    // Manager Menu: Option 7
    public static void analyzeProductSimilarity() {
        System.out.println("\n------------------------------------------------");
        System.out.println("  ANALYZE PRODUCT SIMILARITY (SUFFIX ARRAY + LCP)");
        System.out.println("------------------------------------------------");
        System.out.println("Available Products:");
        for (Product p : DataStore.products) {
            System.out.printf("[%d] %-32s (%s)%n", p.getId(), p.getName(), p.getCategory());
        }

        System.out.println("\nOptions:");
        System.out.println("1. Compare Two Specific Products");
        System.out.println("2. Scan Entire Catalog for Highest Description Overlap");
        int subChoice = MainInput.readInt("Enter choice (1 or 2): ");

        if (subChoice == 1) {
            int id1 = MainInput.readInt("Enter Product 1 ID: ");
            int id2 = MainInput.readInt("Enter Product 2 ID: ");

            Product p1 = findProduct(id1);
            Product p2 = findProduct(id2);

            if (p1 == null || p2 == null || id1 == id2) {
                System.out.println("Invalid product IDs selected.");
                return;
            }

            long startTime = System.nanoTime();
            LCSResult result = computeLCS(p1.getDescription(), p2.getDescription());
            long elapsed = System.nanoTime() - startTime;

            ResultFormatter.printHeader(
                "Suffix Array + Kasai LCP",
                "CO2 - Suffix Structures",
                "analyzeProductSimilarity()",
                p1.getName() + " vs " + p2.getName()
            );

            System.out.println("Product 1: " + p1.getName());
            System.out.println("Description 1: " + p1.getDescription());
            System.out.println("\nProduct 2: " + p2.getName());
            System.out.println("Description 2: " + p2.getDescription());
            System.out.println("\n------------------------------------------------");
            System.out.println("Longest Common Substring (LCS) : \"" + result.longestCommonSubstring + "\"");
            System.out.println("Common Feature Match Length    : " + result.length + " characters");
            System.out.printf("Description Overlap Ratio      : %.2f%%%n", result.similarityScore);

            ResultFormatter.printFooter(
                elapsed,
                "O(N log N) Suffix Array construction + O(N) Kasai LCP",
                "O(N) for SA, Rank, and LCP arrays",
                "Manager Menu"
            );

        } else {
            long startTime = System.nanoTime();
            double highestScore = -1;
            Product bestP1 = null;
            Product bestP2 = null;
            LCSResult bestResult = null;

            int n = DataStore.products.size();
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    Product a = DataStore.products.get(i);
                    Product b = DataStore.products.get(j);
                    LCSResult res = computeLCS(a.getDescription(), b.getDescription());
                    if (res.similarityScore > highestScore) {
                        highestScore = res.similarityScore;
                        bestP1 = a;
                        bestP2 = b;
                        bestResult = res;
                    }
                }
            }
            long elapsed = System.nanoTime() - startTime;

            ResultFormatter.printHeader(
                "Suffix Array + Kasai LCP (Catalog-Wide Scan)",
                "CO2 - Suffix Structures",
                "analyzeProductSimilarity()",
                "All " + n + " Products Pairwise Scan"
            );

            if (bestP1 != null) {
                System.out.println("Most Similar Product Pair Found:");
                System.out.printf("• [%d] %s%n", bestP1.getId(), bestP1.getName());
                System.out.printf("• [%d] %s%n", bestP2.getId(), bestP2.getName());
                System.out.println("\nShared Feature Substring: \"" + bestResult.longestCommonSubstring + "\"");
                System.out.println("LCS Length               : " + bestResult.length + " chars");
                System.out.printf("Similarity Index         : %.2f%%%n", bestResult.similarityScore);
            }

            ResultFormatter.printFooter(
                elapsed,
                "O(M^2 * N log N) for all-pairs suffix analysis",
                "O(N) per pairwise evaluation",
                "Manager Menu"
            );
        }
    }

    // Customer Menu: Option 6
    public static void findSimilarProducts() {
        System.out.println("\n------------------------------------------------");
        System.out.println("    FIND SIMILAR PRODUCTS (SUFFIX ARRAY + LCP)");
        System.out.println("------------------------------------------------");
        System.out.println("Select a Product to find alternatives/similar items:");
        for (Product p : DataStore.products) {
            System.out.printf("[%d] %-30s | ₹%.2f%n", p.getId(), p.getName(), p.getPrice());
        }

        int targetId = MainInput.readInt("Enter Product ID: ");
        Product target = findProduct(targetId);
        if (target == null) {
            System.out.println("Product not found.");
            return;
        }

        long startTime = System.nanoTime();
        class ScoredProduct {
            Product product;
            LCSResult lcs;
            ScoredProduct(Product p, LCSResult l) { this.product = p; this.lcs = l; }
        }

        List<ScoredProduct> scored = new ArrayList<>();
        for (Product other : DataStore.products) {
            if (other.getId() != target.getId()) {
                LCSResult res = computeLCS(target.getDescription(), other.getDescription());
                scored.add(new ScoredProduct(other, res));
            }
        }

        // Sort descending by similarity score
        scored.sort((a, b) -> Double.compare(b.lcs.similarityScore, a.lcs.similarityScore));
        long elapsed = System.nanoTime() - startTime;

        ResultFormatter.printHeader(
            "Suffix Array + Kasai LCP",
            "CO2 - Suffix Structures",
            "findSimilarProducts()",
            target.getName()
        );

        System.out.println("Top Similar Recommendations for \"" + target.getName() + "\":");
        int count = Math.min(3, scored.size());
        for (int i = 0; i < count; i++) {
            ScoredProduct sp = scored.get(i);
            System.out.printf("%d. [%d] %-30s (₹%.2f)%n", (i + 1), sp.product.getId(), sp.product.getName(), sp.product.getPrice());
            System.out.printf("   Similarity: %.2f%% | Common Substring: \"%s\"%n", sp.lcs.similarityScore, sp.lcs.longestCommonSubstring);
        }

        ResultFormatter.printFooter(
            elapsed,
            "O(K * N log N) where K is catalog size",
            "O(N) working memory",
            "Customer Menu"
        );
    }

    // Customer Menu: Option 7
    public static void compareProductDescriptions() {
        System.out.println("\n------------------------------------------------");
        System.out.println("  COMPARE PRODUCT DESCRIPTIONS (LCS VIA SUFFIX)");
        System.out.println("------------------------------------------------");
        System.out.println("Select two products to compare feature descriptions:");
        for (Product p : DataStore.products) {
            System.out.printf("[%d] %-30s%n", p.getId(), p.getName());
        }

        int id1 = MainInput.readInt("Enter First Product ID : ");
        int id2 = MainInput.readInt("Enter Second Product ID: ");

        Product p1 = findProduct(id1);
        Product p2 = findProduct(id2);

        if (p1 == null || p2 == null || id1 == id2) {
            System.out.println("Invalid products selected.");
            return;
        }

        long startTime = System.nanoTime();
        LCSResult lcs = computeLCS(p1.getDescription(), p2.getDescription());
        long elapsed = System.nanoTime() - startTime;

        ResultFormatter.printHeader(
            "Longest Common Substring (Suffix Array + LCP)",
            "CO2 - Suffix Structures",
            "compareProductDescriptions()",
            p1.getName() + " vs " + p2.getName()
        );

        System.out.println("Product 1: " + p1.getName());
        System.out.println("• " + p1.getDescription());
        System.out.println("\nProduct 2: " + p2.getName());
        System.out.println("• " + p2.getDescription());
        System.out.println("\nComparison Results:");
        System.out.println("• Longest Shared Description Segment: \"" + lcs.longestCommonSubstring + "\"");
        System.out.println("• Shared Character Length             : " + lcs.length + " characters");
        System.out.printf("• Textual Overlap Ratio               : %.2f%%%n", lcs.similarityScore);

        ResultFormatter.printFooter(
            elapsed,
            "O(N log N) + O(N)",
            "O(N)",
            "Customer Menu"
        );
    }

    private static Product findProduct(int id) {
        for (Product p : DataStore.products) {
            if (p.getId() == id) return p;
        }
        return null;
    }
}
