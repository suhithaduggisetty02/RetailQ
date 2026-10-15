package retailiq.service;

import java.util.Arrays;

import retailiq.util.ResultFormatter;

public class BipartiteMatchingService {

    // Hand-built Maximum Bipartite Matching via augmenting DFS paths (Kuhn's Algorithm)
    private static boolean dfsBipartite(int u, boolean[][] adj, boolean[] visited, int[] matchTo) {
        int m = matchTo.length;
        for (int v = 0; v < m; v++) {
            if (adj[u][v] && !visited[v]) {
                visited[v] = true;
                if (matchTo[v] < 0 || dfsBipartite(matchTo[v], adj, visited, matchTo)) {
                    matchTo[v] = u;
                    return true;
                }
            }
        }
        return false;
    }

    public static int maxBipartiteMatching(boolean[][] adj, int n, int m, int[] matchTo) {
        Arrays.fill(matchTo, -1);
        int matches = 0;
        for (int u = 0; u < n; u++) {
            boolean[] visited = new boolean[m];
            if (dfsBipartite(u, adj, visited, matchTo)) {
                matches++;
            }
        }
        return matches;
    }

    public static void assignOrders() {
        // Pending retail orders to fulfill
        String[] pendingOrders = {
            "Order #5001 [Headphones - Fragile Audio]",
            "Order #5002 [Fast Chargers - Standard Parcel]",
            "Order #5003 [Smart Watch - High Value Express]",
            "Order #5004 [Gaming Keyboard - Medium Parcel]",
            "Order #5005 [Laptop Backpack - Bulk Volume]"
        };

        // Available delivery vehicles / agents
        String[] deliveryAgents = {
            "Agent Alpha (Bike Courier - Small/Standard)",
            "Agent Beta (Electric Van - Fragile/Medium)",
            "Agent Gamma (Drone Express - High Value/Light)",
            "Agent Delta (Cargo Truck - Bulk Volume)",
            "Agent Epsilon (Eco Scooter - Standard Parcel)"
        };

        int n = pendingOrders.length;
        int m = deliveryAgents.length;

        // Eligibility / Capability matrix
        boolean[][] eligibility = new boolean[n][m];
        // Order 0 (Headphones) can be handled by Beta, Gamma
        eligibility[0][1] = true;
        eligibility[0][2] = true;

        // Order 1 (Chargers) can be handled by Alpha, Beta, Epsilon
        eligibility[1][0] = true;
        eligibility[1][1] = true;
        eligibility[1][4] = true;

        // Order 2 (Smart Watch) can be handled by Beta, Gamma
        eligibility[2][1] = true;
        eligibility[2][2] = true;

        // Order 3 (Gaming Keyboard) can be handled by Beta, Epsilon
        eligibility[3][1] = true;
        eligibility[3][4] = true;

        // Order 4 (Backpack) can be handled by Delta
        eligibility[4][3] = true;

        long startTime = System.nanoTime();
        int[] matchTo = new int[m];
        int totalAssigned = maxBipartiteMatching(eligibility, n, m, matchTo);
        long elapsed = System.nanoTime() - startTime;

        ResultFormatter.printHeader(
            "Hopcroft-Karp / Augmenting Path Bipartite Matching",
            "CO4 - Graph & Network Flows",
            "assignOrders()",
            n + " Pending Retail Orders to " + m + " Fulfillment Resources"
        );

        System.out.println("Optimal Order-to-Resource Dispatch Assignments:");
        System.out.println("------------------------------------------------");
        int[] orderAssignedTo = new int[n];
        Arrays.fill(orderAssignedTo, -1);

        for (int v = 0; v < m; v++) {
            if (matchTo[v] >= 0) {
                int u = matchTo[v];
                orderAssignedTo[u] = v;
                System.out.printf("• %-46s ---> %s%n", pendingOrders[u], deliveryAgents[v]);
            }
        }

        System.out.println("\nUnassigned Pending Orders:");
        boolean allAssigned = true;
        for (int u = 0; u < n; u++) {
            if (orderAssignedTo[u] < 0) {
                System.out.println("• [UNASSIGNED] " + pendingOrders[u]);
                allAssigned = false;
            }
        }
        if (allAssigned) {
            System.out.println("  None! All pending orders successfully matched to fulfillment partners.");
        }

        System.out.printf("%nTotal Orders Assigned: %d / %d (%.1f%% assignment efficiency)%n",
            totalAssigned, n, (totalAssigned * 100.0) / n);

        ResultFormatter.printFooter(
            elapsed,
            "O(V * E) Augmenting Path Matching",
            "O(V + E) adjacency & matching arrays",
            "Manager Menu"
        );
    }
}
