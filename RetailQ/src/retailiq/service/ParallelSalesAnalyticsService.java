package retailiq.service;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import retailiq.model.SaleRecord;
import retailiq.util.DataStore;
import retailiq.util.ResultFormatter;

public class ParallelSalesAnalyticsService {

    public static class SalesAggregate {
        public double totalRevenue;
        public int totalUnits;
        public double maxSale;
        public int count;

        public SalesAggregate(double totalRevenue, int totalUnits, double maxSale, int count) {
            this.totalRevenue = totalRevenue;
            this.totalUnits = totalUnits;
            this.maxSale = maxSale;
            this.count = count;
        }

        public static SalesAggregate combine(SalesAggregate a, SalesAggregate b) {
            return new SalesAggregate(
                a.totalRevenue + b.totalRevenue,
                a.totalUnits + b.totalUnits,
                Math.max(a.maxSale, b.maxSale),
                a.count + b.count
            );
        }
    }

    // Parallel RecursiveTask for Parallel Tree Reduce
    static class ParallelReduceTask extends RecursiveTask<SalesAggregate> {
        private final SaleRecord[] records;
        private final int start;
        private final int end;
        private static final int THRESHOLD = 10; // Base case chunk size

        public ParallelReduceTask(SaleRecord[] records, int start, int end) {
            this.records = records;
            this.start = start;
            this.end = end;
        }

        @Override
        protected SalesAggregate compute() {
            if (end - start <= THRESHOLD) {
                double revenue = 0;
                int units = 0;
                double maxVal = 0;
                for (int i = start; i < end; i++) {
                    revenue += records[i].getAmount();
                    units += records[i].getQuantity();
                    if (records[i].getAmount() > maxVal) {
                        maxVal = records[i].getAmount();
                    }
                }
                return new SalesAggregate(revenue, units, maxVal, end - start);
            }

            int mid = (start + end) / 2;
            ParallelReduceTask leftTask = new ParallelReduceTask(records, start, mid);
            ParallelReduceTask rightTask = new ParallelReduceTask(records, mid, end);

            leftTask.fork();
            SalesAggregate rightResult = rightTask.compute();
            SalesAggregate leftResult = leftTask.join();

            return SalesAggregate.combine(leftResult, rightResult);
        }
    }

    public static void analyzeSales() {
        int n = DataStore.sales.size();
        SaleRecord[] records = DataStore.sales.toArray(new SaleRecord[0]);

        ForkJoinPool pool = ForkJoinPool.commonPool();

        long startTime = System.nanoTime();
        SalesAggregate result = pool.invoke(new ParallelReduceTask(records, 0, n));
        long elapsed = System.nanoTime() - startTime;

        ResultFormatter.printHeader(
            "Parallel Reduce (ForkJoin Framework)",
            "CO6 - Randomized & Parallel Algorithms",
            "analyzeSales()",
            n + " Historical Retail Sales Records"
        );

        System.out.println("Parallel Aggregate Sales Metrics:");
        System.out.println("---------------------------------");
        System.out.printf("• Total Processed Transactions : %d sales%n", result.count);
        System.out.printf("• Gross Retail Revenue Lift    : ₹%.2f%n", result.totalRevenue);
        System.out.printf("• Total Inventory Units Sold   : %d units%n", result.totalUnits);
        System.out.printf("• Average Basket Size / Order  : ₹%.2f%n", result.totalRevenue / result.count);
        System.out.printf("• Peak Single Transaction Value: ₹%.2f%n", result.maxSale);
        System.out.printf("• Active Available Worker Cores: %d CPU threads%n", Runtime.getRuntime().availableProcessors());

        ResultFormatter.printFooter(
            elapsed,
            "Work W(n) = O(n), Span S(n) = O(log n) tree reduction",
            "O(log n) call stack space",
            "Manager Menu"
        );
    }
}
