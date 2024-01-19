/**
 * Fracturepoints Class has the location(row,column) and value as the property.
 * @author Pal Patel 000865048
 */

public class FracturePoints {
    /**  Holding row value of the fracturePoint */
    private int row;
    /**  Holding column value of the fracturePoint */
    private int column;
    /**  Holding value of the fracturePoint */
    private int value;

    /**
     *
     * @param row rowNumber for that FracturePoint
     * @param column column Number for that FracturePoint
     * @param value value of that FracturePoint
     */
    public FracturePoints(int row, int column, int value) {
        this.row = row;
        this.column = column;
        this.value = value;
    }

    /**
     *
     * @return the row number of that fracturePoint.
     */
    public int getRow() {
        return row;
    }

    /**
     *
     * @return the column of that fracturePoint.
     */
    public int getColumn() {
        return column;
    }

    /**
     *
     * @return value of that fracturePoint.
     */
    public int getValue() {
        return value;
    }


    /**
     *
     * @return row, column and value of that fracturepoint.
     */
    @Override
    public String toString() {
        return  "FracturePoint:"+value+ " at [" + row + "][" + column +"]";
    }
}
