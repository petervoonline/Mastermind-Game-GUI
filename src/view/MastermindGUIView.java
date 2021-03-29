package view;

import java.util.ArrayList;
import java.util.List;
import controller.MastermindController;
import exception.MastermindIllegalColorException;
import exception.MastermindIllegalLengthException;
import model.MastermindModel;
import javafx.application.Application;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.HPos;
import javafx.geometry.Insets;

/**
 * This class serves as the view for the MVC (Mode/Control/View) and let user interacts with the game through GUI 
 * 
 * @author Peter Vo
 */

public class MastermindGUIView extends Application {

	private MastermindModel model = new MastermindModel(); //MastermindModel object
	private MastermindController controller = new MastermindController(model); //MastermindController object
	private static List<String> colors; //List of all the colors' initials in the order of {r,o,y,g,b,p}
	private static Button guessButton; //The guess button in the bottom GridPane
	private static Circle circle1; //The 1st circle in the bottom GridPane
	private static Circle circle2; //The 2nd circle in the bottom GridPane
	private static Circle circle3; //The 3rd circle in the bottom GridPane
	private static Circle circle4; //The 4th circle in the bottom GridPane
	private static int colorIndex1; //The current index of the circle1's color in the colors field 
	private static int colorIndex2; //The current index of the circle2's color in the colors field
	private static int colorIndex3; //The current index of the circle3's color in the colors field
	private static int colorIndex4; //The current index of the circle4's color in the colors field
	private static int guessNum = 1; // The number of time the players has guessed
	private static boolean gameStatus = true; //true if the game is still ongoing and false if it ended

	public static void main(String[] args) {
		colors = new ArrayList<String>();
		// Add all the letters that represent the 6 colors in the list
		colors.add("r"); colors.add("o"); colors.add("y"); 
		colors.add("g"); colors.add("b"); colors.add("p");
		//Initializing the colorIndex fields
		colorIndex1 = 0;
		colorIndex2 = 0;
		colorIndex3 = 0;
		colorIndex4 = 0;
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Mastermind");
		BorderPane pane = new BorderPane();		
		Scene scene = new Scene(pane, 400, 600);

		//Center VBox
		VBox vbox = new VBox(10);
		vbox.setBackground(new Background(new BackgroundFill(Color.TAN, new CornerRadii(0), Insets.EMPTY)));
		vbox.setPadding(new Insets(10));
		pane.setCenter(vbox);

		//Set up the circles in the bottom GridPane and their event handlers
		eventCirc1();
		eventCirc2();
		eventCirc3();
		eventCirc4();

		//Bottom GridPane
		GridPane gridBot = buildBottomGridPane();
		pane.setBottom(gridBot);
		
		//Event handler for the guess button in the bottom GridPane
		guessButton.setOnAction((event) -> { 
			//if the game ended
			if (gameStatus == false) {
				Alert a = new Alert(Alert.AlertType.INFORMATION);
				a.setTitle("Message");
				a.setContentText("The game has already ended");
				a.setHeaderText("INVALID ACTION");
				a.showAndWait();
				return;
			}
			
			//If the player didn't fill out all the circles with colors
			if (circle1.getFill() == Color.BLACK || circle2.getFill() == Color.BLACK || 
					circle3.getFill() == Color.BLACK || circle4.getFill() == Color.BLACK) {
				handleInvalidGuess();
				return;
			}
			//Getting the players guess
			String guess = "";
			guess += colors.get(colorIndex1 - 1) + colors.get(colorIndex2 - 1) 
			+ colors.get(colorIndex3 - 1) + colors.get(colorIndex4 - 1);

			try {
				// Check whether or not the input is correct (by asking the controller)
				if (controller.isCorrect(guess) == true) {
					//Display the winning guess
					displayGuessHistory(guess, vbox);
					results("win");
				} else if(guessNum == 10 && controller.isCorrect(guess) == false){ 
					// The game ends if the player guesses wrong in all 10 attempts  
					results("lose");
				} else if (gameStatus == true) {
					//Display the guess history after each incorrect guess
					displayGuessHistory(guess, vbox);
				}
				
			} catch (MastermindIllegalLengthException e) {
				e.printStackTrace();
			} catch (MastermindIllegalColorException e) {
				e.printStackTrace();
			}

			// update the number of attempts
			guessNum += 1; 
			reset();
		});

		//Set and show stage 
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * This function helps build the bottom GridPane with all the
	 * event handlers of the guess button and the four circles
	 * 
	 * @return the bottom GridPane
	 */
	private GridPane buildBottomGridPane() {
		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(10));
		gridPane.setStyle("-fx-background-color: #FFFAF0;");

		for (int col = 0; col < 5; col++) {
			ColumnConstraints column = new ColumnConstraints();
			column.setPercentWidth(20);
			column.setHalignment(HPos.CENTER);
			gridPane.getColumnConstraints().add(column);

		}
		gridPane.add(circle1, 0, 0);
		gridPane.add(circle2, 1, 0);
		gridPane.add(circle3, 2, 0);
		gridPane.add(circle4, 3, 0);

		guessButton = new Button("Guess");
		guessButton.setStyle("-fx-font-size:15");
		gridPane.add(guessButton, 4, 0);
		return gridPane;

	}

