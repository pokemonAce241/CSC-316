/**
 * @author Gitesh Agarwal
 * 
 */
public class Warning extends Exception {

	public Warning() {
		this("this is a warning");
	}

	public Warning(String message) {
		super("Warning: " + message);
	}

	public Warning(String message, Throwable cause) {
		super(message, cause);
	}

}
