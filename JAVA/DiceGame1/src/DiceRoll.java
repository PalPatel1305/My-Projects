/**
 * DiceRoll model for the view which will implement all the logic
 *
 * @author Pal Patel-000865048
 */


public class DiceRoll {

    /*for tracking the tie, how many times' player won and computer won */
    private int tied = 0, playerCount, computerCount;

    /* 5 rounds */
    private int rounds = 0;

    /*storing two random numbers */
    private int firstRandomNum, secondRandomNum;


    /*who won will be stored */
    private int won;

    /**
     * @return playerCount how many time player won in each round
     */
    public int getPlayercount() {
        return playerCount;
    }

    /**
     * @return computerCount how many times computer won
     */
    public int getComputercount() {
        return computerCount;
    }

    /**
     * @return won -who won the game
     */
    public int getWon() {
        return won;
    }

    /**
     * @return tied how many times the game tied
     */
    public int getTied() {
        return tied;
    }

    /**
     * @return firstRandomNum is random number generated for player
     */
    public int getFirstRandomNum() {
        return firstRandomNum;
    }

    /**
     * @return secondRandomNum is random number generated for computer
     */
    public int getSecondRandomNum() {
        return secondRandomNum;
    }

    /*reset method will reset the game*/

    public void reset() {
        playerCount = 0;
        computerCount = 0;
        tied = 0;
        won = 0;
        rounds = 0;

    }

    /**
     * method roll will roll the dice and will decide the winner for each round
     *
     * @param sides indicates the number of sides each dice have
     */

    public void roll(int sides) {

        /*random numbers*/
        firstRandomNum = (int) Math.floor((Math.random() * sides + 1));
        secondRandomNum = (int) Math.floor((Math.random() * sides + 1));
        /*increasing rounds*/
        rounds++;

        if (rounds <= 5) {
            if (firstRandomNum != secondRandomNum) {

                if (firstRandomNum > secondRandomNum) {
                    playerCount++;

                } else {
                    computerCount++;
                }

            } else {
                tied++;
            }

            if (rounds == 5) {
                if (playerCount > computerCount) {
                    won = 1;
                } else if (playerCount < computerCount) {
                    won = 2;
                } else {
                    won = 3;
                }
            }
        }
    }

}