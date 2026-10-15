package retailiq.service;

import retailiq.model.User;
import retailiq.util.DataStore;
import retailiq.util.MainInput;

public class AuthService {

    public static User managerLogin() {
        System.out.println("\n----------------------------------------");
        System.out.println("            MANAGER LOGIN");
        System.out.println("----------------------------------------");
        System.out.println("(Default credentials: manager / admin123)");
        String username = MainInput.readString("Enter Username: ");
        String password = MainInput.readString("Enter Password: ");

        for (User user : DataStore.users) {
            if (user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(password)) {
                if ("MANAGER".equalsIgnoreCase(user.getRole())) {
                    System.out.println("\nLogin Successful! Welcome, " + user.getFullName() + ".");
                    return user;
                } else {
                    System.out.println("\nAccess Denied: Account is not authorized as Manager.");
                    return null;
                }
            }
        }

        System.out.println("\nInvalid username or password. Please try again.");
        return null;
    }

    public static User customerLogin() {
        System.out.println("\n----------------------------------------");
        System.out.println("            CUSTOMER LOGIN");
        System.out.println("----------------------------------------");
        System.out.println("(Sample accounts: rahul / pass123, priya / pass123)");
        String username = MainInput.readString("Enter Username: ");
        String password = MainInput.readString("Enter Password: ");

        for (User user : DataStore.users) {
            if (user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(password)) {
                if ("CUSTOMER".equalsIgnoreCase(user.getRole())) {
                    System.out.println("\nLogin Successful! Welcome back, " + user.getFullName() + ".");
                    System.out.printf("Current Wallet Balance: ₹%.2f%n", user.getWalletBalance());
                    return user;
                }
            }
        }

        System.out.println("\nInvalid credentials. If you are a new customer, please register first.");
        return null;
    }

    public static User customerRegistration() {
        System.out.println("\n----------------------------------------");
        System.out.println("         CUSTOMER REGISTRATION");
        System.out.println("----------------------------------------");
        String fullName = MainInput.readString("Enter Full Name: ");
        if (fullName.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return null;
        }

        String username;
        while (true) {
            username = MainInput.readString("Choose Username: ");
            if (username.isEmpty()) {
                System.out.println("Username cannot be empty.");
                continue;
            }
            boolean exists = false;
            for (User u : DataStore.users) {
                if (u.getUsername().equalsIgnoreCase(username)) {
                    exists = true;
                    break;
                }
            }
            if (exists) {
                System.out.println("Username already taken. Please choose another username.");
            } else {
                break;
            }
        }

        String password = MainInput.readString("Choose Password: ");
        double initialBalance = MainInput.readDouble("Initial Wallet Deposit (₹): ");
        if (initialBalance < 0) {
            initialBalance = 0;
        }

        User newUser = new User(username, password, fullName, "CUSTOMER", initialBalance);
        DataStore.users.add(newUser);
        System.out.println("\nRegistration Successful!");
        System.out.println("Account created for " + fullName + " (" + username + ") with balance ₹" + initialBalance);
        return newUser;
    }
}
