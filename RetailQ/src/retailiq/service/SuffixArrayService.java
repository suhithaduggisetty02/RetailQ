package retailiq.service;

import java.util.Arrays;

import retailiq.model.Product;
import retailiq.util.DataStore;
import retailiq.util.MainInput;

public class SuffixArrayService {

    public static int[] buildSuffixArray(String text) {

        int n = text.length();
        Integer[] suffixes = new Integer[n];

        for (int i = 0; i < n; i++) {
            suffixes[i] = i;
        }

        Arrays.sort(suffixes, (a, b) -> {
            int i = a;
            int j = b;

            while (i < n && j < n) {
                if (text.charAt(i) != text.charAt(j)) {
                    return Character.compare(
                            text.charAt(i),
                            text.charAt(j)
                    );
                }

                i++;
                j++;
            }

            return Integer.compare(n - a, n - b);
        });

        int[] result = new int[n];

        for (int i = 0; i < n; i++) {
            result[i] = suffixes[i];
        }

        return result;
    }

    public static void performSuffixArray() {

        Product product = chooseProduct();

        if (product == null) {
            return;
        }

        String text = product.getDescription().toLowerCase();

        int[] suffixArray = buildSuffixArray(text);

        System.out.println();
        System.out.println("========== SUFFIX ARRAY ==========");
        System.out.println("Product: " + product.getName());
        System.out.println("Text: " + text);
        System.out.println();

        for (int i = 0; i < suffixArray.length; i++) {

            int index = suffixArray[i];

            System.out.printf(
                    "%2d -> %s%n",
                    index,
                    text.substring(index)
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
            if (product.getId() == id) {
                return product;
            }
        }

        System.out.println("Product not found.");
        return null;
    }
}