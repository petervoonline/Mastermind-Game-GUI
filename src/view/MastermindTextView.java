package view;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import controller.MastermindController;
import exception.MastermindIllegalColorException;
import exception.MastermindIllegalLengthException;
import model.MastermindModel;

/**
 * This class serves as the view for the MVC (Mode/Control/View) and let tplay interact with the game through text forms
 * 
 * @author Peter Vo
 */

public class MastermindTextView {

	public static void main(String[] args) {
	
    	List<String> colors = new ArrayList<String>();
    	
    	// Add all the letters that represent the 6 colors in the list
    	colors.add("r"); colors.add("o"); colors.add("y"); 
    	colors.add("g"); colors.add("b"); colors.add("p");
		
		// This class represents the view, it should be how uses play the game
		System.out.println("Welcome to Mastermind!");
		System.out.println("Would you like to play?");
		Scanner sc = new Scanner(System.in);
		String action = sc.nextLine().toLowerCase();
		int guessNum = 1; // The number of time the players has guessed

		// If the the player types in anything other than no or yes
		while(!action.equals("no") && !action.equals("yes")) {
			System.out.println("\nInput must be YES or NO. Please try agan.");
			action = sc.nextLine().toLowerCase();
		}
		
		// If the player decides not to play from the start
		if(action.equals("no")) {
			System.out.println("\nYou should attempt to at least play next time");
			System.out.println("!Thank You For Not Playing!");
			System.exit(0);
		}
			
		while (action.equals("yes")) {
			String solution = "";
			MastermindModel model = new MastermindModel();
		 	for(int i = 0; i < 4; i++) {
	    		char color = model.getColorAt(i); 
	    		solution += color;
	    	}
	
		 	// Construct the controller, passing in the model
			MastermindController controller = new MastermindController(model);
			
			// Read up to ten user inputs
			while (guessNum <= 10) {
				System.out.println("Enter guess number " + guessNum + ": ");
				String guess = sc.nextLine().toLowerCase();
				boolean isCorrect;
				int RightColorRightPlaceStat;
				int RightColorWrongPlaceStat;
				
				// throw an exception if the user's guess is invalid
				while (true) {
					try {
						isCorrect = controller.isCorrect(guess);
						RightColorRightPlaceStat = controller.getRightColorRightPlace(guess);
						RightColorWrongPlaceStat = controller.getRightColorWrongPlace(guess);
						break;
					} catch (MastermindIllegalColorException e) {
						System.out.println("Incorrect color detected. Please try again:");
						guess = sc.nextLine().toLowerCase();
					} catch (MastermindIllegalLengthException e) {
						System.out.println("Incorrect length. Please try agian:");
						guess = sc.nextLine().toLowerCase();
					}
				}
				// Check whether or not the input is correct (by asking the controller)
				if (isCorrect == true) {
					System.out.println("Your answer was correct");
					System.out.println("!!! YOU ARE A MASTERMIND !!!");
					break;
				}
				
				// Display the relevant statistics (by asking the controller)
				System.out.println("Colors in the correct place: " + RightColorRightPlaceStat);
				System.out.println("Colors correct but in the wrong position: "+ RightColorWrongPlaceStat);
				System.out.println();
				guessNum += 1; // update the number of attempts
			}
			
			// The game ends if the player guesses wrong in all 10 attempts
		 	if(guessNum > 10){
		 		System.out.println("\nYou have exceed the guess limit");
		 		System.out.println("Correct answer: " + solution);
		 		System.out.println("~~ GAME OVER ~~");
		 	}

		 	// Letting the player play again
			System.out.println("\nWelcome to Mastermind!");
			System.out.println("Would you like to play?");
			action = sc.nextLine().toLowerCase();
			// If the the player types in anything other than no or yes
			while(!action.equals("no") && !action.equals("yes")) {
				System.out.println("\nInput must be YES or NO. Please try agan.");
				action = sc.nextLine().toLowerCase();
			}
			// If the player decide not to play again
			if(action.equals("no")) {
				sc.close();
				System.out.println("\n!Thank You For Playing!");
				System.exit(0);
			} else if(action.equals("yes")) {
				// Resetting the game
				guessNum = 1;
			}
		}
							
	}
}
