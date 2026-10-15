package retailiq.service;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import retailiq.model.SaleRecord;
import retailiq.util.DataStore;
import retailiq.util.MainInput;
import retailiq.util.ResultFormatter;

public class SalesSortingService {

    private static final Random random = new Random();

    // Hand-built Randomized QuickSort
    public static void randomizedQuickSort(SaleRecord[] arr, int low, int high) {
        if (low < high) {
            int pi = randomizedPartition(arr, low, high);
            randomizedQuickSort(arr, low, pi - 1);
            randomizedQuickSort(arr, pi + 1, high);
        }
    }

    private static int randomizedPartition(SaleRecord[] arr, int low, int high) {
        int randomIndex = low + random.nextInt(high - low + 1);
        swap(arr, randomIndex, high);

        double pivot = arr[high].getAmount();
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j].getAmount() <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    private static void swap(SaleRecord[] arr, int i, int j) {
        SaleRecord temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // Hand-built Parallel MergeSort Task
    static class ParallelMergeSortTask extends RecursiveAction {
        private final SaleRecord[] arr;
        private final int low;
        private final int high;
        private static final int THRESHOLD = 16;

        public ParallelMergeSortTask(SaleRecord[] arr, int low, int high) {
            this.arr = arr;
            this.low = low;
            this.high = high;
        }

        @Override
        protected void compute() {
            if (high - low <= THRESHOLD) {
                // Sequential insertion sort base case
                for (int i = low + 1; i <= high; i++) {
                    SaleRecord key = arr[i];
                    int j = i - 1;
                    while (j >= low && arr[j].getAmount() > key.getAmount()) {
                        arr[j + 1] = arr[j];
                        j--;
                    }
                    arr[j + 1] = key;
                }
                return;
            }

            int mid = (low + high) / 2;
            ParallelMergeSortTask left = new ParallelMergeSortTask(arr, low, mid);
            ParallelMergeSortTask right = new ParallelMergeSortTask(arr, mid + 1, high);

            invokeAll(left, right);
            merge(arr, low, mid, high);
        }

        private void merge(SaleRecord[] a, int l, int m, int r) {
            int n1 = m - l + 1;
            int n2 = r - m;

            SaleRecord[] left = new SaleRecord[n1];
            SaleRecord[] right = new SaleRecord[n2];

            System.arraycopy(a, l, left, 0, n1);
            System.arraycopy(a, m + 1, right, 0, n2);

            int i = 0, j = 0, k = l;
            while (i < n1 && j < n2) {
                if (left[i].getAmount() <= right[j].getAmount()) {
                    a[k++] = left[i++];
                } else {
                    a[k++] = right[j++];
                }
            }
            while (i < n1) a[k++] = left[i++];
            while (j < n2) a[k++] = right[j++];
        }
    }

    public static void sortSalesRecords() {
        System.out.println("\n------------------------------------------------");
        System.out.println("   SORT SALES RECORDS (CO6 ADVANCED SORTING)");
        System.out.println("------------------------------------------------");
        System.out.println("Select Sorting Algorithm:");
        System.out.println("1. Randomized QuickSort (Random Pivot Partitioning)");
        System.out.println("2. Parallel MergeSort (Multi-threaded ForkJoin)");
        System.out.println("3. Comparative Benchmark (Both Side-by-Side)");
        System.out.println("4. Back");
        int choice = MainInput.readInt("Enter choice: ");
        if (choice == 4) return;

        int n = DataStore.sales.size();

        if (choice == 1) {
            SaleRecord[] arr = DataStore.sales.toArray(new SaleRecord[0]);
            long startTime = System.nanoTime();
            randomizedQuickSort(arr, 0, n - 1);
            long elapsed = System.nanoTime() - startTime;

            ResultFormatter.printHeader(
                "Randomized QuickSort",
                "CO6 - Randomized Algorithms",
                "sortSalesRecords()",
                n + " Sales Transactions Sorted by Revenue (Ascending)"
            );

            displaySampleSorted(arr);

            ResultFormatter.printFooter(
                elapsed,
                "Expected O(n log n) with probability 1 - 1/n, Space O(log n)",
                "O(log n) recursion stack",
                "Manager Menu"
            );

        } else if (choice == 2) {
            SaleRecord[] arr = DataStore.sales.toArray(new SaleRecord[0]);
            ForkJoinPool pool = ForkJoinPool.commonPool();

            long startTime = System.nanoTime();
            pool.invoke(new ParallelMergeSortTask(arr, 0, n - 1));
            long elapsed = System.nanoTime() - startTime;

            ResultFormatter.printHeader(
                "Parallel MergeSort (ForkJoin)",
                "CO6 - Parallel Algorithms",
                "sortSalesRecords()",
                n + " Sales Transactions Sorted by Revenue (Ascending)"
            );

            displaySampleSorted(arr);

            ResultFormatter.printFooter(
                elapsed,
                "Work W(n) = O(n log n), Span S(n) = O(log^2 n)",
                "O(n) auxiliary merge buffer",
                "Manager Menu"
            );

        } else if (choice == 3) {
            SaleRecord[] arr1 = DataStore.sales.toArray(new SaleRecord[0]);
            SaleRecord[] arr2 = DataStore.sales.toArray(new SaleRecord[0]);

            long start1 = System.nanoTime();
            randomizedQuickSort(arr1, 0, n - 1);
            long elapsed1 = System.nanoTime() - start1;

            ForkJoinPool pool = ForkJoinPool.commonPool();
            long start2 = System.nanoTime();
            pool.invoke(new ParallelMergeSortTask(arr2, 0, n - 1));
            long elapsed2 = System.nanoTime() - start2;

            ResultFormatter.printHeader(
                "Randomized QuickSort vs Parallel MergeSort",
                "CO6 - Sorting Benchmark",
                "sortSalesRecords()",
                n + " Records Benchmark"
            );

            System.out.println("Empirical Performance Comparison:");
            System.out.printf("1. Randomized QuickSort : %d ns (%.4f ms)%n", elapsed1, elapsed1 / 1_000_000.0);
            System.out.printf("2. Parallel MergeSort   : %d ns (%.4f ms)%n", elapsed2, elapsed2 / 1_000_000.0);
            System.out.println("\nSample Top 5 Lowest and Highest Transactions:");
            displaySampleSorted(arr1);

            ResultFormatter.printFooter(
                elapsed1 + elapsed2,
                "Randomized QuickSort: Expected O(n log n) | Parallel Sort: Span O(log^2 n)",
                "QuickSort: O(log n) | MergeSort: O(n)",
                "Manager Menu"
            );
        }
    }

    private static void displaySampleSorted(SaleRecord[] arr) {
        System.out.println("Top 3 Lowest Value Transactions:");
        for (int i = 0; i < Math.min(3, arr.length); i++) {
            System.out.println("  " + arr[i]);
        }
        System.out.println("...");
        System.out.println("Top 3 Highest Value Transactions:");
        for (int i = Math.max(0, arr.length - 3); i < arr.length; i++) {
            System.out.println("  " + arr[i]);
        }
    }
}
