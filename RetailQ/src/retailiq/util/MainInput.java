package retailiq.util;

import java.util.Scanner;

public class MainInput {

    private static final Scanner scanner = new Scanner(System.in);

    public static String readString(String message) {
        System.out.print(message);
        if (!scanner.hasNextLine()) {
            return "";
        }
        return scanner.nextLine().trim();
    }

    public static int readInt(String message) {
        while (true) {
            System.out.print(message);
            if (!scanner.hasNextLine()) {
                return -1;
            }
            try {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    return -1;
                }
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter a valid integer.");
            }
        }
    }

    public static double readDouble(String message) {
        while (true) {
            System.out.print(message);
            if (!scanner.hasNextLine()) {
                return -1.0;
            }
            try {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    return -1.0;
                }
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter a valid floating-point number.");
            }
        }
    }

    public static void pressEnterToContinue(String menuName) {
        System.out.println("\nPress Enter to return to the " + menuName + "...");
        try {
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
        } catch (Exception ignored) {
        }
    }
}