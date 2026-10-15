package retailiq.service;

import java.util.ArrayList;
import java.util.List;

import retailiq.util.ResultFormatter;

public class HamiltonianPathService {

    public static void performHamiltonianPath() {
        String[] nodes = { "Depot 1 (North)", "Depot 2 (East)", "Depot 3 (Central)", "Depot 4 (South)", "Depot 5 (West)" };
        int n = nodes.length;

        // Connectivity graph (available highway corridors between depots)
        boolean[][] adj = new boolean[n][n];
        adj[0][1] = adj[1][0] = true;
        adj[0][2] = adj[2][0] = true;
        adj[1][2] = adj[2][1] = true;
        adj[1][3] = adj[3][1] = true;
        adj[2][4] = adj[4][2] = true;
        adj[3][4] = adj[4][3] = true;

        long startTime = System.nanoTime();

        int numStates = 1 << n;
        boolean[][] dp = new boolean[numStates][n];
        int[][] parent = new int[numStates][n];

        // Base cases: single node paths
        for (int i = 0; i < n; i++) {
            dp[1 << i][i] = true;
            parent[1 << i][i] = -1;
        }

        for (int mask = 1; mask < numStates; mask++) {
            for (int u = 0; u < n; u++) {
                if (!dp[mask][u]) continue;

                for (int v = 0; v < n; v++) {
                    if ((mask & (1 << v)) == 0 && adj[u][v]) {
                        int nextMask = mask | (1 << v);
                        if (!dp[nextMask][v]) {
                            dp[nextMask][v] = true;
                            parent[nextMask][v] = u;
                        }
                    }
                }
            }
        }

        int fullMask = numStates - 1;
        int endNode = -1;
        for (int i = 0; i < n; i++) {
            if (dp[fullMask][i]) {
                endNode = i;
                break;
            }
        }

        List<Integer> path = new ArrayList<>();
        if (endNode != -1) {
            int currMask = fullMask;
            int curr = endNode;
            while (curr != -1) {
                path.add(0, curr);
                int p = parent[currMask][curr];
                currMask ^= (1 << curr);
                curr = p;
            }
        }

        long elapsed = System.nanoTime() - startTime;

        ResultFormatter.printHeader(
            "Hamiltonian Path via Bitmask Dynamic Programming",
            "CO3 - Dynamic Programming",
            "hamiltonianPath()",
            n + " Regional Retail Hubs Connectivity Network"
        );

        System.out.println("Available Highway Corridors:");
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (adj[i][j]) {
                    System.out.printf("• %s <===> %s%n", nodes[i], nodes[j]);
                }
            }
        }

        System.out.println("\nInspection Audit Route Result:");
        System.out.println("------------------------------");
        if (endNode != -1) {
            System.out.println("Valid Hamiltonian Inspection Path Found (Visits each hub exactly once):");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < path.size(); i++) {
                sb.append(nodes[path.get(i)]);
                if (i < path.size() - 1) sb.append(" ---> ");
            }
            System.out.println("Route: " + sb.toString());
        } else {
            System.out.println("No Hamiltonian path exists in this topology.");
        }

        ResultFormatter.printFooter(
            elapsed,
            "O(N^2 * 2^N) Bitmask DP vs O(N!) Brute Force",
            "O(N * 2^N) state DP table",
            "Algorithm Lab"
        );
    }
}
