package retailiq.service;

import retailiq.model.Product;
import retailiq.util.DataStore;
import retailiq.util.MainInput;
import retailiq.util.ResultFormatter;

public class ManagerService {

    public static void addProduct() {
        System.out.println("\n----------------------------------------");
        System.out.println("              ADD PRODUCT");
        System.out.println("----------------------------------------");

        int id = MainInput.readInt("Enter Product ID: ");
        for (Product p : DataStore.products) {
            if (p.getId() == id) {
                System.out.println("Error: Product with ID " + id + " already exists.");
                MainInput.pressEnterToContinue("Manager Menu");
                return;
            }
        }

        String name = MainInput.readString("Enter Product Name: ");
        String category = MainInput.readString("Enter Category: ");
        double price = MainInput.readDouble("Enter Price (₹): ");
        int stock = MainInput.readInt("Enter Initial Stock Quantity: ");
        String description = MainInput.readString("Enter Product Description: ");

        long startTime = System.nanoTime();
        Product product = new Product(id, name, category, price, stock, description);
        DataStore.products.add(product);
        long elapsed = System.nanoTime() - startTime;

        ResultFormatter.printHeader(
            "Standard Data Operation",
            "Standard Operations",
            "addProduct()",
            name + " (ID: " + id + ")"
        );
        System.out.println("Product successfully added to inventory catalog:");
        System.out.println(product);
        System.out.println("Current Total Products in Catalog: " + DataStore.products.size());

        ResultFormatter.printFooter(elapsed, "O(1) amortized", "O(1)", "Manager Menu");
    }

    public static void updateProduct() {
        System.out.println("\n----------------------------------------");
        System.out.println("            UPDATE PRODUCT");
        System.out.println("----------------------------------------");

        int id = MainInput.readInt("Enter Product ID to update: ");
        Product target = null;
        for (Product p : DataStore.products) {
            if (p.getId() == id) {
                target = p;
                break;
            }
        }

        if (target == null) {
            System.out.println("Product with ID " + id + " not found.");
            MainInput.pressEnterToContinue("Manager Menu");
            return;
        }

        System.out.println("\nCurrent Product Details:");
        System.out.println(target);
        System.out.println("Description: " + target.getDescription());

        System.out.println("\n(Press Enter on any field to keep the existing value)");
        String newName = MainInput.readString("New Name [" + target.getName() + "]: ");
        if (!newName.isEmpty()) {
            target.setName(newName);
        }

        String newCategory = MainInput.readString("New Category [" + target.getCategory() + "]: ");
        if (!newCategory.isEmpty()) {
            target.setCategory(newCategory);
        }

        String priceInput = MainInput.readString("New Price [₹" + target.getPrice() + "]: ");
        if (!priceInput.isEmpty()) {
            try {
                double np = Double.parseDouble(priceInput);
                if (np > 0) target.setPrice(np);
            } catch (NumberFormatException ignored) {}
        }

        String stockInput = MainInput.readString("New Stock [" + target.getStock() + "]: ");
        if (!stockInput.isEmpty()) {
            try {
                int ns = Integer.parseInt(stockInput);
                if (ns >= 0) target.setStock(ns);
            } catch (NumberFormatException ignored) {}
        }

        String newDesc = MainInput.readString("New Description [" + target.getDescription() + "]: ");
        if (!newDesc.isEmpty()) {
            target.setDescription(newDesc);
        }

        long startTime = System.nanoTime();
        // Updated in place
        long elapsed = System.nanoTime() - startTime;

        ResultFormatter.printHeader(
            "Standard Data Operation",
            "Standard Operations",
            "updateProduct()",
            "Product ID: " + id
        );
        System.out.println("Product updated successfully:");
        System.out.println(target);
        System.out.println("Updated Description: " + target.getDescription());

        ResultFormatter.printFooter(elapsed, "O(n) linear scan lookup", "O(1)", "Manager Menu");
    }

    public static void deleteProduct() {
        System.out.println("\n----------------------------------------");
        System.out.println("            DELETE PRODUCT");
        System.out.println("----------------------------------------");

        int id = MainInput.readInt("Enter Product ID to delete: ");
        int removeIndex = -1;
        for (int i = 0; i < DataStore.products.size(); i++) {
            if (DataStore.products.get(i).getId() == id) {
                removeIndex = i;
                break;
            }
        }

        if (removeIndex == -1) {
            System.out.println("Product with ID " + id + " not found.");
            MainInput.pressEnterToContinue("Manager Menu");
            return;
        }

        Product removed = DataStore.products.get(removeIndex);
        String confirm = MainInput.readString("Are you sure you want to delete '" + removed.getName() + "'? (y/n): ");
        if (!confirm.equalsIgnoreCase("y")) {
            System.out.println("Product deletion cancelled.");
            MainInput.pressEnterToContinue("Manager Menu");
            return;
        }

        long startTime = System.nanoTime();
        DataStore.products.remove(removeIndex);
        long elapsed = System.nanoTime() - startTime;

        ResultFormatter.printHeader(
            "Standard Data Operation",
            "Standard Operations",
            "deleteProduct()",
            "Product ID: " + id
        );
        System.out.println("Successfully deleted product: " + removed.getName());
        System.out.println("Remaining Products in Catalog: " + DataStore.products.size());

        ResultFormatter.printFooter(elapsed, "O(n) list removal", "O(1)", "Manager Menu");
    }

    public static void displayProducts() {
        long startTime = System.nanoTime();
        DataStore.displayProducts();

        double totalInventoryValue = 0;
        int totalUnits = 0;
        for (Product p : DataStore.products) {
            totalInventoryValue += (p.getPrice() * p.getStock());
            totalUnits += p.getStock();
        }
        long elapsed = System.nanoTime() - startTime;

        ResultFormatter.printHeader(
            "Data Traversal",
            "Standard Operations",
            "displayProducts()",
            "Entire Inventory Catalog"
        );
        System.out.printf("Total Active SKUs       : %d%n", DataStore.products.size());
        System.out.printf("Total Units in Stock    : %d units%n", totalUnits);
        System.out.printf("Gross Inventory Value   : ₹%.2f%n", totalInventoryValue);

        ResultFormatter.printFooter(elapsed, "O(n) linear traversal", "O(1)", "Manager Menu");
    }
}
