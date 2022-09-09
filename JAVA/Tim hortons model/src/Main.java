public class Main {
    /**
     * Author @Pal Patel 000865048, 10/8/22
     * Tim hortons app
     * @param args
     */
    public static void main(String[] args) {

        TimsOrder timsOrder = TimsOrder.create();
        System.out.println(timsOrder);
        System.out.printf("******  Total Price: $%.2f ******  \n", timsOrder.getAmountDue());

    }
}
