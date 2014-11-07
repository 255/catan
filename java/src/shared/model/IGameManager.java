package shared.model;

import shared.definitions.CatanColor;

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
     * @param gameIndex index of the game the user wants to join
     * @param user the user who wants to join the game
     * @param playerColor color that player wants to be in the game.
     * @return game object of the game that the player joined
     */
    public Game joinGame(int gameIndex, IUser user, CatanColor playerColor);

    /**
     * Creates a new game on the server.
     *
     * @param gameName name to give the new game
     * @param randomPorts flag indicating whether ports should be placed randomly
     * @param randomTiles flag indicating whether tiles should be placed randomly
     * @param randomNumbers flag indicating whether numbers should be placed randomly
     * @return the newly created game object
     */
    public Game createGame(String gameName, boolean randomPorts, boolean randomTiles, boolean randomNumbers);

    /**
     * Gets the game with the specified index
     *
     * @param gameIndex index of the requested game
     * @return the requested game object
     */
    public Game getGame(int gameIndex);

    /**
     * Removes the game with the specified index from the server
     *
     * @param gameIndex index of game to remove
     */
    public void removeGame(int gameIndex);
}
