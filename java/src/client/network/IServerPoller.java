package client.network;

/**
 * The server poller periodically updates the game state.
 * It is constructed with references to the Model Serializer and the Server Proxy.
 *
 * Created by Wyatt on 9/21/2014.
 */
public interface IServerPoller {
    /**
     * Update the game state with the latest from the server.
     * This calls /game/model and then has the model serializer initialize the model.
     */
    public void updateGame();

    /**
     * Set which server proxy the poller talks to.
     * @param serverProxy the server proxy from which the poller gets updates
     */
    public void setProxy(IServerProxy serverProxy);
}
