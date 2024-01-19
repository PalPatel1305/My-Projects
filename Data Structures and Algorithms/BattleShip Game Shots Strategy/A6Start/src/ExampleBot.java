import battleship.*;
import battleship.CellState;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Arrays;

public class ExampleBot implements BattleShipBot {
    private int gameSize;
    private BattleShip2 battleShip;
    private Random random;

    private CellState[][] boardStatus;
    private int[][] probabilities;
    private int[] ships;

    private boolean hitsSkewProbabilities = true;
    private int skewFactor = 3;

    @Override
    public void initialize(BattleShip2 battleship) {
        battleShip = battleship;
        gameSize = battleship.BOARD_SIZE;
        random = new Random();

        probabilities = new int[gameSize][gameSize];
        ships = battleship.getShipSizes();

        boardStatus = new CellState[gameSize][gameSize];
        for (int i = 0; i < gameSize; i++) {
            for (int j = 0; j < gameSize; j++) {
                boardStatus[i][j] = CellState.Empty;
            }
        }

        for (int sn = 0; sn < ships.length; sn++) {
            int[][] temp = new int[gameSize][gameSize];
            for (int i = 0; i < gameSize; i++) {
                for (int j = 0; j < gameSize; j++) {
                    temp[i][j] = min(i + 1, ships[sn], gameSize - i) + min(j + 1, ships[sn], gameSize - j);
                }
            }
            probabilities = add2DArrays(probabilities, temp);
        }
    }

    @Override
    public String getAuthors() {
        return "Mikulkumar Patel, Jeet Alpesh Patel";
    }

    @Override
    public void fireShot() {
        Point shot = getBestUnplayedShot();
        boolean hit = battleShip.shoot(shot);

        if (hit) {
            boardStatus[shot.x][shot.y] = CellState.Hit;
            calculateProbabilities();
        } else {
            boardStatus[shot.x][shot.y] = CellState.Miss;

        }


//        printBoard();
//        System.out.println();
    }

    private void calculateProbabilities() {
        ArrayList<Point> hits = new ArrayList<>();

        for (int y = 0; y < gameSize; y++) {
            for (int x = 0; x < gameSize; x++) {
                probabilities[y][x] = 0;
                if (hitsSkewProbabilities && boardStatus[x][y] == CellState.Hit) {
                    hits.add(new Point(x, y));
                }
            }
        }

        for (int i = 0; i < ships.length; i++) {
            for (int x = 0; x < gameSize; x++) {
                for (int y = 0; y < gameSize; y++) {
                    if (shipCanOccupyPosition(CellState.Miss, new Point(x, y), ships[i], false)) {
                        decreaseProbability(new Point(x, y), ships[i], false);
                    }
                    if (shipCanOccupyPosition(CellState.Miss, new Point(x, y), ships[i], true)) {
                        decreaseProbability(new Point(x, y), ships[i], true);
                    }
                }
            }
        }

        if (hitsSkewProbabilities) {
            skewProbabilityOfNearbyPoints(hits);
        }
    }

    private void increaseProbability(Point pos, int shipSize, boolean vertical) {
        int x = pos.x, y = pos.y, z = (vertical ? x : y), end = z + shipSize - 1;

        for (int i = z; i <= end; i++) {
            if (vertical)
                probabilities[i][y]++;
            else
                probabilities[x][i]++;
        }
    }
    private void adjustProbabilitiesForMiss(Point shot) {
        int radius = 2; // Define the radius to adjust (2 columns and 2 rows in all directions)
        int minX = Math.max(0, shot.x - radius);
        int maxX = Math.min(probabilities.length - 1, shot.x + radius);
        int minY = Math.max(0, shot.y - radius);
        int maxY = Math.min(probabilities[0].length - 1, shot.y + radius);

        for (int i = minX; i <= maxX; i++) {
            for (int j = minY; j <= maxY; j++) {
                probabilities[i][j] -= 1;
            }
        }
    }

