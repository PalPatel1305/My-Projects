/**
 * author @Pal Patel 000865048
 */

import java.util.Scanner;

/**
 * Donut class extends TimsProduct and implements Consumable
 */
public class Donut extends TimsProduct implements Consumable {
    // description of product
    private String description;
    // calories of product
    private int calorieCount;

    /**
     * Constructor
     * @param name
     * @param description
     * @param cost
     * @param price
     * @param calorieCount
     */
    private Donut(String name, String description, double cost, double price, int calorieCount) {
        super(name, cost, price);
        this.description = description;
        this.calorieCount = calorieCount;
    }

    /**
     *
     * @return a donut object
     */

    public static Donut create() {

        Scanner sc = new Scanner(System.in);
        // user cost and price
        double userCost, userPrice;
        // type of donuts
        System.out.println("___Which type of donut would you like to have?___\n1.Boston Cream \n2.Double Chocolate \n3.Apple Fritter");
        // choice of donut
        int choice = sc.nextInt();
        // user cost and price
        System.out.println("Enter the cost of Donut!!");
        userCost = sc.nextDouble();
        System.out.println("Enter the price of Donut!!");
        userPrice = sc.nextDouble();

        switch (choice) {
            case 1:
                return new Donut("Boston Cream", "Best in Tim", userCost, userPrice, 350);
            case 2:
                return new Donut("Double Chocolate", "Good with French Vanilla", userCost, userPrice, 350);
            case 3:
                return new Donut("Apple Fritter", "Good with Iced Cap", userCost, userPrice, 499);
            default:
                return new Donut("Apple Fritter", "Good with Iced Cap", userCost, userPrice, 499);
        }

    }

    /**
     *
     * @return description
     */

    public String getDescription(){
        return description;
    }

    /**
     *
     * @return caloriecount
     */

    public int getCalorieCount(){
        return calorieCount;
    }

    /**
     *
     * @return eat it
     */

    public String getConsumptionMethod(){
        return "Eat it";
    }

    /**
     *
     * @return toString
     */

    @Override
    public String toString() {
        return "\nDonut - {" + super.toString()+
                 " consumption: " +getConsumptionMethod() +
                "!! } \n            {Description=' " + getDescription() + '\'' +
                " , calorieCount= " + getCalorieCount() +
                '}'+ "\n";
    }
}
