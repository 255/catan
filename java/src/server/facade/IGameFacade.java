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
     * @param theModel
     */
    //TODO update inputs and outputs for model()
    public Game model(int theModel);

    /**
     * Resets the current game
     * (Optional)
     * Swagger URL Equivalent: /game/reset
     */
    public Game reset();

    /**
     * Send a list of moves to the server
     * (Optional)
     * Swagger URL Equivalent: /game/commands (post request)
     */
    public Game postCommands();

    /**
     * Fetch a list of commands for the current game
     * (Optional)
     * Swagger URL Equivalent: /game/commands (get request)
     */
    public Game getCommands();

    /**
     * Add an AI player to the game
     * (Optional)
     * Swagger URL Equivalent: /game/addAI
     */
    public Game addAI(AddAIRequestParams addAI);

    /**
     * Get a list of the AI Players
     * (Optional)
     * Swagger URL Equivalent: /game/listAI (get request)
     */
    public Game listAI();
}
