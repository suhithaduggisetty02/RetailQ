package retailiq.service;

import java.util.ArrayList;
import java.util.List;

import retailiq.model.Order;
import retailiq.model.Product;
import retailiq.model.Review;
import retailiq.model.SaleRecord;
import retailiq.model.User;
import retailiq.util.DataStore;
import retailiq.util.MainInput;
import retailiq.util.ResultFormatter;

public class CustomerService {

    // Customer Option 1: Browse Products
    public static void browseProducts() {
        long start = System.nanoTime();
        DataStore.displayProducts();
        long elapsed = System.nanoTime() - start;

        ResultFormatter.printHeader(
            "Data Traversal",
            "Customer Operations",
            "browseProducts()",
            "All Active Catalog Products"
        );
        System.out.println("All product listings retrieved successfully.");
        ResultFormatter.printFooter(elapsed, "O(n) linear scan", "O(1)", "Customer Menu");
    }

    // Customer Option 9: Compare Products
    public static void compareProducts() {
        System.out.println("\n------------------------------------------------");
        System.out.println("               COMPARE PRODUCTS");
        System.out.println("------------------------------------------------");
        System.out.println("Select two products for side-by-side comparison:");
        for (Product p : DataStore.products) {
            System.out.printf("[%d] %-30s | ₹%.2f%n", p.getId(), p.getName(), p.getPrice());
        }

        int id1 = MainInput.readInt("Enter First Product ID : ");
        int id2 = MainInput.readInt("Enter Second Product ID: ");

        Product p1 = findProduct(id1);
        Product p2 = findProduct(id2);

        if (p1 == null || p2 == null || id1 == id2) {
            System.out.println("Invalid products selected.");
            return;
        }

        long start = System.nanoTime();

        // Compute review stats
        double avgRating1 = getAverageRating(id1);
        double avgRating2 = getAverageRating(id2);
        int revCount1 = getReviewCount(id1);
        int revCount2 = getReviewCount(id2);

        long elapsed = System.nanoTime() - start;

        ResultFormatter.printHeader(
            "Direct Attribute Comparison",
            "Customer Operations",
            "compareProducts()",
            p1.getName() + " vs " + p2.getName()
        );

        System.out.println("==================== SIDE-BY-SIDE COMPARISON ====================");
        System.out.printf("%-20s | %-30s | %-30s%n", "Attribute", p1.getName(), p2.getName());
        System.out.println("----------------------------------------------------------------------------------");
        System.out.printf("%-20s | %-30d | %-30d%n", "Product ID", p1.getId(), p2.getId());
        System.out.printf("%-20s | %-30s | %-30s%n", "Category", p1.getCategory(), p2.getCategory());
        System.out.printf("%-20s | ₹%-29.2f | ₹%-29.2f%n", "Price", p1.getPrice(), p2.getPrice());
        System.out.printf("%-20s | %-30s | %-30s%n", "Stock Availability", p1.getStock() + " units", p2.getStock() + " units");
        System.out.printf("%-20s | %-30s | %-30s%n", "Customer Rating", String.format("%.1f/5 (%d reviews)", avgRating1, revCount1), String.format("%.1f/5 (%d reviews)", avgRating2, revCount2));
        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("Product 1 Description: " + p1.getDescription());
        System.out.println("Product 2 Description: " + p2.getDescription());

        ResultFormatter.printFooter(elapsed, "O(1) direct attribute lookup", "O(1)", "Customer Menu");
    }

