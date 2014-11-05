package server.facade;

import shared.communication.*;

/**
 * Created by Spencer Weight - 11/5/2014.
 */
public class JoinGameFacade implements IJoinGameFacade{
    /**
     * Get a list of all games in progress
     * Swagger URL Equivalent: /games/list (get request)
     */
    @Override
    public GamesList list() {
        return null;
    }

    /**
     * Creates a game and puts it in the Games List
     * Swagger URL Equivalent: /games/create
     *
     * @param createGame
     */
    @Override
    public void create(CreateGameRequestParams createGame) {

    }

    /**
     * Join or re-join a game (modifies cookie)
     * Swagger URL Equivalent: /games/join
     *
     * @param joinGame
     */
    @Override
    public void join(JoinGameRequestParams joinGame) {

    }

    /**
     * Saves a game
     * Swagger URL Equivalent: /games/save
     *
     * @param saveGame
     */
    @Override
    public void save(SaveGameRequestParams saveGame) {

    }

    /**
     * Loads a game
     * Swagger URL Equivalent: /games/load
     *
     * @param loadGame
     */
    @Override
    public void load(LoadGameRequestParams loadGame) {

    }
}
