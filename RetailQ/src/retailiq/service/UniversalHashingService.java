package retailiq.service;

import java.util.Random;

import retailiq.util.ResultFormatter;

public class UniversalHashingService {

    private static final long PRIME = 1_000_003L; // Large prime > max barcode
    private static final Random random = new Random(42);

    public static void performUniversalHashing() {
        // Retail product SKU barcode numbers (6-digit integer barcodes)
        long[] barcodes = {
            890101L, 890111L, 890121L, 890131L, 890141L,
            890205L, 890215L, 890225L, 890308L, 890318L,
            890412L, 890422L, 890530L, 890540L, 890650L
        };
        int tableSize = 11; // Modulo hash bucket count

        long startTime = System.nanoTime();

        // Universal Hash Family parameters: a in [1, p-1], b in [0, p-1]
        long a = 1 + (Math.abs(random.nextLong()) % (PRIME - 1));
        long b = Math.abs(random.nextLong()) % PRIME;

        int[] universalBuckets = new int[tableSize];
        int universalCollisions = 0;

        int[] naiveBuckets = new int[tableSize];
        int naiveCollisions = 0;

        for (long code : barcodes) {
            // 2-Universal Hash: ((a * x + b) mod p) mod m
            int hUniv = (int) (((a * code + b) % PRIME) % tableSize);
            if (universalBuckets[hUniv] > 0) universalCollisions++;
            universalBuckets[hUniv]++;

            // Naive Hash: code mod m (prone to systematic pattern clustering)
            int hNaive = (int) (code % tableSize);
            if (naiveBuckets[hNaive] > 0) naiveCollisions++;
            naiveBuckets[hNaive]++;
        }

        long elapsed = System.nanoTime() - startTime;

        ResultFormatter.printHeader(
            "2-Universal Hash Family vs Naive Modulo Hashing",
            "CO6 - Randomized Algorithms",
            "productCodeHashing()",
            barcodes.length + " Retail Barcode Keys into " + tableSize + " Hash Slots"
        );

        System.out.printf("Universal Hash Function Chosen: h_{%d,%d}(x) = ((%d * x + %d) mod %d) mod %d%n",
            a, b, a, b, PRIME, tableSize);
        System.out.println("----------------------------------------------------------------------------------");

        System.out.printf("%-10s | %-28s | %-28s%n", "Bucket #", "Universal Hash Count", "Naive Hash Count");
        System.out.println("----------------------------------------------------------------------------------");
        for (int i = 0; i < tableSize; i++) {
            System.out.printf("Slot [%2d]  | %-28s | %-28s%n",
                i,
                universalBuckets[i] + " keys " + "*".repeat(universalBuckets[i]),
                naiveBuckets[i] + " keys " + "*".repeat(naiveBuckets[i])
            );
        }

        System.out.println("----------------------------------------------------------------------------------");
        System.out.printf("Total Detected Collisions : Universal Hash = %d | Naive Hash = %d%n",
            universalCollisions, naiveCollisions);
        System.out.printf("Collision Probability     : P(h(x) = h(y)) <= 1/m = 1/%d = %.3f%n",
            tableSize, 1.0 / tableSize);

        ResultFormatter.printFooter(
            elapsed,
            "O(1) Evaluation per key, Guaranteeing <= n/m expected collisions",
            "O(m) table buckets",
            "Algorithm Lab"
        );
    }
}
