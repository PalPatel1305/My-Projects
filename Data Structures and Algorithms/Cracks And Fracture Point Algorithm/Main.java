import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    /**
     * Assignment 1 : Calculating FracturePoints and CrackPoints for IceSheet
     * @param args
     * @author Pal Patel, 000865048
     */
    public static void main(String[] args) {
        // Reading file and getting each Ice-sheet
        try {
            //File to be read.
            final String fileName = "src/ICESHEETS.txt";
            File file = new File(fileName);
            Scanner fileInput = new Scanner(file);
            // getting Icesheets.
            int iceSheets = fileInput.nextInt();
            int iceSheetNum=0, fracturePointsCount = 0, TotalFP = 0, Totalcracks = 0, MaxFP = 0, MaxFPSheetNum = 0;
            double fraction;
            String result="";
            //Storing data in "data" array.
            do {
                int Rows = fileInput.nextInt();
                int Columns = fileInput.nextInt();
                int[][] data = new int[Rows][Columns];
                for (int i = 0; i < Rows; i++) {
                    for (int j = 0; j < Columns; j++) {
                        data[i][j] = fileInput.nextInt();
                    }
                }

                //Calling function CalculateFracturePoints
                FracturePoints[] fracturePointsArray = calculateFracturePoints(data);
                //Calling function CalculateCracks
                FracturePoints[] crackResults = calculateCracks(data, fracturePointsArray);

                if(crackResults.length!=0){
                    result+="\nSheet " + iceSheetNum  + " has: \n";
                }
                for (FracturePoints fracturePoint : crackResults) {
                    result+= "Crack Detected: " + fracturePoint + "\n";

                }

                
                fracturePointsCount = fracturePointsArray.length;
                Totalcracks += crackResults.length;

                TotalFP += fracturePointsCount;
                // Caluculating which sheet has maximum fracturePoints
                if (fracturePointsCount > MaxFP) {
                    MaxFP = fracturePointsCount;
                    MaxFPSheetNum = iceSheetNum;
                }
                iceSheetNum++;
            } while (fileInput.hasNext() && iceSheetNum < iceSheets);

            fileInput.close();
            System.out.println("Part A Results: " + "\n\n" +"Total Fracture Points: " + TotalFP);
            //IceSheet count starts from 0 so max is 4. But in actual data it should be 5th Icesheet.
            System.out.println("Maximum fracture points are in Sheet " + MaxFPSheetNum + " which are " + MaxFP);
            System.out.println(" \n\n"+ "Part B Results: \nFracturePoints that can lead to a crack are:  ");
            System.out.println(result);
            System.out.println("Totalcracks Found: " + Totalcracks);
            fraction = (double) Totalcracks/TotalFP;
            System.out.println("Fraction of Fracture points will lead to a Crack : "+String.format("%.3f", fraction));

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Calculate FracturePoints of an IceSheet.
     * @param data Single IceSheet data
     * @return Array of FracturePoints which will store the object of fracturePoint with its location(x,y) and value.
     */
    public static FracturePoints[] calculateFracturePoints(int[][] data) {
        int Rows = data.length;
        int Cols= data[0].length; // because of Single sheet
        int count = 0;
        FracturePoints[] fracturePoints = new FracturePoints[Rows * Cols];
        for (int i = 0; i < Rows; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] <= 200 && data[i][j] % 50 == 0) {
                    fracturePoints[count++] = new FracturePoints(i, j, data[i][j]);
                }
            }
        }
        return Arrays.copyOf(fracturePoints, count);
    }

    /**
     * Calculate fracture points that can lead to crack.
     * @param data Single IceSheet data
     * @param fracturePoints Fracturepoints of that IceSheet data
     * @return Array of FracturePoints which can lead to crack.
     */

    public static FracturePoints[] calculateCracks(int[][] data, FracturePoints[] fracturePoints) {
        FracturePoints[] fractureCrackPoints = new FracturePoints[fracturePoints.length];
        int count = 0;
        if (data.length != 1) {
            for (FracturePoints fracturePoint : fracturePoints) {
                // getting x and y coordinates of fracturePoint
                int fracturePointX = fracturePoint.getRow();
                int fracturePointY = fracturePoint.getColumn();
                
                boolean crackPointFound = false;
                //row loop
                for (int i = -1; i <= 1; i++) {
                    //For the exit of the row loop if one crackpoint found.
                    if (crackPointFound) {
                        break;
                    }
                    //Column loop
                    for (int j = -1; j <= 1; j++) {

                        // if the coordinates lead to coordinates of fracture point itself
                        if (i == 0 && j == 0)continue;

                            int neighborValueX = fracturePointX + i;
                            int neighborValueY = fracturePointY + j;

                            // For edge and corner values, if it gets out of bound, skipping that index.
                            if(neighborValueX < 0 || neighborValueX >= data.length || neighborValueY < 0 || neighborValueY >= data[0].length )continue;
                            // accessing the value
                            int neighborValue = data[neighborValueX][neighborValueY];
                            if (neighborValue % 10 == 0) {
                                fractureCrackPoints[count++] = new FracturePoints(fracturePointX, fracturePointY, data[fracturePointX][fracturePointY]);
                                crackPointFound = true;
                                break;
                            }

                    }
                }
            }
        }
        return Arrays.copyOf(fractureCrackPoints, count);
    }
}