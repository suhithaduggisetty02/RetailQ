package retailiq.service;

import java.util.concurrent.ForkJoinPool;

import retailiq.model.Product;
import retailiq.model.SaleRecord;
import retailiq.service.DeliveryTSPService.TSPResult;
import retailiq.service.NetworkFlowService.FlowResult;
import retailiq.service.ParallelSalesAnalyticsService.ParallelReduceTask;
import retailiq.service.ParallelSalesAnalyticsService.SalesAggregate;
import retailiq.service.SalesSortingService.ParallelMergeSortTask;
import retailiq.util.DataStore;
import retailiq.util.MainInput;
import retailiq.util.ResultFormatter;

public class AlgorithmComparisonService {

    public static void compareAlgorithms() {
        while (true) {
            System.out.println("\n========================================================");
            System.out.println("   ALGORITHM PERFORMANCE COMPARISON LAB (CO1 - CO6)");
            System.out.println("========================================================");
            System.out.println("1. CO1: KMP vs Rabin-Karp vs Z-Algorithm");
            System.out.println("2. CO3: Levenshtein vs Damerau-Levenshtein vs Weighted");
            System.out.println("3. CO4: Edmonds-Karp vs Dinic Maximum Flow");
            System.out.println("4. CO5: Exact Held-Karp TSP vs 2-Approximation Heuristic");
            System.out.println("5. CO6: Randomized QuickSort vs Parallel MergeSort");
            System.out.println("6. Run Full Comprehensive Benchmark Across All COs");
            System.out.println("7. Back to Manager Menu");
            System.out.println("========================================================");

            int choice = MainInput.readInt("Enter choice: ");
            if (choice == 7) return;

            switch (choice) {
                case 1:
                    benchmarkCO1();
                    break;
                case 2:
                    benchmarkCO3();
                    break;
                case 3:
                    benchmarkCO4();
                    break;
                case 4:
                    benchmarkCO5();
                    break;
                case 5:
                    benchmarkCO6();
                    break;
                case 6:
                    runFullBenchmark();
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void benchmarkCO1() {
        String keyword = "headphones";
        int iterations = 1000;

        // KMP
        long startKMP = System.nanoTime();
        int kmpMatches = 0;
        for (int it = 0; it < iterations; it++) {
            for (Product p : DataStore.products) {
                if (KMPService.search(p.getName(), keyword) || KMPService.search(p.getDescription(), keyword)) {
                    kmpMatches++;
                }
            }
        }
        long timeKMP = (System.nanoTime() - startKMP) / iterations;

        // Rabin-Karp
        long startRK = System.nanoTime();
        int rkMatches = 0;
        for (int it = 0; it < iterations; it++) {
            for (Product p : DataStore.products) {
                if (RabinKarpService.search(p.getName(), keyword) || RabinKarpService.search(p.getDescription(), keyword)) {
                    rkMatches++;
                }
            }
        }
        long timeRK = (System.nanoTime() - startRK) / iterations;

        // Z-Algorithm
        long startZ = System.nanoTime();
        int zMatches = 0;
        for (int it = 0; it < iterations; it++) {
            for (Product p : DataStore.products) {
                if (ZAlgorithmService.search(p.getName(), keyword) || ZAlgorithmService.search(p.getDescription(), keyword)) {
                    zMatches++;
                }
            }
        }
        long timeZ = (System.nanoTime() - startZ) / iterations;

        ResultFormatter.printHeader(
            "CO1 String Algorithms Empirical Benchmark",
            "CO1 - String Algorithms",
            "compareAlgorithms()",
            "Search keyword: '" + keyword + "' over " + DataStore.products.size() + " products (Averaged over " + iterations + " runs)"
        );

        System.out.printf("%-18s | %-16s | %-20s | %-15s%n", "Algorithm", "Avg Time (ns)", "Time Complexity", "Space Complexity");
        System.out.println("-----------------------------------------------------------------------------");
        System.out.printf("%-18s | %-16d | %-20s | %-15s%n", "KMP", timeKMP, "O(n + m)", "O(m)");
        System.out.printf("%-18s | %-16d | %-20s | %-15s%n", "Rabin-Karp", timeRK, "Avg O(n + m), W O(nm)", "O(1)");
        System.out.printf("%-18s | %-16d | %-20s | %-15s%n", "Z-Algorithm", timeZ, "O(n + m)", "O(n + m)");

        ResultFormatter.printFooter(timeKMP + timeRK + timeZ, "O(n + m) optimal bound", "O(m) to O(n+m)", "Comparison Lab");
    }

    private static void benchmarkCO3() {
        String a = "mechanical keyboard";
        String b = "mechnical keybord";

        long startLev = System.nanoTime();
        int d1 = LevenshteinService.distance(a, b);
        long timeLev = System.nanoTime() - startLev;

        long startDam = System.nanoTime();
        int d2 = DamerauLevenshteinService.distance(a, b);
        long timeDam = System.nanoTime() - startDam;

        long startWeight = System.nanoTime();
        int d3 = WeightedEditDistanceService.distance(a, b);
        long timeWeight = System.nanoTime() - startWeight;

        ResultFormatter.printHeader(
            "CO3 Edit Distance DP Benchmark",
            "CO3 - Dynamic Programming",
            "compareAlgorithms()",
            "\"" + a + "\" vs \"" + b + "\""
        );

        System.out.printf("%-22s | %-10s | %-14s | %-15s%n", "Algorithm", "Distance", "Exec Time (ns)", "Theoretical Complexity");
        System.out.println("-------------------------------------------------------------------------");
        System.out.printf("%-22s | %-10d | %-14d | %-15s%n", "Levenshtein", d1, timeLev, "O(m * n)");
        System.out.printf("%-22s | %-10d | %-14d | %-15s%n", "Damerau-Levenshtein", d2, timeDam, "O(m * n)");
        System.out.printf("%-22s | %-10d | %-14d | %-15s%n", "Weighted Edit Distance", d3, timeWeight, "O(m * n)");

        ResultFormatter.printFooter(timeLev + timeDam + timeWeight, "O(m * n) DP", "O(m * n) table", "Comparison Lab");
    }

    private static void benchmarkCO4() {
        int[][] cap = DataStore.logisticsCapacity;

        long startEK = System.nanoTime();
        FlowResult resEK = NetworkFlowService.edmondsKarp(cap, 0, 5);
        long timeEK = System.nanoTime() - startEK;

        long startDinic = System.nanoTime();
        FlowResult resDinic = NetworkFlowService.dinic(cap, 0, 5);
        long timeDinic = System.nanoTime() - startDinic;

        ResultFormatter.printHeader(
            "CO4 Maximum Flow Benchmark",
            "CO4 - Graph & Network Flows",
            "compareAlgorithms()",
            "Logistics Network (6 nodes, Source=0, Sink=5)"
        );

        System.out.printf("%-18s | %-10s | %-14s | %-18s | %-15s%n",
            "Algorithm", "Max Flow", "Augmentations", "Exec Time (ns)", "Complexity");
        System.out.println("---------------------------------------------------------------------------------");
        System.out.printf("%-18s | %-10d | %-14d | %-18d | %-15s%n",
            "Edmonds-Karp", resEK.maxFlow, resEK.augmentations, timeEK, "O(V * E^2)");
        System.out.printf("%-18s | %-10d | %-14d | %-18d | %-15s%n",
            "Dinic's Algorithm", resDinic.maxFlow, resDinic.augmentations, timeDinic, "O(V^2 * E)");

        ResultFormatter.printFooter(timeEK + timeDinic, "Dinic O(V^2 * E) vs Edmonds-Karp O(V * E^2)", "O(V^2)", "Comparison Lab");
    }

    private static void benchmarkCO5() {
        int[][] dist = DataStore.deliveryDistanceMatrix;

        long startExact = System.nanoTime();
        TSPResult exact = DeliveryTSPService.solveHeldKarp(dist);
        long timeExact = System.nanoTime() - startExact;

        long startApprox = System.nanoTime();
        TSPResult approx = DeliveryTSPService.solveNearestNeighbor(dist);
        long timeApprox = System.nanoTime() - startApprox;

        ResultFormatter.printHeader(
            "CO5 TSP Routing Benchmark",
            "CO5 - Complexity & Approximations",
            "compareAlgorithms()",
            dist.length + " Logistics Delivery Hubs"
        );

        System.out.printf("%-25s | %-14s | %-16s | %-18s%n", "Approach", "Tour Distance", "Exec Time (ns)", "Complexity");
        System.out.println("----------------------------------------------------------------------------");
        System.out.printf("%-25s | %-14s | %-16d | %-18s%n", "Held-Karp (Exact DP)", exact.totalDistance + " km", timeExact, "O(N^2 * 2^N)");
        System.out.printf("%-25s | %-14s | %-16d | %-18s%n", "Nearest Neighbor (Heuristic)", approx.totalDistance + " km", timeApprox, "O(N^2)");
        System.out.printf("%nTour Quality Ratio: %.2fx | Empirical Speedup: %.1fx%n",
            (double) approx.totalDistance / exact.totalDistance, (double) timeExact / Math.max(1, timeApprox));

        ResultFormatter.printFooter(timeExact + timeApprox, "Exact: O(N^2 * 2^N) vs Approx: O(N^2)", "O(N * 2^N) vs O(N)", "Comparison Lab");
    }

    private static void benchmarkCO6() {
        int n = DataStore.sales.size();
        SaleRecord[] arr1 = DataStore.sales.toArray(new SaleRecord[0]);
        SaleRecord[] arr2 = DataStore.sales.toArray(new SaleRecord[0]);

        long startQS = System.nanoTime();
        SalesSortingService.randomizedQuickSort(arr1, 0, n - 1);
        long timeQS = System.nanoTime() - startQS;

        ForkJoinPool pool = ForkJoinPool.commonPool();
        long startMS = System.nanoTime();
        pool.invoke(new ParallelMergeSortTask(arr2, 0, n - 1));
        long timeMS = System.nanoTime() - startMS;

        ResultFormatter.printHeader(
            "CO6 Sorting & Parallel Algorithms Benchmark",
            "CO6 - Randomized & Parallel Algorithms",
            "compareAlgorithms()",
            n + " Historical Sales Transactions"
        );

        System.out.printf("%-22s | %-16s | %-20s | %-15s%n", "Algorithm", "Exec Time (ns)", "Work Complexity", "Span / Space");
        System.out.println("---------------------------------------------------------------------------------");
        System.out.printf("%-22s | %-16d | %-20s | %-15s%n", "Randomized QuickSort", timeQS, "O(n log n) expected", "O(log n) stack");
        System.out.printf("%-22s | %-16d | %-20s | %-15s%n", "Parallel MergeSort", timeMS, "Work O(n log n)", "Span O(log^2 n)");

        ResultFormatter.printFooter(timeQS + timeMS, "O(n log n) work", "O(log n) vs O(n)", "Comparison Lab");
    }

    private static void runFullBenchmark() {
        System.out.println("\nExecuting comprehensive cross-module benchmark suite...");
        benchmarkCO1();
        benchmarkCO3();
        benchmarkCO4();
        benchmarkCO5();
        benchmarkCO6();
    }
}
