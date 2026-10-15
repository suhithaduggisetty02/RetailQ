package retailiq.model;

public class Promotion {

    private int id;
    private String code;
    private String name;
    private double cost;
    private double projectedLift;
    private String category;

    public Promotion(int id, String code, String name, double cost, double projectedLift, String category) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.cost = cost;
        this.projectedLift = projectedLift;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    public double getProjectedLift() {
        return projectedLift;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return String.format(
            "[%d] %-10s | %-32s | Cost: ₹%6.0f | Expected Lift: ₹%7.0f | %s",
            id, code, name, cost, projectedLift, category
        );
    }
}
