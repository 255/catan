package shared.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
                        "\"playerIndex\":" + "2," +
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

    }
}