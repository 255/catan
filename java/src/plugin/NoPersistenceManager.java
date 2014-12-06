package plugin;

import server.persistence.*;

/**
 * A persistence manager that does nothing.
 */
public class NoPersistenceManager extends AbstractPersistenceManager {
    protected NoPersistenceManager(int commandsBetweenCheckpoints) throws PersistenceException {
        super(commandsBetweenCheckpoints);
    }

    @Override
    public void startTransaction() {
    }

    @Override
    public void endTransaction() {
    }

    @Override
    public void clear() {
    }

    @Override
    public IUsersDAO createUsersDAO() {
        return new NoPersistenceUsersDAO();
    }

    @Override
    public IGamesDAO createGamesDAO() {
        return new NoPersistenceGamesDAO();
    }

    @Override
    public ICommandsDAO createCommandsDAO() {
        return new NoPersistenceCommandsDAO();
    }
}
