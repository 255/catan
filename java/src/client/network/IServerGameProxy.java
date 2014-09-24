package client.network;

import java.util.List;

/**
 * Interface for interacting with the server. This interface
 * largely mimics the server api for game administration.
 */
public interface IServerGameProxy {
    /**
     * Logs an existing user into the game server
     *
     * @param username username of player attempting to login
     * @param password password of player attempting to login
     * @return flag indicating whether the login was successful or not
     *
     * @throws NetworkException something went wrong when attempting to communicate with the server
     */
    public boolean login(String username, String password) throws NetworkException;

    /**
     * Creates a new user account and logs the new
     * user into the game server.
     *
     * @param username the username of the user attempting to register
     * @param password the password of the user attempting to register
     * @return a flag indicating whether the registration of this user was successful
     *
     * @throws NetworkException something went wrong when attempting to communicate with the server
     */
    public boolean register(String username, String password) throws NetworkException;

    /**
     * Returns information of all the current games on the server
     *
     * @return a list of games
     *
     * @throws NetworkException something went wrong when attempting to communicate with the server
     */
    public String listGames() throws NetworkException;

    /**
     * Creates an empty game on the game server
     *
     * @param randTiles flag indicating whether the tiles in the game should be assigned randomly
     * @param randNum flag indicating whether the number tokens on the tiles should be assigned randomly
     * @param randPorts flag indicating whether the ports in the game should be assigned randomly
     * @param name name to give the game on the server
     * @return a JSON object representing the current Catan Model after this method has been called
     *
     * @throws NetworkException something went wrong when attempting to communicate with the server
     */
    public String createGame(boolean randTiles, boolean randNum, boolean randPorts, String name) throws NetworkException;

    /**
     * Adds a player to the game and sets their game cookie
     *
     * @param username username of user attempting to join the game
     * @param password password of user attempting to join game
     * @return a JSON object representing the current Catan Model after this method has been called
     *
     * @throws NetworkException something went wrong when attempting to communicate with the server
     */
    public String joinGame(String username, String password) throws NetworkException;

    /**
     *
     * @return list of AI
     * @return a JSON object representing the current Catan Model after this method has been called
     *
     * @throws NetworkException indicates that an error occurred while attempting to communicate with the server
     */
    public List<String> listAI() throws NetworkException;

    /**
     * Adds an AI to the game
     *
     * @param name name to give the AI
     * @return a flag indicating whether the AI was added to the game successfully
     *
     * @throws NetworkException indicates that an error occurred while attempting to communicate with the server
     */
    public String addAI(String name) throws NetworkException;
}
