package client.poller;

import client.network.IServerProxy;
import client.network.TestServerProxy;
import client.poller.ServerPoller;
import org.junit.Assert.*;

public class ServerPollerTest {

    private IServerPoller m_serverPoller;
    private IServerProxy m_mockServerProxy;

    @org.junit.Before
    public void setUp() throws Exception {
        m_mockServerProxy = new TestServerProxy();
        m_serverPoller = new ServerPoller(m_mockServerProxy);
    }

    @org.junit.After
    public void tearDown() throws Exception {

    }

    @org.junit.Test
    public void testUpdateGame() throws Exception {
        m_serverPoller.updateGame();
    }
}