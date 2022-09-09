import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.scene.paint.Color;


/**
 * This project defines two villages with a king house and some graphics drawing with the calculations.
 *
 * @author Pal Patel-000865048
 */
public class TwoVillages extends Application {

    /**
     * Start method (use this instead of main).
     *
     * @param stage The FX stage to draw on
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root);
        Canvas canvas = new Canvas(900, 600);
        stage.setTitle("FXGraphicsTemplate");
        root.getChildren().add(canvas);
        stage.setScene(scene);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BEIGE);
        gc.fillRect(0, 0, 900, 600);

        /* town1 and town2 are two objects for two different villages*/
        Village town1 = new Village("LockWood", 100, 100, Color.BURLYWOOD);
        Village town2 = new Village("ArkWood", 400, 400, Color.ROSYBROWN);
        /* kingPalace is the object for the kings palace*/
        House kingPalace = new House(750, 200);
        /* calling method draw to draw town1,town2 and kingPalace */
        /* @param gc for draw method */
        town1.draw(gc);
        town2.draw(gc);
        kingPalace.draw(gc);
        //show
        stage.show();
    }

    /**
     * The actual main method that launches the app.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        launch(args);
    }
}
