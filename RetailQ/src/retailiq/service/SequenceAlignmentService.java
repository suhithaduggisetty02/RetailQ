package retailiq.service;

import retailiq.util.MainInput;
import retailiq.util.ResultFormatter;

public class SequenceAlignmentService {

    private static final int MATCH_SCORE = 2;
    private static final int MISMATCH_PENALTY = -1;
    private static final int GAP_PENALTY = -1;

    // Hand-built Needleman-Wunsch Global Sequence Alignment
    public static void performNeedlemanWunsch() {
        System.out.println("\n------------------------------------------------");
        System.out.println("  SEQUENCE ALIGNMENT: NEEDLEMAN-WUNSCH (CO3)");
        System.out.println("------------------------------------------------");
        System.out.println("Compares SKU codes or supplier product descriptions.");
        String s1 = MainInput.readString("Enter first SKU/Sequence [default: BLUETOOTH]: ");
        if (s1.isEmpty()) s1 = "BLUETOOTH";
        String s2 = MainInput.readString("Enter second SKU/Sequence [default: BLUE-TOOTH]: ");
        if (s2.isEmpty()) s2 = "BLUE-TOOTH";

        s1 = s1.toUpperCase();
        s2 = s2.toUpperCase();

        int m = s1.length();
        int n = s2.length();

        long startTime = System.nanoTime();

        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) dp[i][0] = i * GAP_PENALTY;
        for (int j = 0; j <= n; j++) dp[0][j] = j * GAP_PENALTY;

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                int match = dp[i - 1][j - 1] + (s1.charAt(i - 1) == s2.charAt(j - 1) ? MATCH_SCORE : MISMATCH_PENALTY);
                int delete = dp[i - 1][j] + GAP_PENALTY;
                int insert = dp[i][j - 1] + GAP_PENALTY;
                dp[i][j] = Math.max(match, Math.max(delete, insert));
            }
        }

        // Traceback alignment
        StringBuilder align1 = new StringBuilder();
        StringBuilder align2 = new StringBuilder();
        int i = m, j = n;

        while (i > 0 || j > 0) {
            if (i > 0 && j > 0 && dp[i][j] == dp[i - 1][j - 1] + (s1.charAt(i - 1) == s2.charAt(j - 1) ? MATCH_SCORE : MISMATCH_PENALTY)) {
                align1.append(s1.charAt(i - 1));
                align2.append(s2.charAt(j - 1));
                i--; j--;
            } else if (i > 0 && dp[i][j] == dp[i - 1][j] + GAP_PENALTY) {
                align1.append(s1.charAt(i - 1));
                align2.append('-');
                i--;
            } else {
                align1.append('-');
                align2.append(s2.charAt(j - 1));
                j--;
            }
        }

        String res1 = align1.reverse().toString();
        String res2 = align2.reverse().toString();

        long elapsed = System.nanoTime() - startTime;

        ResultFormatter.printHeader(
            "Needleman-Wunsch Global Sequence Alignment",
            "CO3 - Dynamic Programming",
            "sequenceAlignment()",
            s1 + " vs " + s2
        );

        System.out.println("Optimal Global Alignment Result:");
        System.out.println("Sequence 1 : " + res1);
        System.out.println("Sequence 2 : " + res2);
        System.out.println("-----------------------------------");
        System.out.println("Optimal Alignment Score: " + dp[m][n]);

        ResultFormatter.printFooter(
            elapsed,
            "O(m * n) Dynamic Programming Table",
            "O(m * n) matrix + O(m + n) traceback",
            "Algorithm Lab"
        );
    }
}