    private void decreaseProbability(Point pos, int shipSize, boolean vertical) {
        int x = pos.x, y = pos.y, z = (vertical ? x : y), end = z + shipSize - 1;

        // Decrease probabilities for ship positions
        for (int i = z; i <= end; i++) {
            if (vertical && i < gameSize && probabilities[i][y] > 0)
                probabilities[i][y]--;
            else if (!vertical && i < gameSize && probabilities[x][i] > 0)
                probabilities[x][i]--;
        }

        // Decrease probabilities for surrounding area
        for (int i = z - 1; i <= end + 1; i++) {
            if (vertical) {
                if (i >= 0 && i < gameSize && y - 1 >= 0 && y - 1 < gameSize && probabilities[i][y - 1] > 0)
                    probabilities[i][y - 1]--;
                if (i >= 0 && i < gameSize && y + 1 >= 0 && y + 1 < gameSize && probabilities[i][y + 1] > 0)
                    probabilities[i][y + 1]--;
            } else {
                if (x - 1 >= 0 && x - 1 < gameSize && i >= 0 && i < gameSize && probabilities[x - 1][i] > 0)
                    probabilities[x - 1][i]--;
                if (x + 1 >= 0 && x + 1 < gameSize && i >= 0 && i < gameSize && probabilities[x + 1][i] > 0)
                    probabilities[x + 1][i]--;
            }
        }
    }

    private boolean isExists(ArrayList<Point> list, Point p) {
        for (Point point : list) {
            if (point.x == p.x && point.y == p.y)
                return true;
        }
        return false;
    }

    private void skewProbabilityOfNearbyPoints(ArrayList<Point> toSkew) {
        ArrayList<Point> uniques = new ArrayList<>();
        uniques.addAll(toSkew);

        for (Point point : toSkew) {
            for (Point adjPoint : getAdjacentPositions(point)) {
                if (!isExists(uniques, adjPoint))
                    uniques.add(adjPoint);
            }
        }

        for (Point p : uniques) {
            probabilities[p.x][p.y] *= skewFactor;
        }
    }

    private ArrayList<Point> getAdjacentPositions(Point pos) {
        int x = pos.x, y = pos.y;
        ArrayList<Point> adj = new ArrayList<>();

        if (y + 1 < gameSize) adj.add(new Point(x, y + 1));
        if (y - 1 >= 0) adj.add(new Point(x, y - 1));
        if (x + 1 < gameSize) adj.add(new Point(x + 1, y));
        if (x - 1 >= 0) adj.add(new Point(x - 1, y));

        return adj;
    }

    private Point getBestUnplayedShot() {
        int bestProb = 0;
        Point bestPos = null;

        for (int i = 0; i < gameSize; i++) {
            for (int j = 0; j < gameSize; j++) {
                if (boardStatus[i][j] == CellState.Empty && probabilities[i][j] > bestProb) {
                    bestProb = probabilities[i][j];
                    bestPos = new Point(i, j);
                }
            }
        }

        return bestPos;
    }

    private boolean shipCanOccupyPosition(CellState rejectionStates, Point pos, int shipSize, boolean vertical) {
        int x = pos.x, y = pos.y, z = (vertical ? x : y), end = z + shipSize - 1;

        if (end > gameSize - 1) return false;

        for (int i = z; i <= end; i++) {
            CellState state = (vertical ? boardStatus[i][y] : boardStatus[x][i]);
            if (state == rejectionStates)
                return false;


        }
        return true;
    }

    private static int[][] add2DArrays(int[][] a, int[][] b) {
        int n = a.length;
        int[][] ab = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                ab[i][j] = a[i][j] + b[i][j];
            }
        }
        return ab;
    }

    private int min(int x, int y, int z) {
        int[] arr = {x, y, z};
        Arrays.sort(arr);
        return arr[0];
    }
    public void printBoard() {
        for (int y = 0; y < gameSize; y++) {
            for (int x = 0; x < gameSize; x++) {
                switch (boardStatus[x][y]) {
                    case Hit:
                        System.out.print("X "); // Print 'X' for hits
                        break;
                    case Miss:
                        System.out.print("O "); // Print 'O' for misses
                        break;
                    default:
                        System.out.print("~ "); // Print '~' for unknown cells
                        break;
                }
            }
            System.out.println(); // Move to the next row after printing each row
        }
    }

}

