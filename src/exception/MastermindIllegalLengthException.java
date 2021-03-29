package exception;

/**
 * This is an exception class for the MastermindIllegalColorException
 * @author Peter Vo
 */

@SuppressWarnings("serial")
public class MastermindIllegalLengthException extends Exception {
	
	/**
	 * @param message A string message
	 */
	public MastermindIllegalLengthException(String message) {
		super(message);
	}
}
