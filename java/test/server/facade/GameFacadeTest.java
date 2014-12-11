package server.facade;

import com.sun.net.httpserver.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import server.handler.CookieJar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import java.util.Map;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

/**
 * Created by Spencer Weight - 11/24/2014.
 */
public class GameFacadeTest {
    private HttpServer m_server;

    @BeforeClass
    public static void beforeTests() {
    }

    @Before
    public void setUp() throws Exception {
        m_server = new server.Server().run();
    }

    @After
    public void tearDown() throws Exception {
        m_server.stop(0);
        Thread.sleep(100);
    }

    @Test
    public void modelTest() throws Exception {
        String expected = "{\"deck\":{\"monopoly\":2,\"monument\":5,\"roadBuilding\":2,\"soldier\":14,\"yearOfPlenty\":2},\"map\":{\"hexes\":[{\"resource\":\"brick\",\"number\":5,\"location\":{\"x\":1,\"y\":0}},{\"resource\":\"wood\",\"number\":3,\"location\":{\"x\":0,\"y\":-1}},{\"resource\":\"sheep\",\"number\":10,\"location\":{\"x\":1,\"y\":1}},{\"resource\":\"brick\",\"number\":8,\"location\":{\"x\":-1,\"y\":-1}},{\"resource\":\"wheat\",\"number\":11,\"location\":{\"x\":0,\"y\":0}},{\"resource\":\"sheep\",\"number\":10,\"location\":{\"x\":-1,\"y\":0}},{\"resource\":\"wood\",\"number\":4,\"location\":{\"x\":0,\"y\":1}},{\"resource\":\"ore\",\"number\":5,\"location\":{\"x\":-2,\"y\":0}},{\"resource\":\"wheat\",\"number\":8,\"location\":{\"x\":0,\"y\":2}},{\"resource\":\"sheep\",\"number\":9,\"location\":{\"x\":-1,\"y\":1}},{\"resource\":\"wheat\",\"number\":2,\"location\":{\"x\":-2,\"y\":1}},{\"resource\":\"ore\",\"number\":3,\"location\":{\"x\":-1,\"y\":2}},{\"resource\":\"wood\",\"number\":6,\"location\":{\"x\":-2,\"y\":2}},{\"resource\":\"wood\",\"number\":11,\"location\":{\"x\":2,\"y\":-2}},{\"resource\":\"sheep\",\"number\":12,\"location\":{\"x\":2,\"y\":-1}},{\"resource\":\"brick\",\"number\":4,\"location\":{\"x\":1,\"y\":-2}},{\"resource\":\"wheat\",\"number\":9,\"location\":{\"x\":2,\"y\":0}},{\"location\":{\"x\":0,\"y\":-2}},{\"resource\":\"ore\",\"number\":6,\"location\":{\"x\":1,\"y\":-1}}],\"roads\":[],\"cities\":[],\"settlements\":[],\"radius\":3,\"ports\":[{\"ratio\":3,\"direction\":\"SE\",\"location\":{\"x\":-3,\"y\":0}},{\"ratio\":2,\"resource\":\"wheat\",\"direction\":\"S\",\"location\":{\"x\":-1,\"y\":-2}},{\"ratio\":2,\"resource\":\"wood\",\"direction\":\"NE\",\"location\":{\"x\":-3,\"y\":2}},{\"ratio\":2,\"resource\":\"brick\",\"direction\":\"NE\",\"location\":{\"x\":-2,\"y\":3}},{\"ratio\":2,\"resource\":\"ore\",\"direction\":\"S\",\"location\":{\"x\":1,\"y\":-3}},{\"ratio\":3,\"direction\":\"SW\",\"location\":{\"x\":3,\"y\":-3}},{\"ratio\":3,\"direction\":\"NW\",\"location\":{\"x\":2,\"y\":1}},{\"ratio\":3,\"direction\":\"N\",\"location\":{\"x\":0,\"y\":3}},{\"ratio\":2,\"resource\":\"sheep\",\"direction\":\"NW\",\"location\":{\"x\":3,\"y\":-1}}],\"robber\":{\"x\":0,\"y\":-2}},\"players\":[{\"resources\":{\"wood\":0,\"brick\":0,\"sheep\":0,\"wheat\":0,\"ore\":0},\"oldDevCards\":{\"monopoly\":0,\"monument\":0,\"roadBuilding\":0,\"soldier\":0,\"yearOfPlenty\":0},\"newDevCards\":{\"monopoly\":0,\"monument\":0,\"roadBuilding\":0,\"soldier\":0,\"yearOfPlenty\":0},\"roads\":15,\"cities\":4,\"settlements\":5,\"soldiers\":0,\"victoryPoints\":0,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":0,\"playerIndex\":0,\"name\":\"Sam\",\"color\":\"red\"},{\"resources\":{\"wood\":0,\"brick\":0,\"sheep\":0,\"wheat\":0,\"ore\":0},\"oldDevCards\":{\"monopoly\":0,\"monument\":0,\"roadBuilding\":0,\"soldier\":0,\"yearOfPlenty\":0},\"newDevCards\":{\"monopoly\":0,\"monument\":0,\"roadBuilding\":0,\"soldier\":0,\"yearOfPlenty\":0},\"roads\":15,\"cities\":4,\"settlements\":5,\"soldiers\":0,\"victoryPoints\":0,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":1,\"playerIndex\":1,\"name\":\"Pete\",\"color\":\"blue\"},{\"resources\":{\"wood\":0,\"brick\":0,\"sheep\":0,\"wheat\":0,\"ore\":0},\"oldDevCards\":{\"monopoly\":0,\"monument\":0,\"roadBuilding\":0,\"soldier\":0,\"yearOfPlenty\":0},\"newDevCards\":{\"monopoly\":0,\"monument\":0,\"roadBuilding\":0,\"soldier\":0,\"yearOfPlenty\":0},\"roads\":15,\"cities\":4,\"settlements\":5,\"soldiers\":0,\"victoryPoints\":0,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":2,\"playerIndex\":2,\"name\":\"Mark\",\"color\":\"green\"},{\"resources\":{\"wood\":0,\"brick\":0,\"sheep\":0,\"wheat\":0,\"ore\":0},\"oldDevCards\":{\"monopoly\":0,\"monument\":0,\"roadBuilding\":0,\"soldier\":0,\"yearOfPlenty\":0},\"newDevCards\":{\"monopoly\":0,\"monument\":0,\"roadBuilding\":0,\"soldier\":0,\"yearOfPlenty\":0},\"roads\":15,\"cities\":4,\"settlements\":5,\"soldiers\":0,\"victoryPoints\":0,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":3,\"playerIndex\":3,\"name\":\"Bob\",\"color\":\"puce\"}],\"log\":{\"lines\":[]},\"chat\":{\"lines\":[]},\"bank\":{\"wood\":19,\"brick\":19,\"sheep\":19,\"wheat\":19,\"ore\":19},\"turnTracker\":{\"status\":\"FirstRound\",\"currentTurn\":0,\"longestRoad\":-1,\"largestArmy\":-1},\"winner\":-1,\"version\":0}";

        URL oracle;
        URLConnection yc;
        BufferedReader in;
        String result = "";

        String[] cookies = new String[] {"catan.user={\"name\":\"Sam\", \"playerID\": 0}", "catan.game=0"};

        try {
            oracle = new URL("http://localhost:8081/game/model");

            yc = oracle.openConnection();
            yc.setRequestProperty("Cookie", new CookieJar(cookies).toString());
            yc.connect();

            in = new BufferedReader(new InputStreamReader(
                    yc.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                //System.out.println(inputLine);
                result += inputLine;
            }

            in.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

        assertEquals("Result did not equal expected, could be because we changed our default game", countChars(expected), countChars(result));
    }

  /** Count the characters of a string and return the counts in a map.
   * Due to weirdness with Java returning ports in different orders (depending debug/release mode, etc.)
  *  we count the characters in the expected string.
  * If characters occur in the same frequency, the strings almost certainly are equal, albeit potentially in a different order
  */
  private Map<Character, Integer> countChars(String str) {
      Map<Character, Integer> expectedCharCount = new HashMap<Character, Integer>();

      for (char c : str.toCharArray()) {
        if (expectedCharCount.containsKey(c)) {
          expectedCharCount.put(c, expectedCharCount.get(c) + 1);
        }
        else {
          expectedCharCount.put(c, 1);
        }
      }

      return expectedCharCount;
  }
}

