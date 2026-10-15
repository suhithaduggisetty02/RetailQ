package retailiq.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import retailiq.service.NetworkFlowService.FlowResult;
import retailiq.util.DataStore;
import retailiq.util.ResultFormatter;

public class MinCutService {

    public static class BottleneckEdge {
        public int from;
        public int to;
        public int capacity;

        public BottleneckEdge(int from, int to, int capacity) {
            this.from = from;
            this.to = to;
            this.capacity = capacity;
        }
    }

    public static void identifySupplyBottlenecks() {
        int source = 0; // Central Depot
        int sink = 5;   // Fulfillment Center
        int[][] capacity = DataStore.logisticsCapacity;
        int n = capacity.length;

        long startTime = System.nanoTime();

        // 1. Compute Max Flow via Dinic / Edmonds-Karp to obtain residual graph
        FlowResult flowRes = NetworkFlowService.dinic(capacity, source, sink);

        // 2. BFS from source in residual graph to find reachable partition S
        boolean[] visited = new boolean[n];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        visited[source] = true;

        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int v = 0; v < n; v++) {
                if (!visited[v] && flowRes.residualCapacity[u][v] > 0) {
                    visited[v] = true;
                    queue.add(v);
                }
            }
        }

        // 3. Edges from visited (S) to unvisited (T) in original graph form the Min-Cut
        List<BottleneckEdge> bottlenecks = new ArrayList<>();
        int minCutCapacity = 0;

        for (int u = 0; u < n; u++) {
            if (visited[u]) {
                for (int v = 0; v < n; v++) {
                    if (!visited[v] && capacity[u][v] > 0) {
                        bottlenecks.add(new BottleneckEdge(u, v, capacity[u][v]));
                        minCutCapacity += capacity[u][v];
                    }
                }
            }
        }

        long elapsed = System.nanoTime() - startTime;

        ResultFormatter.printHeader(
            "Max-Flow Min-Cut Theorem (Minimum Cut)",
            "CO4 - Graph & Network Flows",
            "identifySupplyBottlenecks()",
            "Logistics Network Capacity Matrix"
        );

        System.out.println("Supply Chain Reachable Partition (Source Side S):");
        for (int i = 0; i < n; i++) {
            if (visited[i]) {
                System.out.println("  [+] " + DataStore.LOGISTICS_NODES[i]);
            }
        }

        System.out.println("\nIsolated Supply Sink Partition (Sink Side T):");
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                System.out.println("  [-] " + DataStore.LOGISTICS_NODES[i]);
            }
        }

        System.out.println("\n================ IDENTIFIED SUPPLY BOTTLENECK EDGES ================");
        System.out.println("These saturated shipping lanes represent the critical operational chokepoints:");
        for (int i = 0; i < bottlenecks.size(); i++) {
            BottleneckEdge b = bottlenecks.get(i);
            System.out.printf("%d. %s -> %s%n",
                (i + 1), DataStore.LOGISTICS_NODES[b.from], DataStore.LOGISTICS_NODES[b.to]);
            System.out.printf("   Saturated Capacity: %d units/day (Flow: %d/%d)%n",
                b.capacity, flowRes.flowMatrix[b.from][b.to], b.capacity);
            System.out.println("   Recommendation    : Expand lane logistics or add secondary transport carrier.\n");
        }

        System.out.printf("Total Min-Cut Bottleneck Capacity: %d units/day (Matches Max Flow: %d)%n",
            minCutCapacity, flowRes.maxFlow);

        ResultFormatter.printFooter(
            elapsed,
            "O(V^2 * E) Max Flow + O(V + E) BFS Reachability",
            "O(V + E) partition storage",
            "Manager Menu"
        );
    }
}
