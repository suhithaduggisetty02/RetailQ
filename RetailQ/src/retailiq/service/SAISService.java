package retailiq.service;

import java.util.Arrays;

import retailiq.model.Product;
import retailiq.util.DataStore;
import retailiq.util.MainInput;

public class SAISService {

    /*
     * SA-IS implementation.
     * Builds suffix array using induced sorting.
     */

    private static int[] sais(int[] s, int upper) {

        int n = s.length;

        if (n == 0)
            return new int[0];

        if (n == 1)
            return new int[] { 0 };

        if (n == 2) {

            if (s[0] < s[1])
                return new int[] { 0, 1 };

            return new int[] { 1, 0 };
        }

        boolean[] isS = new boolean[n];

        isS[n - 1] = true;

        for (int i = n - 2; i >= 0; i--) {
            isS[i] = s[i] < s[i + 1]
                    || (s[i] == s[i + 1] && isS[i + 1]);
        }

        boolean[] isLMS = new boolean[n];

        for (int i = 1; i < n; i++) {
            isLMS[i] = isS[i] && !isS[i - 1];
        }

        int[] sumL = new int[upper + 1];
        int[] sumS = new int[upper + 1];

        for (int value : s) {
            if (value == upper) {
                sumS[value]++;
            } else {
                sumL[value]++;
            }
        }

        for (int i = 0; i <= upper; i++) {

            if (i < upper)
                sumS[i] += sumL[i];

            if (i > 0)
                sumL[i] += sumS[i - 1];
        }

        int[] sa = new int[n];

        Arrays.fill(sa, -1);

        induce(sa, s, isS, isLMS, sumL, sumS, upper);

        int[] lms = new int[n];
        int m = 0;

        for (int i = 1; i < n; i++) {
            if (isLMS[i])
                lms[m++] = i;
        }

        int[] sortedLMS = new int[m];
        int p = 0;

        for (int value : sa) {

            if (value >= 0 && isLMS[value]) {
                sortedLMS[p++] = value;
            }
        }

        int[] lmsMap = new int[n];
        Arrays.fill(lmsMap, -1);

        for (int i = 0; i < m; i++) {
            lmsMap[lms[i]] = i;
        }

        int recUpper = 0;
        int[] recS = new int[m];

        recS[lmsMap[sortedLMS[0]]] = 0;

        for (int i = 1; i < m; i++) {

            int left = sortedLMS[i - 1];
            int right = sortedLMS[i];

            boolean different = false;

            for (int d = 0; ; d++) {

                if (s[left + d] != s[right + d]
                        || isLMS[left + d] != isLMS[right + d]) {

                    different = true;
                    break;
                }

                if (d > 0 &&
                    (isLMS[left + d] || isLMS[right + d])) {
                    break;
                }
            }

            if (different)
                recUpper++;

            recS[lmsMap[right]] = recUpper;
        }

        int[] recSA;

        if (recUpper + 1 == m) {
            recSA = new int[m];

            for (int i = 0; i < m; i++) {
                recSA[recS[i]] = i;
            }

        } else {

            recSA = sais(recS, recUpper);
        }

        for (int i = 0; i < m; i++) {
            sortedLMS[i] = lms[recSA[i]];
        }

        Arrays.fill(sa, -1);

        induce(sa, s, isS, isLMS, sumL, sumS,
                upper, sortedLMS);

        return sa;
    }

    private static void induce(
            int[] sa,
            int[] s,
            boolean[] isS,
            boolean[] isLMS,
            int[] sumL,
            int[] sumS,
            int upper) {

        induce(sa, s, isS, isLMS,
                sumL, sumS, upper, null);
    }

    private static void induce(
            int[] sa,
            int[] s,
            boolean[] isS,
            boolean[] isLMS,
            int[] sumL,
            int[] sumS,
            int upper,
            int[] sortedLMS) {

        Arrays.fill(sa, -1);

        int n = s.length;

        int[] buffer = new int[upper + 1];

        if (sortedLMS != null) {

            System.arraycopy(sumS, 0, buffer, 0, buffer.length);

            for (int i = sortedLMS.length - 1; i >= 0; i--) {

                int p = sortedLMS[i];

                sa[--buffer[s[p]]] = p;
            }
        }

        System.arraycopy(sumL, 0, buffer, 0, buffer.length);

        sa[buffer[s[n - 1]]++] = n - 1;

        for (int i = 0; i < n; i++) {

            int p = sa[i];

            if (p > 0 && !isS[p - 1]) {

                sa[buffer[s[p - 1]]++] = p - 1;
            }
        }

        System.arraycopy(sumS, 0, buffer, 0, buffer.length);

        for (int i = n - 1; i >= 0; i--) {

            int p = sa[i];

            if (p > 0 && isS[p - 1]) {

                sa[--buffer[s[p - 1]]] = p - 1;
            }
        }
    }

    public static int[] buildSuffixArray(String text) {

        text = text.toLowerCase();

        int n = text.length();

        int[] values = new int[n + 1];

        for (int i = 0; i < n; i++) {
            values[i] = text.charAt(i) + 1;
        }

        // Sentinel is smaller than every character.
        values[n] = 0;

        int[] result = sais(values, 256 + 1);

        // Remove sentinel position.
        int[] answer = new int[n];

        int index = 0;

        for (int value : result) {

            if (value != n) {
                answer[index++] = value;
            }
        }

        return answer;
    }

    public static void performSAIS() {

        Product product = chooseProduct();

        if (product == null)
            return;

        String text = product.getDescription();

        int[] sa = buildSuffixArray(text);

        System.out.println();
        System.out.println("========== SA-IS SUFFIX ARRAY ==========");
        System.out.println("Product: " + product.getName());

        for (int i = 0; i < sa.length; i++) {

            System.out.printf(
                    "%2d -> %s%n",
                    sa[i],
                    text.substring(sa[i])
            );
        }
    }

    private static Product chooseProduct() {

        System.out.println("\nAvailable Products:");

        for (Product product : DataStore.products) {
            System.out.println(
                    product.getId() + " - " + product.getName()
            );
        }

        int id = MainInput.readInt("Enter product ID: ");

        for (Product product : DataStore.products) {

            if (product.getId() == id)
                return product;
        }

        System.out.println("Product not found.");
        return null;
    }
}