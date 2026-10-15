package retailiq.service;

import java.util.ArrayList;
import java.util.List;

import retailiq.model.Promotion;
import retailiq.util.DataStore;
import retailiq.util.MainInput;
import retailiq.util.ResultFormatter;

public class KnapsackPromotionService {

    public static void optimizePromotions() {
        System.out.println("\n------------------------------------------------");
        System.out.println("   OPTIMIZE PROMOTIONS (0/1 KNAPSACK DP - CO5)");
        System.out.println("------------------------------------------------");
        System.out.println("Available Marketing Campaigns:");
        for (Promotion p : DataStore.promotions) {
            System.out.println(p);
        }

        double defaultBudget = 30000;
        System.out.printf("%nEnter Marketing Campaign Budget in ₹ [Default: ₹%.0f]: ", defaultBudget);
        String bInput = MainInput.readString("");
        double budget = defaultBudget;
        if (!bInput.isEmpty()) {
            try {
                budget = Double.parseDouble(bInput);
            } catch (NumberFormatException ignored) {}
        }

        int capacity = (int) (budget / 1000); // Scale by 1000 for integer knapsack
        int n = DataStore.promotions.size();

        int[] weights = new int[n];
        int[] values = new int[n];
        for (int i = 0; i < n; i++) {
            weights[i] = (int) (DataStore.promotions.get(i).getCost() / 1000);
            values[i] = (int) (DataStore.promotions.get(i).getProjectedLift() / 1000);
        }

        long startTime = System.nanoTime();

        // Hand-built 0/1 Knapsack DP Table
        int[][] dp = new int[n + 1][capacity + 1];

        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= capacity; w++) {
                if (weights[i - 1] <= w) {
                    dp[i][w] = Math.max(
                        dp[i - 1][w],
                        dp[i - 1][w - weights[i - 1]] + values[i - 1]
                    );
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        // Backtracking to find selected promotions
        List<Promotion> selected = new ArrayList<>();
        int currW = capacity;
        for (int i = n; i > 0 && currW > 0; i--) {
            if (dp[i][currW] != dp[i - 1][currW]) {
                selected.add(DataStore.promotions.get(i - 1));
                currW -= weights[i - 1];
            }
        }

        long elapsed = System.nanoTime() - startTime;

        double totalCost = 0;
        double totalLift = 0;
        for (Promotion p : selected) {
            totalCost += p.getCost();
            totalLift += p.getProjectedLift();
        }

        ResultFormatter.printHeader(
            "0/1 Knapsack Dynamic Programming",
            "CO5 - Greedy & Dynamic Programming / NP-Complete",
            "optimizePromotions()",
            String.format("Promotional Budget Limit: ₹%.2f across %d campaigns", budget, n)
        );

        System.out.println("Optimal Promotion Portfolio Selected:");
        System.out.println("--------------------------------------");
        for (int i = 0; i < selected.size(); i++) {
            Promotion p = selected.get(i);
            System.out.printf("%d. [%s] %-30s | Cost: ₹%6.0f | Expected Lift: ₹%7.0f%n",
                (i + 1), p.getCode(), p.getName(), p.getCost(), p.getProjectedLift());
        }

        System.out.println("--------------------------------------");
        System.out.printf("Total Budget Invested : ₹%.2f / ₹%.2f%n", totalCost, budget);
        System.out.printf("Total Projected Lift  : ₹%.2f%n", totalLift);
        System.out.printf("Projected Net ROI     : %.1f%%%n", ((totalLift - totalCost) / totalCost) * 100.0);

        ResultFormatter.printFooter(
            elapsed,
            "O(N * W) Pseudo-polynomial DP where N = campaigns, W = budget capacity",
            "O(N * W) DP table state storage",
            "Manager Menu"
        );
    }
}
