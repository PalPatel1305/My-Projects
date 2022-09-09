import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

// Class Door
public class Door {
    // x and y coordinates and height for the door
    private double x, y, height;

    //constructor x and y coordinates and height for the door
    public Door(double x, double y, double height) {
        this.x = x;
        this.y = y;
        this.height = height;

    }
    /*draw method
      @param draw graphics
     */

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BROWN);
        gc.fillRect(x, y, height / 2, height);

    }
}

