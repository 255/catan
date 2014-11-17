package server.facade.stubs;

import server.facade.IJoinGameFacade;
import shared.communication.*;
import shared.model.Game;
import shared.model.ModelException;

/**
 * Created by Spencer Weight - 11/17/2014.
 */
public class JoinGameFacadeStub implements IJoinGameFacade {
    /**
     * Get a list of all games in progress
     * Swagger URL Equivalent: /games/list (get request)
     *
     * @return GamesList object containing the list of current running games on the server
     */
    @Override
    public GameInfo[] list() {
        return new GameInfo[0];
    }

    /**
     * Creates a game and puts it in the Games List
     * Swagger URL Equivalent: /games/create
     *
     * @param createGame the JSON wrapper with the parameters for creating a game
     * @return Game object containing a pointer to the created game
     */
    @Override
    public GameInfo create(CreateGameRequestParams createGame) throws ModelException {
        return null;
    }

    /**
     * Join or re-join a game (modifies cookie)
     * Swagger URL Equivalent: /games/join
     *
     * @param joinGame the JSON wrapper with the parameters for joining a game
     * @return boolean containing true or false depending on if the join was successful
     */
    @Override
    public Integer join(JoinGameRequestParams joinGame) throws ModelException {
        return null;
    }

    /**
     * Saves a game
     * Swagger URL Equivalent: /games/save
     *
     * @param saveGame the JSON wrapper with the parameters for saving a game
     * @return boolean containing true or false depending on if the save was successful
     */
    @Override
    public boolean save(SaveGameRequestParams saveGame) {
        return false;
    }

    /**
     * Loads a game
     * Swagger URL Equivalent: /games/load
     *
     * @param loadGame the JSON wrapper with the parameters for loading a game
     * @return Game object containing a pointer to the loaded game
     */
    @Override
    public Game load(LoadGameRequestParams loadGame) {
        return null;
    }
}
