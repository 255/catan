package client.network;

import client.data.*;
import shared.definitions.CatanColor;
import java.io.IOException;
import java.util.List;

/**
 * Interface to control flow of program when a user is outside of a game.
 * Controls logging in, registering users, showing current games, joining games,
 * and creating new games.
 *
 * @author StevenBarnett
 */
public interface IGameAdministrator {
    public void setGameAdminServerProxy(IGameAdminServerProxy gameAdminServerProxy);

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
    public List<GameInfo> listGames() throws NetworkException, IOException;

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
     * @return an object holding information about the created game
     */
    public GameInfo createGame(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String gameName) throws NetworkException, IOException;

    /**
     * Returns a list of AI players to choose from
     *
     * @return list of names of AI players as strings.
     */
    public String[] listAI() throws NetworkException;

    /**
     * Adds an AI player to the game you are currently in
     *
     * @param nameOfAI name of AI player to add to the game
     * @return flag indicating whether adding the AI player was successful
     */
    public boolean addAI(String nameOfAI) throws NetworkException;

    /**
     * Gets the name of the local player
     *
     * @return local player name
     */
    public String getLocalPlayerName();

    /**
     * Gets the local player id
     *
     * @return id of local player
     */
    public int getLocalPlayerId();

}
