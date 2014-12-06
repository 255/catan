package server.persistence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A persistence manager that stores data in folders on disk.
 */
public class FolderPersistenceManager extends AbstractPersistenceManager {
    protected FolderPersistenceManager(int commandsBetweenCheckpoints) throws PersistenceException {
        super(commandsBetweenCheckpoints);
    }

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
    public IGamesDAO createGamesDAO() throws PersistenceException {
        return new FolderGamesDAO(this);
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
