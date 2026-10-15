package retailiq.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Order {

    private int orderId;
    private String customerUsername;
    private int productId;
    private String productName;
    private int quantity;
    private double unitPrice;
    private double totalAmount;
    private String orderDate;
    private String status;

    public Order(int orderId, String customerUsername, int productId, String productName,
                 int quantity, double unitPrice, double totalAmount, String status) {
        this.orderId = orderId;
        this.customerUsername = customerUsername;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalAmount = totalAmount;
        this.orderDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.status = status;
    }

    public Order(int orderId, String customerUsername, int productId, String productName,
                 int quantity, double unitPrice, double totalAmount, String orderDate, String status) {
        this.orderId = orderId;
        this.customerUsername = customerUsername;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format(
            "Order #%d | %s | %s x%d | Total: ₹%.2f | Date: %s | Status: %s",
            orderId, customerUsername, productName, quantity, totalAmount, orderDate, status
        );
    }
}
