package retailiq.model;

public class Review {

    private int productId;
    private String customerName;
    private String reviewText;
    private int rating;

    public Review(int productId, String customerName,
                  String reviewText, int rating) {
        this.productId = productId;
        this.customerName = customerName;
        this.reviewText = reviewText;
        this.rating = rating;
    }

    public int getProductId() {
        return productId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getReviewText() {
        return reviewText;
    }

    public int getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return customerName + " (" + rating + "/5): " + reviewText;
    }
}