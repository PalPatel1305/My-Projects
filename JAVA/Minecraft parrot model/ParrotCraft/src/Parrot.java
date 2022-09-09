import java.util.Scanner;
/**
 * methods for parrot what the user want to do with which includes fly, tamed,dead,giving name and all
 * @author Pal Patel-000865048
 * **/
public class Parrot {
    /* parrot name */
    private String parrotName;
    /*
     * flying or sitting
     * tamed or untamed
     * alive or dead
     */
    private boolean flying, tamed, alive;
    /* hearts of parrot */
    private int heart;

    /* seeds owned by parrot */
    private double seeds;

    /* Constructor for getting the default value and mutable value */
    public Parrot(String parrotName, double seeds) {
        this.parrotName = parrotName;
        this.seeds = seeds;
        this.flying = true;
        this.tamed = false;
        this.alive = true;
        this.heart = 3;
    }

    /* tostring to display  */
    @Override
    public String toString() {
        String detail = parrotName + "  dead/alive:";
        if (alive) {
            detail += "alive" + "  tamed/untamed:";
        }                                                   /* alive or dead check */
        else {
            detail += "dead" + "  tamed/untamed:";
        }

        if (tamed) {
            detail += "tamed" + " flying/sitting:";
        }                                                    /* tamed or untamed check */
        else {
            detail += "untamed" + "  flying/sitting:";
        }

        if (flying) {
            detail += "flying";
        }                                                       /* flying or sitting check */
        else {
            detail += "sitting";
        }

        detail += " hearts: " + heart + " " + "Seeds:" + " " +
                seeds + "g";                                             /* hearts and seeds */

        return detail;

    }

    /**
     * feed method is used to feed the parrot
     *
     * @param food type of food
     * @return is like you feed them
     */
    public boolean feed(String food) {
        if (!alive) {
            System.out.println(" Can't feed. Parrot is Dead");
        } else {

            if (food.equals("seed")) {
                System.out.println("How many grams you want to feed?");
                Scanner gc = new Scanner(System.in);
                double amount = gc.nextDouble();
                if (amount <= 0) {
                    System.out.println("Error! You entered negative value. Please, Try again");
                } else {
                    seeds = seeds + amount;
                    /* the percentage amount to change the parrot tamed from untamed*/
                    if (((0.2 * amount >= Math.random()))) {
                        tamed = true;
                        System.out.println("You tamed " + parrotName);
                        if (heart == 2 || heart == 1) {
                            heart += 1;

                        }
                    }
                }
            } else if (food.equals("cookie")) {
                System.out.println("Oh! You made" +" " + parrotName +" Dead.");
                heart = 0;
                alive = false;

            }
            /* If user enters other than seed or cookie than an error msg will pop */
            else {
                System.out.println("You entered wrong food. Please feed with seed or cookie.");
            }
        }
        return true;
    }

    /**
     * command method will allow the user to make the parrot sitting or flying
     **/

    void command() {

        if (!alive) {
            System.out.println(parrotName + " is Dead");
        } else {
            /* the user can only command a tamed parrot */
            String state;  /* to store in what state the parrot is */
            if (tamed) {
                System.out.println("sitting or flying: ");
                Scanner d = new Scanner(System.in);
                state = d.nextLine();

                if (state.equals("sitting")) {
                    flying = false;
                } else if (state.equals("flying")) {
                    flying = true;
                } else {
                    System.out.println("You entered wrong state.Please, try again with sitting or flying");
                }

            }
            /* if parrot is untamed */
            else {
                flying = true;
                System.out.println("Parrot is untamed so You cannot command parrot");
            }
        }

    }

    /**
     * play method will allow the user to make two parrots play
     *
     * @param parrot is the parameter which is the different parrot with whom the original parrot is playing
     **/
    public String play(Parrot parrot) {

        /* if both parrot are tamed then they can play */
        if ((parrot.tamed) && (tamed)) {
            tamed = false;
            flying = true;
            parrot.tamed = false;
            parrot.flying = true;
            return (parrot.parrotName + " and " + parrotName + " is Untamed");
        }
        /* if both are untamed this will be displayed */
        else if ((!tamed) && (!parrot.tamed)) {

            return ("Both are Untamed");
        }
        /* if one of the parrot is untamed*/
        else {
            return "One of them is untamed";
        }
    }

    /**
     * hit method will allow the user to hit the parrot.
     **/
    void hit() {
        if (!alive) {
            System.out.println(" Can't hit. Parrot is Dead.");
        }
        /* if user hits we are decreasing the value of hearts*/
        else {
            if ((heart > 0) && (heart <= 3)) {
                heart -= 1;
                System.out.println("Ouch! You hit " + parrotName);
                tamed = false;
                flying = true;
            }
            /* if heart becomes zero then it becomes dead */
            if (heart == 0) {
                alive = false;
            }

        }
    }
}

