/**
 * Customer Class holds the items and the time needed tpo serve that customer based on the items they carry.
 * @author Pal Patel 000865048
 */
public class Customer {

    // New field to store the total number of the items a customer carry in the cart
    private int numberOfItems = 0;
    private int timeToServe; // New field to store the time needed for service


    /**
     * Constructor
     * @param numberOfItems total number of the items a customer carry in the cart.
     */
    public Customer(int numberOfItems) {
        this.numberOfItems = numberOfItems;
        this.timeToServe = calculationTimeOfServing(); // Set initial service time
    }

    /**
     * Calculate the time needed to serve a customer
     * @return total time needed to serve that customer
     */
    public int calculationTimeOfServing() {
        return 45 + 5 * numberOfItems;
    }


    /**
     * Return the time needed to serve a customer
     * @return The time needed to serve a customer
     */
    public int getTimeToServe() {
        return timeToServe;
    }

    /**
     * Set the time needed to serve that particular customer
     * @param timeToServe The time needed to serve that customer
     */
    public void setTimeToServe(int timeToServe) {
        this.timeToServe = timeToServe;
    }

    /**
     * String representation of an object of the Customer
     * @return String representation of an object of the Customer
     */
    @Override
    public String toString() {
        return "{Items= " +
                numberOfItems +
                ", Time= " + timeToServe +
                "s}";
    }
}
