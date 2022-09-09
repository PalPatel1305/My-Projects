

/**
 * class TimOrder
 *
 * @author Pal Patel 000865048
 */

import java.util.Scanner;

public class TimsOrder {
    //    size of the order
    private int size;
    // name of the customer
    private String name;

    //array of timsProduct

    private static TimsProduct[] timsProduct;

    /**
     * Constructor
     * @param name
     * @param size
     */
    private TimsOrder(String name, int size) {
        this.name = name;
        this.size = size;
        timsProduct = new TimsProduct[size];
    }

    /**
     * to create the order and have a dialouge with customer
     * @return timorder order of the customer
     */
    public static TimsOrder create() {
        Scanner sc = new Scanner(System.in);
//        asking for name
        System.out.println("Enter Customer's Name:");
        String name = sc.nextLine();

        // amount of items customer wants to order
        System.out.println("How many items do you like to order?");
        // storing value of total items
        int totalProduct = sc.nextInt();

        // new object of Timsorder
        TimsOrder timsOrder = new TimsOrder(name, totalProduct);
        // taking order
        for (int i = 0; i < timsProduct.length; i++) {
            System.out.println("___What do you like to order? Enter only numbers___\n1-Donut \n2-Muffins \n3-Mug \n4-Caps");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    timsProduct[i] = Donut.create();
                    break;
                case 2:
                    timsProduct[i] = Muffins.create();
                    break;
                case 3:
                    timsProduct[i] = Mug.create();
                    break;
                case 4:
                    timsProduct[i] = Caps.create();
                    break;
            }

        }

        return timsOrder;
    }

    /**
     *
     * @return totalprice - for the order
     */

    public double getAmountDue() {
        double totalPrice = 0.0;

        for (TimsProduct product : timsProduct) {
            totalPrice += product.getRetailPrice();
        }
        return totalPrice;
    }

    /**
     *
     * @return toString
     */
    @Override
    public String toString() {
        String details = "\n\n****** Customer Order Detail ******\n" +"Customer Name:  "+name + "\n";
        for (TimsProduct product : timsProduct) {
            details += product.toString();
        }
        return details;

    }
}
