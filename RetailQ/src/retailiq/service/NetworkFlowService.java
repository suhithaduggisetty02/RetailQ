package retailiq.service;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import retailiq.util.DataStore;
import retailiq.util.MainInput;
import retailiq.util.ResultFormatter;

public class NetworkFlowService {

    public static class FlowResult {
        public int maxFlow;
        public int[][] flowMatrix;
        public int[][] residualCapacity;
        public int augmentations;

        public FlowResult(int maxFlow, int[][] flowMatrix, int[][] residual, int aug) {
            this.maxFlow = maxFlow;
            this.flowMatrix = flowMatrix;
            this.residualCapacity = residual;
            this.augmentations = aug;
        }
    }

    // Hand-built Edmonds-Karp Algorithm (BFS augmenting paths)
    public static FlowResult edmondsKarp(int[][] capacity, int source, int sink) {
        int n = capacity.length;
        int[][] residual = new int[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(capacity[i], 0, residual[i], 0, n);
        }

        int[][] flow = new int[n][n];
        int[] parent = new int[n];
        int maxFlow = 0;
        int augmentations = 0;

        while (bfsEdmondsKarp(residual, source, sink, parent)) {
            int pathFlow = Integer.MAX_VALUE;
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                pathFlow = Math.min(pathFlow, residual[u][v]);
            }

            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                residual[u][v] -= pathFlow;
                residual[v][u] += pathFlow;
                flow[u][v] += pathFlow;
            }

            maxFlow += pathFlow;
            augmentations++;
        }

        return new FlowResult(maxFlow, flow, residual, augmentations);
    }

    private static boolean bfsEdmondsKarp(int[][] residual, int source, int sink, int[] parent) {
        int n = residual.length;
        boolean[] visited = new boolean[n];
        Queue<Integer> queue = new LinkedList<>();

        queue.add(source);
        visited[source] = true;
        parent[source] = -1;

        while (!queue.isEmpty()) {
            int u = queue.poll();
            if (u == sink) return true;

            for (int v = 0; v < n; v++) {
                if (!visited[v] && residual[u][v] > 0) {
                    visited[v] = true;
                    parent[v] = u;
                    queue.add(v);
                }
            }
        }
        return false;
    }

    // Hand-built Dinic's Algorithm (Level graph BFS + Blocking flow DFS)
    public static FlowResult dinic(int[][] capacity, int source, int sink) {
        int n = capacity.length;
        int[][] residual = new int[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(capacity[i], 0, residual[i], 0, n);
        }

        int[][] flow = new int[n][n];
        int[] level = new int[n];
        int maxFlow = 0;
        int augmentations = 0;

        while (dinicBFS(residual, source, sink, level)) {
            int[] ptr = new int[n];
            while (true) {
                int pushed = dinicDFS(residual, level, ptr, source, sink, Integer.MAX_VALUE);
                if (pushed == 0) break;
                maxFlow += pushed;
                augmentations++;
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (capacity[i][j] > 0) {
                    flow[i][j] = capacity[i][j] - residual[i][j];
                }
            }
        }

        return new FlowResult(maxFlow, flow, residual, augmentations);
    }

    private static boolean dinicBFS(int[][] residual, int s, int t, int[] level) {
        Arrays.fill(level, -1);
        level[s] = 0;
        Queue<Integer> q = new LinkedList<>();
        q.add(s);

        while (!q.isEmpty()) {
            int u = q.poll();
            for (int v = 0; v < residual.length; v++) {
                if (residual[u][v] > 0 && level[v] < 0) {
                    level[v] = level[u] + 1;
                    q.add(v);
                }
            }
        }
        return level[t] >= 0;
    }

    private static int dinicDFS(int[][] residual, int[] level, int[] ptr, int u, int t, int pushed) {
        if (pushed == 0 || u == t) return pushed;

        for (int v = ptr[u]; v < residual.length; v++) {
            ptr[u] = v;
            if (level[u] + 1 == level[v] && residual[u][v] > 0) {
                int tr = dinicDFS(residual, level, ptr, v, t, Math.min(pushed, residual[u][v]));
                if (tr == 0) continue;
                residual[u][v] -= tr;
                residual[v][u] += tr;
                return tr;
            }
        }
        return 0;
    }

    // Manager Menu: Option 10
    public static void optimizeInventoryDistribution() {
        System.out.println("\n------------------------------------------------");
        System.out.println("  OPTIMIZE INVENTORY DISTRIBUTION (MAX FLOW CO4)");
        System.out.println("------------------------------------------------");
        System.out.println("Select Algorithm:");
        System.out.println("1. Dinic's Algorithm (Level Graph + Blocking Flow)");
        System.out.println("2. Edmonds-Karp Algorithm (BFS Augmenting Paths)");
        System.out.println("3. Back");
        int choice = MainInput.readInt("Enter choice: ");
        if (choice == 3) return;

        int source = 0; // Central Depot
        int sink = 5;   // Fulfillment Center
        int[][] capacity = DataStore.logisticsCapacity;

        long startTime = System.nanoTime();
        FlowResult result = (choice == 1)
            ? dinic(capacity, source, sink)
            : edmondsKarp(capacity, source, sink);
        long elapsed = System.nanoTime() - startTime;

        String algoName = (choice == 1) ? "Dinic's Algorithm" : "Edmonds-Karp Algorithm";

        ResultFormatter.printHeader(
            algoName,
            "CO4 - Graph & Network Flows",
            "optimizeInventoryDistribution()",
            "Logistics Network: " + DataStore.LOGISTICS_NODES[source] + " -> " + DataStore.LOGISTICS_NODES[sink]
        );

        System.out.println("Optimal Inventory Flow Routing Across Supply Lanes:");
        System.out.println("----------------------------------------------------");
        for (int i = 0; i < capacity.length; i++) {
            for (int j = 0; j < capacity.length; j++) {
                if (capacity[i][j] > 0) {
                    System.out.printf("• %-26s -> %-26s : %3d / %3d units%n",
                        DataStore.LOGISTICS_NODES[i], DataStore.LOGISTICS_NODES[j],
                        result.flowMatrix[i][j], capacity[i][j]);
                }
            }
        }

        System.out.println("----------------------------------------------------");
        System.out.printf("MAXIMUM INVENTORY THROUGHPUT : %d units/day%n", result.maxFlow);
        System.out.printf("Augmentation Phases / Paths  : %d%n", result.augmentations);

        ResultFormatter.printFooter(
            elapsed,
            (choice == 1) ? "O(V^2 * E) Dinic" : "O(V * E^2) Edmonds-Karp",
            "O(V^2) residual and level matrices",
            "Manager Menu"
        );
    }
}
