import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.Scanner;


/**
 * This project is about Star constellation where user can give the coordinates
 * and it can draw the constellation for them.
 * It is created on 25-05-2022/25th May 2022
 *
 * @author Pal Patel-000865048
 */
public class StarConstellation extends Application {

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
        Canvas canvas = new Canvas(900, 800); // canvas Size in Pixels
        stage.setTitle("Star Constellation"); //  window title
        root.getChildren().add(canvas);
        stage.setScene(scene);
        GraphicsContext gc = canvas.getGraphicsContext2D();


        //content
        double randomStarX, randomStarY, width, height;  //        For generating random stars
        double usersXCordinate, usersYCordinate, lineX = 0, lineY = 0, constantX = 0, constantY = 0;// users x and y cordinates,line x and y for connecting the stars,constant value for connecting last and first star.
        int stars; //Number of stars user enter.
        String definedName; //constellation name given by user
        gc.setFill(Color.BLACK); // background black screen
        gc.fillRect(0, 0, 900, 800);

        System.out.println("Hie! I will draw a constellation for you. " +
                "Please Enter the number of stars you want to be in your amazing Constellation:");
        Scanner sc = new Scanner(System.in);
        stars = sc.nextInt();


        for (double iteration = 0; iteration <= 400; iteration++) { //generating random stars
            randomStarX = Math.random() * 900;
            randomStarY = Math.random() * 800;
            width = Math.random() * 4;
            height = Math.random() * 4;
            gc.setFill(Color.DARKGRAY);
            gc.fillOval(randomStarX, randomStarY, width, height);
        }
        //moon
        gc.setFill(Color.WHITESMOKE);
        gc.fillOval(690, 80, 50, 50);
        gc.setFill(Color.BLACK);
        gc.fillOval(710, 80, 50, 50);

        // for users cordinates input
        int i=1;
        while(i <=stars){
            System.out.println("Enter position of star for x less than 900 and y less than 800 : X and Y");
            usersXCordinate = sc.nextDouble();
            usersYCordinate = sc.nextDouble();

            //processing
            if (usersXCordinate > 900 || usersYCordinate > 800 || usersYCordinate<0 || usersXCordinate<0 ) { //if the cordinates are out of screen
                System.out.println("Opps! You exceeded the cordinates.Let's try once again(x<900,y<800)");
                i--;
            } else {
                gc.setFill(Color.WHITESMOKE); //drawing the users stars
                gc.fillOval(usersXCordinate, usersYCordinate, 14, 14);
            }

            //connecting stars

            if (i == 1 && usersXCordinate < 900 && usersYCordinate < 800 && usersXCordinate>0 && usersYCordinate>0){
                lineX = usersXCordinate;
                lineY = usersYCordinate;
                constantX = usersXCordinate;//storing/copying first x user value to join
                constantY = usersYCordinate;//storing/copying first y user value to join
            } else if (i <= stars && usersXCordinate < 900 && usersYCordinate < 800 && usersXCordinate>0 && usersYCordinate>0) {

                gc.setStroke(Color.GHOSTWHITE);
                gc.strokeLine(lineX + 7, lineY + 7, usersXCordinate + 7, usersYCordinate + 7);//we are adding 7 for getting the line from centre as half of width and height.
                lineX = usersXCordinate; //overwriting the value
                lineY = usersYCordinate;

                if (i == stars) { //connecting last and first star
                    gc.setStroke(Color.GHOSTWHITE);
                    gc.strokeLine(usersXCordinate + 7, usersYCordinate + 7, constantX + 7, constantY + 7);
                }
            }
            i++;
        }
        //output
        System.out.println(" Give beautiful name of your extraordinary constellation:"); //giving name
        Scanner name = new Scanner(System.in);
        definedName = name.nextLine();
        Font font1 = Font.font("Monospaced", 40); //formatting font
        gc.setFont(font1);
        gc.setStroke(Color.SNOW);
        gc.strokeText("" + definedName + "'s  Constellation", 350, 750);
        gc.strokeText("Designed by Pal Patel", 335, 780);


        stage.show();
    }
    //content finished
    /**
     * The actual main method that launches the app.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        launch(args);
    }
}
