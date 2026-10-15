package retailiq.service;

import retailiq.util.ResultFormatter;

public class OptimalBSTService {

    public static void performOptimalBST() {
        // Retail product keys and search access frequencies
        String[] keys = { "Charger (105)", "Headphones (101)", "Keyboard (102)", "Mouse (103)", "Watch (104)" };
        int[] freq = { 34, 45, 28, 52, 21 }; // Search frequencies in thousands
        int n = keys.length;

        long startTime = System.nanoTime();

        int[][] cost = new int[n + 1][n + 1];
        int[][] root = new int[n + 1][n + 1];

        // Base cases: single keys
        for (int i = 0; i < n; i++) {
            cost[i][i] = freq[i];
            root[i][i] = i;
        }

        // Subtree lengths from 2 to n
        for (int L = 2; L <= n; L++) {
            for (int i = 0; i <= n - L; i++) {
                int j = i + L - 1;
                cost[i][j] = Integer.MAX_VALUE;

                int sumF = 0;
                for (int k = i; k <= j; k++) sumF += freq[k];

                for (int r = i; r <= j; r++) {
                    int c = ((r > i) ? cost[i][r - 1] : 0) +
                            ((r < j) ? cost[r + 1][j] : 0) + sumF;

                    if (c < cost[i][j]) {
                        cost[i][j] = c;
                        root[i][j] = r;
                    }
                }
            }
        }

        long elapsed = System.nanoTime() - startTime;

        ResultFormatter.printHeader(
            "Optimal Binary Search Tree (OBST)",
            "CO3 - Dynamic Programming",
            "optimalBST()",
            n + " Retail Product Search Keys with Frequency Weights"
        );

        System.out.println("Key Access Frequencies (in '000s):");
        for (int i = 0; i < n; i++) {
            System.out.printf("• %-20s : %d searches%n", keys[i], freq[i]);
        }

        System.out.println("\nOptimal BST Construction Summary:");
        System.out.printf("• Minimum Weighted Expected Search Cost: %d%n", cost[0][n - 1]);
        System.out.printf("• Optimal Root for Full Tree            : %s%n", keys[root[0][n - 1]]);
        System.out.println("• Left Subtree Root                     : " + keys[root[0][root[0][n - 1] - 1]]);
        System.out.println("• Right Subtree Root                    : " + keys[root[root[0][n - 1] + 1][n - 1]]);

        ResultFormatter.printFooter(
            elapsed,
            "O(n^3) DP or O(n^2) with Knuth's Monotonicity",
            "O(n^2) cost and root tables",
            "Algorithm Lab"
        );
    }
}
