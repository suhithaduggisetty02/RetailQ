package retailiq.service;

import java.util.ArrayList;
import java.util.List;

import retailiq.model.Product;
import retailiq.util.DataStore;
import retailiq.util.MainInput;
import retailiq.util.ResultFormatter;

public class BitmaskDPService {

    public static void recommendProductsWithinBudget(String returnMenu) {
        System.out.println("\n------------------------------------------------");
        System.out.println("   SHOP WITHIN BUDGET (BITMASK DP OPTIMIZER)");
        System.out.println("------------------------------------------------");
        double budget = MainInput.readDouble("Enter your shopping budget (₹): ");
        if (budget <= 0) {
            System.out.println("Budget must be greater than 0.");
            return;
        }

        List<Product> products = DataStore.products;
        int n = Math.min(products.size(), 18); // Safe bitmask constraint (2^18 = 262,144)

        long startTime = System.nanoTime();

        int totalMasks = 1 << n;
        double bestValue = -1;
        int bestMask = 0;
        double bestCost = 0;

        for (int mask = 1; mask < totalMasks; mask++) {
            double cost = 0;
            double value = 0;

            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    Product p = products.get(i);
                    cost += p.getPrice();
                    // Value model: utility = stock availability score + price/100
                    value += (p.getStock() * 2.0) + (p.getPrice() / 100.0);
                }
            }

            if (cost <= budget && value > bestValue) {
                bestValue = value;
                bestMask = mask;
                bestCost = cost;
            }
        }

        long elapsed = System.nanoTime() - startTime;

        ResultFormatter.printHeader(
            "Bitmask Dynamic Programming Subset Optimization",
            "CO3 - Dynamic Programming",
            "recommendProductsWithinBudget()",
            String.format("Budget: ₹%.2f across %d candidate products", budget, n)
        );

        if (bestMask == 0) {
            System.out.println("No product combination found that fits within budget ₹" + budget);
        } else {
            System.out.println("Optimal Product Basket Recommended for Your Budget:");
            System.out.println("--------------------------------------------------");
            int itemCount = 0;
            for (int i = 0; i < n; i++) {
                if ((bestMask & (1 << i)) != 0) {
                    Product p = products.get(i);
                    itemCount++;
                    System.out.printf("%d. [%d] %-30s | Price: ₹%7.2f | In Stock: %d%n",
                        itemCount, p.getId(), p.getName(), p.getPrice(), p.getStock());
                }
            }
            System.out.println("--------------------------------------------------");
            System.out.printf("Total Basket Cost      : ₹%.2f%n", bestCost);
            System.out.printf("Remaining Wallet Funds : ₹%.2f%n", (budget - bestCost));
            System.out.printf("Satisfaction Metric    : %.2f utility points%n", bestValue);
        }

        ResultFormatter.printFooter(
            elapsed,
            "O(2^N * N) Bitmask State Exploration",
            "O(1) auxiliary space",
            returnMenu
        );
    }

    public static void perform() {
        recommendProductsWithinBudget("Menu");
    }
}