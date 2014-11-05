package server.facade;

import shared.communication.*;

/**
 * Created by Spencer Weight - 11/4/2014.
 */
public interface IJoinGameFacade {

    /**
     * Get a list of all games in progress
     * Swagger URL Equivalent: /games/list (get request)
     */
    //TODO update inputs and outputs for list()
    public GamesList list();

    /**
     * Creates a game and puts it in the Games List
     * Swagger URL Equivalent: /games/create
     */
    public void create(CreateGameRequestParams createGame);

    /**
     * Join or re-join a game (modifies cookie)
     * Swagger URL Equivalent: /games/join
     */
    public void join(JoinGameRequestParams joinGame);

    /**
     * Saves a game
     * Swagger URL Equivalent: /games/save
     */
    public void save(SaveGameRequestParams saveGame);

    /**
     * Loads a game
     * Swagger URL Equivalent: /games/load
     */
    public void load(LoadGameRequestParams loadGame);
}
