package shared.model;

import org.junit.*;
import org.junit.Assert.*;
import shared.model.IGame;
import shared.model.IModelSerializer;
import shared.model.ModelSerializer;
import shared.model.ServerFacade;
import client.network.IServerProxy;
import client.network.TestServerProxy;

import java.lang.Exception;
import java.lang.System;

public class ServerFacadeTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        return;
    }

    private IServerFacade facade;
    private IServerProxy proxy;
    private IModelSerializer serializer;
    private IGame game;

    @Before
    public void setUp() throws Exception {
        facade = new ServerFacade();
        proxy = new TestServerProxy();
        serializer = new ModelSerializer();
        game = serializer.convertJSONtoModel(proxy.getGameState());
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetChatHistory() throws Exception {
//        assertEquals(facade.getChatHistory(), game.getChatHistory().getMessages());
//        System.out.println(game.getChatHistory().getMessages().get(0).getMessage());
    }

    @Test
    public void testGetMoveHistory() throws Exception {
//        assertEquals(facade.getMoveHistory(), game.getGameplayLog());
//        for (int i = 0; i < facade.getMoveHistory().size(); i++) {
//            assertEquals(facade.getMoveHistory().get(i), game.getGameplayLog().get(i));
//        }
//        System.out.println(game.getGameplayLog().getMessages().get(0).getMessage());
    }

    @Test
    public void testSendChat() throws Exception {
        facade.sendChat("This is a test message.");
        System.out.println(game.getChatHistory().getMessages().get(0).getMessage());
        assert("This is a test message.".equals(game.getChatHistory().getMessages().get(0).getMessage()));
    }
}