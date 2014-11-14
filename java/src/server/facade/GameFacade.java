package server.facade;

import shared.communication.AddAIRequestParams;
import shared.communication.GameModelParam;
import shared.model.Game;
import shared.model.IGame;
import shared.model.IGameManager;
import shared.model.User;

/**
 * A facade to support /game operations
 * Created by Spencer Weight - 11/5/2014.
 */
public class GameFacade implements IGameFacade {
    private IGameManager m_gameManager;

    public GameFacade(IGameManager m_gameManager) {
        this.m_gameManager = m_gameManager;
    }

    /**
     * Fetch the model
     * Swagger URL Equivalent: /game/model (get request)
     *
     * @param param an integer with the id of the game to get the model of
     * @return Game object containing a pointer to the model
     */
    @Override
    public IGame model(GameModelParam param) {
        return m_gameManager.getGame(param.getGameId());
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
     * @param params JSON wrapper containing the parameters for adding an AI player
     * @return boolean true/false depending on if AI is added or not
     */
    @Override
    public boolean addAI(AddAIRequestParams params) {
        // TODO: this is temporary test code
        IGame game = m_gameManager.getGame(params.getGameId());
        game.joinGame(new User("Hal 9000", "", 9000), shared.definitions.CatanColor.RED);
        game.joinGame(new User("GLaDOS", "", 1234567), shared.definitions.CatanColor.BLUE);
        game.joinGame(new User("The MCP", "", 1980), shared.definitions.CatanColor.WHITE);
        return true;
    }

    /**
     * Get a list of the AI Players
     * (Optional)
     * Swagger URL Equivalent: /game/listAI (get request)
     *
     * @return Game object containing a pointer to the model
     */
    @Override
    public String[] listAI() {
        return new String[]{"Hal 9000", "The MCP", "GLaDOS"};
    }
}
