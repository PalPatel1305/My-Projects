import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/*
 Class Village */
public class Village {

    //x and y for the x and y coordinates from where the village will start
    // size is the size for village
    private double x, y, size = 0;

    //the color for village
    private Color color;

    // the name for the village
    private String name;
    // home1,home2 and home3 are objects of House
    private House home1, home2, home3;

    /* Constructor for village
     * name for the village-name
     * x and y is the coordinates
     * color is for the village color */
    public Village(String name, double x, double y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.name = name;

        // space1 and space2 creates the spaces between two houses.
        double space1 = Math.random() * 30;
        double space2 = Math.random() * 25;
        // defining home1, home2 and home3
        home1 = new House(x, y, 70, color);
        // x coordinate is x + the size for the home1 + the space between them.
        // y coordinate is y + the sizeof home1 - sizeof house ,to get them in a straight line.
        home2 = new House(x + home1.getSize() + space1 + 5, y + 30, 40, color);
        home3 = new House(x + home1.getSize() + home2.getSize() + space1 + space2 + 10, y + 20, 50, color);
        // village of the size would be the addition of all the lengths.
        // rounding off upto two decimals by some calculations.
        size = Math.round((home1.getSize() + home2.getSize() + home3.getSize() + space1 + space2 + 15) * 100) / 100.0;
    }

    public void draw(GraphicsContext gc) {
        /* calling method draw to draw home1,home2 and home3 */
        home1.draw(gc);
        home2.draw(gc);
        home3.draw(gc);
        // printing th statements for the details of the village
        gc.strokeText("" + name + " (Area " + size + "m)  (Population: " + (home1.getOccupants() + home2.getOccupants() + home3.getOccupants()) + ")", x, y + 85);


    }
}
