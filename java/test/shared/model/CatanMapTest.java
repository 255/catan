package shared.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import shared.definitions.CatanColor;
import shared.definitions.HexType;
import shared.definitions.PortType;
import shared.locations.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class CatanMapTest {
    private CatanMap map = null;
    private IPlayer player1 = null;
    private IPlayer player2 = null;

    /* Generate hex types based on the location */
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

    /* Generate a tile for testing */
    private ITile generateTile(HexLocation hex) {
        if (calculateType(hex) == HexType.DESERT) {
            return new Tile(calculateType(hex), hex, Tile.DESERT_NUMBER);
        }
        else {
            return new Tile(calculateType(hex), hex, (hex.hashCode() % 6 + 1) * 2);
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
             tiles.put(hex, generateTile(hex));
        }

        Map<EdgeLocation, PortType> ports = new HashMap<>();
        ports.put(new EdgeLocation(-2, 0, EdgeDirection.NorthWest), PortType.BRICK);
        ports.put(new EdgeLocation(1, -2, EdgeDirection.North), PortType.THREE);
        ports.put(new EdgeLocation(2, -1, EdgeDirection.SouthEast), PortType.WOOD);
        ports.put(new EdgeLocation(0, 2, EdgeDirection.South), PortType.SHEEP);
        ports.put(new EdgeLocation(-1, 2, EdgeDirection.SouthWest), PortType.ORE);
        // ports ?

        map = new CatanMap(tiles, ports);

        player1 = new Player("Dave", 1337, CatanColor.BLUE, 0);
        player2 = new Player("Hal", 1010, CatanColor.RED, 0);
    }

    @After
    public void tearDown() throws Exception {
        map = null;
        player1 = player2 = null;
    }

    @Test
    public void testGetTowns() throws Exception {
        assertEquals(0, map.getTowns().size());



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

    /* Test canPlaceRoad and canPlaceInitialRoad methods */
    @Test
    public void testCanPlaceSettlement() throws Exception {
        // cannot place a settlement with no roads
        assertFalse(map.canPlaceSettlement(player1, new VertexLocation(0, 0, VertexDirection.NorthEast)));
        assertFalse(map.canPlaceSettlement(player1, new VertexLocation(1, -1, VertexDirection.West)));
        assertFalse(map.canPlaceSettlement(player1, new VertexLocation(0, -1, VertexDirection.SouthEast)));

        assertFalse(map.canPlaceSettlement(player1, new VertexLocation(1, 1, VertexDirection.SouthEast)));

        IRoad road = new Road(player1);
        map.placeRoad(road, new EdgeLocation(0, 0, EdgeDirection.NorthEast));

        // simple placement next to a road
        assertTrue(map.canPlaceSettlement(player1, new VertexLocation(0, 0, VertexDirection.NorthEast)));
        assertTrue(map.canPlaceSettlement(player1, new VertexLocation(1, -1, VertexDirection.West)));
        assertTrue(map.canPlaceSettlement(player1, new VertexLocation(0, -1, VertexDirection.SouthEast)));

        map.placeSettlement(new Settlement(player1), new VertexLocation(0, 0, VertexDirection.NorthEast));

        // test whether a settlement can be placed where there already is a settlement
        assertFalse(map.canPlaceSettlement(player1, new VertexLocation(0, 0, VertexDirection.NorthEast)));
        assertFalse(map.canPlaceSettlement(player1, new VertexLocation(1, -1, VertexDirection.West)));
        assertFalse(map.canPlaceSettlement(player1, new VertexLocation(0, -1, VertexDirection.SouthEast)));

        // test whether settlements can be placed right next to each other
        assertNotNull(map.getTownAt(new VertexLocation(0, 0, VertexDirection.NorthEast)));
        assertNull(map.getTownAt(new VertexLocation(0, 0, VertexDirection.East)));

        assertFalse(map.canPlaceSettlement(player1, new VertexLocation(0, 0, VertexDirection.East)));
        assertFalse(map.canPlaceSettlement(player1, new VertexLocation(1, -1, VertexDirection.SouthWest)));
        assertFalse(map.canPlaceSettlement(player1, new VertexLocation(1, 0, VertexDirection.NorthWest)));

        assertFalse(map.canPlaceSettlement(player2, new VertexLocation(0, 0, VertexDirection.SouthEast)));
        assertFalse(map.canPlaceSettlement(player2, new VertexLocation(0, 1, VertexDirection.NorthEast)));
        assertFalse(map.canPlaceSettlement(player2, new VertexLocation(1, 0, VertexDirection.West)));

        // opponent's roads do not count for placement (player1 can place, not player2)
        map.placeRoad(new Road(player1), new EdgeLocation(0, 0, EdgeDirection.SouthEast));
        assertTrue(map.canPlaceSettlement(player1, new VertexLocation(0, 0, VertexDirection.SouthEast)));
        assertTrue(map.canPlaceSettlement(player1, new VertexLocation(0, 1, VertexDirection.NorthEast)));
        assertTrue(map.canPlaceSettlement(player1, new VertexLocation(1, 0, VertexDirection.West)));

        assertFalse(map.canPlaceSettlement(player2, new VertexLocation(0, 0, VertexDirection.SouthEast)));
        assertFalse(map.canPlaceSettlement(player2, new VertexLocation(0, 1, VertexDirection.NorthEast)));
        assertFalse(map.canPlaceSettlement(player2, new VertexLocation(1, 0, VertexDirection.West)));

        // now, give player2 a road, so placement is valid for player2
        map.placeRoad(new Road(player2), new EdgeLocation(0, 1, EdgeDirection.NorthEast));

        assertTrue(map.canPlaceSettlement(player2, new VertexLocation(0, 0, VertexDirection.SouthEast)));
        assertTrue(map.canPlaceSettlement(player2, new VertexLocation(0, 1, VertexDirection.NorthEast)));
        assertTrue(map.canPlaceSettlement(player2, new VertexLocation(1, 0, VertexDirection.West)));

        // test whether a settlement can be placed where there is already a city
        map.placeSettlement(new Settlement(player1), new VertexLocation(2, -2, VertexDirection.NorthEast));
        assertFalse(map.canPlaceSettlement(player1, new VertexLocation(2, -2, VertexDirection.NorthEast)));
        assertFalse(map.canPlaceSettlement(player2, new VertexLocation(2, -2, VertexDirection.NorthEast)));
    }

    @Test
    public void testCanPlaceCity() throws Exception {
         // cannot place a city where there is nothing
        assertFalse(map.canPlaceCity(player1, new VertexLocation(0, 0, VertexDirection.NorthEast)));
        assertFalse(map.canPlaceCity(player1, new VertexLocation(1, -1, VertexDirection.West)));
        assertFalse(map.canPlaceCity(player1, new VertexLocation(0, -1, VertexDirection.SouthEast)));

        assertFalse(map.canPlaceCity(player1, new VertexLocation(1, 1, VertexDirection.SouthEast)));

        IRoad road = new Road(player1);
        map.placeRoad(road, new EdgeLocation(0, 0, EdgeDirection.NorthEast));

        // simple placement next to a road with no settlement (valid for settlement, not city)
        assertFalse(map.canPlaceCity(player1, new VertexLocation(0, 0, VertexDirection.NorthEast)));
        assertFalse(map.canPlaceCity(player1, new VertexLocation(1, -1, VertexDirection.West)));
        assertFalse(map.canPlaceCity(player1, new VertexLocation(0, -1, VertexDirection.SouthEast)));

        map.placeSettlement(new Settlement(player1), new VertexLocation(0, 0, VertexDirection.NorthEast));

        // Now that there is a settlement, should be valid for placement
        assertTrue(map.canPlaceCity(player1, new VertexLocation(0, 0, VertexDirection.NorthEast)));
        assertTrue(map.canPlaceCity(player1, new VertexLocation(1, -1, VertexDirection.West)));
        assertTrue(map.canPlaceCity(player1, new VertexLocation(0, -1, VertexDirection.SouthEast)));

        // verify that one player cannot upgrade another player's settlement
        map.placeRoad(new Road(player2), new EdgeLocation(0, 1, EdgeDirection.NorthEast));
        assertTrue(map.canPlaceSettlement(player2, new VertexLocation(0, 0, VertexDirection.SouthEast)));
        map.placeSettlement(new Settlement(player2), new VertexLocation(0, 0, VertexDirection.SouthEast));

        assertTrue(map.canPlaceCity(player2, new VertexLocation(0, 0, VertexDirection.SouthEast)));
        assertTrue(map.canPlaceCity(player2, new VertexLocation(0, 1, VertexDirection.NorthEast)));
        assertTrue(map.canPlaceCity(player2, new VertexLocation(1, 0, VertexDirection.West)));

        assertFalse(map.canPlaceCity(player1, new VertexLocation(0, 0, VertexDirection.SouthEast)));
        assertFalse(map.canPlaceCity(player1, new VertexLocation(0, 1, VertexDirection.NorthEast)));
        assertFalse(map.canPlaceCity(player1, new VertexLocation(1, 0, VertexDirection.West)));

        // test whether a city can be placed on another city
        map.placeCity(new City(player2), new VertexLocation(1, 0, VertexDirection.West));
        assertFalse(map.canPlaceCity(player2, new VertexLocation(0, 0, VertexDirection.SouthEast)));
        assertFalse(map.canPlaceCity(player2, new VertexLocation(0, 1, VertexDirection.NorthEast)));
        assertFalse(map.canPlaceCity(player2, new VertexLocation(1, 0, VertexDirection.West)));
        assertFalse(map.canPlaceCity(player1, new VertexLocation(0, 0, VertexDirection.SouthEast)));
        assertFalse(map.canPlaceCity(player1, new VertexLocation(0, 1, VertexDirection.NorthEast)));
        assertFalse(map.canPlaceCity(player1, new VertexLocation(1, 0, VertexDirection.West)));
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