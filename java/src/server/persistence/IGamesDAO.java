package server.persistence;

import shared.model.IGame;
import server.command.ICommand;

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
}
