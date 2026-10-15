package retailiq.service;

import java.util.ArrayList;
import java.util.List;

import retailiq.model.Promotion;
import retailiq.util.DataStore;
import retailiq.util.ResultFormatter;

public class ConflictAnalysisService {

    public static void analyzePromotionConflicts() {
        int n = DataStore.promotions.size();
        boolean[][] conflict = DataStore.promoConflictMatrix;

        long startTime = System.nanoTime();

        // Exact Maximum Independent Set via Bitmask Search
        int bestMask = 0;
        int maxCount = 0;

        int totalMasks = 1 << n;
        for (int mask = 0; mask < totalMasks; mask++) {
            boolean valid = true;
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    for (int j = i + 1; j < n; j++) {
                        if ((mask & (1 << j)) != 0 && conflict[i][j]) {
                            valid = false;
                            break;
                        }
                    }
                }
                if (!valid) break;
            }

            if (valid) {
                int count = Integer.bitCount(mask);
                if (count > maxCount) {
                    maxCount = count;
                    bestMask = mask;
                }
            }
        }

        // The complement of the Maximum Independent Set is the Minimum Vertex Cover!
        int vertexCoverMask = ((1 << n) - 1) ^ bestMask;

        List<Promotion> independentSet = new ArrayList<>();
        List<Promotion> vertexCover = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if ((bestMask & (1 << i)) != 0) {
                independentSet.add(DataStore.promotions.get(i));
            } else {
                vertexCover.add(DataStore.promotions.get(i));
            }
        }

        long elapsed = System.nanoTime() - startTime;

        ResultFormatter.printHeader(
            "Vertex Cover & Independent Set Reductions",
            "CO5 - NP-Completeness & Exact Solvers",
            "analyzePromotionConflicts()",
            n + " Promotional Campaigns in Conflict Network"
        );

        System.out.println("Active Conflict Relations in System:");
        System.out.println("------------------------------------");
        int conflictEdges = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (conflict[i][j]) {
                    conflictEdges++;
                    System.out.printf("• [%s] %s  <--->  [%s] %s (Category Overlap)%n",
                        DataStore.promotions.get(i).getCode(), DataStore.promotions.get(i).getName(),
                        DataStore.promotions.get(j).getCode(), DataStore.promotions.get(j).getName());
                }
            }
        }
        System.out.println("Total Conflict Edges: " + conflictEdges);

        System.out.println("\n================ MAXIMUM INDEPENDENT SET (COMPATIBLE CAMPAIGNS) ================");
        System.out.println("Largest possible bundle of promotions that can run CONCURRENTLY without conflict:");
        for (int i = 0; i < independentSet.size(); i++) {
            Promotion p = independentSet.get(i);
            System.out.printf("%d. [%s] %-32s (Cost: ₹%.0f, Lift: ₹%.0f)%n",
                (i + 1), p.getCode(), p.getName(), p.getCost(), p.getProjectedLift());
        }
        System.out.println("Compatible Bundle Size: " + independentSet.size() + " promotions");

        System.out.println("\n================ MINIMUM VERTEX COVER (PROMOTIONS TO CANCEL) ===================");
        System.out.println("Smallest set of conflicting promotions to retire to eliminate ALL rule conflicts:");
        for (int i = 0; i < vertexCover.size(); i++) {
            Promotion p = vertexCover.get(i);
            System.out.printf("%d. [%s] %-32s (Category: %s)%n",
                (i + 1), p.getCode(), p.getName(), p.getCategory());
        }
        System.out.println("Retirement Set Size: " + vertexCover.size() + " promotions");

        System.out.println("\nTheoretical Verification:");
        System.out.printf("|MIS| (%d) + |MVC| (%d) = Total Promotions |V| (%d) [Gallai's Identity]\n",
            independentSet.size(), vertexCover.size(), n);

        ResultFormatter.printFooter(
            elapsed,
            "O(2^N * N^2) Exact NP-Complete Bitmask Solver",
            "O(N) subset state representation",
            "Manager Menu"
        );
    }
}
