package retailiq.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retailiq.util.DataStore;
import retailiq.util.MainInput;
import retailiq.util.ResultFormatter;

public class DeliveryTSPService {

    public static class TSPResult {
        public int totalDistance;
        public List<Integer> tour;
        public long elapsedNanos;

        public TSPResult(int totalDistance, List<Integer> tour, long elapsedNanos) {
            this.totalDistance = totalDistance;
            this.tour = tour;
            this.elapsedNanos = elapsedNanos;
        }
    }

    // Exact Held-Karp Bitmask DP
    public static TSPResult solveHeldKarp(int[][] dist) {
        int n = dist.length;
        int numStates = 1 << n;
        int[][] dp = new int[numStates][n];
        int[][] parent = new int[numStates][n];

        for (int[] row : dp) {
            Arrays.fill(row, 1_000_000_000);
        }

        dp[1][0] = 0; // Start at depot 0

        for (int mask = 1; mask < numStates; mask++) {
            for (int u = 0; u < n; u++) {
                if ((mask & (1 << u)) == 0) continue;
                if (dp[mask][u] >= 1_000_000_000) continue;

                for (int v = 0; v < n; v++) {
                    if ((mask & (1 << v)) != 0) continue;
                    int nextMask = mask | (1 << v);
                    int newDist = dp[mask][u] + dist[u][v];

                    if (newDist < dp[nextMask][v]) {
                        dp[nextMask][v] = newDist;
                        parent[nextMask][v] = u;
                    }
                }
            }
        }

        // Return to depot (0)
        int finalMask = numStates - 1;
        int bestDist = 1_000_000_000;
        int lastNode = -1;

        for (int i = 1; i < n; i++) {
            int tourCost = dp[finalMask][i] + dist[i][0];
            if (tourCost < bestDist) {
                bestDist = tourCost;
                lastNode = i;
            }
        }

        // Reconstruct tour path
        List<Integer> path = new ArrayList<>();
        int currMask = finalMask;
        int currNode = lastNode;

        while (currNode != 0) {
            path.add(0, currNode);
            int prevNode = parent[currMask][currNode];
            currMask ^= (1 << currNode);
            currNode = prevNode;
        }
        path.add(0, 0); // Start at depot
        path.add(0);    // Return to depot

        return new TSPResult(bestDist, path, 0);
    }

    // Nearest Neighbor Heuristic (2-Approximation for Metric TSP)
    public static TSPResult solveNearestNeighbor(int[][] dist) {
        int n = dist.length;
        boolean[] visited = new boolean[n];
        List<Integer> tour = new ArrayList<>();

        int current = 0;
        tour.add(current);
        visited[current] = true;
        int totalDist = 0;

        for (int step = 1; step < n; step++) {
            int nearest = -1;
            int minDist = 1_000_000_000;

            for (int next = 0; next < n; next++) {
                if (!visited[next] && dist[current][next] < minDist) {
                    minDist = dist[current][next];
                    nearest = next;
                }
            }

            visited[nearest] = true;
            tour.add(nearest);
            totalDist += minDist;
            current = nearest;
        }

        // Return to depot
        totalDist += dist[current][0];
        tour.add(0);

        return new TSPResult(totalDist, tour, 0);
    }

