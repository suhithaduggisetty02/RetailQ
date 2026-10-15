package retailiq.service;

import java.util.ArrayList;
import java.util.List;

import retailiq.model.Product;
import retailiq.util.DataStore;
import retailiq.util.MainInput;
import retailiq.util.ResultFormatter;

public class ProductSearchService {

    public static void managerSearchMenu() {
        while (true) {
            System.out.println("\n----------------------------------------");
            System.out.println("             PRODUCT SEARCH");
            System.out.println("----------------------------------------");
            System.out.println("Select algorithm:");
            System.out.println("1. KMP");
            System.out.println("2. Rabin-Karp");
            System.out.println("3. Back");
            System.out.println("----------------------------------------");

            int choice = MainInput.readInt("Enter choice: ");
            if (choice == 3) {
                return;
            }

            if (choice == 1) {
                searchWithKMP("Manager Menu");
                return;
            } else if (choice == 2) {
                searchWithRabinKarp("Manager Menu");
                return;
            } else {
                System.out.println("Invalid choice. Please select 1, 2, or 3.");
            }
        }
    }

    public static void searchWithKMP(String returnMenu) {
        String keyword = MainInput.readString("Enter search keyword: ");
        if (keyword.isEmpty()) {
            System.out.println("Keyword cannot be empty.");
            return;
        }

        long startTime = System.nanoTime();
        List<Product> matches = new ArrayList<>();
        for (Product product : DataStore.products) {
            boolean nameMatch = KMPService.search(product.getName(), keyword);
            boolean descMatch = KMPService.search(product.getDescription(), keyword);
            if (nameMatch || descMatch) {
                matches.add(product);
            }
        }
        long elapsed = System.nanoTime() - startTime;

        ResultFormatter.printHeader(
            "KMP (Knuth-Morris-Pratt)",
            "CO1 - String Algorithms",
            "searchProducts()",
            keyword
        );

        System.out.println("Matching Products:");
        if (matches.isEmpty()) {
            System.out.println("No products matched the search query.");
        } else {
            for (int i = 0; i < matches.size(); i++) {
                Product p = matches.get(i);
                System.out.printf("%d. %s (₹%.2f, Stock: %d)%n", (i + 1), p.getName(), p.getPrice(), p.getStock());
            }
        }
        System.out.println("\nTotal matches: " + matches.size());

        ResultFormatter.printFooter(elapsed, "O(n + m) linear search", "O(m) for LPS auxiliary array", returnMenu);
    }

    public static void searchWithRabinKarp(String returnMenu) {
        String keyword = MainInput.readString("Enter search keyword: ");
        if (keyword.isEmpty()) {
            System.out.println("Keyword cannot be empty.");
            return;
        }

        long startTime = System.nanoTime();
        List<Product> matches = new ArrayList<>();
        for (Product product : DataStore.products) {
            boolean nameMatch = RabinKarpService.search(product.getName(), keyword);
            boolean descMatch = RabinKarpService.search(product.getDescription(), keyword);
            if (nameMatch || descMatch) {
                matches.add(product);
            }
        }
        long elapsed = System.nanoTime() - startTime;

        ResultFormatter.printHeader(
            "Rabin-Karp Rolling Hash",
            "CO1 - String Algorithms",
            "searchProducts()",
            keyword
        );

        System.out.println("Matching Products:");
        if (matches.isEmpty()) {
            System.out.println("No products matched the search query.");
        } else {
            for (int i = 0; i < matches.size(); i++) {
                Product p = matches.get(i);
                System.out.printf("%d. %s (₹%.2f, Stock: %d)%n", (i + 1), p.getName(), p.getPrice(), p.getStock());
            }
        }
        System.out.println("\nTotal matches: " + matches.size());

        ResultFormatter.printFooter(elapsed, "Average O(n + m), Worst O(n * m)", "O(1) auxiliary space", returnMenu);
    }
}
