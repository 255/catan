package server.persistence;

/**
 * The persistence manager handles transactions with the persistence layer.
 * It also serves as an abstract factory for the database access objects.
 */
public interface IPersistenceManager {
    /**
     * Set how many commands to go before updating the game's saved checkpoint.
     * @return commandsBetweenCheckpoints the number, which needs to be non-negative
     */
    public int getCommandsBetweenCheckpoints();

    /**
     * Begins a transaction with the persistence layer.
     */
    public void startTransaction() throws PersistenceException;

    /**
     * Ends a transaction with the persistence layer.
     */
    public void endTransaction(boolean commit) throws PersistenceException;

    /**
     * Delete any stored data on disk.
     */
    public void clear() throws PersistenceException;

    /**
     * Creates the Database Access Object that will handle users.
     * @return a users DAO
     */
    public IUsersDAO createUsersDAO() throws PersistenceException;

    /**
     * Creates the Database Access Object that will handle games.
     * @return a games DAO
     */
    public IGamesDAO createGamesDAO() throws PersistenceException;

    /**
     * Creates the Database Access Object that will handle commands.
     * @return a commands DAO
     */
    public ICommandsDAO createCommandsDAO() throws PersistenceException;
}
