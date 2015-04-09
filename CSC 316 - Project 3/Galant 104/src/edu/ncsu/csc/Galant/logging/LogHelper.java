package edu.ncsu.csc.Galant.logging;

/**
 * Debug class for monitoring GALANT's actions
 * @author Michael Owoc
 */
public class LogHelper {

    private static final String INDENT_STRING = "..";
	private static LogHelper logHelper = null;
	private static boolean loggingEnabled = false;
    private static boolean savedState = loggingEnabled;
	
	private static int spaces = 0;
	public static LogHelper getInstance() {
		if (logHelper == null) {
			logHelper = new LogHelper();
		}
		
		return logHelper;
	}
	
	private LogHelper() {
	}

    public static void setEnabled( boolean enabled ) {
        savedState = loggingEnabled;
        loggingEnabled = enabled;
    }

    public static boolean isEnabled() {
        return loggingEnabled;
    }

    public static void restoreState() {
        loggingEnabled = savedState;
    }
	
	public static void logDebug(String msg) {
		if (loggingEnabled)
			System.out.println(spaceString() + msg);
	}
	
	public static void enterConstructor(Class<?> cls) {
		spaces++;
		
		if (loggingEnabled)
			System.out.println(spaceString() + "=> " + cls.getName() + "()");
	}
	
	public static void exitConstructor(Class<?> cls) {
		if (loggingEnabled)
			System.out.println(spaceString() + "<= " + cls.getName() + "()");
		
		spaces--;
	}
	
	public static void enterConstructor(Class<?> cls, String message) {
		spaces++;
		
		if (loggingEnabled)
			System.out.println(spaceString() + "=> " + cls.getName() + "(): " + message);
	}
	
	public static void exitConstructor(Class<?> cls, String message) {
		if (loggingEnabled)
			System.out.println(spaceString() + "<= " + cls.getName() + "(): " + message);
		
		spaces--;
	}
	
	public static void enterMethod(Class<?> cls, String methodName) {
		spaces++;
		
		if (loggingEnabled)
			System.out.println(spaceString() + "-> " + cls.getName() + "." + methodName);
	}
	
	public static void exitMethod(Class<?> cls, String methodName) {
		if (loggingEnabled)
			System.out.println(spaceString() + "<- " + cls.getName() + "." + methodName);
		
		spaces--;
	}
	
    public static void beginIndent() {
        spaces++;
    }

    public static void endIndent() {
        spaces--;
    }

	private static String spaceString() {
		String spaceStr = "";
		if (spaces > 0) {
			int spc = 0;
			while (spc < spaces) {
				spaceStr = spaceStr + INDENT_STRING;
				spc++;
			}
		}
		
		return spaceStr;
	}
}

//  [Last modified: 2015 03 26 at 20:42:57 GMT]
