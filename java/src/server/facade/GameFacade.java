package server.facade;

import shared.communication.AddAIRequestParams;
import shared.model.Game;

/**
 * Created by Spencer Weight - 11/5/2014.
 */
public class GameFacade implements IGameFacade{

    /**
     * Fetch the model
     * Swagger URL Equivalent: /game/model (get request)
     *
     * @param theModel an integer with the id of the game to get the model of
     * @return Game object containing a pointer to the model
     */
    @Override
    public Game model(int theModel) {
        return null;
    }

    /**
     * Resets the current game
     * (Optional)
     * Swagger URL Equivalent: /game/reset
     *
     * @return Game object containing a pointer to the model
     */
    @Override
    public Game reset() {
        return null;
    }

    /**
     * Send a list of moves to the server
     * (Optional)
     * Swagger URL Equivalent: /game/commands (post request)
     *
     * @return Game object containing a pointer to the model
     */
    @Override
    public Game postCommands() {
        return null;
    }

    /**
     * Fetch a list of commands for the current game
     * (Optional)
     * Swagger URL Equivalent: /game/commands (get request)
     *
     * @return Game object containing a pointer to the model
     */
    @Override
    public Game getCommands() {
        return null;
    }

    /**
     * Add an AI player to the game
     * (Optional)
     * Swagger URL Equivalent: /game/addAI
     *
     * @param addAI JSON wrapper containing the parameters for adding an AI player
     * @return boolean true/false depending on if AI is added or not
     */
    @Override
    public boolean addAI(AddAIRequestParams addAI) {
        return false;
    }

    /**
     * Get a list of the AI Players
     * (Optional)
     * Swagger URL Equivalent: /game/listAI (get request)
     *
     * @return Game object containing a pointer to the model
     */
    @Override
    public Game listAI() {
        return null;
    }
}