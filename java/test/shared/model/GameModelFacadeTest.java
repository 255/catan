package shared.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.locations.*;

import static org.junit.Assert.*;

public class GameModelFacadeTest {
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

        game.setCurrentPlayer(game.getPlayers().get(1));
        game.setLocalPlayer(game.getPlayers().get(1));
        assertFalse("Not enough resources", facade.canPlaceRoad(edge1));

        game.setCurrentPlayer(game.getPlayers().get(2));
        game.setLocalPlayer(game.getPlayers().get(2));
        assertFalse("No road pieces", facade.canPlaceRoad(edge1));

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
    public void testCanPlaceInitialRoad() throws Exception {
        IGame initGame = initAGame("sample/empty_board.json");
    }

    @Test
    public void testGetCurrentPlayer() throws Exception {

    }

    @Test
    public void testGetPlayerResources() throws Exception {

    }

    @Test
    public void testGetPlayerPorts() throws Exception {

    }

    @Test
    public void testGetChatHistory() throws Exception {

    }

    @Test
    public void testGetMoveHistory() throws Exception {

    }

    @Test
    public void testCanBuyCity() throws Exception {
        assertTrue("Player can buy city", facade.canBuyCity());

        game.setLocalPlayer(game.getPlayers().get(1));
        assertFalse("Not your turn", facade.canBuyCity());

        game.setCurrentPlayer(game.getPlayers().get(1));
        assertFalse("Not enough resources", facade.canBuyCity());

        game.setCurrentPlayer(game.getPlayers().get(2));
        game.setLocalPlayer(game.getPlayers().get(2));
        assertFalse("Not enough city pieces", facade.canBuyCity());

        game.setCurrentPlayer(game.getPlayers().get(0));
        game.setLocalPlayer(game.getPlayers().get(0));
        game.setGameState(GameState.DISCARDING);
        assertFalse("Not playing phase", facade.canBuyCity());
    }

    @Test
    public void testCanBuyRoad() throws Exception {
        assertTrue("Player can buy road", facade.canBuyRoad());

        game.setLocalPlayer(game.getPlayers().get(1));
        assertFalse("Not your turn", facade.canBuyRoad());

        game.setCurrentPlayer(game.getPlayers().get(1));
        assertFalse("Not enough resources", facade.canBuyRoad());

        game.setCurrentPlayer(game.getPlayers().get(2));
        game.setLocalPlayer(game.getPlayers().get(2));
        assertFalse("Not enough road pieces", facade.canBuyRoad());

        game.setCurrentPlayer(game.getPlayers().get(0));
        game.setLocalPlayer(game.getPlayers().get(0));
        game.setGameState(GameState.DISCARDING);
        assertFalse("Not playing phase", facade.canBuyRoad());
    }

    @Test
    public void testCanBuySettlement() throws Exception {
        assertTrue("Player can buy settlement", facade.canBuySettlement());

        game.setLocalPlayer(game.getPlayers().get(1));
        assertFalse("Not your turn", facade.canBuySettlement());

        game.setCurrentPlayer(game.getPlayers().get(1));
        assertFalse("Not enough resources", facade.canBuySettlement());

        game.setCurrentPlayer(game.getPlayers().get(2));
        game.setLocalPlayer(game.getPlayers().get(2));
        assertFalse("Not enough settlement pieces", facade.canBuySettlement());

        game.setCurrentPlayer(game.getPlayers().get(0));
        game.setLocalPlayer(game.getPlayers().get(0));
        game.setGameState(GameState.DISCARDING);
        assertFalse("Not playing phase", facade.canBuySettlement());
    }

    @Test
    public void testCanBuyDevCard() throws Exception {
        assertTrue("Player can buy dev card", facade.canBuyDevCard());

        game.setLocalPlayer(game.getPlayers().get(1));
        assertFalse("Not your turn", facade.canBuyDevCard());

        game.setCurrentPlayer(game.getPlayers().get(1));
        assertFalse("Not enough resources", facade.canBuyDevCard());

        game.setCurrentPlayer(game.getPlayers().get(0));
        game.setLocalPlayer(game.getPlayers().get(0));
        game.setDevCards(new DevCardHand());
        assertFalse("No available dev cards", facade.canBuyDevCard());

        game.setCurrentPlayer(game.getPlayers().get(0));
        game.setLocalPlayer(game.getPlayers().get(0));
        game.setGameState(GameState.DISCARDING);
        assertFalse("Not playing phase", facade.canBuyDevCard());
    }

    @Test
    public void testCanAcceptTrade() throws Exception {

    }

