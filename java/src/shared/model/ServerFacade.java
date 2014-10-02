package shared.model;

import client.network.HttpCommunicator;
import client.network.IServerProxy;
import client.network.NetworkException;
import client.network.ServerProxy;

/**
 * Handles all the calls to the ServerProxy
 */
public class ServerFacade implements IServerFacade {
    private IServerProxy m_theProxy;
    private IGame m_theGame;
    private static ServerFacade m_theFacade = null;

    /**
     * This is a private constructor that is called only when the ServerFacade has not been initialized yet
     */
    private ServerFacade(IServerProxy theProxy, IGame theGame) {
        setProxyAndGame(theProxy, theGame);
    }

    /**
     * This static function is called to get the current instance of the GameFacade
     *
     * @return the current instance of the GameFacade
     */
    public static ServerFacade getFacadeInstance() {
        if(m_theFacade == null)
            m_theFacade = new ServerFacade(new ServerProxy(new HttpCommunicator()), new Game());
        return m_theFacade;
    }

    /**
     * This function sets the Game object that the GameFacade will point at
     *
     * @param theProxy is the proxy object to point the ServerFacade at
     */
    public void setProxyAndGame(IServerProxy theProxy, IGame theGame) {
        assert theProxy != null;
        assert theGame != null;

        m_theProxy = theProxy;
        m_theGame = theGame;
    }

    /**
     * Takes in a chat message to be added to the chat log
     *
     * @param message the string of text to add to the chat log
     */
    @Override
    public void sendChat(String message) {
        assert message != null;

        try {
            m_theProxy.sendChat(m_theGame.getLocalPlayer().getIndex(), message);
        } catch (NetworkException e) {
            e.printStackTrace(); //TODO error handling
        }
    }
}
