package shared.model;

import shared.definitions.HexType;
import shared.definitions.PortType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

import java.util.*;

/**
 * Created by Boffin on 11/13/2014.
 */
public class MapGenerator {
    private boolean randomPorts;
    private boolean randomTiles;
    private boolean randomNumbers;

    private Map<HexLocation, ITile> tiles;
    private Map<EdgeLocation, PortType> ports;
    HexLocation robber;

    private final static HexType[] HEXES = {
            // column -2
            HexType.ORE,
            HexType.WHEAT,
            HexType.WOOD,
            // column -1
            HexType.BRICK,
            HexType.SHEEP,
            HexType.SHEEP,
            HexType.ORE,
            // column 0
            HexType.DESERT,
            HexType.WOOD,
            HexType.WHEAT,
            HexType.WOOD,
            HexType.WHEAT,
            // column 1
            HexType.BRICK,
            HexType.ORE,
            HexType.BRICK,
            HexType.SHEEP,
            // column 2
            HexType.WOOD,
            HexType.SHEEP,
            HexType.WHEAT,
    };

    private final static Integer[] NUMBERS = {
            // column -2
            5,
            2,
            6,
            // column -1
            8,
            10,
            9,
            3,
            // column 0
            //Tile.DESERT_NUMBER, // desert hex: EXCLUDED since whichever hex is desert gets this #
            3,
            11,
            4,
            8,
            // column 1
            4,
            9,
            5,
            10,
            // column 2
            11,
            12,
            6,
    };

    private List<Integer> numberIndices;
    private List<Integer> tileIndices;
    private List<Integer> portIndices;

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
    }

    public ICatanMap generateMap() throws ModelException {
        // traverse the tiles left-to-right, top-to-bottom
        final int MIN_X = -2;
        final int MAX_X =  2;
        final int MIN_Y = -2;
        final int MAX_Y =  2;

        Int tileCount = new Int(0);
        Int numberCount = new Int(0);

        for (int x = MIN_X; x <= MAX_X; ++x) {
            int y_start;
            int y_end;

            if (x <= 0) {
                y_start = -x - MAX_X;
                y_end = MAX_Y;
            }
            else {
                y_start = MIN_Y;
                y_end = MAX_X - x;
            }
            for (int y = y_start; y <= y_end; ++y) {
                placeTile(x, y, tileCount, numberCount);
            }
        }

        // TODO: GENERATE / PLACE PORTS!

        return new CatanMap(tiles, new HashMap<VertexLocation, ITown>(), new HashMap<EdgeLocation, IRoad>(), ports, robber);
    }

    /**
     * Create a place a tile a the specified location.
     * This checks if it is random or not.
     * @param x the x location
     * @param y the y
     * @param tileCount which tile this is
     */
    private void placeTile(int x, int y, Int tileCount, Int numberCount) {
        HexLocation hexLoc = new HexLocation(x, y);

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
