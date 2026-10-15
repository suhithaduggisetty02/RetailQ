package retailiq.service;

import retailiq.model.Product;
import retailiq.util.DataStore;
import retailiq.util.MainInput;

public class LCPService {

    // Kasai algorithm
    public static int[] buildLCP(
            String text,
            int[] suffixArray) {

        int n = text.length();

        int[] rank = new int[n];
        int[] lcp = new int[n];

        for (int i = 0; i < n; i++) {
            rank[suffixArray[i]] = i;
        }

        int k = 0;

        for (int i = 0; i < n; i++) {

            if (rank[i] == n - 1) {
                k = 0;
                continue;
            }

            int j = suffixArray[rank[i] + 1];

            while (i + k < n
                    && j + k < n
                    && text.charAt(i + k)
                    == text.charAt(j + k)) {

                k++;
            }

            lcp[rank[i]] = k;

            if (k > 0)
                k--;
        }

        return lcp;
    }

    public static void performLCP() {

        Product product = chooseProduct();

        if (product == null)
            return;

        String text = product.getDescription().toLowerCase();

        int[] suffixArray =
                SuffixArrayService.buildSuffixArray(text);

        int[] lcp =
                buildLCP(text, suffixArray);

        System.out.println();
        System.out.println("========== LCP / KASAI ==========");
        System.out.println("Product: " + product.getName());

        System.out.println();

        for (int i = 0; i < suffixArray.length; i++) {

            System.out.printf(
                    "SA[%d] = %d | LCP = %d | %s%n",
                    i,
                    suffixArray[i],
                    lcp[i],
                    text.substring(suffixArray[i])
            );
        }
    }

    private static Product chooseProduct() {

        System.out.println("\nAvailable Products:");

        for (Product product : DataStore.products) {
            System.out.println(
                    product.getId() + " - " + product.getName()
            );
        }

        int id = MainInput.readInt("Enter product ID: ");

        for (Product product : DataStore.products) {

            if (product.getId() == id)
                return product;
        }

        System.out.println("Product not found.");

        return null;
    }
}