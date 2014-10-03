package client.poller;

import client.network.IServerProxy;
import client.network.TestServerProxy;
import client.poller.ServerPoller;
import org.junit.Assert.*;

import static org.junit.Assert.*;

public class ServerPollerTest {

    private IServerPoller m_serverPoller;
    private IServerProxy m_mockServerProxy;

    @org.junit.Test
    public void testUpdateGame() throws Exception {
        m_mockServerProxy = new TestServerProxy();
        m_serverPoller = new ServerPoller(m_mockServerProxy);
        Thread.sleep(31000);

        assertEquals("Poller should have polled 10 times in 30 seconds", 10, m_serverPoller.getPollCount());
    }
}