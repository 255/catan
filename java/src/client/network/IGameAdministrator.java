package client.network;

import shared.definitions.CatanColor;

import java.util.List;

/**
 * Interface to control flow of program when a user is outside of a game.
 * Controls logging in, registering users, showing current games, joining games,
 * and creating new games.
 *
 * @author StevenBarnett
 */
public interface IGameAdministrator {
    /**
     * Logs an existing user into the game server
     *
     * @param username username of existing user attempting to login
     * @param password password of existing user attempting to login
     * @return flag indicating whether login was successful
     */
    public boolean login(String username, String password) throws NetworkException;

    /**
     * Creates a new user account and logs that user into the game server
     *
     * @param username username for new user to use in the game
     * @param password password for new user to use when logging in
     * @return flag indicating whether the registration and login was successful
     */
    public boolean register(String username, String password) throws NetworkException;

    /**
     * Returns a list of games that are currently running on the game server. Used to
     * show users what games they can login to.
     *
     * @return list of objects containing information about individual games
     */
    public List<IGameInfo> listGames() throws NetworkException;

    /**
     * Allows a user to join a game
     *
     * @param gameIndex index of game user wants to join
     * @param playerColor color user wants to use in this game
     * @return a JSON string representing the Catan Model for the game to be joined
     */
    public boolean joinGame(int gameIndex, CatanColor playerColor) throws NetworkException;

    /**
     * Creates a new game on the game server
     *
     * @param randomTiles flag indicating if the game should be created from random tiles
     * @param randomNumbers flag indicating if the number tokens should be placed randomly
     * @param randomPorts flag indicating if the ports should be assigned randomly
     * @param gameName name of the new game
     * @return a JSON string representing the Catan Model for the newly created game
     */
    public String createGame(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String gameName) throws NetworkException;
}
