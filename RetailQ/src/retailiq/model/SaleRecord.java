package retailiq.model;

public class SaleRecord implements Comparable<SaleRecord> {

    private int saleId;
    private int productId;
    private String productName;
    private int quantity;
    private double amount;
    private long timestamp;
    private String region;

    public SaleRecord(int saleId, int productId, String productName, int quantity,
                      double amount, long timestamp, String region) {
        this.saleId = saleId;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.amount = amount;
        this.timestamp = timestamp;
        this.region = region;
    }

    public int getSaleId() {
        return saleId;
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

    public double getAmount() {
        return amount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getRegion() {
        return region;
    }

    @Override
    public int compareTo(SaleRecord other) {
        return Double.compare(this.amount, other.amount);
    }

    @Override
    public String toString() {
        return String.format(
            "Txn #%04d | Product: %-28s | Qty: %2d | Amount: ₹%8.2f | Region: %-7s",
            saleId, productName, quantity, amount, region
        );
    }
}
