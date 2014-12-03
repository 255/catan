package server.persistence;

/**
 * Created by Spencer Weight - 12/1/2014.
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
    public ICommandsDAO createGamesDAO();
}
