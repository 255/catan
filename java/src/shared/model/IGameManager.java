package shared.model;

import shared.definitions.CatanColor;

import java.util.Collection;

/**
 * Interface that manages all the games that are
 * currently on the server
 *
 * @author StevenBarnett
 */
public interface IGameManager {

    /**
     * Adds the specified user to the
     * specified game.
     *
     * @param gameId id of the game the user wants to join
     * @param user the user who wants to join the game
     * @param playerColor color that player wants to be in the game.
     * @return game object of the game that the player joined
     */
    public boolean joinGame(int gameId, IUser user, CatanColor playerColor) throws ModelException;

    /**
     * Creates a new game on the server.
     *
     * @param gameName name to give the new game
     * @param randomPorts flag indicating whether ports should be placed randomly
     * @param randomTiles flag indicating whether tiles should be placed randomly
     * @param randomNumbers flag indicating whether numbers should be placed randomly
     * @return the newly created game object
     */
    public IGame createGame(String gameName, boolean randomPorts, boolean randomTiles, boolean randomNumbers) throws ModelException;

    /**
     * Gets the game with the specified index
     *
     * @param gameId id of the requested game
     * @return the requested game object
     */
    public IGame getGame(int gameId) throws ModelException;

    /**
     * Get a list of ongoing games.
     * Finished games are not included in this list.
     * @return a list of games
     */
    public Collection<IGame> listGames();

    /**
     * Replace the game with the provided one.
     * If no game with the same ID exists, the game is created
     * @param game the game being loaded
     */
    public void loadGame(IGame game);

    /**
     * Resets the next game id variable to 0
     */
    public void resetNextGameId();
}
