/**
 * Creating diecollection that will report minimum sum, maximum sum,  currentside and their total
 *
 * @author Pal Patel
 */

public class DiceCollection {
    /**
     * an object for Die class
     **/
    private static Die[] dice;
    /**
     * current is for the current side face
     * maxSum is for maximum sum of sides for each dice
     * minSum is for minimum sum of sides for each dice
     * currentTotal is for the total of the current side facing
     **/
    private int current, maxSum, minSum, currentTotal;

    /**
     * toString
     **/
    @Override
    public String toString() {
        String diceInfo = "";
        for (Die d : dice) {  //enhance for loop for getting the info of each dice
            diceInfo += d;
        }
        return "DiceCollection:" +
                " " + diceInfo + " " + " " +
                "Maximum Sum: " + maxSum + " " +
                "Minimum Sum: " + minSum + " " +
                "CurrentTotal: " + currentTotal + " "
                ;
    }

    /**
     * @return minimum sum of the sides
     **/
    public int getMinSum() {
        return minSum;
    }

    /**
     * @param sideArray containes the sides for each dice
     **/
    public DiceCollection(int[] sideArray) {
        dice = new Die[sideArray.length]; //initializing the length of Die object
        for (int j = 0; j < sideArray.length; j++) {
            current = (int) (Math.random() * sideArray[j]) + 1;
            /**
             * @param sideArray as the side at index j
             * @param current is the current side facing
             **/
            dice[j] = new Die(sideArray[j], current);
            //maximum sum
            maxSum += sideArray[j];
            //current total
            currentTotal += current;

        }
        //minimum sum
        minSum = sideArray.length;


    }

    /**
     * @param rolls containes the value of how many times the dice should be rolled
     * @return the array for the frequency
     **/
    public int[] histogram(int rolls) {

        int[] frequency = new int[maxSum - minSum + 1]; //array for the frequency
        for (int roll = 0; roll < rolls; roll++) {
            roll(); //calling roll method
            int count = 0; //variable for getting the value at that index in the frequency array
            for (int i = minSum; i <= maxSum; i++) {
                if (currentTotal == i) { //if both are same then
                    frequency[count]++;
                }
                count++;
            }

        }
        return frequency;
    }

    /**
     * method  by which the dice is rolled
     **/

    public void roll() {
        currentTotal = 0;
        for (int i = 0; i < dice.length; i++) {
            currentTotal += dice[i].roll(); //calling the roll method in Die class

        }
    }
}
