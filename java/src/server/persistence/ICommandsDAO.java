package server.persistence;

import server.command.ICommand;
import shared.model.IGame;

import java.util.List;

/**
 * The Database Access Object for storing and loading commands.
 */
public interface ICommandsDAO {
    /**
     * Saves a command to the data persistence implementation.
     * Calls to this function automatically update the game snapshot as needed.
     *
     * @param command the command to save
     */
    public void saveCommand(ICommand command) throws PersistenceException;

    /**
     * Load the commands associated with a game.
     * 
     * @param game the game object for which the commands should be loaded
     * @return a list of commands in the order they should be executed
     */
    public List<ICommand> loadCommands(IGame game) throws PersistenceException;
}
