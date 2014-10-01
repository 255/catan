package shared.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import shared.definitions.HexType;
import shared.definitions.PortType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class CatanMapTest {
    private CatanMap map = null;
    private IPlayer player1 = null;
    private IPlayer player2 = null;

    private HexType calculateType(HexLocation loc) {
        switch (loc.hashCode() % 6) {
            case 0: return HexType.BRICK;
            case 1: return HexType.WOOD;
            case 2: return HexType.WHEAT;
            case 3: return HexType.ORE;
            case 4: return HexType.SHEEP;
            case 5: return HexType.DESERT;
            default:
                assert false;
                return null;
        }
    }

    @Before
    public void setUp() throws Exception {
        Collection<HexLocation> hexes = new ArrayList<>();

        for (int y =  0; y <= 2; ++y) hexes.add(new HexLocation(-2, y));
        for (int y = -1; y <= 2; ++y) hexes.add(new HexLocation(-1, y));
        for (int y = -2; y <= 2; ++y) hexes.add(new HexLocation( 0, y));
        for (int y = -2; y <= 1; ++y) hexes.add(new HexLocation( 1, y));
        for (int y = -2; y <= 0; ++y) hexes.add(new HexLocation( 2, y));

        Map<HexLocation, ITile> tiles = new HashMap<>();
        for (HexLocation hex : hexes) {
            tiles.put(hex, new Tile(calculateType(hex), hex, hex.hashCode()));
        }

        Map<EdgeLocation, PortType> ports = new HashMap<>();
        // ports ?

        map = new CatanMap(tiles, ports);
    }

    @After
    public void tearDown() throws Exception {
        map = null;
        player1 = player2 = null;
    }

    @Test
    public void testGetTowns() throws Exception {

    }

    @Test
    public void testGetRoads() throws Exception {

    }

    @Test
    public void testGetTiles() throws Exception {

    }

    @Test
    public void testGetPorts() throws Exception {

    }

    @Test
    public void testGetTownAt() throws Exception {

    }

    @Test
    public void testGetRoad() throws Exception {

    }

    @Test
    public void testGetPlayersPorts() throws Exception {

    }

    @Test
    public void testCanPlaceRoad() throws Exception {

    }

    @Test
    public void testCanPlaceSettlement() throws Exception {

    }

    @Test
    public void testCanPlaceCity() throws Exception {

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
    public void testGetRobber() throws Exception {

    }

    @Test
    public void testMoveRobber() throws Exception {

    }
}