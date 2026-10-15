package retailiq.service;

import retailiq.util.MainInput;

public class DamerauLevenshteinService {

    public static int distance(String a, String b) {

        a = a.toLowerCase();
        b = b.toLowerCase();

        int m = a.length();
        int n = b.length();

        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= m; i++) {

            for (int j = 1; j <= n; j++) {

                int cost =
                        a.charAt(i - 1) == b.charAt(j - 1)
                        ? 0 : 1;

                dp[i][j] = Math.min(
                        Math.min(
                                dp[i - 1][j] + 1,
                                dp[i][j - 1] + 1
                        ),
                        dp[i - 1][j - 1] + cost
                );

                if (i > 1 && j > 1
                        && a.charAt(i - 1) == b.charAt(j - 2)
                        && a.charAt(i - 2) == b.charAt(j - 1)) {

                    dp[i][j] = Math.min(
                            dp[i][j],
                            dp[i - 2][j - 2] + 1
                    );
                }
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
        System.out.println("======= DAMERAU-LEVENSHTEIN DISTANCE =======");
        System.out.println("First  : " + first);
        System.out.println("Second : " + second);
        System.out.println("Distance: " + result);
    }
}