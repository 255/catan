package client.poller;

import client.network.IServerProxy;
import client.network.TestServerProxy;

import static org.junit.Assert.*;

public class ServerPollerTest {

    private IServerPoller m_serverPoller;
    private IServerProxy m_mockServerProxy;

    @org.junit.Test
    public void testUpdateGame() throws Exception {
        m_mockServerProxy = new TestServerProxy(true);
        m_serverPoller = ServerPoller.getInstance();
        m_serverPoller.setProxy(m_mockServerProxy);
        m_serverPoller.startPolling();

        Thread.sleep(10000);

        assertEquals("Poller should have polled 3 times in 10 seconds", 3, m_serverPoller.getPollCount());
    }
}