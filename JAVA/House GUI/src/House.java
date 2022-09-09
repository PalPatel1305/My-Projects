import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

// CLass House
public class House {
    // x and y is the house coordinates and size is size of the house
    private double x, y, size;
    // occupants for each house
    private int occupants;
    // color for the house
    private Color color;

    // door object for Door
    private Door door;

    //window object foe Window
    private Window window;

    // House constructor x and y coordinates and color is the color
    public House(double x, double y, double size, Color color) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
        //randomly generating occupants for each house
        this.occupants = (int) Math.floor(Math.random() * 10);
        // defining door and window
        door = new Door(x + 20, y + (size / 2), size / 2);
        window = new Window(x + 4, y + 4, 14);
    }

    // constructor for kings house
    public House(double x, double y) {
        this.x = x;
        this.y = y;
        this.size = 100;
        this.color = Color.SANDYBROWN;
        this.occupants = 1;
        //defining door and window objects
        door = new Door(x + 20, y + (size / 2), (size / 2));
        window = new Window(x + 4, y + 4, 14);
    }

    /*
     * draw method
     * @param  graphics draw
     */

    public void draw(GraphicsContext gc) {

        gc.setFill(color);
        gc.fillRect(x, y, size, size);
        door.draw(gc);
        window.draw(gc);

    }

    /*getter for occupants
     * @return occupants of the house
     */
    public int getOccupants() {
        return occupants;
    }

    // setter for occupants
    public void setOccupants(int occupants) {
        this.occupants = occupants;
    }

    /* getter for size
     * @return the size of the house
     */
    public double getSize() {
        return size;
    }
}
