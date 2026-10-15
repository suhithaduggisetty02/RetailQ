package retailiq.service;

import retailiq.util.ResultFormatter;

public class SOSDPService {

    public static void performSOSDP() {
        int n = 4; // 4 retail customer profile feature flags (2^4 = 16 sub-segments)
        String[] flags = { "Prime Member", "App Installed", "Discount Hunter", "High Spender" };

        // Base segment daily revenue (in ₹1,000s) for exact bitmask profiles
        int totalStates = 1 << n;
        int[] baseRevenue = { 12, 18, 25, 30, 15, 22, 40, 55, 20, 28, 45, 60, 35, 48, 70, 95 };

        long startTime = System.nanoTime();

        // Hand-built SOS DP in O(N * 2^N)
        int[] sos = new int[totalStates];
        System.arraycopy(baseRevenue, 0, sos, 0, totalStates);

        for (int i = 0; i < n; i++) {
            for (int mask = 0; mask < totalStates; mask++) {
                if ((mask & (1 << i)) != 0) {
                    sos[mask] += sos[mask ^ (1 << i)];
                }
            }
        }

        long elapsed = System.nanoTime() - startTime;

        ResultFormatter.printHeader(
            "Sum Over Subsets Dynamic Programming (SOS DP)",
            "CO3 - Dynamic Programming",
            "categorySubsetAnalysis()",
            n + " Customer Feature Flags (" + totalStates + " Persona Bitmasks)"
        );

        System.out.println("Feature Flags: [b0: Prime, b1: App, b2: Discount, b3: HighSpender]");
        System.out.printf("%-8s | %-32s | %-12s | %-16s%n", "Mask", "Feature Profile", "Base Rev (₹)", "Cumulative SOS (₹)");
        System.out.println("-------------------------------------------------------------------------------");

        for (int mask = 0; mask < totalStates; mask++) {
            StringBuilder sb = new StringBuilder();
            for (int bit = 0; bit < n; bit++) {
                if ((mask & (1 << bit)) != 0) {
                    if (sb.length() > 0) sb.append("+");
                    sb.append(flags[bit].split(" ")[0]);
                }
            }
            if (sb.length() == 0) sb.append("None");
            System.out.printf("%-8s | %-32s | ₹%-11d | ₹%-15d%n",
                String.format("%4s", Integer.toBinaryString(mask)).replace(' ', '0'),
                sb.toString(),
                baseRevenue[mask] * 1000,
                sos[mask] * 1000
            );
        }

        ResultFormatter.printFooter(
            elapsed,
            "O(N * 2^N) SOS DP vs O(3^N) Naive Submask Iteration",
            "O(2^N) state array",
            "Algorithm Lab"
        );
    }
}
