import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * A game where user enter their and sides of the dice
 * and the game will roll the dice and compare it with user dice face and computers dice face
 * and then will print on the GUI.
 *
 * @author Pal Patel-000865048
 */
public class View extends Application {
    /* created game object of the DiceRoll class */
    private DiceRoll game;

    /* created text-fields playerField for the username and number-field for the dice sides users want to enter.*/
    private TextField playerField, numberField;

    /*created labels for the user count,tie, random-number and result.*/
    private Label outputLabel, numberFieldLabel, playerCount, computerCount, player, computer, tie;

    /* created buttons for roll and reset*/
    private Button roll, reset;

    /* for storing username in a variable*/
    private String name;

    /* for storing dice sides which is being entered by the user*/
    private int number;

    /**
     * buttonHandler function
     *
     * @param event is for the event handlers
     */

    private void buttonHandler(ActionEvent event) {

        name = playerField.getText();
        number = Integer.parseInt(numberField.getText());

        /* after it is entered it should not be edited until reset so->*/
        playerField.setEditable(false);
        numberField.setEditable(false);

        /*calling method from DiceRoll*/
        game.roll(number);

        /*setting text for GUI components*/
        player.setText(name + " : " + game.getFirstRandomNum());
        computer.setText("Computer : " + game.getSecondRandomNum());
        playerCount.setText("Win : " + game.getPlayercount());
        computerCount.setText("Win : " + game.getComputercount());
        tie.setText("Tie : " + game.getTied());

        /*checking result and setting text*/
        if (game.getWon() == 1) {
            outputLabel.setText("Hurray " + name + " won!!!");
        } else if (game.getWon() == 2) {
            outputLabel.setText("Opps! Computer Won!!");
        } else if (game.getWon() == 3) {
            outputLabel.setText("Its a Tie with " + game.getPlayercount() + " Ties");
        } else {
            outputLabel.setText("Results are Waiting!!");
        }

        /*the roll button should not visible until it reset*/
        if (game.getWon() == 1 || game.getWon() == 2 || game.getWon() == 3) {
            roll.setVisible(false);
        }
    }

    /**
     * reset method for event handlers
     *
     * @param event for event on click
     **/
    private void resethandler(ActionEvent event) {
        /* calling reset method from DiceRoll*/
        game.reset();

     /*the roll button should be visible after it get reset*/
        roll.setVisible(true);

        /*resetting text-fields*/
        player.setText(name + " : " + number);
        computer.setText("Computer : " + number);
        playerCount.setText("Win : " + game.getPlayercount());
        computerCount.setText("Win : " + game.getComputercount());
        tie.setText("Tie : " + game.getTied());

        /* after it is reset it should be edited so->*/
        playerField.setEditable(true);
        numberField.setEditable(true);
        outputLabel.setText("Results are Waiting-5 rounds!!");
    }

    /**
     * Creating GUI
     *
     * @param stage
     * @throws Exception
     */

    public void start(Stage stage) throws Exception {
        Pane root = new Pane();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Dice game");
        stage.setScene(scene);
        /* setting background to the root*/
        root.setStyle("-fx-background-color: #efe8d4");
        /* the model object*/
        game = new DiceRoll();
        /* 2. initializing the GUI components*/

        /*text-fields*/
        playerField = new TextField("Your Name Here!!!");
        numberField = new TextField("8");

        /*labels*/
        numberFieldLabel = new Label("How many sides you want on your dice??");
        outputLabel = new Label("Results are Waiting-5 rounds!!");
        player = new Label("User : " + number);
        computer = new Label("Computer : " + number);
        playerCount = new Label("Win : " + game.getPlayercount());
        computerCount = new Label("Win : " + game.getComputercount());
        tie = new Label("Tie : " + game.getTied());

        /*button*/
        roll = new Button("Ready to roll?");
        reset = new Button("Reset");


        /* Adding components to the root*/
        root.getChildren().addAll(playerField, numberField, playerCount, tie, player, computer, computerCount, outputLabel, numberFieldLabel, roll, reset);

        /*setting location and style for roll button*/
        roll.relocate(305, 550);
        roll.setStyle("-fx-border-color:black;-fx-background-color: peachpuff;-fx-alignment:center;-fx-font-weight:bolder");
        /* setting location and style for reset button*/
        reset.relocate(410, 550);
        reset.setStyle("-fx-border-color:black;-fx-background-color: peachpuff;-fx-alignment:center;-fx-font-weight:bolder");

        /* setting location, font style and style for tie Label.*/
        tie.relocate(360, 340);
        tie.setFont(new Font("Courier New", 20));
        tie.setStyle("-fx-font-weight:bolder;-fx-text-fill:brown");

        /* setting location, font style and style for playerCount Label.*/
        playerCount.relocate(150, 340);
        playerCount.setFont(new Font("Courier New", 20));
        playerCount.setStyle("-fx-font-weight:bolder");

        /* setting location, font style and style for computerCount Label.*/
        computerCount.relocate(560, 340);
        computerCount.setFont(new Font("Courier New", 20));
        computerCount.setStyle("-fx-font-weight:bolder");

        /* setting location, font style and style for player Label.*/
        player.relocate(125, 240);
        player.setFont(new Font("Courier New", 21));
        player.setStyle("-fx-font-weight:bolder;-fx-text-fill:brown");

        /* setting location, font style and style for computer Label.*/
        computer.relocate(540, 240);
        computer.setFont(new Font("Courier New", 21));
        computer.setStyle("-fx-font-weight:bolder;-fx-text-fill:brown");

        /* setting location, font style,size and style for outputLabel.*/
        outputLabel.relocate(145, 420);
        outputLabel.setFont(new Font("Courier New", 25));
        outputLabel.setStyle("-fx-wrap-text:true;-fx-background-color:white;-fx-text-fill:brown;-fx-alignment:center; -fx-border-color:black;-fx-cursor: crosshair;-fx-font-weight:bolder");
        outputLabel.setPrefSize(500, 80);

        /* setting location, font style and style for numberFeildlabel.*/
        numberFieldLabel.relocate(170, 95);
        numberFieldLabel.setFont(new Font("Courier New", 20));
        numberFieldLabel.setStyle("-fx-text-fill:black;-fx-font-weight:bolder");

        /* setting location, font style and style for numberFeild textfeild.*/
        numberField.setStyle("-fx-wrap-text:true;-fx-border-color:black;-fx-background-color: white;-fx-text-fill:brown;-fx-alignment:center;");
        numberField.relocate(200, 140);
        numberField.setPrefWidth(400);

        /* setting location, font style, size and style for playerFeild textfeild.*/
        playerField.setStyle("-fx-wrap-text:true;-fx-alignment:center;-fx-border-color:black;-fx-text-fill:brown");
        playerField.relocate(250, 20);
        playerField.setPrefSize(300, 60);
        playerField.setFont(new Font("System", 30));

        /* Event Handlers on button roll and reset*/
        roll.setOnAction(this::buttonHandler);
        reset.setOnAction(this::resethandler);


        /*Showing the stage*/
        stage.show();
    }

    /**
     * Make no changes here.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        launch(args);
    }
}
