package retailiq.service;

import java.util.ArrayList;
import java.util.List;

import retailiq.model.Review;
import retailiq.util.DataStore;
import retailiq.util.MainInput;

public class RabinKarpService {

    private static final int PRIME = 101;

    public static boolean search(String text, String pattern) {

        if (pattern == null || pattern.isEmpty())
            return true;

        if (text == null || pattern.length() > text.length())
            return false;

        text = text.toLowerCase();
        pattern = pattern.toLowerCase();

        int m = pattern.length();
        int n = text.length();

        int patternHash = 0;
        int textHash = 0;
        int h = 1;

        for (int i = 0; i < m - 1; i++)
            h = (h * 256) % PRIME;

        for (int i = 0; i < m; i++) {
            patternHash =
                    (256 * patternHash + pattern.charAt(i)) % PRIME;
            textHash =
                    (256 * textHash + text.charAt(i)) % PRIME;
        }

        for (int i = 0; i <= n - m; i++) {

            if (patternHash == textHash) {

                boolean match = true;

                for (int j = 0; j < m; j++) {
                    if (text.charAt(i + j) != pattern.charAt(j)) {
                        match = false;
                        break;
                    }
                }

                if (match)
                    return true;
            }

            if (i < n - m) {
                textHash =
                        (256 * (textHash - text.charAt(i) * h)
                        + text.charAt(i + m)) % PRIME;

                if (textHash < 0)
                    textHash += PRIME;
            }
        }

        return false;
    }

    public static void performReviewSearch() {

        String keyword = MainInput.readString(
                "Enter review keyword: ");

        List<Review> results = new ArrayList<>();

        for (Review review : DataStore.reviews) {
            if (search(review.getReviewText(), keyword))
                results.add(review);
        }

        System.out.println("\n========== RABIN-KARP REVIEW SEARCH ==========");

        if (results.isEmpty()) {
            System.out.println("No matching reviews found.");
        } else {

            for (Review review : results)
                System.out.println(review);

            System.out.println("----------------------------------------");
            System.out.println(results.size() + " review(s) found.");
        }
    }
}