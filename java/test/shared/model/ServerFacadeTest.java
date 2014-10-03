package shared.model;

import org.junit.*;
import client.network.IServerProxy;

import java.lang.Exception;

public class ServerFacadeTest {
    private IServerModelFacade facade;
    private IServerProxy proxy;
    private IModelInitializer serializer;
    private IGame game;

    @Before
    public void setUp() throws Exception {
        facade = ServerModelFacade.getInstance();
//        proxy = new TestServerProxy();
//        serializer = new ModelInitializer();
//        serializer.initializeClientModel(proxy.getGameState());
//        game = GameModelFacade.getInstance().getGame();
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