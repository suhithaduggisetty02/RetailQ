package retailiq.service;

import retailiq.util.MainInput;

public class MatrixChainService {

    public static int matrixChainOrder(int[] dimensions) {

        int n = dimensions.length - 1;

        int[][] dp = new int[n][n];

        for (int length = 2; length <= n; length++) {

            for (int i = 0; i <= n - length; i++) {

                int j = i + length - 1;

                dp[i][j] = Integer.MAX_VALUE;

                for (int k = i; k < j; k++) {

                    int cost =
                            dp[i][k]
                            + dp[k + 1][j]
                            + dimensions[i]
                            * dimensions[k + 1]
                            * dimensions[j + 1];

                    dp[i][j] =
                            Math.min(dp[i][j], cost);
                }
            }
        }

        return dp[0][n - 1];
    }

    public static void perform() {

        int count =
                MainInput.readInt(
                        "Enter number of matrices: "
                );

        if (count < 2) {

            System.out.println(
                    "At least 2 matrices are required."
            );

            return;
        }

        int[] dimensions = new int[count + 1];

        System.out.println(
                "\nEnter matrix dimensions."
        );

        for (int i = 0; i <= count; i++) {

            dimensions[i] =
                    MainInput.readInt(
                            "Dimension " + (i + 1) + ": "
                    );
        }

        int result =
                matrixChainOrder(dimensions);

        System.out.println();
        System.out.println(
                "======= MATRIX CHAIN MULTIPLICATION ======="
        );

        System.out.println(
                "Minimum scalar multiplications: "
                + result
        );
    }
}