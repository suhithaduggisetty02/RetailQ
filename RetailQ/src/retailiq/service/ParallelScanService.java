package retailiq.service;

import retailiq.util.ResultFormatter;

public class ParallelScanService {

    // Hand-built Blelloch Parallel Prefix Scan (Work O(n), Span O(log n))
    public static void performParallelScan() {
        // Daily revenue streams in ₹'000 for 8 consecutive retail business days
        int[] dailyRevenue = { 15, 24, 18, 32, 40, 28, 35, 50 };
        int n = dailyRevenue.length; // Power of 2 (8 elements)

        long startTime = System.nanoTime();

        int[] tree = new int[n];
        System.arraycopy(dailyRevenue, 0, tree, 0, n);

        // 1. Up-sweep (Reduction / Parallel Tree Phase)
        // Levels: d = 0 to log2(n) - 1
        for (int d = 0; d < 3; d++) {
            int stride = 1 << (d + 1);
            int half = 1 << d;
            for (int k = 0; k < n; k += stride) {
                tree[k + stride - 1] += tree[k + half - 1];
            }
        }

        // 2. Down-sweep Phase
        // Clear root of tree to 0 for exclusive scan
        int totalSum = tree[n - 1];
        tree[n - 1] = 0;

        for (int d = 2; d >= 0; d--) {
            int stride = 1 << (d + 1);
            int half = 1 << d;
            for (int k = 0; k < n; k += stride) {
                int leftVal = tree[k + half - 1];
                tree[k + half - 1] = tree[k + stride - 1];
                tree[k + stride - 1] += leftVal;
            }
        }

        // Convert exclusive prefix scan to inclusive cumulative sales by shifting and appending total
        int[] inclusiveCumulative = new int[n];
        for (int i = 0; i < n - 1; i++) {
            inclusiveCumulative[i] = tree[i + 1];
        }
        inclusiveCumulative[n - 1] = totalSum;

        long elapsed = System.nanoTime() - startTime;

        ResultFormatter.printHeader(
            "Blelloch Parallel Prefix Scan (Up-sweep / Down-sweep)",
            "CO6 - Parallel Algorithms",
            "cumulativeSalesAnalysis()",
            n + " Consecutive Business Day Retail Revenue Timeline"
        );

        System.out.printf("%-10s | %-20s | %-26s%n", "Timeline", "Daily Sales (₹'000)", "Cumulative Trajectory (₹'000)");
        System.out.println("------------------------------------------------------------------");
        for (int i = 0; i < n; i++) {
            System.out.printf("Day %-6d | ₹%-18d | ₹%-24d%n",
                (i + 1), dailyRevenue[i], inclusiveCumulative[i]);
        }
        System.out.println("------------------------------------------------------------------");
        System.out.printf("Total Period Gross Sales : ₹%d,000%n", totalSum);

        ResultFormatter.printFooter(
            elapsed,
            "Work W(n) = O(n), Span S(n) = O(log n) tree depth",
            "O(n) auxiliary tree array",
            "Algorithm Lab"
        );
    }
}
