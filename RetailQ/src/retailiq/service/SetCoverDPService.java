package retailiq.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retailiq.util.ResultFormatter;

public class SetCoverDPService {

    static class Campaign {
        int id;
        String name;
        int cost;
        int coverMask;
        String covers;

        Campaign(int id, String name, int cost, int coverMask, String covers) {
            this.id = id;
            this.name = name;
            this.cost = cost;
            this.coverMask = coverMask;
            this.covers = covers;
        }
    }

    public static void performSetCoverDP() {
        String[] demographics = { "Students", "Gamers", "Corporate Pros", "Fitness Buffs", "Home Makers" };
        int m = demographics.length;
        int fullMask = (1 << m) - 1;

        List<Campaign> campaigns = new ArrayList<>();
        // Campaign 1: Students + Gamers (mask 0b00011 = 3)
        campaigns.add(new Campaign(1, "College Gaming Fest", 12000, 0b00011, "Students, Gamers"));
        // Campaign 2: Corporate Pros + Fitness Buffs (mask 0b01100 = 12)
        campaigns.add(new Campaign(2, "Executive Wellness Deal", 18000, 0b01100, "Corporate Pros, Fitness Buffs"));
        // Campaign 3: Students + Home Makers (mask 0b10001 = 17)
        campaigns.add(new Campaign(3, "Back to School Family Pack", 14000, 0b10001, "Students, Home Makers"));
        // Campaign 4: Gamers + Pros + Fitness (mask 0b01110 = 14)
        campaigns.add(new Campaign(4, "Tech Enthusiast Flash Sale", 22000, 0b01110, "Gamers, Corporate Pros, Fitness Buffs"));
        // Campaign 5: Home Makers + Fitness Buffs (mask 0b11000 = 24)
        campaigns.add(new Campaign(5, "Active Lifestyle Promo", 15000, 0b11000, "Fitness Buffs, Home Makers"));

        long startTime = System.nanoTime();

        int numStates = 1 << m;
        int[] dp = new int[numStates];
        int[] parentMask = new int[numStates];
        int[] parentCamp = new int[numStates];

        Arrays.fill(dp, 1_000_000_000);
        Arrays.fill(parentMask, -1);
        Arrays.fill(parentCamp, -1);

        dp[0] = 0;

        for (int mask = 0; mask < numStates; mask++) {
            if (dp[mask] >= 1_000_000_000) continue;

            for (int i = 0; i < campaigns.size(); i++) {
                Campaign c = campaigns.get(i);
                int nextMask = mask | c.coverMask;
                int newCost = dp[mask] + c.cost;

                if (newCost < dp[nextMask]) {
                    dp[nextMask] = newCost;
                    parentMask[nextMask] = mask;
                    parentCamp[nextMask] = i;
                }
            }
        }

        // Backtrack optimal solution
        List<Campaign> chosen = new ArrayList<>();
        int curr = fullMask;
        while (curr > 0 && parentCamp[curr] != -1) {
            int cIndex = parentCamp[curr];
            chosen.add(campaigns.get(cIndex));
            curr = parentMask[curr];
        }

        long elapsed = System.nanoTime() - startTime;

        ResultFormatter.printHeader(
            "Set Cover via Bitmask Dynamic Programming",
            "CO3 - Dynamic Programming",
            "setCoverPromotionPlanning()",
            m + " Target Demographics to Cover across " + campaigns.size() + " Multi-Segment Campaigns"
        );

        System.out.println("Target Customer Demographics:");
        for (int i = 0; i < m; i++) {
            System.out.printf("  [Bit %d] %s%n", i, demographics[i]);
        }

        System.out.println("\nAvailable Marketing Packages:");
        for (Campaign c : campaigns) {
            System.out.printf("• [%d] %-28s | Cost: ₹%5d | Coverage: %s%n", c.id, c.name, c.cost, c.covers);
        }

        System.out.println("\n================ OPTIMAL MINIMUM-COST SET COVER ================");
        int totalCost = 0;
        for (int i = 0; i < chosen.size(); i++) {
            Campaign c = chosen.get(i);
            totalCost += c.cost;
            System.out.printf("%d. %-28s (₹%5d) -> Covers: %s%n", (i + 1), c.name, c.cost, c.covers);
        }
        System.out.println("----------------------------------------------------------------");
        System.out.printf("Total Campaigns Needed : %d campaigns%n", chosen.size());
        System.out.printf("Minimum Total Budget   : ₹%d (100%% Demographics Covered)%n", totalCost);

        ResultFormatter.printFooter(
            elapsed,
            "O(N * 2^M) Bitmask DP vs O(2^N) Brute Force",
            "O(2^M) DP table",
            "Algorithm Lab"
        );
    }
}
