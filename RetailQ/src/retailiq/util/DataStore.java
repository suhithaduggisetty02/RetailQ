package retailiq.util;

import java.util.ArrayList;
import java.util.List;

import retailiq.model.Order;
import retailiq.model.Product;
import retailiq.model.Promotion;
import retailiq.model.Review;
import retailiq.model.SaleRecord;
import retailiq.model.User;

public class DataStore {

    public static final List<Product> products = new ArrayList<>();
    public static final List<Review> reviews = new ArrayList<>();
    public static final List<User> users = new ArrayList<>();
    public static final List<Order> orders = new ArrayList<>();
    public static final List<SaleRecord> sales = new ArrayList<>();
    public static final List<Promotion> promotions = new ArrayList<>();

    // Conflict graph for promotions: adjacency matrix
    public static boolean[][] promoConflictMatrix;

    // Logistics Network for Max-Flow / Min-Cut (CO4)
    // 6 nodes: 0:Central Depot (Source), 1:Hub North, 2:Hub South, 3:Store East, 4:Store West, 5:Fulfillment Center (Sink)
    public static final String[] LOGISTICS_NODES = {
        "Central Depot (Source)",
        "Hub North",
        "Hub South",
        "Store East",
        "Store West",
        "Fulfillment Center (Sink)"
    };
    public static int[][] logisticsCapacity;

    // Delivery Locations for TSP (CO3 / CO5)
    public static final String[] DELIVERY_LOCATIONS = {
        "Main Depot (0)",
        "Sector 12 Hub (1)",
        "Downtown Outlet (2)",
        "Airport Mall (3)",
        "Tech Park Center (4)",
        "Suburban Depot (5)"
    };
    public static int[][] deliveryDistanceMatrix;

    static {
        loadUsers();
        loadProducts();
        loadReviews();
        loadOrders();
        loadSales();
        loadPromotions();
        loadLogisticsNetwork();
        loadDeliveryMatrix();
    }

    private static void loadUsers() {
        users.add(new User("manager", "admin123", "Chief Operations Manager", "MANAGER", 0.0));
        users.add(new User("admin", "admin123", "System Administrator", "MANAGER", 0.0));
        users.add(new User("rahul", "pass123", "Rahul Sharma", "CUSTOMER", 15000.00));
        users.add(new User("priya", "pass123", "Priya Verma", "CUSTOMER", 22000.00));
        users.add(new User("arjun", "pass123", "Arjun Nair", "CUSTOMER", 8500.00));
    }

    private static void loadProducts() {
        products.add(new Product(
            101,
            "Wireless Bluetooth Headphones",
            "Electronics",
            1999.00,
            25,
            "Wireless Bluetooth headphones with noise cancellation and high fidelity sound quality"
        ));

        products.add(new Product(
            102,
            "Gaming Mechanical Keyboard",
            "Electronics",
            2499.00,
            15,
            "Mechanical keyboard with RGB backlight for gaming and tactile mechanical switches"
        ));

        products.add(new Product(
            103,
            "Wireless Gaming Mouse",
            "Electronics",
            1499.00,
            30,
            "Wireless gaming mouse with adjustable DPI and ergonomic grip design"
        ));

        products.add(new Product(
            104,
            "Smart Fitness Watch",
            "Wearables",
            2999.00,
            20,
            "Smart watch with fitness tracking, heart rate monitoring, and long battery life"
        ));

        products.add(new Product(
            105,
            "USB-C Fast Charger",
            "Accessories",
            999.00,
            40,
            "Fast USB-C charger compatible with smartphones, tablets, and lightweight laptops"
        ));

        products.add(new Product(
            106,
            "Laptop Backpack",
            "Bags",
            1299.00,
            18,
            "Water resistant laptop backpack with multiple compartments and anti-theft zipper"
        ));

        products.add(new Product(
            107,
            "Bluetooth Portable Speaker",
            "Audio",
            1799.00,
            22,
            "Portable Bluetooth speaker with deep bass, waterproof casing, and long battery life"
        ));

        products.add(new Product(
            108,
            "Noise Cancelling Earbuds",
            "Audio",
            2199.00,
            16,
            "True wireless earbuds with active noise cancellation and ergonomic comfortable fit"
        ));

        products.add(new Product(
            109,
            "Ergonomic Mouse Pad",
            "Accessories",
            499.00,
            50,
            "Ergonomic mouse pad with gel wrist support and smooth tracking surface"
        ));

        products.add(new Product(
            110,
            "Ultra HD 4K Webcam",
            "Electronics",
            3499.00,
            12,
            "High resolution 4K webcam with dual stereo microphones and autofocus lens"
        ));
    }

