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
     */
    @Override
    public Game model() {
        return null;
    }

    /**
     * Resets the current game
     * Swagger URL Equivalent: /game/reset
     */
    @Override
    public Game reset() {
        return null;
    }

    /**
     * Send a list of moves to the server
     * Swagger URL Equivalent: /game/commands (post request)
     */
    @Override
    public Game postCommands() {
        return null;
    }

    /**
     * Fetch a list of commands for the current game
     * Swagger URL Equivalent: /game/commands (get request)
     */
    @Override
    public Game getCommands() {
        return null;
    }

    /**
     * Add an AI player to the game
     * Swagger URL Equivalent: /game/addAI
     *
     * @param addAI
     */
    @Override
    public Game addAI(AddAIRequestParams addAI) {
        return null;
    }

    /**
     * Get a list of the AI Players
     * Swagger URL Equivalent: /game/listAI (get request)
     */
    @Override
    public Game listAI() {
        return null;
    }
}
