package client.poller;

import client.network.IServerProxy;
import client.network.NetworkException;
import shared.model.IModelSerializer;
import shared.model.ModelException;

/**
 * Class that implements the IServerPoller interface
 *
 * @author StevenBarnett
 */
public class ServerPoller implements IServerPoller {

    private IServerProxy m_serverProxy;
    private IModelSerializer m_modelSerializer;

    public ServerPoller(IServerProxy serverProxy) {
        m_serverProxy = serverProxy;
    }
    @Override
    public void updateGame() throws ModelException, NetworkException {
        m_modelSerializer.convertJSONtoModel(m_serverProxy.getGameState());
    }

    @Override
    public void setProxy(IServerProxy serverProxy) {
        m_serverProxy = serverProxy;
    }
}
