package retailiq.main;

import retailiq.model.User;
import retailiq.service.AhoCorasickService;
import retailiq.service.AlgorithmComparisonService;
import retailiq.service.AlgorithmLabService;
import retailiq.service.AuthService;
import retailiq.service.BipartiteMatchingService;
import retailiq.service.BitmaskDPService;
import retailiq.service.ConflictAnalysisService;
import retailiq.service.CustomerService;
import retailiq.service.DeliveryTSPService;
import retailiq.service.KnapsackPromotionService;
import retailiq.service.ManagerService;
import retailiq.service.MinCutService;
import retailiq.service.NetworkFlowService;
import retailiq.service.ParallelSalesAnalyticsService;
import retailiq.service.ProductSearchService;
import retailiq.service.ProductSimilarityService;
import retailiq.service.RepeatedPhrasesService;
import retailiq.service.ReservoirSamplingService;
import retailiq.service.SalesSortingService;
import retailiq.service.SearchSuggestionsService;
import retailiq.util.MainInput;

public class Main {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("       WELCOME TO RETAILIQ ANALYTICS ENGINE");
        System.out.println("   Enterprise Operations & Advanced DSA Systems");
        System.out.println("==================================================");

        while (true) {
            showMainMenu();
        }
    }

    // =====================================================
    // MAIN MENU
    // =====================================================
    private static void showMainMenu() {
        System.out.println();
        System.out.println("MAIN MENU");
        System.out.println("---------");
        System.out.println("1. Manager Login");
        System.out.println("2. Customer Login");
        System.out.println("3. Customer Registration");
        System.out.println("4. Exit");
        System.out.println("---------");

        int choice = MainInput.readInt("Enter choice: ");

        switch (choice) {
            case 1:
                User manager = AuthService.managerLogin();
                if (manager != null) {
                    showManagerMenu(manager);
                }
                break;

            case 2:
                User customer = AuthService.customerLogin();
                if (customer != null) {
                    showCustomerMenu(customer);
                }
                break;

            case 3:
                User newCust = AuthService.customerRegistration();
                if (newCust != null) {
                    System.out.println("Logging you in automatically...");
                    showCustomerMenu(newCust);
                }
                break;

            case 4:
                System.out.println();
                System.out.println("Thank you for using RetailIQ Analytics Platform!");
                System.out.println("System shutdown complete.");
                System.exit(0);
                break;

            default:
                System.out.println("Invalid selection. Please choose an option from 1 to 4.");
        }
    }

    // =====================================================
    // MANAGER MENU
    // =====================================================
    private static void showManagerMenu(User manager) {
        while (true) {
            System.out.println();
            System.out.println("==================================================");
            System.out.println("                  MANAGER MENU");
            System.out.println("    Active User: " + manager.getFullName() + " (" + manager.getRole() + ")");
            System.out.println("==================================================");
            System.out.println("1.  Add Product [Standard Operation]");
            System.out.println("2.  Update Product [Standard Operation]");
            System.out.println("3.  Delete Product [Standard Operation]");
            System.out.println("4.  View Products [Data Traversal]");
            System.out.println("5.  Search Products [CO1: KMP / Rabin-Karp]");
            System.out.println("6.  Analyze Review Keywords [CO1: Aho-Corasick]");
            System.out.println("7.  Analyze Product Description Similarity [CO2: Suffix Array + LCP/Kasai]");
            System.out.println("8.  Detect Repeated Review Phrases [CO2: Suffix Tree]");
            System.out.println("9.  View Search Suggestions [CO3: Levenshtein / Damerau-Levenshtein]");
            System.out.println("10. Optimize Inventory Distribution [CO4: Dinic / Edmonds-Karp]");
            System.out.println("11. Identify Supply Bottlenecks [CO4: Minimum Cut]");
            System.out.println("12. Assign Orders to Resources [CO4: Bipartite Matching]");
            System.out.println("13. Optimize Promotions [CO5: Knapsack / Subset Sum]");
            System.out.println("14. Analyze Promotion Conflicts [CO5: Vertex Cover / Independent Set]");
            System.out.println("15. Plan Delivery Routes [CO3 / CO5: Bitmask DP & TSP Approx]");
            System.out.println("16. Analyze Sales [CO6: Parallel Reduce]");
            System.out.println("17. Sort Sales Records [CO6: Randomized QuickSort / Parallel Sort]");
            System.out.println("18. Sample Customer Reviews [CO6: Reservoir Sampling]");
            System.out.println("19. Compare Algorithm Performance [All CO Modules]");
            System.out.println("20. Algorithm Lab [CO3-CO6 Educational Demonstrations]");
            System.out.println("21. Logout");
            System.out.println("==================================================");

            int choice = MainInput.readInt("Enter choice: ");

            switch (choice) {
                case 1:
                    ManagerService.addProduct();
                    break;
                case 2:
                    ManagerService.updateProduct();
                    break;
                case 3:
                    ManagerService.deleteProduct();
                    break;
                case 4:
                    ManagerService.displayProducts();
                    break;
                case 5:
                    ProductSearchService.managerSearchMenu();
                    break;
                case 6:
                    AhoCorasickService.analyzeReviewKeywords();
                    break;
                case 7:
                    ProductSimilarityService.analyzeProductSimilarity();
                    break;
                case 8:
                    RepeatedPhrasesService.detectRepeatedPhrases();
                    break;
                case 9:
                    SearchSuggestionsService.generateSearchSuggestions("Manager Menu");
                    break;
                case 10:
                    NetworkFlowService.optimizeInventoryDistribution();
                    break;
                case 11:
                    MinCutService.identifySupplyBottlenecks();
                    break;
                case 12:
                    BipartiteMatchingService.assignOrders();
                    break;
                case 13:
                    KnapsackPromotionService.optimizePromotions();
                    break;
                case 14:
                    ConflictAnalysisService.analyzePromotionConflicts();
                    break;
                case 15:
                    DeliveryTSPService.planDeliveryRoutes();
                    break;
                case 16:
                    ParallelSalesAnalyticsService.analyzeSales();
                    break;
                case 17:
                    SalesSortingService.sortSalesRecords();
                    break;
                case 18:
                    ReservoirSamplingService.sampleCustomerReviews();
                    break;
                case 19:
                    AlgorithmComparisonService.compareAlgorithms();
                    break;
                case 20:
                    AlgorithmLabService.showAlgorithmLabMenu();
                    break;
                case 21:
                    System.out.println("\nManager session ended. Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a valid number (1 to 21).");
            }
        }
    }

    // =====================================================
    // CUSTOMER MENU
    // =====================================================
    private static void showCustomerMenu(User customer) {
        while (true) {
            System.out.println();
            System.out.println("==================================================");
            System.out.println("                 CUSTOMER MENU");
            System.out.printf("  Customer: %s | Wallet Balance: ₹%.2f%n", customer.getFullName(), customer.getWalletBalance());
            System.out.println("==================================================");
            System.out.println("1.  Browse Products [Data Traversal]");
            System.out.println("2.  Search Products [CO1: KMP]");
            System.out.println("3.  Search Multiple Keywords [CO1: Aho-Corasick]");
            System.out.println("4.  Search Using Rolling Hash [CO1: Rabin-Karp]");
            System.out.println("5.  Get Spelling Suggestions [CO3: Levenshtein / Damerau-Levenshtein]");
            System.out.println("6.  Find Similar Products [CO2: Suffix Array + LCP/Kasai]");
            System.out.println("7.  Compare Product Descriptions [CO2: Longest Common Substring]");
            System.out.println("8.  Shop Within Budget [CO3: Bitmask DP Budget Optimizer]");
            System.out.println("9.  Compare Products [Direct Attribute Comparison]");
            System.out.println("10. View Product Details [Data Lookup]");
            System.out.println("11. Place Order [Order Processing]");
            System.out.println("12. Submit Review [Standard Data Operation]");
            System.out.println("13. View Order History [Data Filtering & Traversal]");
            System.out.println("14. Logout");
            System.out.println("==================================================");

            int choice = MainInput.readInt("Enter choice: ");

            switch (choice) {
                case 1:
                    CustomerService.browseProducts();
                    break;
                case 2:
                    ProductSearchService.searchWithKMP("Customer Menu");
                    break;
                case 3:
                    AhoCorasickService.searchMultipleKeywords();
                    break;
                case 4:
                    ProductSearchService.searchWithRabinKarp("Customer Menu");
                    break;
                case 5:
                    SearchSuggestionsService.generateSearchSuggestions("Customer Menu");
                    break;
                case 6:
                    ProductSimilarityService.findSimilarProducts();
                    break;
                case 7:
                    ProductSimilarityService.compareProductDescriptions();
                    break;
                case 8:
                    BitmaskDPService.recommendProductsWithinBudget("Customer Menu");
                    break;
                case 9:
                    CustomerService.compareProducts();
                    break;
                case 10:
                    CustomerService.viewProductDetails();
                    break;
                case 11:
                    CustomerService.placeOrder(customer);
                    break;
                case 12:
                    CustomerService.submitReview(customer);
                    break;
                case 13:
                    CustomerService.viewOrderHistory(customer);
                    break;
                case 14:
                    System.out.println("\nCustomer session ended. Thank you for shopping with RetailIQ!");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a valid number (1 to 14).");
            }
        }
    }
}