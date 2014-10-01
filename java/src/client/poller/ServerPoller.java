package client.poller;

import client.network.IServerProxy;
import client.network.NetworkException;
import shared.model.IModelSerializer;
import shared.model.ModelException;
import shared.model.ModelSerializer;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Class that implements the IServerPoller interface
 *
 * @author StevenBarnett
 */
public class ServerPoller implements IServerPoller {

    private final int c_millisecondsPerSecond = 1000;

    private IServerProxy m_serverProxy;
    private IModelSerializer m_modelSerializer;
    private Timer m_timer;

    public ServerPoller(IServerProxy serverProxy) {
        m_serverProxy = serverProxy;
        m_modelSerializer = new ModelSerializer();
        m_timer = new Timer();
        m_timer.schedule(new QueryTask(), c_millisecondsPerSecond * 3, c_millisecondsPerSecond * 3);
    }
    @Override
    public void updateGame() {
        try {
            m_modelSerializer.convertJSONtoModel(m_serverProxy.getGameState());
        } catch (ModelException ex1) {

        } catch (NetworkException ex2) {

        }
    }

    @Override
    public void setProxy(IServerProxy serverProxy) {
        m_serverProxy = serverProxy;
    }

    class QueryTask extends TimerTask {
        public void run() {
            updateGame();
        }
    }


}
