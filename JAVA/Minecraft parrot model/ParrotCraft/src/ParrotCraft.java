import java.util.Scanner;

public class ParrotCraft {

    /**
     * A method to test the object
     * @param args
     *
     **/

    public static void main(String[] args) {

        /*
          calling constructor to set the name and seeds
          @param args
         * **/

        Parrot p1 = new Parrot("Julius", 0.1);
        Parrot p2 = new Parrot("Nancy", 0.6);
        Parrot p3 = new Parrot("Johnette", 1.0);

        /*
          whats the user choice for methods and which parrot they want to do that
           */

        int user_choice, user_parrot;

        Scanner sc = new Scanner(System.in);

        do {
            /* Displaying the toString */
            System.out.println();
            System.out.println("1. " + p1);
            System.out.println("2. " + p2);
            System.out.println("3. " + p3);
            System.out.println();
            System.out.println("1.Feed 2.Command 3.Play 4.Hit 5.Quit");
            user_choice = sc.nextInt(); /* storing the choice of user */

            if (user_choice == 5) /* if user quits  */ {
                System.out.println("Thanks for playing!");
                break;
            }
            System.out.println("Which Parrot");
            user_parrot = sc.nextInt(); /* storing the parrot of user */

            switch (user_choice) {    /* switch case for user choice */
                case 1: /* for feed  */
                    System.out.println("seed or cookie(all in small letters)"); /* asking the choice of user */
                    Scanner sc1 = new Scanner(System.in);
                    String food = sc1.nextLine();  /* storing the choice of  food of user */
                    if (user_parrot == 1) {
                        p1.feed(food);
                    } else if (user_parrot == 2) {
                        p2.feed(food);
                    } else if (user_parrot == 3) {
                        p3.feed(food);
                    } else {
                        System.out.println("Wrong input");
                    }
                    break;

                case 2: /* for command */
                    if (user_parrot == 1) {
                        p1.command();
                    } else if (user_parrot == 2) {
                        p2.command();
                    } else if (user_parrot == 3) {
                        p3.command();
                    } else {
                        System.out.println("Wrong input");

                    }
                    break;

                case 3: /* for play*/
                    System.out.println("Which another parrot you want to play");
                    Scanner gc = new Scanner(System.in);
                    int playwith = gc.nextInt(); /* storing the choice of user of which parrot they want to play with */
                    /* checking which parrot and parrot have to play*/
                    if (user_parrot == 1) {
                        if (playwith == 2) {
                            System.out.println(p1.play(p2));
                        }
                        if (playwith == 3) {
                            System.out.println(p1.play(p3));
                        }

                    } else if (user_parrot == 2) {
                        if (playwith == 1) {
                            System.out.println(p2.play(p1));
                        }
                        if (playwith == 3) {
                            System.out.println(p2.play(p3));
                        }


                    } else if (user_parrot == 3) {
                        if (playwith == 1) {
                            System.out.println(p3.play(p1));
                        }
                        if (playwith == 2) {
                            System.out.println(p3.play(p2));
                        }

                    } else {
                        System.out.println("Wrong input");
                    }

                    break;

                case 4: /* for hit method  */
                    if (user_parrot == 1) {
                        p1.hit();
                    } else if (user_parrot == 2) {
                        p2.hit();
                    } else if (user_parrot == 3) {
                        p3.hit();
                    } else {
                        System.out.println("Wrong input");

                    }
                    break;
            }
        }
        while (user_choice != 5);

    }
}
