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
    String get(String url) throws NetworkException;

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
    String post(String url, String data) throws NetworkException;
}
