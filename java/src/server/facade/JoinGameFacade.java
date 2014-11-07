package server.facade;

import shared.communication.*;
import shared.model.Game;

/**
 * Created by Spencer Weight - 11/5/2014.
 */
public class JoinGameFacade implements IJoinGameFacade{

    /**
     * Get a list of all games in progress
     * Swagger URL Equivalent: /games/list (get request)
     *
     * @return GamesList object containing the list of current running games on the server
     */
    @Override
    public GamesList list() {
        return null;
    }

    /**
     * Creates a game and puts it in the Games List
     * Swagger URL Equivalent: /games/create
     *
     * @param createGame the JSON wrapper with the parameters for creating a game
     * @return Game object containing a pointer to the created game
     */
    @Override
    public Game create(CreateGameRequestParams createGame) {
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
    public boolean join(JoinGameRequestParams joinGame) {
        return false;
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