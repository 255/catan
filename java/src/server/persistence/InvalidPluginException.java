package server.persistence;

/**
 * An exception thrown when something goes wrong while trying to load a plugin.
 */
@SuppressWarnings("serial")
public class InvalidPluginException extends Exception {
	/**
	 * Construct an exception with no specified message.
	 */
	InvalidPluginException() {}

	/**
	 * Construct an exception with a specified message.
	 * @param message a message containing details about the problem
	 */
	InvalidPluginException(String message) {
		super(message);
	}

	/**
	 * Construct an with another exception as its cause.
	 * @param cause the exception that triggered this exception
	 */
	InvalidPluginException(Throwable cause) {
		super(cause);
	}

	/**
	 * Create an exception with another exception as a cause and a
	 * specified message.
	 * @param message a message to add to the exception
	 * @param cause the exception that triggered this exception
	 */
	InvalidPluginException(String message, Throwable cause) {
		super(message, cause);
	}
}
