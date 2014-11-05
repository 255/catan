package server.facade;

import shared.communication.AddAIRequestParams;
import shared.model.Game;

/**
 * Created by Spencer Weight - 11/4/2014.
 */
public interface IGameFacade {

    /**
     * Fetch the model
     * Swagger URL Equivalent: /game/model (get request)
     */
    //TODO update inputs and outputs for model()
    public Game model();

    /**
     * Resets the current game
     * Swagger URL Equivalent: /game/reset
     */
    //TODO update inputs and outputs for reset()
    public Game reset();

    /**
     * Send a list of moves to the server
     * Swagger URL Equivalent: /game/commands (post request)
     */
    //TODO update inputs and outputs for postCommands()
    public Game postCommands();

    /**
     * Fetch a list of commands for the current game
     * Swagger URL Equivalent: /game/commands (get request)
     */
    //TODO update inputs and outputs for getCommands()
    public Game getCommands();

    /**
     * Add an AI player to the game
     * Swagger URL Equivalent: /game/addAI
     */
    public Game addAI(AddAIRequestParams addAI);

    /**
     * Get a list of the AI Players
     * Swagger URL Equivalent: /game/listAI (get request)
     */
    //TODO update inputs and outputs for listAI()
    public Game listAI();
}
