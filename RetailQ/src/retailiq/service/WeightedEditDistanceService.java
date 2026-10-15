package retailiq.service;

import retailiq.util.MainInput;

public class WeightedEditDistanceService {

    private static final int INSERT_COST = 1;
    private static final int DELETE_COST = 1;
    private static final int REPLACE_COST = 2;

    public static int distance(String a, String b) {

        a = a.toLowerCase();
        b = b.toLowerCase();

        int m = a.length();
        int n = b.length();

        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            dp[i][0] = i * DELETE_COST;
        }

        for (int j = 0; j <= n; j++) {
            dp[0][j] = j * INSERT_COST;
        }

        for (int i = 1; i <= m; i++) {

            for (int j = 1; j <= n; j++) {

                int replace =
                        a.charAt(i - 1) == b.charAt(j - 1)
                        ? 0 : REPLACE_COST;

                dp[i][j] = Math.min(
                        dp[i - 1][j] + DELETE_COST,
                        Math.min(
                                dp[i][j - 1] + INSERT_COST,
                                dp[i - 1][j - 1] + replace
                        )
                );
            }
        }

        return dp[m][n];
    }

    public static void perform() {

        String first =
                MainInput.readString("Enter first word: ");

        String second =
                MainInput.readString("Enter second word: ");

        int result = distance(first, second);

        System.out.println();
        System.out.println("======= WEIGHTED EDIT DISTANCE =======");
        System.out.println("Insertion Cost : " + INSERT_COST);
        System.out.println("Deletion Cost  : " + DELETE_COST);
        System.out.println("Replace Cost   : " + REPLACE_COST);
        System.out.println("Distance       : " + result);
    }
}