	/**
	 * This function sends different Alert based on the results is "win" or
	 * "lose" and it disables all the event handlers so that the players
	 * can't guess or change the colors of the circles anymore
	 * 
	 * @param result A String that shows whether the player won or lost
	 */
	private void results(String result) {
		Alert a = new Alert(Alert.AlertType.INFORMATION);
		a.setTitle("Message");
		if (result.equals("win")) {
			a.setContentText("You won!");
		} else {
			a.setContentText("You lost due to exceeding the guess limit\n" + "Correct answer is " + getSolution());
		}
		a.setHeaderText("Message");
		a.showAndWait();

		//Changing the game status to false
		gameStatus = false;
	}

	/**
	 * This functions displays the UI of the guess history after each incorrect guess.
	 * It builds GridPane that shows the current number of guesses, the guess itself, 
	 * helper statistics like "right color right place" and "right color wrong place"
	 * 
	 * @param guess the player's guess
	 * @param vbox The VBox object the is in the center
	 * 
	 * @throws MastermindIllegalLengthException if the length is over 4 letters long
	 * @throws MastermindIllegalColorException if an invalid color is used
	 */
	private void displayGuessHistory(String guess, VBox vbox) throws MastermindIllegalLengthException, MastermindIllegalColorException {
		GridPane tempGrid = new GridPane();
		for (int col = 0; col < 6; col++) {
			ColumnConstraints column = new ColumnConstraints();
			column.setPercentWidth(16.67);
			column.setHalignment(HPos.CENTER);
			tempGrid.getColumnConstraints().add(column);
		}
		//The current attempt
		Text text = new Text(String.valueOf(guessNum));
		text.setStyle("-fx-font: 20 arial;");
		tempGrid.add(text, 0, 0);
		//The colors of the circles that the player guessed
		tempGrid.add(new Circle(20, getColor(colorIndex1 - 1)), 1, 0);
		tempGrid.add(new Circle(20, getColor(colorIndex2 - 1)), 2, 0);
		tempGrid.add(new Circle(20, getColor(colorIndex3 - 1)), 3, 0);
		tempGrid.add(new Circle(20, getColor(colorIndex4 - 1)), 4, 0);

		//Feedback Helper GridPane
		GridPane feedbackGrid = buildFeedbackPane(guess);
		tempGrid.add(feedbackGrid, 5, 0);

		//Adding the guess history GridPane to the VBox
		vbox.getChildren().add(tempGrid);
	}

	/**
	 * This function build the feedback helper GridPane that shows helper statistics like 
	 * "right color right place" and "right color wrong place" in black and white circles, respectively
	 * 
	 * @param guess The player's guess
	 * 
	 * @return The feedback helper GriPane
	 * 
	 * @throws MastermindIllegalLengthException if the length is over 4 letters long
	 * @throws MastermindIllegalColorException if an invalid color is used
	 */
	private GridPane buildFeedbackPane(String guess) throws MastermindIllegalLengthException, MastermindIllegalColorException {
		GridPane pane = new GridPane();
		
		for (int col = 0; col < controller.getRightColorRightPlace(guess); col++) {
			pane.add(new Circle(5, Color.BLACK), col, 0);
		}
		
		for (int col = 0; col < controller.getRightColorWrongPlace(guess); col++) {
			pane.add(new Circle(5, Color.WHITE), col, 1);
		}
		
		pane.setPadding(new Insets(5));
		pane.setHgap(5);
		pane.setVgap(5);
		return pane;
	}

	/**
	 * This function gives the user an alert if the didn't fill out all the four circles with a 
	 * color and left some (or all) of the circle black
	 */
	private void handleInvalidGuess() {
		Alert a = new Alert(Alert.AlertType.INFORMATION);
		a.setTitle("Message");
		a.setContentText("Your guess contained an invalid color!\n" + "Please try again");
		a.setHeaderText("INVALID GUESS");
		//Reset to all black circle and have the players cycle through the colors, starting 
		//again with Red.
		reset();
		a.showAndWait();
	}

	/**
	 * This function returns the solution for the game
	 * 
	 * @return The solution for the game
	 */
	private String getSolution() {
		//Getting the solution to show the player in case the player loses
		String solution = "";
		for(int i = 0; i < 4; i++) {
			char color = model.getColorAt(i); 
			solution += color;
		}
		return solution;
	}

