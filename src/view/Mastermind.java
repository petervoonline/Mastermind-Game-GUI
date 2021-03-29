package view;

/**
 * This class is the main class of Mastermind and it launches different versions of the game based on the given argument
 * 
 * @author Peter Vo
 */

public class Mastermind {

	public static void main(String[] args) {
		
		if (args[0].equals("-text")) {
			MastermindTextView.main(args);
		} else if (args[0].equals("-window")) {
			MastermindGUIView.main(args);
		} 
	}
		
}
