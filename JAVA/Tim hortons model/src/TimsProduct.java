/**
 * Tims product class
 * @author Pal Patel 000865048
 */

public abstract class TimsProduct implements Commodity {
    //   to store name of the product
    private String name;
    // price and cost of the product
    private double cost,price;

    /**
     * constructor
     * @param name
     * @param cost
     * @param price
     */

    public TimsProduct(String name, double cost, double price) {
        this.name = name;
        this.cost = cost;
        this.price = price;
    }

    /**
     *
     * @return name - name of the product
     */
    public String getName(){
        return name;
    }

    /**
     *
     * @return cost - cost of the product
     */

    public double getProductionCost(){
        return cost;
    }

    /**
     *
     * @return retail price of the product
     */
    public double getRetailPrice(){
        return price;
    }

    /**
     *
     * @return toString
     */
    @Override
    public String toString() {
        return "" +
                "name='" + name + '\'' +
                ", cost=" + cost +
                ", price=" + price +
                ' ';
    }
}
