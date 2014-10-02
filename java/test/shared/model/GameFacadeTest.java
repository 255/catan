package shared.model;

import shared.locations.*;
import client.network.IServerProxy;
import client.network.TestServerProxy;

import java.lang.Exception;

import org.junit.*;

public class GameFacadeTest {

    @AfterClass
    public static void setUpBeforeClass() throws Exception {

    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {

    }

    private IGameFacade facade;
    private IServerProxy proxy;
    private IModelInitializer serializer;
    private IGame game;

    @Before
    public void setUp() throws Exception {
        facade = GameFacade.getFacadeInstance();
        proxy = new TestServerProxy();
        serializer = new ModelInitializer();
        serializer.initializeClientModel(proxy.getGameState());
        game = GameFacade.getFacadeInstance().getGame();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCanPlaceRoad() throws Exception {
        HexLocation hex1 = new HexLocation(0, 0);
        HexLocation hex2 = new HexLocation(1, 1);
        EdgeLocation goodEdge = new EdgeLocation(hex1, EdgeDirection.North);
        EdgeLocation badEdge = new EdgeLocation(hex2, EdgeDirection.South);
        assert(facade.canPlaceRoad(goodEdge) == true);
        assert(facade.canPlaceRoad(badEdge) == false);
    }

    @Test
    public void testCanPlaceSettlement() throws Exception {
        HexLocation hex1 = new HexLocation(0, 0);
        HexLocation hex2 = new HexLocation(1, 1);
        VertexLocation goodVer = new VertexLocation(hex1, VertexDirection.West);
        VertexLocation badVer = new VertexLocation(hex2, VertexDirection.East);
        assert(facade.canPlaceSettlement(goodVer) == true);
        assert(facade.canPlaceSettlement(badVer) == false);
    }

    @Test
    public void testCanPlaceCity() throws Exception {
        HexLocation hex1 = new HexLocation(0, 0);
        HexLocation hex2 = new HexLocation(1, 1);
        VertexLocation goodVer = new VertexLocation(hex1, VertexDirection.West);
        VertexLocation badVer = new VertexLocation(hex2, VertexDirection.East);
        assert(facade.canPlaceCity(goodVer) == true);
        assert(facade.canPlaceCity(badVer) == false);
    }

    @Test
    public void testPlaceRoad() throws Exception {

    }

    @Test
    public void testPlaceSettlement() throws Exception {

    }

    @Test
    public void testPlaceCity() throws Exception {

    }

    @Test
    public void testPlaceRobber() throws Exception {

    }

    @Test
    public void testTakeCardFromPlayer() throws Exception {

    }

    @Test
    public void testGiveCardToPlayer() throws Exception {

    }

    @Test
    public void testGetCurPlayerInfo() throws Exception {

    }

    @Test
    public void testGetRoads() throws Exception {

    }

    @Test
    public void testGetSettlements() throws Exception {

    }

    @Test
    public void testGetCities() throws Exception {

    }

    @Test
    public void testGetNumberPieces() throws Exception {

    }

    @Test
    public void testGetHexes() throws Exception {

    }

    @Test
    public void testGetRobber() throws Exception {
        HexLocation hex = new HexLocation(0, 0);
        assert(facade.getRobber() == hex);
    }

    @Test
    public void testGetPlayerResources() throws Exception {

    }

    @Test
    public void testGetPlayerPorts() throws Exception {

    }
}