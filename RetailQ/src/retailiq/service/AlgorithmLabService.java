package retailiq.service;

import retailiq.util.MainInput;

public class AlgorithmLabService {

    public static void showAlgorithmLabMenu() {
        while (true) {
            System.out.println();
            System.out.println("==================================================");
            System.out.println("               ALGORITHM LAB MENU");
            System.out.println("   (Educational Syllabus Demos for Managers)");
            System.out.println("==================================================");
            System.out.println("1.  Sequence Alignment [CO3: Needleman-Wunsch]");
            System.out.println("2.  Matrix Analytics Optimization [CO3: Matrix Chain Multiplication]");
            System.out.println("3.  Optimal Product Lookup Tree [CO3: Optimal Binary Search Tree]");
            System.out.println("4.  Tree-Based Category Analysis [CO3: Tree DP / Rerooting DP]");
            System.out.println("5.  Category Subset Analysis [CO3: SOS DP]");
            System.out.println("6.  Small Graph Optimization [CO3: Hamiltonian Path Bitmask DP]");
            System.out.println("7.  Set-Cover Promotion Planning [CO3: Set Cover DP]");
            System.out.println("8.  Network Flow Comparison [CO4: Ford-Fulkerson / Edmonds-Karp / Dinic]");
            System.out.println("9.  Retail Constraint Solver [CO5: SAT / 3-SAT]");
            System.out.println("10. Graph Conflict Analysis [CO5: CLIQUE / Independent Set / Vertex Cover]");
            System.out.println("11. Delivery Optimization Comparison [CO5: Exact TSP vs Approximation]");
            System.out.println("12. Product-Code Hashing [CO6: Universal Hashing / Perfect Hashing]");
            System.out.println("13. Large-Integer Primality Test [CO6: Miller-Rabin]");
            System.out.println("14. Parallel Sales Aggregation [CO6: Parallel Reduce]");
            System.out.println("15. Cumulative Sales Analysis [CO6: Parallel Scan]");
            System.out.println("16. Parallel Performance Analysis [CO6: Work / Span / Brent's Theorem]");
            System.out.println("17. Back to Manager Menu");
            System.out.println("==================================================");

            int choice = MainInput.readInt("Enter choice: ");

            switch (choice) {
                case 1:
                    SequenceAlignmentService.performNeedlemanWunsch();
                    break;
                case 2:
                    MatrixChainService.perform();
                    break;
                case 3:
                    OptimalBSTService.performOptimalBST();
                    break;
                case 4:
                    TreeDPService.performTreeDP();
                    break;
                case 5:
                    SOSDPService.performSOSDP();
                    break;
                case 6:
                    HamiltonianPathService.performHamiltonianPath();
                    break;
                case 7:
                    SetCoverDPService.performSetCoverDP();
                    break;
                case 8:
                    FlowComparisonService.performFlowComparison();
                    break;
                case 9:
                    SATConstraintService.perform3SATSolver();
                    break;
                case 10:
                    GraphReductionService.performGraphConflictAnalysis();
                    break;
                case 11:
                    DeliveryTSPService.planDeliveryRoutes();
                    break;
                case 12:
                    UniversalHashingService.performUniversalHashing();
                    break;
                case 13:
                    MillerRabinService.performMillerRabin();
                    break;
                case 14:
                    ParallelSalesAnalyticsService.analyzeSales();
                    break;
                case 15:
                    ParallelScanService.performParallelScan();
                    break;
                case 16:
                    ParallelPerformanceService.performParallelAnalysis();
                    break;
                case 17:
                    System.out.println("\nReturning to Manager Menu...");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 17.");
            }
        }
    }
}
