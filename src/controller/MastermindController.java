package controller;
import java.util.ArrayList;
import java.util.List;

import exception.MastermindIllegalColorException;
import exception.MastermindIllegalLengthException;
import model.MastermindModel;

/**
 * This class is serves as the controller for the MVC (Mode/Control/View). 
 * This class creates a MastermindController object that performs task with the player's
 * guesses. This class can check if the player's guess matches the solution. It can also
 * return statistics about the plyer's guess like the number of letters that are right 
 * colors that are in the right places and number of letters that are the right colors 
 * but are in the wrong places.
 * 
 * @author Peter Vo
 */

public class MastermindController {
	
	// Only these methods may be public - you may not create any additional 
	// public methods (and NO public fields)
	
	private MastermindModel model = new MastermindModel();
	private List<String> colors;
	private String solution;
	
	public MastermindController(MastermindModel model) {
		
    	colors = new ArrayList<String>();
    	
    	// Add all the letters that represent the 6 colors in the list
    	colors.add("r"); colors.add("o"); colors.add("y"); 
    	colors.add("g"); colors.add("b");colors.add("p");
		this.model = model;
		this.solution = "";
    	for(int i = 0; i < 4; i++) {
    		char color = model.getColorAt(i); 
    		this.solution += color;
    	}
	}

    /**
     * This method takes in a string and compares it to the solution, then it returns 
     * true if the guess matches the solution and false if it doesn't
     * 
     * @param guess The player's guess that is a string
     * 
	 * @throws MastermindIllegalColorException when user's guess contain invalid color(s)
	 * @throws MastermindIllegalLengthException when user's guess is not 4 letter long
	 * 
     * @return true if the guess matches the solution and false if it doesn't 
     */
	public boolean isCorrect(String guess) throws MastermindIllegalLengthException, 
    MastermindIllegalColorException {
    	checkException(guess);

    	int count = getRightColorRightPlace(guess); 
    	if(count == 4) {
    		return true;
    	} else {
        	return false; 
    	}
    }

    /**
     * This method takes in a string and compares it to the solution, then it counts 
     * and returns the number of letters that are the right color in the right places
     * 
     * @param guess The player's guess that is a string
     * 
	 * @throws MastermindIllegalColorException when user's guess contain invalid color(s)
	 * @throws MastermindIllegalLengthException when user's guess is not 4 letter long
	 * 
     * @return the number of letters that are the right color and in the right places
     */
    public int getRightColorRightPlace(String guess) throws MastermindIllegalLengthException, 
    MastermindIllegalColorException {  
    	checkException(guess);
    	int count = 0;
		for (int i = 0; i < 4; i++) {
			// If the chars match then the count will be incremented by 1
			if (model.getColorAt(i) == guess.charAt(i)) {
				count += 1;
			}
		}
		return count;
    }

    /**
     * This method takes in a string and compares it to the solution, then it counts 
     * and returns the number of letters that are the right colors but are in the 
     * wrong places
     * 
     * @param guess The player's guess that is a string
     * 
	 * @throws MastermindIllegalColorException when user's guess contain invalid color(s)
	 * @throws MastermindIllegalLengthException when user's guess is not 4 letter long
     * 
     * @return  the number of letters that are the right color but are in the wrong places
     */
    public int getRightColorWrongPlace(String guess) throws MastermindIllegalLengthException, 
    MastermindIllegalColorException {
    	checkException(guess);
    	int count = 0;

    	// Converting the solution and the guess to arrays
    	char[] solutionArray = convertToArray(solution);
    	char[] guessArray =  convertToArray(guess);

    	// Preventing counting chars that are the right colors in the right places
    	for (int i = 0; i < 4; i++) {
    		if (solutionArray[i] == guessArray[i]) {
    			// Replacing the chars with different chars that don't represent any color
    			// in the game
    			solutionArray[i] = 'n';
    			guessArray[i] = 'm';
    		}
    	}
    	// Count the letters that are the right colors but are in the wrong places
    	for (int i = 0; i < 4; i++) {
    		for (int j = 0; j < 4; j++) {
    			if (solutionArray[i] == guessArray[j]) {
    				count += 1;
    				solutionArray[i] = 'n';
    				guessArray[j] = 'm';
    				break;
    			}
    		}
    	}

    	return count;
    }
    
    /**
     * This is a private method that takes in a string and convert the string into an array 
     * with each item in the array being a char in the string. 
     * 
     * @param str Any given string
     * @return  an array with the chars in the given string as items
     */
    private char[] convertToArray(String str) {
    	char[] arr = new char[4];
    	for(int i = 0; i < 4; i++) {
    		char color = str.charAt(i); 
    		arr[i] = color;
    	}
    	return arr;
    }
    
    /**
     * This is a private method checks if the user's guess is valid or not -
     * guess has to be 4 letter long and can only contain the specific given 
     * colors
     * 
     * @param guess The player's guess that is a string
     * 
	 * @throws MastermindIllegalColorException when user's guess contain invalid color(s)
	 * @throws MastermindIllegalLengthException when user's guess is not 4 letter long
     * 
     */
    private void checkException(String guess) throws MastermindIllegalLengthException, 
    MastermindIllegalColorException {
    	if (guess.length() == 4) {
    		for (int i = 0; i < 4; i++) {
    			if (colors.contains(guess.substring(i, i + 1))) {
    			} else {
    				throw new MastermindIllegalColorException(guess);
    			}
    		}
    	} else {
    		throw new MastermindIllegalLengthException(guess);
    	}

    }
}
