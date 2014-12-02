package server.persistence;

/**
 * Created by Spencer Weight - 12/1/2014.
 */
public interface IPersistenceManager {

    /**
     *
     */
    public void startTransaction();

    /**
     *
     */
    public void endTransaction();

    /**
     *
     */
    public void createUserDAO();

    /**
     *
     */
    public void createGamesDAO();
}