    private static void loadReviews() {
        reviews.add(new Review(
            101,
            "Rahul",
            "The headphones have excellent sound quality, great battery life, and comfortable ear cushions.",
            5
        ));

        reviews.add(new Review(
            101,
            "Priya",
            "Very comfortable wireless headphones with good noise cancellation and clear call clarity.",
            4
        ));

        reviews.add(new Review(
            101,
            "Sneha",
            "Affordable price and great battery life. Excellent sound quality for daily online meetings.",
            5
        ));

        reviews.add(new Review(
            102,
            "Arjun",
            "The keyboard has excellent RGB lighting, responsive keys, and sturdy mechanical switches.",
            5
        ));

        reviews.add(new Review(
            102,
            "Vikas",
            "Solid tactile feel but a bit noisy in office spaces. Great gaming mechanical keyboard overall.",
            4
        ));

        reviews.add(new Review(
            103,
            "Sneha",
            "The wireless mouse is comfortable and works smoothly with responsive cursor tracking.",
            4
        ));

        reviews.add(new Review(
            103,
            "Rohan",
            "Lightweight wireless gaming mouse with great battery life and comfortable ergonomic design.",
            5
        ));

        reviews.add(new Review(
            104,
            "Kiran",
            "The fitness watch provides useful health tracking, step counting, and great battery life.",
            5
        ));

        reviews.add(new Review(
            104,
            "Meera",
            "Stylish smart watch with heart rate monitoring. Battery lasts 5 days without issue.",
            4
        ));

        reviews.add(new Review(
            105,
            "Anjali",
            "The charger is fast, does not overheat, and works well with all my USB-C gadgets.",
            4
        ));

        reviews.add(new Review(
            106,
            "Amit",
            "Durable water resistant fabric with spacious laptop compartment. Excellent build quality.",
            5
        ));

        reviews.add(new Review(
            107,
            "Divya",
            "Punchy bass and crisp sound. Perfect outdoor portable speaker with great battery life.",
            5
        ));

        reviews.add(new Review(
            108,
            "Sanjay",
            "Superb noise cancelling earbuds with comfortable fit and excellent sound quality.",
            5
        ));
    }

    private static void loadOrders() {
        orders.add(new Order(
            5001, "rahul", 101, "Wireless Bluetooth Headphones", 1, 1999.00, 1999.00, "2026-03-10 14:20", "Delivered"
        ));
        orders.add(new Order(
            5002, "rahul", 105, "USB-C Fast Charger", 2, 999.00, 1998.00, "2026-03-12 11:05", "Delivered"
        ));
        orders.add(new Order(
            5003, "priya", 104, "Smart Fitness Watch", 1, 2999.00, 2999.00, "2026-03-14 16:45", "Shipped"
        ));
        orders.add(new Order(
            5004, "arjun", 102, "Gaming Mechanical Keyboard", 1, 2499.00, 2499.00, "2026-03-15 09:30", "Processing"
        ));
    }

