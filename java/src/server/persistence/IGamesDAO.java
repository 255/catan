package server.persistence;

import shared.model.IGame;
import server.command.ICommand;
import shared.model.IGameManager;

/**
 * Created by Spencer Weight - 12/1/2014.
 */
public interface IGamesDAO {
    /**
     * Save a game to the data persistence implementation.
     * This is called for new games.
     *
     * @param game the game model to save
     */
    public void saveGame(IGame game);

    /**
     * Restore the games that are stored on disk.
     * If there are no games on disk, an empty GameManager is returned.
     * This is called when the server is started.
     * @return a GameManager object with the games in it
     */
    public IGameManager loadGames();
}
