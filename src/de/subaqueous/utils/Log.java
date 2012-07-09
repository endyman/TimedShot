package de.subaqueous.utils;

/**
 * Custom logging Class utilizing android's build in log levels to set the
 * loglevel use "adb shell setprop log.tag.<TAG> <LEVEL>"
 * 
 * @author Nils Domrose
 * 
 */
public class Log {

	public static String LOG_TAG = "";
	public static Boolean LOG_LIFECYCLE = false;
	public static Boolean LOG_EVENTS = false;

	private Log() {

	}

	/**
	 * log lifecycle events as DEBUG messages
	 * 
	 * @param lclass
	 *            the log message prefix to be used - use the class name here
	 * @param message
	 *            message to be logged
	 */
	public static void lifecycle(String lclass, String message) {
		if (android.util.Log.isLoggable(LOG_TAG, android.util.Log.DEBUG)
				&& LOG_LIFECYCLE) {
			android.util.Log.d(LOG_TAG, "LIFECYCLE - " + lclass + ": "
					+ message);
		}
	}

	/**
	 * log events as DEBUG messages
	 * 
	 * @param lclass
	 *            the log message prefix to be used - use the class name here
	 * @param message
	 *            message to be logged
	 */
	public static void event(String lclass, String message) {
		if (android.util.Log.isLoggable(LOG_TAG, android.util.Log.DEBUG)
				&& LOG_EVENTS) {
			android.util.Log.d(LOG_TAG, "EVENT - " + lclass + ": " + message);
		}
	}

	/**
	 * log DEBUG messages
	 * 
	 * @param lclass
	 *            the log message prefix to be used - use the class name here
	 * @param message
	 *            message to be logged
	 */
	public static void d(String lclass, String message) {
		if (android.util.Log.isLoggable(LOG_TAG, android.util.Log.DEBUG)) {
			android.util.Log.d(LOG_TAG, lclass + ": " + message);
		}
	}

	/**
	 * log INFO messages
	 * 
	 * @param lclass
	 *            the log message prefix to be used - use the class name here
	 * @param message
	 *            message to be logged
	 */
	public static void i(String lclass, String message) {
		if (android.util.Log.isLoggable(LOG_TAG, android.util.Log.INFO)) {
			android.util.Log.i(LOG_TAG, lclass + ": " + message);
		}
	}

	/**
	 * log ERROR messages
	 * 
	 * @param lclass
	 *            the log message prefix to be used - use the class name here
	 * @param message
	 *            message to be logged
	 */
	public static void e(String lclass, String message) {
		if (android.util.Log.isLoggable(LOG_TAG, android.util.Log.ERROR)) {
			android.util.Log.e(LOG_TAG, lclass + ": " + message);
		}
	}

	/**
	 * log ERROR messages
	 * 
	 * @param lclass
	 *            the log message prefix to be used - use the class name here
	 * @param message
	 *            message to be logged
	 * @param exception
	 *            exception to be logged
	 */
	public static void e(String lclass, String message, Exception exception) {
		if (android.util.Log.isLoggable(LOG_TAG, android.util.Log.ERROR)) {
			android.util.Log.e(LOG_TAG, lclass + ": " + message, exception);
		}
	}

	/**
	 * log VERBOSE messages
	 * 
	 * @param lclass
	 *            the log message prefix to be used - use the class name here
	 * @param message
	 *            message to be logged
	 */
	public static void v(String lclass, String message) {
		if (android.util.Log.isLoggable(LOG_TAG, android.util.Log.VERBOSE)) {
			android.util.Log.v(LOG_TAG, lclass + ": " + message);
		}
	}

	/**
	 * log WARN messages
	 * 
	 * @param lclass
	 *            the log message prefix to be used - use the class name here
	 * @param message
	 *            message to be logged
	 */
	public static void w(String lclass, String message) {
		if (android.util.Log.isLoggable(LOG_TAG, android.util.Log.WARN)) {
			android.util.Log.w(LOG_TAG, lclass + ": " + message);
		}
	}

}
