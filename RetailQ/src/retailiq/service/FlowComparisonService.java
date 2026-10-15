package retailiq.service;

import retailiq.service.NetworkFlowService.FlowResult;
import retailiq.util.DataStore;
import retailiq.util.ResultFormatter;

public class FlowComparisonService {

    // Hand-built Ford-Fulkerson using DFS for augmenting paths
    public static FlowResult fordFulkersonDFS(int[][] capacity, int source, int sink) {
        int n = capacity.length;
        int[][] residual = new int[n][n];
        for (int i = 0; i < n; i++) System.arraycopy(capacity[i], 0, residual[i], 0, n);

        int[][] flow = new int[n][n];
        int maxFlow = 0;
        int augmentations = 0;

        while (true) {
            boolean[] visited = new boolean[n];
            int pushed = dfsPath(residual, source, sink, Integer.MAX_VALUE, visited);
            if (pushed == 0) break;
            maxFlow += pushed;
            augmentations++;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (capacity[i][j] > 0) flow[i][j] = capacity[i][j] - residual[i][j];
            }
        }
        return new FlowResult(maxFlow, flow, residual, augmentations);
    }

    private static int dfsPath(int[][] res, int u, int t, int minCap, boolean[] visited) {
        if (u == t) return minCap;
        visited[u] = true;

        for (int v = 0; v < res.length; v++) {
            if (!visited[v] && res[u][v] > 0) {
                int pushed = dfsPath(res, v, t, Math.min(minCap, res[u][v]), visited);
                if (pushed > 0) {
                    res[u][v] -= pushed;
                    res[v][u] += pushed;
                    return pushed;
                }
            }
        }
        return 0;
    }

    public static void performFlowComparison() {
        int[][] cap = DataStore.logisticsCapacity;
        int s = 0, t = 5;

        // 1. Ford-Fulkerson (DFS)
        long startFF = System.nanoTime();
        FlowResult resFF = fordFulkersonDFS(cap, s, t);
        long timeFF = System.nanoTime() - startFF;

        // 2. Edmonds-Karp (BFS)
        long startEK = System.nanoTime();
        FlowResult resEK = NetworkFlowService.edmondsKarp(cap, s, t);
        long timeEK = System.nanoTime() - startEK;

        // 3. Dinic's Algorithm (Level Graph + Blocking Flow)
        long startDinic = System.nanoTime();
        FlowResult resDinic = NetworkFlowService.dinic(cap, s, t);
        long timeDinic = System.nanoTime() - startDinic;

        ResultFormatter.printHeader(
            "Tri-Algorithm Maximum Flow Comparison",
            "CO4 - Graph & Network Flows",
            "networkFlowComparison()",
            "Logistics Network Graph (6 nodes, 8 directed shipping lanes)"
        );

        System.out.println("Maximum Flow Tri-Algorithm Comparative Evaluation:");
        System.out.println("-------------------------------------------------------------------------------------------------");
        System.out.printf("%-20s | %-12s | %-15s | %-16s | %-20s%n",
            "Algorithm", "Max Flow", "Augmentations", "Exec Time (ns)", "Theoretical Complexity");
        System.out.println("-------------------------------------------------------------------------------------------------");
        System.out.printf("%-20s | %-12d | %-15d | %-16d | %-20s%n",
            "Ford-Fulkerson (DFS)", resFF.maxFlow, resFF.augmentations, timeFF, "O(E * |maxFlow|)");
        System.out.printf("%-20s | %-12d | %-15d | %-16d | %-20s%n",
            "Edmonds-Karp (BFS)", resEK.maxFlow, resEK.augmentations, timeEK, "O(V * E^2)");
        System.out.printf("%-20s | %-12d | %-15d | %-16d | %-20s%n",
            "Dinic's Algorithm", resDinic.maxFlow, resDinic.augmentations, timeDinic, "O(V^2 * E)");
        System.out.println("-------------------------------------------------------------------------------------------------");
        System.out.println("Key Algorithmic Takeaways:");
        System.out.println("• Ford-Fulkerson: Relies on arbitrary DFS; risk of slow convergence if capacities are large.");
        System.out.println("• Edmonds-Karp: Guarantees shortest augmenting paths via BFS; strictly polynomial O(V * E^2).");
        System.out.println("• Dinic: Constructs layered level graphs & pushes blocking flows simultaneously; fastest in practice.");

        ResultFormatter.printFooter(
            timeFF + timeEK + timeDinic,
            "Dinic O(V^2 * E) < Edmonds-Karp O(V * E^2) < Ford-Fulkerson O(E * f)",
            "O(V^2) residual network",
            "Algorithm Lab"
        );
    }
}
