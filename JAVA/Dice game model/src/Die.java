/**
 * Creating a die for diecollection and assigning each die their property like sides and current
 *
 * @author Pal Patel
 */

public class Die {
    /**
     * sides for the sides of dice
     * current for current side facing
     **/
    private int sides, current;

    /**
     * @param sides   giving the sides of each die
     * @param current giving the value of current side facing
     **/
    public Die(int sides, int current) {
        this.sides = sides;
        this.current = current;

    }

    /**
     * method  by which the dice is rolled
     **/
    public int roll() {

        return current = (int) (Math.random() * sides) + 1;// assigning the different value to the current
    }

    /**
     * toString
     **/
    @Override
    public String toString() {
        return "  d" + sides +
                " = " + current;
    }
}
