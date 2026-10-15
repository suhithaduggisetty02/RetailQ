package retailiq.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retailiq.model.Review;
import retailiq.util.DataStore;
import retailiq.util.MainInput;
import retailiq.util.ResultFormatter;

public class ReservoirSamplingService {

    private static final Random random = new Random();

    // Hand-built Algorithm R (Reservoir Sampling)
    public static List<Review> sample(List<Review> stream, int k) {
        List<Review> reservoir = new ArrayList<>(k);
        int n = stream.size();

        if (n <= k) {
            return new ArrayList<>(stream);
        }

        // Fill reservoir with first k elements
        for (int i = 0; i < k; i++) {
            reservoir.add(stream.get(i));
        }

        // Process remaining elements from stream
        for (int i = k; i < n; i++) {
            int j = random.nextInt(i + 1); // Random index in [0, i]
            if (j < k) {
                reservoir.set(j, stream.get(i));
            }
        }

        return reservoir;
    }

    public static void sampleCustomerReviews() {
        System.out.println("\n------------------------------------------------");
        System.out.println("  SAMPLE CUSTOMER REVIEWS (RESERVOIR SAMPLING)");
        System.out.println("------------------------------------------------");
        int totalReviews = DataStore.reviews.size();
        System.out.println("Total Available Reviews in Stream: " + totalReviews);

        int k = MainInput.readInt("Enter sample size k (e.g. 3 or 5): ");
        if (k <= 0) k = 3;
        if (k > totalReviews) k = totalReviews;

        long startTime = System.nanoTime();
        List<Review> sampleResults = sample(DataStore.reviews, k);
        long elapsed = System.nanoTime() - startTime;

        ResultFormatter.printHeader(
            "Reservoir Sampling (Algorithm R)",
            "CO6 - Randomized Algorithms",
            "sampleCustomerReviews()",
            "Sample k = " + k + " from n = " + totalReviews + " reviews"
        );

        System.out.printf("Uniformly Sampled Customer Reviews (Each item probability P = %d/%d = %.1f%%):%n",
            k, totalReviews, ((double) k / totalReviews) * 100.0);
        System.out.println("--------------------------------------------------------------------------------");
        for (int i = 0; i < sampleResults.size(); i++) {
            Review r = sampleResults.get(i);
            System.out.printf("%d. [Product #%d] %s (%d/5 stars):%n   \"%s\"%n%n",
                (i + 1), r.getProductId(), r.getCustomerName(), r.getRating(), r.getReviewText());
        }

        ResultFormatter.printFooter(
            elapsed,
            "O(n) single pass streaming time",
            "O(k) auxiliary reservoir space",
            "Manager Menu"
        );
    }
}