    private static void loadSales() {
        int[] pIds = {101, 102, 103, 104, 105, 106, 107, 108, 109, 110};
        String[] pNames = {
            "Wireless Bluetooth Headphones", "Gaming Mechanical Keyboard", "Wireless Gaming Mouse",
            "Smart Fitness Watch", "USB-C Fast Charger", "Laptop Backpack", "Bluetooth Portable Speaker",
            "Noise Cancelling Earbuds", "Ergonomic Mouse Pad", "Ultra HD 4K Webcam"
        };
        double[] prices = {1999.00, 2499.00, 1499.00, 2999.00, 999.00, 1299.00, 1799.00, 2199.00, 499.00, 3499.00};
        String[] regions = {"North", "South", "East", "West", "Central"};

        // Generate 40 realistic historical sales records
        int id = 1001;
        for (int i = 0; i < 40; i++) {
            int pIndex = (i * 7 + 3) % pIds.length;
            int qty = 1 + (i % 4);
            double amount = prices[pIndex] * qty;
            long timestamp = 1710000000L + (i * 3600 * 6);
            String reg = regions[i % regions.length];
            sales.add(new SaleRecord(id++, pIds[pIndex], pNames[pIndex], qty, amount, timestamp, reg));
        }
    }

    private static void loadPromotions() {
        promotions.add(new Promotion(0, "SUMMER10", "Summer Tech Mega Sale", 15000, 48000, "Electronics"));
        promotions.add(new Promotion(1, "AUDIO25", "Audio Enthusiast Flash Deal", 8000, 26000, "Audio"));
        promotions.add(new Promotion(2, "FITNESS20", "Wearables Fitness Fest", 12000, 34000, "Wearables"));
        promotions.add(new Promotion(3, "GAMER30", "Ultimate Esports Gaming Bundle", 18000, 52000, "Electronics"));
        promotions.add(new Promotion(4, "BACKPACK15", "Student Back-to-School Deal", 5000, 14000, "Bags"));
        promotions.add(new Promotion(5, "OFFICECOMBO", "Home Office Gear Promotion", 10000, 31000, "Accessories"));

        // Promotion conflict matrix (cannot run conflicting discounts concurrently)
        int n = promotions.size();
        promoConflictMatrix = new boolean[n][n];
        // Conflicts between overlapping category campaigns
        addConflict(0, 1); // Summer Tech vs Audio Flash
        addConflict(0, 3); // Summer Tech vs Esports Gaming
        addConflict(1, 3); // Audio Flash vs Esports Gaming
        addConflict(3, 5); // Esports vs Office Combo
    }

    private static void addConflict(int u, int v) {
        promoConflictMatrix[u][v] = true;
        promoConflictMatrix[v][u] = true;
    }

    private static void loadLogisticsNetwork() {
        int n = LOGISTICS_NODES.length; // 6 nodes
        logisticsCapacity = new int[n][n];

        // 0: Central Depot -> 1: Hub North (300 units), 2: Hub South (250 units)
        logisticsCapacity[0][1] = 300;
        logisticsCapacity[0][2] = 250;

        // 1: Hub North -> 3: Store East (150 units), 4: Store West (180 units)
        logisticsCapacity[1][3] = 150;
        logisticsCapacity[1][4] = 180;

        // 2: Hub South -> 3: Store East (100 units), 4: Store West (160 units)
        logisticsCapacity[2][3] = 100;
        logisticsCapacity[2][4] = 160;

        // 3: Store East -> 5: Fulfillment Center (220 units)
        // 4: Store West -> 5: Fulfillment Center (310 units)
        logisticsCapacity[3][5] = 220;
        logisticsCapacity[4][5] = 310;
    }

    private static void loadDeliveryMatrix() {
        // Distance matrix between 6 retail delivery hubs in kilometers
        deliveryDistanceMatrix = new int[][] {
            { 0, 12, 19, 28, 15, 22 },
            { 12,  0, 14, 25, 18, 30 },
            { 19, 14,  0, 11, 16, 20 },
            { 28, 25, 11,  0, 24, 15 },
            { 15, 18, 16, 24,  0, 13 },
            { 22, 30, 20, 15, 13,  0 }
        };
    }

    public static void displayProducts() {
        System.out.println("\n=========================== CATALOG PRODUCTS ===========================");
        for (Product product : products) {
            System.out.println(product);
        }
        System.out.println("========================================================================");
    }

    public static void displayReviews() {
        System.out.println("\n=========================== CUSTOMER REVIEWS ===========================");
        for (Review review : reviews) {
            System.out.println(review);
        }
        System.out.println("========================================================================");
    }
}