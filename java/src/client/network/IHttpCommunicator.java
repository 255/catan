package client.network;

/**
 * Interface for the client to communicate with the server. The ServerProxy
 * uses this to make its calls to the server
 */
public interface IHttpCommunicator {

    /**
     * The Http Get Method. Sends a request to a server and gets a response
     *
     * @param url the name mapped to the corresponding handler in the server on the other side
     * @return JSON string as server response
     *
     * @throws NetworkException indicates that an error occurred while connecting to the server
     */
    public String get(String url) throws NetworkException;

    /**
     * The Http Post Methods. It sends data to a server and performs
     * a command.
     *
     * @param url the name mapped to the corresponding handler in the server on the other side
     * @param data the information to perform the command
     * @return JSON string as server response
     *
     * @throws NetworkException indicates that an error occurred while connecting to the server
     */
    public String post(String url, String data) throws NetworkException;

    /**
     * Returns the name of the local player
     *
     * @return name of player
     */
    public String getPlayerName();

    /**
     * Returns the id of the player
     *
     * @return id of player
     */
    public int getPlayerId();

    /**
     * Returns the id of the current game the player is a part of
     *
     * @return current game id
     */
    public int getGameIdCookie();
}
