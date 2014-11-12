package server.facade;

import server.command.IllegalCommandException;
import shared.communication.AddAIRequestParams;
import shared.model.Game;

/**
 * A facade to support /game operations
 * Created by Spencer Weight - 11/4/2014.
 */
public interface IGameFacade {

    /**
     * Fetch the model
     * Swagger URL Equivalent: /game/model (get request)
     *
     * @param theModel an integer with the id of the game to get the model of
     * @return Game object containing a pointer to the model
     */
    public Game model(Integer theModel);

    /**
     * Resets the current game
     * (Optional)
     * Swagger URL Equivalent: /game/reset
     *
     * @return Game object containing a pointer to the model
     */
    public Game reset();

    /**
     * Send a list of moves to the server
     * (Optional)
     * Swagger URL Equivalent: /game/commands (post request)
     *
     * @return Game object containing a pointer to the model
     */
    public Game postCommands();

    /**
     * Fetch a list of commands for the current game
     * (Optional)
     * Swagger URL Equivalent: /game/commands (get request)
     *
     * @return Game object containing a pointer to the model
     */
    public Game getCommands();

    /**
     * Add an AI player to the game
     * (Optional)
     * Swagger URL Equivalent: /game/addAI
     *
     * @param addAI JSON wrapper containing the parameters for adding an AI player
     * @return boolean true/false depending on if AI is added or not
     */
    public boolean addAI(AddAIRequestParams addAI);

    /**
     * Get a list of the AI Players
     * (Optional)
     * Swagger URL Equivalent: /game/listAI (get request)
     *
     * @return Game object containing a pointer to the model
     */
    public Game listAI();
}