    @Test
    public void testCanPlayDevCard() throws Exception {
        assertTrue("Player can play dev card", facade.canPlayDevCard());

        game.setCurrentPlayer(game.getPlayers().get(1));
        game.setLocalPlayer(game.getPlayers().get(1));
        assertFalse("Already played dev card", facade.canPlayDevCard());

        game.setLocalPlayer(game.getPlayers().get(2));
        assertFalse("Not your turn", facade.canPlayDevCard());

        game.setCurrentPlayer(game.getPlayers().get(2));
        assertFalse("No dev cards", facade.canPlayDevCard());

        game.setCurrentPlayer(game.getPlayers().get(0));
        game.setLocalPlayer(game.getPlayers().get(0));
        game.setGameState(GameState.DISCARDING);
        assertFalse("Not playing phase", facade.canPlayDevCard());
    }

    @Test
    public void testCanPlayMonopoly() throws Exception {
        assertTrue("Player can play monopoly", facade.canPlayMonopoly(ResourceType.BRICK));

        game.setCurrentPlayer(game.getPlayers().get(1));
        game.setLocalPlayer(game.getPlayers().get(1));
        assertFalse("Already played dev card", facade.canPlayMonopoly(ResourceType.BRICK));

        game.setLocalPlayer(game.getPlayers().get(2));
        assertFalse("Not your turn", facade.canPlayMonopoly(ResourceType.BRICK));

        game.setCurrentPlayer(game.getPlayers().get(2));
        assertFalse("No monopoly card", facade.canPlayMonopoly(ResourceType.BRICK));

        game.setCurrentPlayer(game.getPlayers().get(0));
        game.setLocalPlayer(game.getPlayers().get(0));
        game.setGameState(GameState.DISCARDING);
        assertFalse("Not playing phase", facade.canPlayMonopoly(ResourceType.BRICK));
    }

    @Test
    public void testCanPlaySoldier() throws Exception {
        // make sure it fails if the local player is not playing
        game.setLocalPlayer(game.getPlayers().get(2));
        assertFalse("A player tried to play a solider when it was not their turn", facade.canPlaySoldier(new HexLocation(0, 2)));

        // make sure it fails if the player has a soldier card, but it's not playable
        game.setLocalPlayer(game.getPlayers().get(0));
        game.getLocalPlayer().getPlayableDevCards().remove(DevCardType.SOLDIER);
        game.getLocalPlayer().getPlayableDevCards().remove(DevCardType.SOLDIER);
        game.getLocalPlayer().getPlayableDevCards().remove(DevCardType.SOLDIER);
        assertFalse("A player tried to play a soldier when it was not in their playable hand", facade.canPlaySoldier(new HexLocation(0, 2)));

        // make sure it fails if the player has no soldier card
        game.getLocalPlayer().getNewDevCards().remove(DevCardType.SOLDIER);
        game.getLocalPlayer().getNewDevCards().remove(DevCardType.SOLDIER);
        game.getLocalPlayer().getNewDevCards().remove(DevCardType.SOLDIER);
        assertFalse("A player tried to play a soldier when they didn't have one", facade.canPlaySoldier(new HexLocation(0, 2)));

        // make sure it fails if the hex location has the robber on it.
        game.getLocalPlayer().getPlayableDevCards().add(DevCardType.SOLDIER);
        assertFalse("A player tried to play a soldier on the hex that the robber was already on", facade.canPlaySoldier(new HexLocation(0, -2)));

        game.getLocalPlayer().getPlayableDevCards().add(DevCardType.SOLDIER);
        assertTrue("A player can play a soldier because they have one in their playable hand, and the hex is valid", facade.canPlaySoldier(new HexLocation(0, 2)));
    }

    @Test
    public void testCanPlayYearOfPlenty() throws Exception {

    }

    @Test
    public void testCanPlayMonument() throws Exception {
        assertTrue("Player can play monument", facade.canPlayMonument());

        game.setCurrentPlayer(game.getPlayers().get(1));
        game.setLocalPlayer(game.getPlayers().get(1));
        assertFalse("Already played dev card", facade.canPlayMonument());

        game.setLocalPlayer(game.getPlayers().get(2));
        assertFalse("Not your turn", facade.canPlayMonument());

        game.setCurrentPlayer(game.getPlayers().get(2));
        assertFalse("No monument card", facade.canPlayMonument());

        game.setCurrentPlayer(game.getPlayers().get(0));
        game.setLocalPlayer(game.getPlayers().get(0));
        game.setGameState(GameState.DISCARDING);
        assertFalse("Not playing phase", facade.canPlayMonument());
    }

    @Test
    public void testCanPlayRoadBuilding() throws Exception {

    }

    @Test
    public void testCanRobPlayer() throws Exception {

    }

    @Test
    public void testIsFreeRound() throws Exception {

    }

    //************************//
    // private helper methods //
    //************************//

    private IGame initAGame(String jsonTestFile) throws Exception {
        String emptyBoardJSON = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(jsonTestFile)));
        ModelInitializer initModel = new ModelInitializer();
        initModel.initializeClientModel(emptyBoardJSON, 0);
        return GameModelFacade.getInstance().getGame();
    }
}