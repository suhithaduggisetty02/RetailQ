package retailiq.service;

import retailiq.util.ResultFormatter;

public class ParallelPerformanceService {

    public static void performParallelAnalysis() {
        // Retail batch data processing parameters
        long workT1 = 1_000_000L;    // T1: Work = 1,000,000 operations
        long spanTInf = 200L;        // T_inf: Span = 200 operations (Critical Path)

        long startTime = System.nanoTime();

        int[] processorCounts = { 1, 2, 4, 8, 16, 32, 64 };
        double maxTheoreticalSpeedup = (double) workT1 / spanTInf;

        long elapsed = System.nanoTime() - startTime;

        ResultFormatter.printHeader(
            "Work / Span & Brent's Scheduling Theorem",
            "CO6 - Parallel Performance Analysis",
            "parallelPerformanceAnalysis()",
            "Retail Batch Analytics Pipeline (Work T1 = " + workT1 + ", Span T∞ = " + spanTInf + ")"
        );

        System.out.println("Theoretical Principles:");
        System.out.println("• Work Law      : T_p >= T_1 / p");
        System.out.println("• Span Law      : T_p >= T_infinity");
        System.out.println("• Brent's Bound : T_p <= (T_1 - T_infinity) / p + T_infinity");
        System.out.printf("• Max Speedup   : S_infinity = T_1 / T_infinity = %.1fx%n%n", maxTheoreticalSpeedup);

        System.out.printf("%-10s | %-16s | %-16s | %-14s | %-12s%n",
            "Cores (p)", "Lower Bound", "Brent's Bound", "Speedup (Sp)", "Efficiency");
        System.out.println("-----------------------------------------------------------------------------");

        for (int p : processorCounts) {
            double lowerBound = Math.max((double) workT1 / p, (double) spanTInf);
            double brentsUpperBound = ((double) (workT1 - spanTInf) / p) + spanTInf;
            double estimatedTp = brentsUpperBound;
            double speedup = (double) workT1 / estimatedTp;
            double efficiency = (speedup / p) * 100.0;

            System.out.printf("%-10d | %-16.1f | %-16.1f | %-13.2fx | %-11.1f%%%n",
                p, lowerBound, brentsUpperBound, speedup, efficiency);
        }

        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("Conclusion: As p increases, execution time approaches Span T_infinity = " + spanTInf);
        System.out.printf("Beyond p = %d cores, adding hardware yields diminishing returns due to Span bottleneck.%n",
            (int) (workT1 / spanTInf));

        ResultFormatter.printFooter(
            elapsed,
            "T_p <= (T_1 - T_infinity)/p + T_infinity (Brent's Theorem)",
            "O(1) analytical calculation",
            "Algorithm Lab"
        );
    }
}
