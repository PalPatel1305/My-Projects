/**
 * Muffins class
 * @author Pal Patel 000865048
 */

import java.util.Scanner;

public class Muffins extends TimsProduct implements Consumable {
    // description of Muffin
    private String description;

    // caloriecount for the muffin
    private int calorieCount;

    /**
     * Constructor
     * @param name
     * @param cost
     * @param price
     * @param description
     * @param calorieCount
     */
    private Muffins(String name, double cost, double price, String description, int calorieCount) {
        super(name, cost, price);
        this.description = description;
        this.calorieCount = calorieCount;
    }

    /**
     * creating muffin order
     * @return muffin new object for the order
     */

    public static Muffins create(){

        Scanner sc = new Scanner(System.in);
        // cost and price for muffins
        double userCost, userPrice;
        // type of muffins
        System.out.println("___Which type of donut would you like to have?___\n1.Chocolate Chips \n2.Blueberry  \n3.Fruit Explosion");
        int choice = sc.nextInt();
        // cost and price of muffin
        System.out.println("Enter the cost of Muffins");
        userCost = sc.nextDouble();
        System.out.println("Enter the price of Muffins");
        userPrice = sc.nextDouble();
        // returning muffin object
        switch (choice) {
            case 1:
                return new Muffins("Chocolate Chips Muffin", userCost, userPrice, "A muffin with choclate chips",428);
            case 2:
                return new Muffins("Blueberry Muffin", userCost, userPrice, "A muffin with blueberry",377);
            case 3:
                return new Muffins("Fruit Explosion Muffin", userCost, userPrice, "A muffin with fruits",408);
            default:
                return new Muffins("Fruit Explosion Muffin", userCost, userPrice, "A muffin with fruits",408);
        }

    }


    /**
     * get description
     * @return description
     */

    public String getDescription() {
        return description;
    }

    /**
     * caloriecount
     * @return caloriecount
     */
    @Override
    public int getCalorieCount() {
        return this.calorieCount;
    }

    /**
     *
     * @return the consumption methods
     */
    @Override
    public String getConsumptionMethod() {
        return "Eat it";
    }

    /**
     *
     * @return toString
     */

    @Override
    public String toString() {
        return "\nMuffins:" + super.toString() + " Consumption: "+getConsumptionMethod() + "!! }\n            {Description: " + getDescription() +" , Calories = " + getCalorieCount() + "}"+ "\n" ;
    }
}