    // Customer Option 10: View Product Details
    public static void viewProductDetails() {
        System.out.println("\n------------------------------------------------");
        System.out.println("              VIEW PRODUCT DETAILS");
        System.out.println("------------------------------------------------");
        int id = MainInput.readInt("Enter Product ID: ");

        long start = System.nanoTime();
        Product product = findProduct(id);
        if (product == null) {
            System.out.println("Product with ID " + id + " not found.");
            return;
        }

        List<Review> pReviews = new ArrayList<>();
        double sumRating = 0;
        for (Review r : DataStore.reviews) {
            if (r.getProductId() == id) {
                pReviews.add(r);
                sumRating += r.getRating();
            }
        }
        double avg = pReviews.isEmpty() ? 0 : sumRating / pReviews.size();
        long elapsed = System.nanoTime() - start;

        ResultFormatter.printHeader(
            "Direct Data Lookup",
            "Customer Operations",
            "viewProductDetails()",
            "Product ID: " + id
        );

        System.out.println("Product Specifications:");
        System.out.println("• ID          : " + product.getId());
        System.out.println("• Name        : " + product.getName());
        System.out.println("• Category    : " + product.getCategory());
        System.out.printf("• Price       : ₹%.2f%n", product.getPrice());
        System.out.println("• Stock Level : " + product.getStock() + " units available");
        System.out.println("• Description : " + product.getDescription());
        System.out.printf("• Avg Rating  : %.1f / 5.0 (%d customer reviews)%n", avg, pReviews.size());

        System.out.println("\nCustomer Reviews for this item:");
        if (pReviews.isEmpty()) {
            System.out.println("  No customer reviews submitted yet for this product.");
        } else {
            for (Review r : pReviews) {
                System.out.printf("  ★ %s (%d/5 stars): \"%s\"%n", r.getCustomerName(), r.getRating(), r.getReviewText());
            }
        }

        ResultFormatter.printFooter(elapsed, "O(n) reviews search & aggregation", "O(1)", "Customer Menu");
    }

    // Customer Option 11: Place Order
    public static void placeOrder(User customer) {
        System.out.println("\n------------------------------------------------");
        System.out.println("                 PLACE AN ORDER");
        System.out.println("------------------------------------------------");
        System.out.printf("Customer: %s | Wallet Balance: ₹%.2f%n", customer.getFullName(), customer.getWalletBalance());
        System.out.println("Available Products:");
        for (Product p : DataStore.products) {
            System.out.printf("[%d] %-30s | ₹%7.2f | In Stock: %d%n", p.getId(), p.getName(), p.getPrice(), p.getStock());
        }

        int pId = MainInput.readInt("Enter Product ID to order: ");
        Product product = findProduct(pId);
        if (product == null) {
            System.out.println("Product not found.");
            return;
        }

        int qty = MainInput.readInt("Enter Quantity: ");
        if (qty <= 0) {
            System.out.println("Quantity must be greater than 0.");
            return;
        }

        if (product.getStock() < qty) {
            System.out.printf("Insufficient stock! Available: %d units, Requested: %d units.%n", product.getStock(), qty);
            return;
        }

        double totalCost = product.getPrice() * qty;
        if (customer.getWalletBalance() < totalCost) {
            System.out.printf("Insufficient funds in wallet! Total: ₹%.2f, Wallet Balance: ₹%.2f.%n",
                totalCost, customer.getWalletBalance());
            String addMoney = MainInput.readString("Would you like to deposit funds to wallet? (y/n): ");
            if (addMoney.equalsIgnoreCase("y")) {
                double dep = MainInput.readDouble("Enter deposit amount (₹): ");
                if (dep > 0) {
                    customer.addBalance(dep);
                    System.out.printf("New Wallet Balance: ₹%.2f%n", customer.getWalletBalance());
                }
            }
            if (customer.getWalletBalance() < totalCost) {
                System.out.println("Transaction cancelled due to insufficient balance.");
                return;
            }
        }

        long start = System.nanoTime();

        // 1. Deduct Stock
        product.setStock(product.getStock() - qty);

        // 2. Deduct Customer Wallet Balance
        customer.deductBalance(totalCost);

        // 3. Create Order Record
        int newOrderId = 5000 + DataStore.orders.size() + 1;
        Order order = new Order(
            newOrderId,
            customer.getUsername(),
            product.getId(),
            product.getName(),
            qty,
            product.getPrice(),
            totalCost,
            "Processing"
        );
        DataStore.orders.add(order);

        // 4. Record in Sales log
        int newSaleId = 1000 + DataStore.sales.size() + 1;
        DataStore.sales.add(new SaleRecord(
            newSaleId,
            product.getId(),
            product.getName(),
            qty,
            totalCost,
            System.currentTimeMillis() / 1000,
            "Online Web"
        ));

        long elapsed = System.nanoTime() - start;

        ResultFormatter.printHeader(
            "Standard Order Processing",
            "Customer Operations",
            "placeOrder()",
            product.getName() + " x " + qty
        );

        System.out.println("Order Confirmed & Payment Successful!");
        System.out.println("-------------------------------------");
        System.out.println("• Order Number        : #" + newOrderId);
        System.out.println("• Purchased SKU       : " + product.getName());
        System.out.println("• Quantity            : " + qty);
        System.out.printf("• Total Amount Billed : ₹%.2f%n", totalCost);
        System.out.printf("• Remaining Balance   : ₹%.2f%n", customer.getWalletBalance());
        System.out.println("• Status              : Processing for Dispatch");

        ResultFormatter.printFooter(elapsed, "O(1) order & inventory atomic update", "O(1)", "Customer Menu");
    }

