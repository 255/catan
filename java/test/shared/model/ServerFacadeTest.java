package shared.model;

import org.junit.*;
import client.network.IServerProxy;
import client.network.TestServerProxy;

import java.lang.Exception;
import java.lang.System;

public class ServerFacadeTest {
    private IServerFacade facade;
    private IServerProxy proxy;
    private IModelInitializer serializer;
    private IGame game;

    @Before
    public void setUp() throws Exception {
        facade = ServerFacade.getFacadeInstance();
//        proxy = new TestServerProxy();
//        serializer = new ModelInitializer();
//        serializer.initializeClientModel(proxy.getGameState());
//        game = GameFacade.getFacadeInstance().getGame();
    }

    @After
    public void tearDown() throws Exception {
        proxy = null;
        serializer = null;
        game = null;
    }

    @Test
    public void testSendChat() throws Exception {

    }
}