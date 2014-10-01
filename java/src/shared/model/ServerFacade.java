package shared.model;

import client.communication.LogEntry;
import client.network.HttpCommunicator;
import client.network.IServerProxy;
import client.network.NetworkException;
import client.network.ServerProxy;

import java.util.List;

/**
 * Handles all the calls to the ServerProxy
 */
public class ServerFacade implements IServerFacade {
    private IServerProxy m_theProxy;
    private static ServerFacade m_theFacade = null;

    /**
     * This is a private constructor that is called only when the ServerFacade has not been initialized yet
     */
    private ServerFacade(IServerProxy theProxy) {
        setServerProxy(theProxy);
    }

    /**
     * This static function is called to get the current instance of the GameFacade
     *
     * @return the current instance of the GameFacade
     */
    public static ServerFacade getFacadeInstance() {
        if(m_theFacade == null)
            m_theFacade = new ServerFacade(new ServerProxy(new HttpCommunicator()));
        return m_theFacade;
    }

    /**
     * This function sets the Game object that the GameFacade will point at
     *
     * @param theProxy is the proxy object to point the ServerFacade at
     */
    public void setServerProxy(IServerProxy theProxy) {
        assert theProxy != null;
        assert theProxy instanceof ServerProxy;

        m_theProxy = theProxy;
    }

    /**
     * Takes in a chat message to be added to the chat log
     *
     * @param message the string of text to add to the chat log
     */
    @Override
    public void sendChat(String message) {
        // TODO should there even be a serverFacade
        // TODO change the player index that the chat message is sent to
        try {
            m_theProxy.sendChat(0, message);
        } catch (NetworkException e) {
            e.printStackTrace(); //TODO error handling
        }
    }
}
