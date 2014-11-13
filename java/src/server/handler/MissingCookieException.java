package server.handler;

/**
 * An exception thrown when the user tries to do something without the proper cookies set.
 */
@SuppressWarnings("serial")
public class MissingCookieException extends Exception {
	/**
	 * Construct an exception with no specified message.
	 */
	public MissingCookieException() {}

	/**
	 * Construct an exception with a specified message.
	 * @param message a message containing details about the problem
	 */
	public MissingCookieException(String message) {
		super(message);
	}

	/**
	 * Construct an with another exception as its cause.
	 * @param cause the exception that triggered this exception
	 */
	public MissingCookieException(Throwable cause) {
		super(cause);
	}

	/**
	 * Create an exception with another exception as a cause and a
	 * specified message.
	 * @param message a message to add to the exception
	 * @param cause the exception that triggered this exception
	 */
	public MissingCookieException(String message, Throwable cause) {
		super(message, cause);
	}
}
