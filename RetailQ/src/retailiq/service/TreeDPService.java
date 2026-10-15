package retailiq.service;

import java.util.ArrayList;
import java.util.List;

import retailiq.util.ResultFormatter;

public class TreeDPService {

    static class TreeNode {
        int id;
        String name;
        int revenue; // Weight in ₹10k
        List<Integer> children = new ArrayList<>();

        TreeNode(int id, String name, int revenue) {
            this.id = id;
            this.name = name;
            this.revenue = revenue;
        }
    }

    public static void performTreeDP() {
        // Retail category hierarchy tree
        // 0: Store Root -> 1: Electronics, 2: Audio, 3: Wearables
        // 1: Electronics -> 4: Peripherals, 5: Computing
        // 2: Audio -> 6: Earphones, 7: Speakers
        List<TreeNode> tree = new ArrayList<>();
        tree.add(new TreeNode(0, "Department Catalog (Root)", 10));
        tree.add(new TreeNode(1, "Electronics", 85));
        tree.add(new TreeNode(2, "Audio Systems", 50));
        tree.add(new TreeNode(3, "Smart Wearables", 40));
        tree.add(new TreeNode(4, "Gaming Peripherals", 35));
        tree.add(new TreeNode(5, "Office Computing", 60));
        tree.add(new TreeNode(6, "Personal Earbuds", 25));
        tree.add(new TreeNode(7, "Bluetooth Speakers", 30));

        tree.get(0).children.add(1);
        tree.get(0).children.add(2);
        tree.get(0).children.add(3);
        tree.get(1).children.add(4);
        tree.get(1).children.add(5);
        tree.get(2).children.add(6);
        tree.get(2).children.add(7);

        int n = tree.size();

        long startTime = System.nanoTime();

        // Tree DP: Maximum Weight Independent Set on Trees
        // dp[u][0] = max revenue when node u is NOT selected
        // dp[u][1] = max revenue when node u IS selected
        int[][] dp = new int[n][2];
        solveTreeDP(0, tree, dp);

        // Subtree sizes and Rerooting DP for all-pairs distance
        int[] subSize = new int[n];
        int[] distDown = new int[n];
        dfsSizes(0, tree, subSize, distDown);

        int[] totalDist = new int[n];
        totalDist[0] = distDown[0];
        dfsReroot(0, tree, subSize, totalDist, n);

        long elapsed = System.nanoTime() - startTime;

        ResultFormatter.printHeader(
            "Tree DP & Rerooting Dynamic Programming",
            "CO3 - Dynamic Programming",
            "treeCategoryAnalysis()",
            n + " Taxonomy Nodes in Category Tree Hierarchy"
        );

        System.out.println("Category Tree Nodes & Maximum Independent Revenue Lift:");
        System.out.println("---------------------------------------------------------");
        for (TreeNode node : tree) {
            System.out.printf("• Node [%d] %-25s | Weight: ₹%2d0k | Excluded DP: ₹%3d0k | Included DP: ₹%3d0k%n",
                node.id, node.name, node.revenue, dp[node.id][0], dp[node.id][1]);
        }

        int maxIndependentRevenue = Math.max(dp[0][0], dp[0][1]);
        System.out.println("---------------------------------------------------------");
        System.out.printf("Max Non-Adjacent Promotion Value (Tree DP)  : ₹%d0,000%n", maxIndependentRevenue);

        // Find centroid (node with minimum total distance to all other categories)
        int bestCentroid = 0;
        int minD = totalDist[0];
        for (int i = 1; i < n; i++) {
            if (totalDist[i] < minD) {
                minD = totalDist[i];
                bestCentroid = i;
            }
        }
        System.out.printf("Optimal Taxonomy Hub (Rerooting Centroid)   : [%d] %s (Total Distance: %d hops)%n",
            bestCentroid, tree.get(bestCentroid).name, minD);

        ResultFormatter.printFooter(
            elapsed,
            "O(N) 2-pass Linear Time Tree & Rerooting DP",
            "O(N) state vectors",
            "Algorithm Lab"
        );
    }

    private static void solveTreeDP(int u, List<TreeNode> tree, int[][] dp) {
        dp[u][0] = 0;
        dp[u][1] = tree.get(u).revenue;

        for (int v : tree.get(u).children) {
            solveTreeDP(v, tree, dp);
            dp[u][0] += Math.max(dp[v][0], dp[v][1]);
            dp[u][1] += dp[v][0];
        }
    }

    private static void dfsSizes(int u, List<TreeNode> tree, int[] subSize, int[] distDown) {
        subSize[u] = 1;
        distDown[u] = 0;
        for (int v : tree.get(u).children) {
            dfsSizes(v, tree, subSize, distDown);
            subSize[u] += subSize[v];
            distDown[u] += distDown[v] + subSize[v];
        }
    }

    private static void dfsReroot(int u, List<TreeNode> tree, int[] subSize, int[] totalDist, int totalN) {
        for (int v : tree.get(u).children) {
            totalDist[v] = totalDist[u] - subSize[v] + (totalN - subSize[v]);
            dfsReroot(v, tree, subSize, totalDist, totalN);
        }
    }
}
