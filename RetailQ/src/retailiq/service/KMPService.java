package retailiq.service;

import java.util.ArrayList;
import java.util.List;

import retailiq.model.Product;
import retailiq.util.DataStore;
import retailiq.util.MainInput;
public class KMPService {

    // Build the LPS (Longest Prefix Suffix) array
    private static int[] buildLPS(String pattern) {

        int[] lps = new int[pattern.length()];

        int length = 0;
        int i = 1;

        while (i < pattern.length()) {

            if (pattern.charAt(i) == pattern.charAt(length)) {
                length++;
                lps[i] = length;
                i++;
            } else {

                if (length != 0) {
                    length = lps[length - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }

        return lps;
    }

    // KMP pattern matching
    public static boolean search(String text, String pattern) {

        if (pattern == null || pattern.isEmpty()) {
            return true;
        }

        if (text == null || text.isEmpty()) {
            return false;
        }

        text = text.toLowerCase();
        pattern = pattern.toLowerCase();

        int[] lps = buildLPS(pattern);

        int i = 0;
        int j = 0;

        while (i < text.length()) {

            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;

                if (j == pattern.length()) {
                    return true;
                }

            } else {

                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
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

    // Display search results
    public static void performProductSearch() {

        String keyword = MainInput.readString(
                "Enter product search keyword: "
        );

        List<Product> results = searchProducts(keyword);

        System.out.println("\n========== KMP SEARCH RESULTS ==========");

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