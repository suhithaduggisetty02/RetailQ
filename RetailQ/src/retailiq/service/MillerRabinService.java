package retailiq.service;

import java.math.BigInteger;
import java.util.Random;

import retailiq.util.MainInput;
import retailiq.util.ResultFormatter;

public class MillerRabinService {

    private static final Random random = new Random();

    // Hand-built Miller-Rabin Probabilistic Primality Test
    public static boolean isProbablePrime(BigInteger n, int k) {
        if (n.compareTo(BigInteger.ONE) <= 0) return false;
        if (n.equals(BigInteger.valueOf(2)) || n.equals(BigInteger.valueOf(3))) return true;
        if (n.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)) return false;

        // Write n - 1 as 2^s * d
        BigInteger d = n.subtract(BigInteger.ONE);
        int s = 0;
        while (d.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)) {
            d = d.divide(BigInteger.valueOf(2));
            s++;
        }

        // Run k independent witness trials
        for (int i = 0; i < k; i++) {
            BigInteger a = uniformRandom(BigInteger.valueOf(2), n.subtract(BigInteger.valueOf(2)));
            BigInteger x = a.modPow(d, n);

            if (x.equals(BigInteger.ONE) || x.equals(n.subtract(BigInteger.ONE))) {
                continue;
            }

            boolean composite = true;
            for (int r = 1; r < s; r++) {
                x = x.modPow(BigInteger.valueOf(2), n);
                if (x.equals(n.subtract(BigInteger.ONE))) {
                    composite = false;
                    break;
                }
            }

            if (composite) {
                return false; // Definitely composite
            }
        }

        return true; // Probably prime with error probability <= 4^(-k)
    }

    private static BigInteger uniformRandom(BigInteger bottom, BigInteger top) {
        BigInteger res;
        do {
            res = new BigInteger(top.bitLength(), random);
        } while (res.compareTo(bottom) < 0 || res.compareTo(top) > 0);
        return res;
    }

    public static void performMillerRabin() {
        System.out.println("\n------------------------------------------------");
        System.out.println("   MILLER-RABIN PRIMALITY TEST (CRYPTOGRAPHY)");
        System.out.println("------------------------------------------------");
        System.out.println("Verifies cryptographic tokens for secure retail transaction gateways.");
        System.out.println("Default Sample Prime Candidate: 1000000007 (or 982451653, 1000000009)");
        String input = MainInput.readString("Enter integer to test [or press Enter for 1000000007]: ");
        if (input.isEmpty()) input = "1000000007";

        BigInteger n;
        try {
            n = new BigInteger(input);
        } catch (Exception e) {
            System.out.println("Invalid large integer format.");
            return;
        }

        int k = 10; // 10 rounds gives error probability < 4^(-10) = 1 in 1,048,576

        long startTime = System.nanoTime();
        boolean prime = isProbablePrime(n, k);
        long elapsed = System.nanoTime() - startTime;

        ResultFormatter.printHeader(
            "Miller-Rabin Probabilistic Primality Test",
            "CO6 - Randomized Algorithms",
            "largeIntegerPrimalityTest()",
            "Candidate: " + n + " with k = " + k + " Witness Rounds"
        );

        System.out.println("Test Verification Results:");
        System.out.println("--------------------------");
        System.out.println("• Candidate Number (N)        : " + n);
        System.out.println("• Number of Bit Digits       : " + n.bitLength() + " bits");
        System.out.println("• Witness Test Rounds (k)    : " + k + " rounds");
        System.out.printf("• Primality Verdict          : %s%n",
            prime ? "PROBABLE PRIME (Secure Key Token Validated!)" : "COMPOSITE (Insecure Token - Discarded)");
        if (prime) {
            System.out.printf("• Max Probability of Error   : <= 4^(-%d) = 1 in %.0f (< 0.0001%%)%n",
                k, Math.pow(4, k));
        }

        ResultFormatter.printFooter(
            elapsed,
            "O(k * log^3 n) Polynomial Time Modular Exponentiation",
            "O(log n) bit storage",
            "Algorithm Lab"
        );
    }
}
