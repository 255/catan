package server;

/**
 * An exception thrown when there is a problem with the server.
 */
@SuppressWarnings("serial")
public class ServerException extends Exception {
	/**
	 * Construct a ServerException with no specified message.
	 */
	ServerException() {}

	/**
	 * Construct an exception with a specified message.
	 * @param message a message containing details about the problem
	 */
	ServerException(String message) {
		super(message);
	}

	/**
	 * Construct a ServerException with another exception as its cause.
	 * @param cause the exception that triggered this exception
	 */
	ServerException(Throwable cause) {
		super(cause);
	}

	/**
	 * Create an exception with another exception as a cause and a
	 * specified message.
	 * @param message a message to add to the exception
	 * @param cause the exception that triggered this exception
	 */
	ServerException(String message, Throwable cause) {
		super(message, cause);
	}
}
