package shared.model;

import com.sun.net.httpserver.HttpExchange;
import shared.definitions.HexType;
import shared.definitions.PortType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

import java.util.*;
import java.util.logging.Logger;

/**
 * Created by Boffin on 11/13/2014.
 */
public class MapGenerator {
    private static Logger logger = Logger.getLogger("catanserver");

    private boolean randomPorts;
    private boolean randomTiles;
    private boolean randomNumbers;

    private Map<HexLocation, ITile> tiles;
    private Map<EdgeLocation, PortType> ports;
    HexLocation robber;

    private List<Integer> numberIndices;
    private List<Integer> tileIndices;
    private List<Integer> portIndices;

    /** The default tile placement */
    private final static HexType[] HEXES = {
            // radius = 2
            HexType.ORE,
            HexType.WHEAT,
            HexType.WOOD,
            HexType.ORE,
            HexType.WHEAT,
            HexType.SHEEP,
            HexType.WHEAT,
            HexType.SHEEP,
            HexType.WOOD,
            HexType.BRICK,
            HexType.DESERT,
            HexType.BRICK,
            // radius = 1
            HexType.SHEEP,
            HexType.SHEEP,
            HexType.WOOD,
            HexType.BRICK,
            HexType.ORE,
            HexType.WOOD,
            // center tile
            HexType.WHEAT,
    };

    /** The default order of number placement */
    private final static Integer[] NUMBERS = {
            5,
            2,
            6,
            3,
            8,
            10,
            9,
            12,
            11,
            4,
            8,
            10,
            9,
            //Tile.DESERT_NUMBER, // desert hex: EXCLUDED since whichever hex is desert gets this #
            4,
            5,
            6,
            3,
            11,
    };

    private final List<HexLocation> HEX_LOCATIONS;

    private void addHex(int x, int y) {
        logger.finest(String.format("HexLoc (%d, %d)", x, y));
        HEX_LOCATIONS.add(new HexLocation(x, y));
    }

    public MapGenerator(boolean randomPorts, boolean randomTiles, boolean randomNumbers) {
        this.randomPorts = randomPorts;
        this.randomTiles = randomTiles;
        this.randomNumbers = randomNumbers;

        numberIndices = (randomNumbers ? generateIndexList(NUMBERS) : null);
        tileIndices = (randomTiles ? generateIndexList(HEXES) : null);
        // TODO: implement this
        //portIndices = (randomPorts ? generateIndexList(PORTS) : null);

        tiles = new HashMap<>();
        ports = new HashMap<>();
        robber = null;

        HEX_LOCATIONS = new ArrayList<>();
        spiral();
        System.out.printf("%d tiles\n", HEX_LOCATIONS.size());
        for (HexLocation hex : HEX_LOCATIONS) {
            System.out.printf("(%d, %d)\n", hex.getX(), hex.getY());
        }
    }

    /**
     * Generate hexes in a spiral order (same as game rules).
     */
    private void spiral() {
        final int RADIUS = 3;

        int max = RADIUS-1;
        int min = -max;

        for (int r = RADIUS-1; r >= 1; --r) {
            int x = min;
            int y = 0;

            for (; y < max; ++y) {
                addHex(x, y);
            }
            addHex(x, y);
            for (++x; x < 0; ++x) {
                addHex(x, y);
            }
            addHex(x, y);
            for (++x, --y; x < max; ++x, --y) {
                addHex(x, y);
            }
            addHex(x, y);
            for (--y; y > min; --y) {
                addHex(x, y);
            }
            addHex(x, y);
            for (--x; x > 0; --x) {
                addHex(x, y);
            }
            addHex(x, y);
            for (--x, ++y; x > min; --x, ++y) {
                addHex(x, y);
            }

            max--;
            min++;
        }

        addHex(0, 0);
    }

    public ICatanMap generateMap() throws ModelException {
        final int RADIUS =  3;

        // traverse the tiles in a spiral from the center
        Int tileCount = new Int(0);
        Int numberCount = new Int(0);

        // TODO: GENERATE / PLACE PORTS!
        for (HexLocation hex : HEX_LOCATIONS) {
            placeTile(hex, tileCount, numberCount);
        }

        return new CatanMap(tiles, new HashMap<VertexLocation, ITown>(), new HashMap<EdgeLocation, IRoad>(), ports, robber);
    }

    /**
     * Create a place a tile a the specified location.
     * This checks if it is random or not.
     * @param hexLoc
     * @param tileCount which tile this is
     */
    private void placeTile(HexLocation hexLoc, Int tileCount, Int numberCount) {
        HexType type = (randomTiles ? getRandom(HEXES, tileIndices)     : HEXES[tileCount.postincrement()]);

        int number;
        if (type == HexType.DESERT) {
            number = Tile.DESERT_NUMBER;
        }
        else {
            number = (randomNumbers ? getRandom(NUMBERS, numberIndices) : NUMBERS[numberCount.postincrement()]);
        }

        ITile newTile = Tile.generateNewTile(type, hexLoc, number);

        // check for the robber's placement
        if (newTile.hasRobber()) {
            assert robber == null : "There appear to be multiple desert tiles!";
            robber = newTile.location();
        }

        tiles.put(hexLoc, newTile);
    }

    private static <T> List<Integer> generateIndexList(T[] sourceArray) {
        List<Integer> indexList = new ArrayList<>(sourceArray.length);
        for (int i = 0; i < sourceArray.length; ++i) {
            indexList.add(i);
        }
        return indexList;
    }

    private static <T> T getRandom(T[] source, List<Integer> remainingIndices) {
        final Random rand = new Random();

        assert remainingIndices.size() > 0 : "No indices left to choose from!";

        int index = rand.nextInt(remainingIndices.size());
        int sourceIndex = remainingIndices.get(index);

        assert sourceIndex < source.length : "Invalid random index found!";

        T value = source[sourceIndex];

        removeItem(remainingIndices, index);

        return value;
    }

    /** Remove in O(1) time by swapping in last index */
    private static <T> void removeItem(List<T> items, int index) {
        items.set(index, items.get(items.size()-1));
        items.remove(items.size()-1);
    }

    /** An int that can be passed by reference */
    private class Int {
        public int value;

        public int preincrement() {
            return ++value;
        }

        public int postincrement() {
            return value++;
        }

        public Int(int value) {
            this.value = value;
        }

        public Int() {
            this.value = 0;
        }
    }
}
