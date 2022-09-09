import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

//Class Window
public class Window {
    // x and y coordinates and diameter for the circle to draw a window
    private double x, y, diameter;

    //constructor
    // x and y coordinates and diameter for the window

    public Window(double x, double y, double diameter) {
        this.x = x;
        this.y = y;
        this.diameter = diameter;
    }

    /*draw method
     * @param graphic context gc
     */
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BROWN);
        gc.fillOval(x, y, diameter, diameter);

    }
}
