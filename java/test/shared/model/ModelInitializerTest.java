package shared.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.model.ILog.ILogMessage;

import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

public class ModelInitializerTest {

    private IGame m_game;
    private IModelInitializer m_modelInitializer;
    @Before
    public void setUp() throws Exception {
        m_modelInitializer = new ModelInitializer();
        String clientModel = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get("sample/test_1.json")));
        m_modelInitializer.initializeClientModel(clientModel, 0);
        m_game = GameFacade.getFacadeInstance().getGame();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testConvertJSONtoModel() throws Exception {
        // Test that the Development Card deck is correctly deserialized
        IDevCardHand testDevCardHand = m_game.getDevCards();
        assertEquals("Incorrect number of Monopoly cards", 2, testDevCardHand.getCount(DevCardType.MONOPOLY));
        assertEquals("Incorrect number of Soldier cards", 14, testDevCardHand.getCount(DevCardType.SOLDIER));
        assertEquals("Incorrect number of Year of Plenty cards", 2, testDevCardHand.getCount(DevCardType.YEAR_OF_PLENTY));
        assertEquals("Incorrect number of Road Building cards", 2, testDevCardHand.getCount(DevCardType.ROAD_BUILD));
        assertEquals("Incorrect number of Monument cards", 5, testDevCardHand.getCount(DevCardType.MONUMENT));

        // Tests that the players have been correctly deserialized
        List<IPlayer> playerList = m_game.getPlayers();

        IPlayer testPlayer = playerList.get(0);
        IResourceBank testPlayerResourceCards = testPlayer.getResources();
        assertEquals("Incorrect number of Brick cards in player hand", 0, testPlayerResourceCards.getCount(ResourceType.BRICK));
        assertEquals("Incorrect number of Wood cards in player hand", 1, testPlayerResourceCards.getCount(ResourceType.WOOD));
        assertEquals("Incorrect number of Sheep cards in player hand", 1, testPlayerResourceCards.getCount(ResourceType.SHEEP));
        assertEquals("Incorrect number of Wheat cards in player hand", 1, testPlayerResourceCards.getCount(ResourceType.WHEAT));
        assertEquals("Incorrect number of Ore cards in player hand", 0, testPlayerResourceCards.getCount(ResourceType.ORE));

        IDevCardHand testPlayerOldDevCardHand = testPlayer.getPlayableDevCards();
        assertEquals("Incorrect number of Monopoly cards in player hand", 0, testPlayerOldDevCardHand.getCount(DevCardType.MONOPOLY));
        assertEquals("Incorrect number of Soldier cards in player hand", 0, testPlayerOldDevCardHand.getCount(DevCardType.SOLDIER));
        assertEquals("Incorrect number of Year of Plenty cards in player hand", 0, testPlayerOldDevCardHand.getCount(DevCardType.YEAR_OF_PLENTY));
        assertEquals("Incorrect number of Road Building cards in player hand", 0, testPlayerOldDevCardHand.getCount(DevCardType.ROAD_BUILD));
        assertEquals("Incorrect number of Monument cards in player hand", 0, testPlayerOldDevCardHand.getCount(DevCardType.MONUMENT));

        IDevCardHand testPlayerNewDevCardHand = testPlayer.getNewDevCards();
        assertEquals("Incorrect number of Monopoly cards in player hand", 0, testPlayerNewDevCardHand.getCount(DevCardType.MONOPOLY));
        assertEquals("Incorrect number of Soldier cards in player hand", 0, testPlayerNewDevCardHand.getCount(DevCardType.SOLDIER));
        assertEquals("Incorrect number of Year of Plenty cards in player hand", 0, testPlayerNewDevCardHand.getCount(DevCardType.YEAR_OF_PLENTY));
        assertEquals("Incorrect number of Road Building cards in player hand", 0, testPlayerNewDevCardHand.getCount(DevCardType.ROAD_BUILD));
        assertEquals("Incorrect number of Monument cards in player hand", 0, testPlayerNewDevCardHand.getCount(DevCardType.MONUMENT));

        IPieceBank playerPieceBank = testPlayer.getPieceBank();
        assertEquals("Incorrect number of roads in player's bank", 13, playerPieceBank.availableRoads());
        assertEquals("Incorrect number of cities in player's bank", 4, playerPieceBank.availableCities());
        assertEquals("Incorrect number of settlements in player's bank", 3, playerPieceBank.availableSettlements());

        assertEquals("Incorrect number of soldiers player has played", 0, testPlayer.getSoldiers());
        assertEquals("Incorrect number of monuments player has played", 0, testPlayer.getMonuments());
        assertEquals("Incorrect number of victory points player", 2, testPlayer.getVictoryPoints());

        assertEquals("Incorrect player id", 0, testPlayer.getId());
        assertEquals("Incorrect player index", 0, testPlayer.getIndex());
        assertEquals("Incorrect player name", "Sam", testPlayer.getName());
        assertEquals("Incorrect player color", CatanColor.ORANGE, testPlayer.getColor());

        testPlayer = playerList.get(1);
        assertEquals("Incorrect player id", 1, testPlayer.getId());
        assertEquals("Incorrect player index", 1, testPlayer.getIndex());
        assertEquals("Incorrect player name", "Brooke", testPlayer.getName());
        assertEquals("Incorrect player color", CatanColor.PUCE, testPlayer.getColor());

        testPlayer = playerList.get(2);
        assertEquals("Incorrect player id", 10, testPlayer.getId());
        assertEquals("Incorrect player index", 2, testPlayer.getIndex());
        assertEquals("Incorrect player name", "Pete", testPlayer.getName());
        assertEquals("Incorrect player color", CatanColor.RED, testPlayer.getColor());

        testPlayer = playerList.get(3);
        assertEquals("Incorrect player id", 11, testPlayer.getId());
        assertEquals("Incorrect player index", 3, testPlayer.getIndex());
        assertEquals("Incorrect player name", "Mark", testPlayer.getName());
        assertEquals("Incorrect player color", CatanColor.GREEN, testPlayer.getColor());

        // Map test
        ICatanMap testCatanMap = m_game.getMap();
        Collection<ITile> testTiles = testCatanMap.getTiles();

        // Port tests

        // Road tests
        Collection<IRoad> testRoads = testCatanMap.getRoads();

        // Settlements tests

        // Robber tests

        // Tests the GamePlay Log
        ILog testMoveLog = m_game.getGameplayLog();
        List<ILogMessage> testLogMessages = testMoveLog.getMessages();

        ILogMessage testMessage = testLogMessages.get(0);

        assertEquals("Message is incorrect", "Sam built a road", testMessage.getMessage());
        assertEquals("Message came from incorrect player", 0, testMessage.getPlayer().getIndex());

        testMessage = testLogMessages.get(1);
        assertEquals("Message is incorrect", "Sam built a settlement", testMessage.getMessage());
        assertEquals("Message came from incorrect player", 0, testMessage.getPlayer().getIndex());

        // Test that the Resource Bank is correctly deserialized
        IResourceBank testResourceBank = m_game.getResourceBank();
        assertEquals("Incorrect number of Brick cards", 23, testResourceBank.getCount(ResourceType.BRICK));
        assertEquals("Incorrect number of Wood cards", 21, testResourceBank.getCount(ResourceType.WOOD));
        assertEquals("Incorrect number of Sheep cards", 20, testResourceBank.getCount(ResourceType.SHEEP));
        assertEquals("Incorrect number of Wheat cards", 22, testResourceBank.getCount(ResourceType.WHEAT));
        assertEquals("Incorrect number of Ore cards", 22, testResourceBank.getCount(ResourceType.ORE));

        // Tests that the game state has been correctly deserialized.
        assertEquals("Game state was Rolling", GameState.ROLLING, m_game.getGameState());
        assertEquals("Index of current player is 0", 0, m_game.getCurrentPlayer().getIndex());
        assertEquals("No winnner. Should return null", null, m_game.getWinner());

    }
}