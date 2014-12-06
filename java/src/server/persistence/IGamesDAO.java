package server.persistence;

import shared.model.IGame;
import shared.model.IGameManager;

/**
 * The Database Access Object for storing and loading games.
 */
public interface IGamesDAO {
    /**
     * Save a game to the data persistence implementation.
     * This is called by the facade for new games.
     *
     * @param game the game model to save
     */
    public void saveGame(IGame game) throws PersistenceException;

    /**
     * Restore the games that are stored on disk.
     * If there are no games on disk, an empty GameManager is returned.
     * This is called when the server is started.
     * @return a GameManager object with the games in it
     */
    public IGameManager loadGames() throws PersistenceException;
}
