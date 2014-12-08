package server.plugin;

import server.persistence.*;

/**
 * A persistence manager that stores data in folders on disk.
 */
public class FolderPersistenceManager extends AbstractPersistenceManager {
    public FolderPersistenceManager(int commandsBetweenCheckpoints) throws PersistenceException {
        super(commandsBetweenCheckpoints);
    }

    /**
     * Begins a transaction with the persistence layer.
     */
    public void startTransaction() {
        // no transactions for folder stuff
    }

    /**
     * Ends a transaction with the persistence layer.
     */
    public void endTransaction(boolean commit) {
        // no transactions for folder stuff
    }

    /**
     * Delete any stored data on disk.
     */
    public void clear() throws PersistenceException {
        new FolderGamesDAO(this).clear();
        new FolderUsersDAO(this).clear();
        new FolderCommandsDAO(this).clear();
    }

    /**
     * Creates the Database Access Object that will handle users.
     * @return a users DAO
     */
    public IUsersDAO createUsersDAO() throws PersistenceException {
        return new FolderUsersDAO(this);
    }

    /**
     * Creates the Database Access Object that will handle games.
     * @return a games DAO
     */
    public IGamesDAO createGamesDAO() throws PersistenceException {
        return new FolderGamesDAO(this);
    }

    /**
     * Creates the Database Access Object that will handle commands.
     * @return a commands DAO
     */
    public ICommandsDAO createCommandsDAO() throws PersistenceException {
        return new FolderCommandsDAO(this);
    }

}
