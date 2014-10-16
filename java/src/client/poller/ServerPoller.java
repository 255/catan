package client.poller;

import client.network.IServerProxy;
import client.network.NetworkException;
import shared.model.IModelInitializer;
import shared.model.ModelException;
import shared.model.ModelInitializer;

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
    private IModelInitializer m_modelSerializer;
    private Timer m_timer;
    private int m_pollCount = 0;

    public ServerPoller(IServerProxy serverProxy) {
        m_serverProxy = serverProxy;
        m_modelSerializer = new ModelInitializer();
        m_timer = new Timer();
        m_timer.schedule(new QueryTask(), c_millisecondsPerSecond * 3, c_millisecondsPerSecond * 3);
    }
    @Override
    public void updateGame() {
        try {
            m_modelSerializer.initializeClientModel(m_serverProxy.getGameState(), m_serverProxy.getPlayerId());
        } catch (ModelException ex1) {
            System.err.println("Polling failed with ModelException.");
            ex1.printStackTrace();
        } catch (NetworkException ex2) {
            System.err.println("Polling failed with NetworkException.");
            ex2.printStackTrace();
        }
    }

    @Override
    public void setProxy(IServerProxy serverProxy) {
        m_serverProxy = serverProxy;
    }

    class QueryTask extends TimerTask {
        public void run() {
           updateGame();
           ++m_pollCount;
        }
    }

    public int getPollCount() {
        return m_pollCount;
    }


}
