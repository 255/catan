package shared.model;

import shared.definitions.CatanColor;
import shared.locations.*;
import client.network.IServerProxy;
import client.network.TestServerProxy;

import java.lang.Exception;

import org.junit.*;

import static org.junit.Assert.*;

public class GameFacadeTest {
    private IGameModelFacade facade;
    private IModelInitializer serializer;
    private IGame game;

    @Before
    public void setUp() throws Exception {
        facade = GameModelFacade.getInstance();
        serializer = new ModelInitializer();
        //initializes the model with test data from JSON file
        String testJSON = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get("sample/test_2.json")));
        serializer.initializeClientModel(testJSON, 0);
        game = GameModelFacade.getInstance().getGame();
        IPlayer player = new Player("Sam", 0, CatanColor.ORANGE, 0);
        game.setLocalPlayer(player);
    }

    @After
    public void tearDown() throws Exception {
        serializer = null;
        game = null;
    }

    @Test
    public void testCanPlaceRoad() throws Exception {
        HexLocation hex1 = new HexLocation(-1, 0);
        EdgeLocation edge1 = new EdgeLocation(hex1, EdgeDirection.South);
        assertTrue("Placing road in valid location", facade.canPlaceRoad(edge1));

        HexLocation hex2 = new HexLocation(0, 0);
        EdgeLocation edge2 = new EdgeLocation(hex2, EdgeDirection.SouthWest);
        assertFalse("The location is not open", facade.canPlaceRoad(edge2));

        HexLocation hex3 = new HexLocation(0, -1);
        EdgeLocation edge3 = new EdgeLocation(hex3, EdgeDirection.North);
        assertFalse("Is not connected to your road", facade.canPlaceRoad(edge3));

        HexLocation hex4 = new HexLocation(-3, 0);
        EdgeLocation edge4 = new EdgeLocation(hex4, EdgeDirection.NorthEast);
        assertFalse("The location is on water", facade.canPlaceRoad(edge4));

        HexLocation hex5 = new HexLocation(0, 1);
        EdgeLocation edge5 = new EdgeLocation(hex5, EdgeDirection.NorthWest);
        assertFalse("Trying to connect to another player's road", facade.canPlaceRoad(edge5));

        HexLocation hex6 = new HexLocation(0, 1);
        EdgeLocation edge6 = new EdgeLocation(hex6, EdgeDirection.NorthEast);
        assertFalse("Road blocked by another player's town", facade.canPlaceRoad(edge6));

        game.setGameState(GameState.DISCARDING);
        assertFalse("Not playing phase", facade.canPlaceRoad(edge1));

        game.setGameState(GameState.FIRST_ROUND);
        assertTrue("Place free road", facade.canPlaceRoad(edge1));
        game.setGameState(GameState.PLAYING);

        game.setCurrentPlayer(game.getPlayers().get(1));
        game.setLocalPlayer(game.getPlayers().get(1));
        assertFalse("Not enough resources", facade.canPlaceRoad(edge1));

        game.setCurrentPlayer(game.getPlayers().get(2));
        game.setLocalPlayer(game.getPlayers().get(2));
        assertFalse("No road pieces", facade.canPlaceRoad(edge1));


//        assertTrue("Road building dev card", facade.canPlaceRoad(edge1));

        game.setCurrentPlayer(game.getPlayers().get(3));
        assertFalse("Not your turn", facade.canPlaceRoad(edge1));
    }

    @Test
    public void testCanPlaceSettlement() throws Exception {
        HexLocation hex1 = new HexLocation(0, 0);
        VertexLocation ver1 = new VertexLocation(hex1, VertexDirection.West);
        assertTrue("Placing settlement in valid location", facade.canPlaceSettlement(ver1));

        HexLocation hex2 = new HexLocation(0, 0);
        VertexLocation ver2 = new VertexLocation(hex2, VertexDirection.SouthEast);
        assertFalse("The location is not open", facade.canPlaceSettlement(ver2));

        HexLocation hex3 = new HexLocation(0, -1);
        VertexLocation ver3 = new VertexLocation(hex3, VertexDirection.NorthEast);
        assertFalse("Is not connected to your road", facade.canPlaceSettlement(ver3));

        HexLocation hex4 = new HexLocation(-3, 0);
        VertexLocation ver4 = new VertexLocation(hex4, VertexDirection.NorthEast);
        assertFalse("The location is on water", facade.canPlaceSettlement(ver4));

        HexLocation hex5 = new HexLocation(0, 2);
        VertexLocation ver5 = new VertexLocation(hex5, VertexDirection.NorthEast);
        assertFalse("Trying to connect to another player's road", facade.canPlaceSettlement(ver5));

        HexLocation hex6 = new HexLocation(0, 0);
        VertexLocation ver6 = new VertexLocation(hex6, VertexDirection.NorthWest);
        assertFalse("Too close to another town", facade.canPlaceSettlement(ver6));

        game.setGameState(GameState.DISCARDING);
        assertFalse("Not playing phase", facade.canPlaceSettlement(ver1));

        game.setGameState(GameState.FIRST_ROUND);
        assertTrue("Place free settlement", facade.canPlaceSettlement(ver1));
        game.setGameState(GameState.PLAYING);

        game.setCurrentPlayer(game.getPlayers().get(1));
        game.setLocalPlayer(game.getPlayers().get(1));
        assertFalse("Not enough resources", facade.canPlaceSettlement(ver1));

        game.setCurrentPlayer(game.getPlayers().get(2));
        game.setLocalPlayer(game.getPlayers().get(2));
        assertFalse("Not enough settlement pieces", facade.canPlaceSettlement(ver1));

        game.setCurrentPlayer(game.getPlayers().get(3));
        assertFalse("Not your turn", facade.canPlaceSettlement(ver1));
    }

    @Test
    public void testCanPlaceCity() throws Exception {
        HexLocation hex1 = new HexLocation(0, 0);
        VertexLocation ver1 = new VertexLocation(hex1, VertexDirection.NorthEast);
        assertTrue("Placing city in valid location", facade.canPlaceCity(ver1));

        HexLocation hex2 = new HexLocation(0, 0);
        VertexLocation ver2 = new VertexLocation(hex2, VertexDirection.West);
        assertFalse("No settlement", facade.canPlaceCity(ver2));

        HexLocation hex3 = new HexLocation(0, 0);
        VertexLocation ver3 = new VertexLocation(hex3, VertexDirection.SouthEast);
        assertFalse("Another player's settlement", facade.canPlaceCity(ver3));

        game.setGameState(GameState.DISCARDING);
        assertFalse("Not playing phase", facade.canPlaceCity(ver1));
        game.setGameState(GameState.PLAYING);

        game.setCurrentPlayer(game.getPlayers().get(1));
        game.setLocalPlayer(game.getPlayers().get(1));
        assertFalse("Not enough resources", facade.canPlaceCity(ver1));

        game.setCurrentPlayer(game.getPlayers().get(2));
        game.setLocalPlayer(game.getPlayers().get(2));
        assertFalse("Not enough city pieces", facade.canPlaceCity(ver1));

        game.setCurrentPlayer(game.getPlayers().get(3));
        assertFalse("Not your turn", facade.canPlaceCity(ver1));
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
        assertEquals("Get current player", facade.getCurrentPlayer(), game.getCurrentPlayer());
    }

    @Test
    public void testGetNumberPieces() throws Exception {
        assertEquals("Get Pieces", facade.getCurrentPlayer().getPieceBank(), game.getCurrentPlayer().getPieceBank());
    }

    @Test
    public void testGetPlayerResources() throws Exception {
        assertEquals("Get resources", facade.getPlayerResources(), game.getLocalPlayer().getResources());
    }

    @Test
    public void testGetPlayerPorts() throws Exception {
        assertEquals("Get ports", facade.getPlayerPorts(), game.getMap().getPlayersPorts(game.getLocalPlayer()));
    }

    @Test
    public void testGetChatHistory() throws Exception {
        assertEquals("Get chat history", facade.getChatHistory(), game.getChatHistory());
    }

    @Test
    public void testGetMoveHistory() throws Exception {
        assertEquals("Get move history", facade.getMoveHistory(), game.getGameplayLog());
    }
}