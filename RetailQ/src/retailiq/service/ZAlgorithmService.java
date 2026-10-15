package retailiq.service;

import java.util.ArrayList;
import java.util.List;

import retailiq.model.Product;
import retailiq.util.DataStore;
import retailiq.util.MainInput;

public class ZAlgorithmService {

    // Builds the Z-array for a string.
    // Z[i] = length of the longest substring starting at i
    // that matches the prefix of the string.
    private static int[] buildZArray(String text) {

        int n = text.length();
        int[] z = new int[n];

        int left = 0;
        int right = 0;

        for (int i = 1; i < n; i++) {

            if (i <= right) {
                z[i] = Math.min(right - i + 1, z[i - left]);
            }

            while (i + z[i] < n
                    && text.charAt(z[i]) == text.charAt(i + z[i])) {

                z[i]++;
            }

            if (i + z[i] - 1 > right) {
                left = i;
                right = i + z[i] - 1;
            }
        }

        return z;
    }

    // Z Algorithm pattern search
    public static boolean search(String text, String pattern) {

        if (pattern == null || pattern.isEmpty()) {
            return true;
        }

        if (text == null || text.isEmpty()) {
            return false;
        }

        text = text.toLowerCase();
        pattern = pattern.toLowerCase();

        /*
         * We create:
         *
         * pattern + "$" + text
         *
         * The special character separates pattern and text.
         */
        String combined = pattern + "$" + text;

        int[] z = buildZArray(combined);

        for (int i = pattern.length() + 1; i < combined.length(); i++) {

            if (z[i] == pattern.length()) {
                return true;
            }
        }

        return false;
    }

    // Search product names and descriptions
    public static List<Product> searchProducts(String keyword) {

        List<Product> results = new ArrayList<>();

        for (Product product : DataStore.products) {

            boolean foundInName =
                    search(product.getName(), keyword);

            boolean foundInDescription =
                    search(product.getDescription(), keyword);

            if (foundInName || foundInDescription) {
                results.add(product);
            }
        }

        return results;
    }

    // Display Z Algorithm search results
    public static void performProductSearch() {

        String keyword = MainInput.readString(
                "Enter product search keyword: "
        );

        List<Product> results = searchProducts(keyword);

        System.out.println();
        System.out.println("========== Z ALGORITHM RESULTS ==========");

        if (results.isEmpty()) {

            System.out.println("No products found.");

        } else {

            for (Product product : results) {
                System.out.println(product);
            }

            System.out.println("----------------------------------------");
            System.out.println(results.size() + " product(s) found.");
        }
    }
}