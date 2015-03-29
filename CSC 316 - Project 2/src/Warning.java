/**
 * This is a warning exception class that is used whenever the program
 * incounters an invalid input,command, or action.
 * 
 * @author Gitesh Agarwal, Mohamad Saleh, Jason Benckert
 * 
 */
public class Warning extends Exception {

	/**
	 * The serial version of this class.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * default constructor that returns a default string.
	 */
	public Warning() {
		this("this is a warning");
	}

	/**
	 * A warning constructor that returns the specific message in the parameter.
	 * 
	 * @param message
	 *            The message to be returned when warning is thrown.
	 */
	public Warning(String message) {
		super("Warning: " + message);
	}

	/**
	 * A warning constructor that returns the specific message in the parameter
	 * and the cause for the warning to be thrown.
	 * 
	 * @param message
	 *            The message to be returned when warning is thrown.
	 * @param cause
	 *            The reason the warning is being thrown.
	 */
	public Warning(String message, Throwable cause) {
		super(message, cause);
	}

}
