/**
 * Caps class
 * @author Pal Patel 000865048
 */


import java.awt.*;
import java.util.Scanner;

public class Caps extends TimsProduct {
    // cap color
    private Color color;

    /**
     * Constructor
     * @param name
     * @param cost
     * @param price
     * @param color
     */

     private Caps(String name, double cost, double price, Color color) {
        super(name, cost, price);
        this.color = color;
    }

    /**
     * creting a cap order
     * @return cap
     */


    public static Caps create() {
        // cost and price for cap
        double userCost, userPrice;
        // color for cap
        Color userColor = Color.RED;
        Scanner sc = new Scanner(System.in);
        // picking color
        System.out.println("___Enter the Color of Cap___");
        System.out.println("1. Black 2. Red 3. White 4. Gray ");
        // choice of color
        int colorchoice = sc.nextInt();
        switch (colorchoice) {
            case 1:
                userColor = Color.BLACK;
                break;
            case 2:
                userColor = Color.RED;
                break;
            case 3:
                userColor = Color.WHITE;
                break;
            case 4:
                userColor = Color.GRAY;
                break;
            default:
                userColor = Color.WHITE;
                break;
        }
        // asking cost of cap
        System.out.println("Enter the cost of Cap");
        userCost = sc.nextDouble();

        // asking price of the cap
        System.out.println("Enter the price of Cap");
        userPrice = sc.nextDouble();

        return new Caps("Tims's Cap",userCost,userPrice,userColor);
    }

    /**
     *
     * @return toString
     */
    @Override
    public String toString() {
        return "\nCaps: { " + super.toString() +
                "\n            {Color=" + color +
                '}' + "\n";
    }
}