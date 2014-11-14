package server.facade;

import shared.communication.*;
import shared.model.Game;
import shared.model.ModelException;

/**
 * A facade to support /games operations.
 * Created by Spencer Weight - 11/4/2014.
 */
public interface IJoinGameFacade {

    /**
     * Get a list of all games in progress
     * Swagger URL Equivalent: /games/list (get request)
     *
     * @return GamesList object containing the list of current running games on the server
     */
    public GameInfo[] list();

    /**
     * Creates a game and puts it in the Games List
     * Swagger URL Equivalent: /games/create
     *
     * @param createGame the JSON wrapper with the parameters for creating a game
     * @return Game object containing a pointer to the created game
     */
    public GameInfo create(CreateGameRequestParams createGame) throws ModelException;

    /**
     * Join or re-join a game (modifies cookie)
     * Swagger URL Equivalent: /games/join
     *
     * @param joinGame the JSON wrapper with the parameters for joining a game
     * @return boolean containing true or false depending on if the join was successful
     */
    public Integer join(JoinGameRequestParams joinGame) throws ModelException;

    /**
     * Saves a game
     * Swagger URL Equivalent: /games/save
     *
     * @param saveGame the JSON wrapper with the parameters for saving a game
     * @return boolean containing true or false depending on if the save was successful
     */
    public boolean save(SaveGameRequestParams saveGame);

    /**
     * Loads a game
     * Swagger URL Equivalent: /games/load
     *
     * @param loadGame the JSON wrapper with the parameters for loading a game
     * @return Game object containing a pointer to the loaded game
     */
    public Game load(LoadGameRequestParams loadGame);
}
