package model;
import exception.MastermindIllegalLengthException;
import exception.MastermindIllegalColorException;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

/**
 * This class is serves as the model for the MVC (Mode/Control/View). 
 * This class creates a MastermindModel object that creates a random 4 letter string
 * solution. Users can get access to the chars of the solution at a given index by using 
 * getColorAt(). 
 * 
 * @author Peter Vo
 */

public class MastermindModel {
	//private variable(s) to store the answer
	private String answer = "";
	private List<String> colors;

	// Only these methods may be public - you may not create any additional 
	// public methods (and NO public fields)
    public MastermindModel() { 
    	colors = new ArrayList<String>();
    	
    	// Add all the letters that represent the 6 colors in the list
    	colors.add("r"); colors.add("o"); colors.add("y"); 
    	colors.add("g"); colors.add("b");colors.add("p"); 
    	
    	// Create a randomly generated 4-letter string answer
    	Random rand = new Random(); 
    	for(int i = 0; i < 4; i++) {
    		String color = colors.get(rand.nextInt(colors.size())); 
    		answer += color;
    	}
    }
    
    /**
     * This method is a special constructor to allow us to use JUnit to test our model.
     * 
     * Instead of creating a random solution, it allows us to set the solution from a 
     * String parameter.
     * 
     * 
     * @param answer A string that represents the four color solution
     * 
	 * @throws MastermindIllegalColorException when user's guess contain invalid color(s)
	 * @throws MastermindIllegalLengthException when user's guess is not 4 letter long
     */
    public MastermindModel(String answer) throws MastermindIllegalColorException, MastermindIllegalLengthException {
    	colors = new ArrayList<String>();
    	
    	// Add all the letters that represent the 6 colors in the list
    	colors.add("r"); colors.add("o"); colors.add("y"); 
    	colors.add("g"); colors.add("b");colors.add("p"); 
    	
    	if (answer.length() == 4) {
    		for (int i = 0; i < 4; i++) {
    			if (colors.contains(answer.substring(i, i + 1))) {
    			} else {
    				throw new MastermindIllegalColorException(answer);
    			}
    		}
    	} else {
    		throw new MastermindIllegalLengthException(answer);
    	}
    	this.answer = answer;

    }

    /**
     * This method takes in an integer and return the char that is at that integer in the answer 
     * 
     * @param index An integer that represents an index in the answer
     * @return the char at the given index
     */
    public char getColorAt(int index) {
          /* Return color at position index as a char
           (first converted if stored as a number) */
    	char color = answer.charAt(index);
    	return color; 
    }
    
    // Create as many private methods as you like
}
