package retailiq.util;

public class ResultFormatter {

    public static void printHeader(String algorithm, String module, String function, String input) {
        System.out.println();
        System.out.println("Algorithm: " + algorithm);
        System.out.println("Module: " + module);
        System.out.println("Function: " + function);
        if (input != null && !input.isEmpty()) {
            System.out.println("Input: " + input);
        }
        System.out.println();
    }

    public static void printFooter(long elapsedNanos, String timeComplexity, String spaceComplexity, String returnMenu) {
        double elapsedMillis = elapsedNanos / 1_000_000.0;
        System.out.println();
        System.out.printf("Execution time: %d ns (%.4f ms)%n", elapsedNanos, elapsedMillis);
        System.out.println();
        System.out.println("Time Complexity: " + timeComplexity);
        System.out.println("Space Complexity: " + spaceComplexity);
        MainInput.pressEnterToContinue(returnMenu);
    }
}
