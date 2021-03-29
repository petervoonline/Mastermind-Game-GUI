package exception;

/**
 * This is an exception class for the MastermindIllegalColorException
 * @author Peter Vo
 */

@SuppressWarnings("serial")
public class MastermindIllegalColorException extends Exception {
	
	/**
	 * @param message A string message
	 */
	public MastermindIllegalColorException(String message) {
		super(message);
	}
	
	
}
