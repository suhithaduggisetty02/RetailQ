package retailiq.model;

public class User {

    private String username;
    private String password;
    private String fullName;
    private String role; // "MANAGER" or "CUSTOMER"
    private double walletBalance;

    public User(String username, String password, String fullName, String role, double walletBalance) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
        this.walletBalance = walletBalance;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getRole() {
        return role;
    }

    public double getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(double walletBalance) {
        this.walletBalance = walletBalance;
    }

    public void deductBalance(double amount) {
        this.walletBalance -= amount;
    }

    public void addBalance(double amount) {
        this.walletBalance += amount;
    }
}
