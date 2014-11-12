package server.command;

/**
 * An exception thrown when a user tries to cheat!
 * Or for some reason an illegal move was requested by a client.
 */
@SuppressWarnings("serial")
public class IllegalCommandException extends Exception {
	/**
	 * Construct a ServerException with no specified message.
	 */
	IllegalCommandException() {}

	/**
	 * Construct an exception with a specified message.
	 * @param message a message containing details about the problem
	 */
	IllegalCommandException(String message) {
		super(message);
	}

	/**
	 * Construct a ServerException with another exception as its cause.
	 * @param cause the exception that triggered this exception
	 */
	IllegalCommandException(Throwable cause) {
		super(cause);
	}

	/**
	 * Create an exception with another exception as a cause and a
	 * specified message.
	 * @param message a message to add to the exception
	 * @param cause the exception that triggered this exception
	 */
	IllegalCommandException(String message, Throwable cause) {
		super(message, cause);
	}
}
