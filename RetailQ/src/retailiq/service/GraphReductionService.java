package retailiq.service;

import java.util.ArrayList;
import java.util.List;

import retailiq.util.ResultFormatter;

public class GraphReductionService {

    public static void performGraphConflictAnalysis() {
        String[] vendors = {
            "Vendor A (Samsung Electronics)",
            "Vendor B (Sony Audio)",
            "Vendor C (Logitech Peripherals)",
            "Vendor D (Apple Accessories)",
            "Vendor E (Bose Sound)"
        };
        int n = vendors.length;

        // Partnership Compatibility Graph G
        // Edge (u, v) means vendor u and v can co-exist in the same retail kiosk
        boolean[][] compatG = new boolean[n][n];
        compatG[0][1] = compatG[1][0] = true; // A & B
        compatG[0][2] = compatG[2][0] = true; // A & C
        compatG[1][2] = compatG[2][1] = true; // B & C
        compatG[1][4] = compatG[4][1] = true; // B & E
        compatG[2][3] = compatG[3][2] = true; // C & D
        compatG[3][4] = compatG[4][3] = true; // D & E

        long startTime = System.nanoTime();

        // 1. Find Max Clique in G (subset where every pair is connected)
        int maxCliqueMask = 0;
        int maxCliqueSize = 0;

        int totalStates = 1 << n;
        for (int mask = 1; mask < totalStates; mask++) {
            boolean isClique = true;
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    for (int j = i + 1; j < n; j++) {
                        if ((mask & (1 << j)) != 0 && !compatG[i][j]) {
                            isClique = false;
                            break;
                        }
                    }
                }
                if (!isClique) break;
            }

            if (isClique) {
                int count = Integer.bitCount(mask);
                if (count > maxCliqueSize) {
                    maxCliqueSize = count;
                    maxCliqueMask = mask;
                }
            }
        }

        // 2. Construct Complement Graph G_comp (conflict edges)
        boolean[][] gComp = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    gComp[i][j] = !compatG[i][j];
                }
            }
        }

        // 3. Max Independent Set in G_comp (exact equivalent to Max Clique in G!)
        // In G_comp, independent set has no conflict edges -> in G they have all compatibility edges!
        int misMaskInComp = maxCliqueMask;

        // 4. Min Vertex Cover in G_comp = V \ MIS
        int mvcMaskInComp = (totalStates - 1) ^ misMaskInComp;

        long elapsed = System.nanoTime() - startTime;

        ResultFormatter.printHeader(
            "CLIQUE / Independent Set / Vertex Cover Reductions",
            "CO5 - NP-Completeness & Polynomial Reductions",
            "graphConflictAnalysis()",
            n + " Retail Vendor Partners Compatibility & Conflict Graph"
        );

        System.out.println("Vendor Compatibility Graph G (Mutual Co-existence Edges):");
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (compatG[i][j]) {
                    System.out.printf("• %s <---> %s%n", vendors[i], vendors[j]);
                }
            }
        }

        System.out.println("\n1. MAXIMUM CLIQUE in Graph G (Largest Mutual Co-existence Alliance):");
        for (int i = 0; i < n; i++) {
            if ((maxCliqueMask & (1 << i)) != 0) {
                System.out.println("  [★] " + vendors[i]);
            }
        }
        System.out.println("  Clique Size: " + maxCliqueSize + " vendors");

        System.out.println("\n2. REDUCTION TO MAXIMUM INDEPENDENT SET in Complement Graph G_comp:");
        System.out.println("  By Karp's reduction: S is a Clique in G <=> S is an Independent Set in G_comp");
        System.out.println("  Max Independent Set in G_comp size = " + Integer.bitCount(misMaskInComp) + " (Identical to Max Clique!)");

        System.out.println("\n3. REDUCTION TO MINIMUM VERTEX COVER in Complement Graph G_comp:");
        System.out.println("  By Gallai's Theorem: |MIS(G_comp)| + |MVC(G_comp)| = |V|");
        for (int i = 0; i < n; i++) {
            if ((mvcMaskInComp & (1 << i)) != 0) {
                System.out.println("  [!] " + vendors[i] + " (Excluded to satisfy conflict constraints)");
            }
        }
        System.out.printf("  Min Vertex Cover Size = %d vendors (|V| - |Clique| = %d - %d = %d)%n",
            Integer.bitCount(mvcMaskInComp), n, maxCliqueSize, Integer.bitCount(mvcMaskInComp));

        ResultFormatter.printFooter(
            elapsed,
            "NP-Complete Exact Reduction: O(2^N * N^2)",
            "O(N^2) adjacency & bitmask representation",
            "Algorithm Lab"
        );
    }
}
