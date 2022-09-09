/**
 * Author: @Pal Patel 000865048
 */

import java.awt.*;
import java.util.Scanner;

/**
 * Mug class
 */

public class Mug extends TimsProduct{
    // color
    private Color color;

    /**
     * Constructor
     * @param name
     * @param cost
     * @param price
     * @param color
     */
    private Mug(String name, double cost, double price, Color color) {
        super(name, cost, price);
        this.color = color;
    }

    /**
     *
     * @return a mug object
     */
    public static Mug create(){
        // usercost and userprice
        double userCost, userPrice;
        // color
        Color userColor = Color.RED;
        Scanner sc = new Scanner(System.in);
        // type of mug
        System.out.println("___Which king of mug would you like to have?___\n1.Plastic \n2.Glass");
        int choice =sc.nextInt();
        //color choice
        System.out.println("Enter the Color of Mug");
        System.out.println("1. Black 2. Blue 3. White 4. Red");
        // color choice
        int colorchoice = sc.nextInt();
        switch (colorchoice) {
            case 1:
                userColor = Color.RED;
                break;
            case 2:
                userColor = Color.BLACK;
                break;
            case 3:
                userColor = Color.WHITE;
                break;
            case 4:
                userColor = Color.BLUE;
                break;
            default:
                userColor = Color.RED;
                break;
        }
        // cost and price
        System.out.println("Enter the cost of Mug");
        userCost = sc.nextDouble();
        System.out.println("Enter the price of Mug");
        userPrice = sc.nextDouble();
        // choice
        if (choice==1){
            return new Mug("Plastic Mug",userCost,userPrice,userColor);
        }else if(choice==2){
            return new Mug("Glass Mug",userCost,userPrice,userColor);
        }else{
            return null;
        }

    }

    /**
     *
     * @return color
     */

    public Color getColor(){
        return color;
    }

    /**
     *
     * @return toString
     */
    @Override
    public String toString() {
        return "\nMug - { Cool looking " + super.toString() + "\n            {Color: " + getColor() + "}" +'\n';
    }
}