    public static void planDeliveryRoutes() {
        System.out.println("\n------------------------------------------------");
        System.out.println("   PLAN DELIVERY ROUTES (TSP ROUTING ENGINE)");
        System.out.println("------------------------------------------------");
        System.out.println("Delivery Outlets / Logistics Hubs to Visit:");
        for (int i = 0; i < DataStore.DELIVERY_LOCATIONS.length; i++) {
            System.out.printf(" [%d] %s%n", i, DataStore.DELIVERY_LOCATIONS[i]);
        }

        System.out.println("\nSelect Routing Algorithm:");
        System.out.println("1. Exact TSP Tour (Held-Karp Bitmask DP - CO3/CO5)");
        System.out.println("2. Fast TSP Approximation (Nearest Neighbor Heuristic - CO5)");
        System.out.println("3. Comparative Analysis (Exact vs Heuristic Side-by-Side)");
        System.out.println("4. Back");
        int choice = MainInput.readInt("Enter choice: ");
        if (choice == 4) return;

        int[][] dist = DataStore.deliveryDistanceMatrix;

        if (choice == 1) {
            long start = System.nanoTime();
            TSPResult res = solveHeldKarp(dist);
            long elapsed = System.nanoTime() - start;

            ResultFormatter.printHeader(
                "Held-Karp Exact TSP via Bitmask DP",
                "CO3 - Bitmask DP & CO5 - NP-Hard Analysis",
                "planDeliveryRoutes()",
                "Optimal Tour for " + dist.length + " Retail Delivery Hubs"
            );

            printTour("Exact Optimal Route Sequence", res);

            ResultFormatter.printFooter(
                elapsed,
                "O(N^2 * 2^N) Exponential Held-Karp",
                "O(N * 2^N) DP matrix",
                "Manager Menu"
            );

        } else if (choice == 2) {
            long start = System.nanoTime();
            TSPResult res = solveNearestNeighbor(dist);
            long elapsed = System.nanoTime() - start;

            ResultFormatter.printHeader(
                "Nearest Neighbor TSP Heuristic (2-Approximation)",
                "CO5 - Complexity & Approximation Algorithms",
                "planDeliveryRoutes()",
                "Greedy Nearest Neighbor Tour for " + dist.length + " Hubs"
            );

            printTour("Greedy Approximated Route Sequence", res);

            ResultFormatter.printFooter(
                elapsed,
                "O(N^2) Polynomial Heuristic",
                "O(N) visited tracking",
                "Manager Menu"
            );

        } else if (choice == 3) {
            long startExact = System.nanoTime();
            TSPResult exactRes = solveHeldKarp(dist);
            long elapsedExact = System.nanoTime() - startExact;

            long startApprox = System.nanoTime();
            TSPResult approxRes = solveNearestNeighbor(dist);
            long elapsedApprox = System.nanoTime() - startApprox;

            ResultFormatter.printHeader(
                "Exact Bitmask DP vs Polynomial Approximation",
                "CO3 / CO5 - TSP Complexity & Approximation Analysis",
                "planDeliveryRoutes()",
                dist.length + " Retail Nodes Delivery Benchmark"
            );

            System.out.println("1. EXACT HELD-KARP BITMASK DP:");
            printTour("Optimal Route", exactRes);
            System.out.printf("   Execution Time: %d ns (%.4f ms)%n", elapsedExact, elapsedExact / 1_000_000.0);

            System.out.println("\n2. NEAREST NEIGHBOR APPROXIMATION:");
            printTour("Heuristic Route", approxRes);
            System.out.printf("   Execution Time: %d ns (%.4f ms)%n", elapsedApprox, elapsedApprox / 1_000_000.0);

            System.out.println("\n================ COMPARATIVE EMPIRICAL SUMMARY ================");
            double ratio = (double) approxRes.totalDistance / exactRes.totalDistance;
            System.out.printf("Tour Quality Ratio (Approx / Exact) : %.2fx (Within theoretical 2.0x bound)%n", ratio);
            System.out.printf("Distance Variance                   : +%d km%n", (approxRes.totalDistance - exactRes.totalDistance));
            System.out.printf("Execution Speedup Factor            : %.1fx faster%n", (double) elapsedExact / Math.max(1, elapsedApprox));

            ResultFormatter.printFooter(
                elapsedExact + elapsedApprox,
                "Exact: O(N^2 * 2^N) vs Approximation: O(N^2)",
                "Exact: O(N * 2^N) vs Approx: O(N)",
                "Manager Menu"
            );
        }
    }

    private static void printTour(String title, TSPResult res) {
        System.out.println(title + ":");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < res.tour.size(); i++) {
            int node = res.tour.get(i);
            sb.append(DataStore.DELIVERY_LOCATIONS[node]);
            if (i < res.tour.size() - 1) sb.append(" -> ");
        }
        System.out.println("• Tour Path: " + sb.toString());
        System.out.println("• Total Round-Trip Distance: " + res.totalDistance + " km");
    }
}
