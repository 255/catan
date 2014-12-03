package server.persistence;

import shared.model.IGame;
import server.command.ICommand;
import shared.model.IGameManager;

/**
 * Created by Spencer Weight - 12/1/2014.
 */
public interface IGamesDAO {
    /**
     * Saves a game to the data persistence implementation
     *
     * @param game the game model to save
     */
    public void saveGame(IGame game);

    /**
     * Saves a command to the data persistence implementation
     *
     * @param command the command to save
     */
    public void saveCommand(ICommand command);

    /**
     * Restore the games that are stored on disk. If no games are on disk, an empty GameManager is returned.
     * This is called when the server is started.
     * @return a GameManager object with the games in it
     */
    public IGameManager loadGames();
}
