import java.util.Scanner;

/**
 * Implementation of a dice game with the help of array
 * It can roll the dice once and 10000 times and getting the histogram for it
 * A method to test the object
 *
 * @param
 * @author Pal Patel-000865048
 */
public class View {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("How many dice you want?");
        int totalDice = sc.nextInt(); //it will store total number of dice
        int[] sideArray = new int[totalDice]; // it will make an array for sides of each dice
        System.out.println("Enter the number of the sides of dice you want for each dice");
        //storing sides in the sideArray
        for (int i = 0; i < totalDice; i++) {
            int sides = sc.nextInt();
            sideArray[i] = sides;
        }
        /* User's choice */
        int choice;
        /* object for dice collection*/
        DiceCollection diceCollection = new DiceCollection(sideArray); //passing sideArray as a parameter
        System.out.println(diceCollection);
        do {
            System.out.println("1: roll once , 2: roll 100000 times, 3: quit ");
            choice = sc.nextInt();

            switch (choice) {
                case 1: /* if user choose roll once */
                    diceCollection.roll(); /* calling roll method */
                    System.out.println("After rolled-" + diceCollection);
                    break;
                case 2:  /* if user choose roll 100000 times */
                    int[] a = diceCollection.histogram(100000); //parameter of how many rolls it should do
                    for (int i = 0, j = diceCollection.getMinSum(); i < a.length; i++, j++) {
                        System.out.println(j + "       " + a[i]);
                    }
                    break;
                case 3:  /* if user choose 3 to quit */
                    System.out.println("Bye!!!");
                    break;
            }
        } while (choice != 3);//the loop will go on until the user choose 3
    }

}
