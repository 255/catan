package server.persistence;

/**
 * The persistence manager handles transactions with the persistence layer.
 * It also serves as an abstract factory for the database access objects.
 */
public interface IPersistenceManager {

    /**
     * Begins a transaction with the persistence layer.
     */
    public void startTransaction();

    /**
     * Ends a transaction with the persistence layer.
     */
    public void endTransaction();

    /**
     * Delete any stored data on disk.
     */
    public void clear();

    /**
     * Creates the Database Access Object that will handle users.
     * @return a users DAO
     */
    public IUsersDAO createUsersDAO();

    /**
     * Creates the Database Access Object that will handle games.
     * @return a games DAO
     */
    public IGamesDAO createGamesDAO();

    /**
     * Creates the Database Access Object that will handle commands.
     * @return a commands DAO
     */
    public ICommandsDAO createCommandsDAO();
}
