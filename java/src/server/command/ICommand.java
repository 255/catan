package server.command;

import shared.model.IGame;
import shared.model.ModelException;

import java.io.Serializable;

/**
 * Interface that facades require to execute certain commands on the Server Model
 *
 * @author StevenBarnett
 */
public interface ICommand extends Serializable {
    /**
     * Implements all the functionality and interaction with the Server Model that
     * this command requires
     */
    public void execute();

    /**
     * Set the game object this command is associated with.
     * The player object is also updated to match the one in the game.
     * This is necessary when loading the game.
     */
    public void setGameAndPlayers(IGame game) throws ModelException;

    /**
     * Get the game object this command is for.
     * @return the game object
     */
    public IGame getGame();
}
