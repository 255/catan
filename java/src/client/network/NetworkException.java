package client.network;

/**
 * An exception that is thrown when there are problems talking to the server.
 *
 * This class INTENTIONALLY uses package visibility for its constructors.
 * Only Network classes should be able to create NetworkExceptions.
 * @author Wyatt
 */
public class NetworkException extends Exception {
    /**
     * Construct a NetworkException with no specified message.
     */
    NetworkException() {}

    /**
     * Construct an exception with a specified message.
     * @param message a message containing details about the problem
     */
    NetworkException(String message) {
        super(message);
    }

    /**
     * Construct a NetworkException with another exception as its cause.
     * @param cause the exception that triggered this exception
     */
    NetworkException(Throwable cause) {
        super(cause);
    }

    /**
     * Construct a NetworkException with another exception as a cause and a
     * specified message.
     * @param message a message to add to the exception
     * @param cause the exception that triggered this exception
     */
    NetworkException(String message, Throwable cause) {
        super(message, cause);
    }
}
