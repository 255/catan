package server.persistence;

import server.command.ICommand;
import shared.model.IGame;

import java.util.Collection;

/**
 * The Database Access Object for storing and loading commands.
 */
public interface ICommandDAO {
    /**
     * Saves a command to the data persistence implementation
     *
     * @param command the command to save
     */
    public void saveCommand(ICommand command);

    /**
     * Load the commands associated with a game.
     * Calls to this function automatically update the game snapshot as needed.
     * @return a list of commands in the order they should be executed
     */
    public List<ICommand> loadCommands(IGame game);
}
