package server.persistence;

/**
 * A persistence manager that uses a SQLite database.
 */
public class SQLitePersistenceManager implements IPersistenceManager {

    /**
     * Begins a transaction with the persistence layer.
     */
    public void startTransaction() {
        // TODO: implement
    }

    /**
     * Ends a transaction with the persistence layer.
     */
    public void endTransaction() {
        // TODO: implement
    }

    /**
     * Delete any stored data on disk.
     */
    public void clear() {
        // TODO: implement
    }

    /**
     * Creates the Database Access Object that will handle users.
     * @return a users DAO
     */
    public IUsersDAO createUsersDAO() {
        // TODO: implement
        return null;
    }

    /**
     * Creates the Database Access Object that will handle games.
     * @return a games DAO
     */
    public IGamesDAO createGamesDAO() {
        // TODO: implement
        return null;
    }

    /**
     * Creates the Database Access Object that will handle commands.
     * @return a commands DAO
     */
    public ICommandsDAO createCommandsDAO() {
        // TODO: implement
        return null;
    }
}