    // Customer Option 12: Submit Review
    public static void submitReview(User customer) {
        System.out.println("\n------------------------------------------------");
        System.out.println("                SUBMIT A REVIEW");
        System.out.println("------------------------------------------------");
        System.out.println("Select a product you wish to review:");
        for (Product p : DataStore.products) {
            System.out.printf("[%d] %s%n", p.getId(), p.getName());
        }

        int id = MainInput.readInt("Enter Product ID: ");
        Product p = findProduct(id);
        if (p == null) {
            System.out.println("Product not found.");
            return;
        }

        int rating = MainInput.readInt("Enter Star Rating (1 to 5): ");
        if (rating < 1) rating = 1;
        if (rating > 5) rating = 5;

        String reviewText = MainInput.readString("Enter your review feedback: ");
        if (reviewText.isEmpty()) {
            System.out.println("Review cannot be empty.");
            return;
        }

        long start = System.nanoTime();
        Review review = new Review(id, customer.getFullName(), reviewText, rating);
        DataStore.reviews.add(review);
        long elapsed = System.nanoTime() - start;

        ResultFormatter.printHeader(
            "Standard Data Operation",
            "Customer Operations",
            "submitReview()",
            p.getName()
        );

        System.out.println("Thank you! Review published successfully:");
        System.out.println(review);

        ResultFormatter.printFooter(elapsed, "O(1) append to review store", "O(1)", "Customer Menu");
    }

    // Customer Option 13: View Order History
    public static void viewOrderHistory(User customer) {
        long start = System.nanoTime();
        List<Order> customerOrders = new ArrayList<>();
        double totalSpent = 0;

        for (Order o : DataStore.orders) {
            if (o.getCustomerUsername().equalsIgnoreCase(customer.getUsername())) {
                customerOrders.add(o);
                totalSpent += o.getTotalAmount();
            }
        }
        long elapsed = System.nanoTime() - start;

        ResultFormatter.printHeader(
            "Data Filtering and Traversal",
            "Customer Operations",
            "viewOrderHistory()",
            "Customer: " + customer.getFullName() + " (" + customer.getUsername() + ")"
        );

        System.out.println("Order History:");
        System.out.println("--------------");
        if (customerOrders.isEmpty()) {
            System.out.println("No past orders found for your account.");
        } else {
            for (Order o : customerOrders) {
                System.out.println(o);
            }
            System.out.println("----------------------------------------------------------------------------------");
            System.out.printf("Total Lifetime Orders Placed : %d orders%n", customerOrders.size());
            System.out.printf("Total Amount Spent           : ₹%.2f%n", totalSpent);
        }

        ResultFormatter.printFooter(elapsed, "O(n) orders traversal & customer filtering", "O(1)", "Customer Menu");
    }

    private static Product findProduct(int id) {
        for (Product p : DataStore.products) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    private static double getAverageRating(int pId) {
        double sum = 0;
        int count = 0;
        for (Review r : DataStore.reviews) {
            if (r.getProductId() == pId) {
                sum += r.getRating();
                count++;
            }
        }
        return count == 0 ? 0 : sum / count;
    }

    private static int getReviewCount(int pId) {
        int count = 0;
        for (Review r : DataStore.reviews) {
            if (r.getProductId() == pId) count++;
        }
        return count;
    }
}
