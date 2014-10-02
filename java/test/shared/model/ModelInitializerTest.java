package shared.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

public class ModelInitializerTest {

    private IGame m_game;
    private IModelInitializer m_modelInitializer;
    @Before
    public void setUp() throws Exception {
        m_modelInitializer = new ModelInitializer();
        String clientModel =
                "{" +
                        "\"deck\":" + "{" +
                        "\"yearOfPlenty\":" + "2" +"," +
                        "\"monopoly\":" + "2" + "," +
                        "\"soldier\":" + "14" + "," +
                        "\"roadBuilding\":" + "2" + "," +
                        "\"monument\":" + "5" + "," +
                        "}" + "," +
                        "\"map\":" + "{" +
                        "\"hexes\":" + "[" +
                        "{" +
                        "\"location\":" + "{" +
                        "\"x\":" + "0" + "," +
                        "\"y\":" + "-2" +
                        "}" +
                        "}" + "," +
                        "{" +
                        "\"resource\":" + "\"brick\"" + "," +
                        "\"location\":" + "{" +
                        "\"x\":" + "1" + "," +
                        "\"y\":" + "-2" +
                        "}" +"," +
                        "\"number\":" + "4" +
                        "}" + "," +
                        "{" +
                        "\"resource\":" + "\"wood\"" + "," +
                        "\"location\":" + "{" +
                        "\"x\":" + "2" + "," +
                        "\"y\":" + "-2" +
                        "}" + "," +
                        "\"number\":" + "11" +
                        "}" + "," +
                        "{" +
                        "\"resource\":" + "\"brick\"" + "," +
                        "\"location\":" + "{" +
                        "\"x\":" + "-1" + "," +
                        "\"y\":" + "-1" +
                        "}" + "," +
                        "\"number\":" + "8" +
                        "}" + "," +
                        "{" +
                        "\"resource\":" + "\"wood\"" + "," +
                        "\"location\":" + "{" +
                        "\"x\":" + "0" + "," +
                        "\"y\":" + "-1" +
                        "}" + "," +
                        "\"number\":" + "3" +
                        "}" + "," +
                        "{" +
                        "\"resource\":" + "\"ore\"" + "," +
                        "\"location\":" + "{" +
                        "\"x\":" + "1" + "," +
                        "\"y\":" + "-1" +
                        "}" + "," +
                        "\"number\":" + "9" +
                        "}" + "," +
                        "{" +
                        "\"resource\":" + "\"sheep\"," +
                        "\"location\":" + "{" +
                        "\"x\":" + "2" + "," +
                        "\"y\":" + "-1" +
                        "}" + "," +
                        "\"number\":" + "12" +
                        "}" + "," +
                        "{" +
                        "\"resource\":" + "\"ore\"" + "," +
                        "\"location\":" + "{" +
                        "\"x\":" + "-2" + "," +
                        "\"y\":" + "0" +
                        "}" + "," +
                        "\"number\":" + "5" +
                        "}" + "," +
                        "{" +
                        "\"resource\":" + "\"sheep\"" + "," +
                        "\"location\":" + "{" +
                        "\"x\":" + "-1" + "," +
                        "\"y\":" + "0" +
                        "}" + "," +
                        "\"number\":" + "10" +
                        "}" + "," +
                        "{" +
                        "\"resource\":" + "\"wheat\"" + "," +
                        "\"location\":" + "{" +
                        "\"x\":" + "0" + "," +
                        "\"y\":" + "0" +
                        "}" + "," +
                        "\"number\":" + "11" +
                        "}" + "," +
                        "{" +
                        "\"resource\":" + "\"brick\"" + "," +
                        "\"location\":" + "{" +
                        "\"x\":" + "1" + "," +
                        "\"y\":" + "0" +
                        "}" + "," +
                        "\"number\":" + "5" +
                        "}" + "," +
                        "{" +
                        "\"resource\":" + "\"wheat\"" + "," +
                        "\"location\":" + "{" +
                        "\"x\":" + "2" + "," +
                        "\"y\":" + "0" +
                        "}" + "," +
                        "\"number\":" + "6" +
                        "}" + "," +
                        "{" +
                        "\"resource\":" + "\"wheat\"" + "," +
                        "\"location\":" + "{" +
                        "\"x\":" + "-2" + "," +
                        "\"y\":" + "1" +
                        "}" + "," +
                        "\"number\":" + "2" +
                        "}" + "," +
                        "{" +
                        "\"resource\":" + "\"sheep\"" + "," +
                        "\"location\":" + "{" +
                        "\"x\":" + "-1" + "," +
                        "\"y\":" + "1" +
                        "}" + "," +
                        "\"number\":" + "9" +
                        "}" + "," +
                        "{" +
                        "\"resource\":" + "\"wood\"" + "," +
                        "\"location\":" + "{" +
                        "\"x\":" + "0" + "," +
                        "\"y\":" + "1" +
                        "}" + "," +
                        "\"number\":" + "4" +
                        "}" + "," +
                        "{" +
                        "\"resource\":" + "\"sheep\"" + "," +
                        "\"location\":" + "{" +
                        "\"x\":" + "1" + "," +
                        "\"y\":" + "1" +
                        "}" + "," +
                        "\"number\":" + "10" +
                        "}" + "," +
                        "{" +
                        "\"resource\":" + "\"wood\"" + "," +
                        "\"location\":" + "{" +
                        "\"x\":" + "-2" + "," +
                        "\"y\":" + "2" +
                        "}" + "," +
                        "\"number\":" + "6" +
                        "}" + "," +
                        "{" +
                        "\"resource\":" + "\"ore\"" + "," +
                        "\"location\":" + "{" +
                        "\"x\":" + "-1" + "," +
                        "\"y\":" + "2" +
                        "}" + "," +
                        "\"number\":" + "3" +
                        "}" + "," +
                        "{" +
                        "\"resource\":" + "\"wheat\"" + "," +
                        "\"location\":" + "{" +
                        "\"x\":" + "0" + "," +
                        "\"y\":" + "2" +
                        "}" + "," +
                        "\"number\":" + "8" +
                        "}" +
                        "]" + "," +
                        "\"roads\":" + "[]," +
                        "\"cities\":" + "[]," +
                        "\"settlements\":" + "[]," +
                        "\"radius\":" + "3," +
                        "\"ports\":" + "[" +
                        "{" +
                        "\"ratio\":" + "2," +
                        "\"resource\":" + "\"ore\"," +
                        "\"direction\":" + "\"S\"," +
                        "\"location\":" + "{" +
                        "\"x\":" + "1," +
                        "\"y\":" + "-3" +
                        "}" +
                        "}," +
                        "{" +
                        "\"ratio\":" +  "3," +
                        "\"direction\":" + "\"SW\"," +
                        "\"location\":" + "{" +
                        "\"x\":" +  "3," +
                        "\"y\":" +  "-3" +
                        "}" +
                        "}," +
                        "{" +
                        "\"ratio\":" + "2," +
                        "\"resource\":" + "\"wood\"," +
                        "\"direction\":" + "\"NE\"," +
                        "\"location\":" + "{" +
                        "\"x\":" + "-3," +
                        "\"y\":" + "2" +
                        "}" +
                        "}," +
                        "{" +
                        "\"ratio\":" + "3," +
                        "\"direction\":" + "\"NW\"," +
                        "\"location\":" + "{" +
                        "\"x\":" + "2," +
                        "\"y\":" + "1" +
                        "}" +
                        "}," +
                        "{" +
                        "\"ratio\":" + "3," +
                        "\"direction\":" + "\"N\"," +
                        "\"location\":" + "{" +
                        "\"x\":" + "0," +
                        "\"y\":" +  "3" +
                        "}" +
                        "}," +
                        "{" +
                        "\"ratio\":" + "3," +
                        "\"direction\":" + "\"SE\"," +
                        "\"location\":" + "{" +
                        "\"x\":" + "-3," +
                        "\"y\":" + "0" +
                        "}" +
                        "}," +
                        "{" +
                        "\"ratio\":" + "2," +
                        "\"resource\":" + "\"sheep\"," +
                        "\"direction\":" + "\"NW\"," +
                        "\"location\":" + "{" +
                        "\"x\":" + "3," +
                        "\"y\":" + "-1" +
                        "}" +
                        "}," +
                        "{" +
                        "\"ratio\":" + "2," +
                        "\"resource\":" + "\"wheat\"," +
                        "\"direction\":" + "\"S\"," +
                        "\"location\":" + "{" +
                        "\"x\":" + "-1," +
                        "\"y\":" + "-2" +
                        "}" +
                        "}," +
                        "{" +
                        "\"ratio\":" + "2," +
                        "\"resource\":" + "\"brick\"," +
                        "\"direction\":" + "\"NE\"," +
                        "\"location\":" + "{" +
                        "\"x\":" + "-2," +
                        "\"y\":" + "3" +
                        "}" +
                        "}" +
                        "]," +
                        "\"robber\":" + "{" +
                        "\"x\":" + "0," +
                        "\"y\":" + "-2" +
                        "}" +
                        "}," +
                        "\"players\":" + "[" +
                        "{" +
                        "\"resources\":" + "{" +
                        "\"brick\":" + "0," +
                        "\"wood\":" + "0," +
                        "\"sheep\":" + "0," +
                        "\"wheat\":" + "0," +
                        "\"ore\":" + "0" +
                        "}," +
                        "\"oldDevCards\":" + "{" +
                        "\"yearOfPlenty\":" + "0," +
                        "\"monopoly\":" + "0," +
                        "\"soldier\":" + "0," +
                        "\"roadBuilding\":" + "0," +
                        "\"monument\":" + "0" +
                        "}," +
                        "\"newDevCards\":" + "{" +
                        "\"yearOfPlenty\":" + "0," +
                        "\"monopoly\":" + "0," +
                        "\"soldier\":" + "0," +
                        "\"roadBuilding\":" + "0," +
                        "\"monument\":" + "0" +
                        "}," +
                        "\"roads\":" + "15," +
                        "\"cities\":" + "4," +
                        "\"settlements\":" + "5," +
                        "\"soldiers\":" + "0," +
                        "\"victoryPoints\":" + "0," +
                        "\"monuments\":" + "0," +
                        "\"playedDevCard\":" + "false," +
                        "\"discarded\":" + "false," +
                        "\"playerID\":" + "0," +
                        "\"playerIndex\":" + "0," +
                        "\"name\":" + "\"Sam\"," +
                        "\"color\":" + "\"orange\"" +
                        "}," +
                        "{" +
                        "\"resources\":" + "{" +
                        "\"brick\":" + "0," +
                        "\"wood\":" + "0," +
                        "\"sheep\":" + "0," +
                        "\"wheat\":" + "0," +
                        "\"ore\":" + "0" +
                        "}," +
                        "\"oldDevCards\":" + "{" +
                        "\"yearOfPlenty\":" + "0," +
                        "\"monopoly\":" + "0," +
                        "\"soldier\":" + "0," +
                        "\"roadBuilding\":" + "0," +
                        "\"monument\":" + "0" +
                        "}," +
                        "\"newDevCards\":" + "{" +
                        "\"yearOfPlenty\":" + "0," +
                        "\"monopoly\":" + "0," +
                        "\"soldier\":" + "0," +
                        "\"roadBuilding\":" + "0," +
                        "\"monument\":" + "0" +
                        "}," +
                        "\"roads\":" + "15," +
                        "\"cities\":" + "4," +
                        "\"settlements\":" + "5," +
                        "\"soldiers\":" + "0," +
                        "\"victoryPoints\":" + "0," +
                        "\"monuments\":" + "0," +
                        "\"playedDevCard\":" + "false," +
                        "\"discarded\":" + "false," +
                        "\"playerID\":" + "1," +
                        "\"playerIndex\":" + "1," +
                        "\"name\":" + "\"Brooke\"," +
                        "\"color\":" + "\"blue\"" +
                        "}," +
                        "{" +
                        "\"resources\":" + "{" +
                        "\"brick\":" + "0," +
                        "\"wood\":" + "0," +
                        "\"sheep\":" + "0," +
                        "\"wheat\":" + "0," +
                        "\"ore\":" + "0" +
                        "}," +
                        "\"oldDevCards\":" + "{" +
                        "\"yearOfPlenty\":" + "0," +
                        "\"monopoly\":" + "0," +
                        "\"soldier\":" + "0," +
                        "\"roadBuilding\":" + "0," +
                        "\"monument\":" + "0" +
                        "}," +
                        "\"newDevCards\":" + "{" +
                        "\"yearOfPlenty\":" + "0," +
                        "\"monopoly\":" + "0," +
                        "\"soldier\":" + "0," +
                        "\"roadBuilding\":" + "0," +
                        "\"monument\":" + "0" +
                        "}," +
                        "\"roads\":" + "15," +
                        "\"cities\":" + "4," +
                        "\"settlements\":" + "5," +
                        "\"soldiers\":" + "0," +
                        "\"victoryPoints\":" + "0," +
                        "\"monuments\":" + "0," +
                        "\"playedDevCard\":" + "false," +
                        "\"discarded\":" + "false," +
                        "\"playerID\":" + "10," +
                        "\"playerIndex\":" + "2," +
                        "\"name\":" + "\"Pete\"," +
                        "\"color\":" + "\"red\"" +
                        "}," +
                        "{" +
                        "\"resources\":" + "{" +
                        "\"brick\":" + "0," +
                        "\"wood\":" + "0," +
                        "\"sheep\":" + "0," +
                        "\"wheat\":" + "0," +
                        "\"ore\":" + "0" +
                        "}," +
                        "\"oldDevCards\":" + "{" +
                        "\"yearOfPlenty\":" + "0," +
                        "\"monopoly\":" + "0," +
                        "\"soldier\":" + "0," +
                        "\"roadBuilding\":" + "0," +
                        "\"monument\":" + "0" +
                        "}," +
                        "\"newDevCards\":" + "{" +
                        "\"yearOfPlenty\":" + "0," +
                        "\"monopoly\":" + "0," +
                        "\"soldier\":" + "0," +
                        "\"roadBuilding\":" + "0," +
                        "\"monument\":" + "0" +
                        "}," +
                        "\"roads\":" + "15," +
                        "\"cities\":" + "4," +
                        "\"settlements\":" + "5," +
                        "\"soldiers\":" + "0," +
                        "\"victoryPoints\":" + "0," +
                        "\"monuments\":" + "0," +
                        "\"playedDevCard\":" + "false," +
                        "\"discarded\":" + "false," +
                        "\"playerID\":" + "11," +
                        "\"playerIndex\":" + "3," +
                        "\"name\":" + "\"Mark\"," +
                        "\"color\":" + "\"green\"" +
                        "}," + "]," +
                        "\"log\":" + "{" +
                        "\"lines\":" + "[]" +
                        "}," +
                        "\"chat\":" + "{" +
                        "\"lines\":" + "[]" +
                        "}," +
                        "\"bank\":" + "{" +
                        "\"brick\":" + "24," +
                        "\"wood\":" + "24," +
                        "\"sheep\":" + "24," +
                        "\"wheat\":" + "24," +
                        "\"ore\":" + "24" +
                        "}," +
                        "\"turnTracker\":" + "{" +
                        "\"status\":" + "\"FirstRound\"," +
                        "\"currentTurn\":" + "0," +
                        "\"longestRoad\":" + "-1," +
                        "\"largestArmy\":" + "-1" +
                        "}," +
                        "\"winner\":" + "-1," +
                        "\"version\":" + "0" +
                        "}";
        m_modelInitializer.initializeClientModel(clientModel);
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

        List<IPlayer> playerList = m_game.getPlayers();

        IPlayer testPlayer = playerList.get(0);
        IResourceBank testPlayerResourceCards = testPlayer.getResources();
        assertEquals("Incorrect number of Brick cards in player hand", 0, testPlayerResourceCards.getCount(ResourceType.BRICK));
        assertEquals("Incorrect number of Wood cards in player hand", 0, testPlayerResourceCards.getCount(ResourceType.WOOD));
        assertEquals("Incorrect number of Sheep cards in player hand", 0, testPlayerResourceCards.getCount(ResourceType.SHEEP));
        assertEquals("Incorrect number of Wheat cards in player hand", 0, testPlayerResourceCards.getCount(ResourceType.WHEAT));
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
        assertEquals("Incorrect number of roads in player's bank", 15, playerPieceBank.availableRoads());
        assertEquals("Incorrect number of cities in player's bank", 4, playerPieceBank.availableCities());
        assertEquals("Incorrect number of settlements in player's bank", 5, playerPieceBank.availableSettlements());

        assertEquals("Incorrect number of soldiers player has played", 0, testPlayer.getSoldiers());
        assertEquals("Incorrect number of monuments player has played", 0, testPlayer.getMonuments());
        assertEquals("Incorrect number of victory points player", 0, testPlayer.getVictoryPoints());

        assertEquals("Incorrect player id", 0, testPlayer.getId());
        assertEquals("Incorrect player index", 0, testPlayer.getIndex());
        assertEquals("Incorrect player name", "Sam", testPlayer.getName());
        assertEquals("Incorrect player color", CatanColor.ORANGE, testPlayer.getColor());

        testPlayer = playerList.get(1);
        assertEquals("Incorrect player id", 1, testPlayer.getId());
        assertEquals("Incorrect player index", 1, testPlayer.getIndex());
        assertEquals("Incorrect player name", "Brooke", testPlayer.getName());
        assertEquals("Incorrect player color", CatanColor.BLUE, testPlayer.getColor());

        testPlayer = playerList.get(2);
        assertEquals("Incorrect player id", 10, testPlayer.getId());
        assertEquals("Incorrect player index", 2, testPlayer.getIndex());
        assertEquals("Incorrect player name", "Pete", testPlayer.getName());
        assertEquals("Incorrect player color", CatanColor.RED, testPlayer.getColor());

        testPlayer = playerList.get(3);
        assertEquals("Incorrect player id", 3, testPlayer.getId());
        assertEquals("Incorrect player index", 11, testPlayer.getIndex());
        assertEquals("Incorrect player name", "Mark", testPlayer.getName());
        assertEquals("Incorrect player color", CatanColor.GREEN, testPlayer.getColor());

 //Robber. Ports. Map

        // Test that the Resource Bank is correctly deserialized
        IResourceBank testResourceBank = m_game.getResourceBank();
        assertEquals("Incorrect number of Brick cards", 24, testResourceBank.getCount(ResourceType.BRICK));
        assertEquals("Incorrect number of Wood cards", 24, testResourceBank.getCount(ResourceType.WOOD));
        assertEquals("Incorrect number of Sheep cards", 24, testResourceBank.getCount(ResourceType.SHEEP));
        assertEquals("Incorrect number of Wheat cards", 24, testResourceBank.getCount(ResourceType.WHEAT));
        assertEquals("Incorrect number of Ore cards", 24, testResourceBank.getCount(ResourceType.ORE));

    }
}