	/**
	 * This function reset to all black circle and have the players cycle through the colors, starting 
	 * again with Red
	 */
	private void reset() {
		circle1.setFill(Color.BLACK);
		circle2.setFill(Color.BLACK);
		circle3.setFill(Color.BLACK);
		circle4.setFill(Color.BLACK);
		colorIndex1 = 0;
		colorIndex2 = 0;
		colorIndex3 = 0;
		colorIndex4 = 0;
	}

	/**
	 * This function takes in a index and return the letter in the colors field
	 * that represents a specific color
	 * 
	 * @param index An integer from 0 to 5
	 * 
	 * @return A Color
	 */
	private Color getColor(int index) {
		if (index == 0 ) {
			return Color.RED;
		} else if(index == 1) {
			return Color.ORANGE;
		} else if(index == 2) {
			return Color.YELLOW;
		} else if(index == 3) {
			return Color.GREEN;
		} else if(index == 4) {
			return Color.BLUE;
		} else if (index == 5) {
			return Color.PURPLE;
		} 
		return null;
	}

	/**
	 * This function sets up the event handler of the 1st circle in the bottom GridPane
	 */
	private void eventCirc1() {
		circle1 = new Circle(20, Color.BLACK);

		//Event handler
		circle1.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> { 
			//if the game ended
			if (gameStatus == false) {
				circle4.setFill(Color.BLACK);
				return;
			}

			if (colorIndex1 > 5) {
				colorIndex1 = 0;
			} 
			if (colorIndex1 == 0) {
				circle1.setFill(Color.RED);
			} else if(colorIndex1 == 1) {
				circle1.setFill(Color.ORANGE);
			} else if(colorIndex1 == 2) {
				circle1.setFill(Color.YELLOW);
			} else if(colorIndex1 == 3) {
				circle1.setFill(Color.GREEN);
			} else if(colorIndex1 == 4) {
				circle1.setFill(Color.BLUE);
			} else if (colorIndex1 == 5) {
				circle1.setFill(Color.PURPLE);
			}
			colorIndex1++;
		});
	}

	/**
	 * This function sets up the event handler of the 2nd circle in the bottom GridPane
	 */
	private void eventCirc2() {
		circle2 = new Circle(20, Color.BLACK);

		//Event handler
		circle2.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> { 
			//if the game ended
			if (gameStatus == false) {
				circle4.setFill(Color.BLACK);
				return;
			}

			if (colorIndex2 > 5) {
				colorIndex2 = 0;
			} 

			if (colorIndex2 == 0) {
				circle2.setFill(Color.RED);
			} else if(colorIndex2 == 1) {
				circle2.setFill(Color.ORANGE);
			} else if(colorIndex2 == 2) {
				circle2.setFill(Color.YELLOW);
			} else if(colorIndex2 == 3) {
				circle2.setFill(Color.GREEN);
			} else if(colorIndex2 == 4) {
				circle2.setFill(Color.BLUE);
			} else if (colorIndex2 == 5) {
				circle2.setFill(Color.PURPLE);
			}
			colorIndex2++;
		});
	}

	/**
	 * This function sets up the event handler of the 3rd circle in the bottom GridPane
	 */
	private void eventCirc3() {
		circle3 = new Circle(20, Color.BLACK);

		//Event handler
		circle3.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> { 
			//if the game ended
			if (gameStatus == false) {
				circle4.setFill(Color.BLACK);
				return;
			}

			if (colorIndex3 > 5) {
				colorIndex3 = 0;
			} 
			if (colorIndex3 == 0) {
				circle3.setFill(Color.RED);
			} else if(colorIndex3 == 1) {
				circle3.setFill(Color.ORANGE);
			} else if(colorIndex3 == 2) {
				circle3.setFill(Color.YELLOW);
			} else if(colorIndex3 == 3) {
				circle3.setFill(Color.GREEN);
			} else if(colorIndex3 == 4) {
				circle3.setFill(Color.BLUE);
			} else if (colorIndex3 == 5) {
				circle3.setFill(Color.PURPLE);
			} 
			colorIndex3++;
		});
	}

	/**
	 * This function sets up the event handler of the 3rd circle in the bottom GridPane
	 */
	private void eventCirc4() {
		circle4 = new Circle(20, Color.BLACK);

		//Event handler
		circle4.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			//if the game ended
			if (gameStatus == false) {
				circle4.setFill(Color.BLACK);
				return;
			}
			if (colorIndex4 > 5) {
				colorIndex4 = 0;
			}
			if (colorIndex4 == 0) {
				circle4.setFill(Color.RED);
			} else if(colorIndex4 == 1) {
				circle4.setFill(Color.ORANGE);
			} else if(colorIndex4 == 2) {
				circle4.setFill(Color.YELLOW);
			} else if(colorIndex4 == 3) {
				circle4.setFill(Color.GREEN);
			} else if(colorIndex4 == 4) {
				circle4.setFill(Color.BLUE);
			} else if (colorIndex4 == 5) {
				circle4.setFill(Color.PURPLE);
			}
			colorIndex4++;
		});
	}
}
