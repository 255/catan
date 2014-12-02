package server.persistence;

/**
 * Created by Spencer Weight - 12/1/2014.
 */
public interface IPersistenceManager {

    /**
     * Opens a connection to the persistence layer
     */
    public void startTransaction();

    /**
     * Closes the connection to the persistence layer
     */
    public void endTransaction();

    /**
     * Creates the Database Access Object that will handle user information
     */
    public void createUserDAO();

    /**
     * Creates the Database Access Object that will handle game information
     */
    public void createGamesDAO();
}
