package shared.model;

import com.google.gson.stream.JsonReader;
import shared.definitions.CatanColor;
import shared.definitions.HexType;
import shared.definitions.PortType;
import shared.locations.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;

/**
 * @author Wyatt
 *
 * TODO: decompose this class
 */
public class ModelSerializer implements IModelSerializer {
    /*
     * Initialize players first since they are referenced several times.
     */
    private SortedMap<Integer, IPlayer> m_players;

    /**
     * Takes in a String of text formatted as JSON, parses it and stores it in
     * the game object which keeps track of the status of the game pieces
     *
     * @param json a string of text that is formatted as JSON
     * @return the Game object that has all the JSON values parsed and stored in it
     */
    @Override
    public IGame convertJSONtoModel(String json) throws ModelException {
        assert json != null;
        // read the players first since some objects refer to players by name
        //     and others refer to players by id

        try (JsonReader playerReader = new JsonReader(new StringReader(json))) {
            initializePlayers(playerReader);
        }
        catch (IOException | IllegalStateException e) {
            throw new ModelException("Error reading player information in JSON.", e);
        }

        try (JsonReader reader = new JsonReader(new StringReader(json))) {
            readClientModel(reader);
        }
        catch (IOException | IllegalStateException e) {
            throw new ModelException("Error deserializing JSON.", e);
        }

        return null; //TODO
    }

    void initializePlayers(JsonReader reader) throws IOException, ModelException {
        m_players = new TreeMap<>();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("players")) {
                reader.beginArray();
                while (reader.hasNext()) {
                    IPlayer player = readPlayer(reader);
                    m_players.put(player.getIndex(), player);
                }
                reader.endArray();

                return;
            }
            else {
                reader.skipValue();
            }
        }
        reader.endObject();

        throw new ModelException("Player information was not found in the JSON.");
    }

    IPlayer readPlayer(JsonReader reader) throws IOException, ModelException {
        reader.beginObject();

        CatanColor color = null;
        Boolean discarded = null;
        Integer monuments = null;
        String playerName = null;
        IDevCardHand newDevCards = null;
        IDevCardHand oldDevCards = null;
        Integer playerIndex = null;
        Boolean playedDevCard = null;
        Integer playerID = null;
        IResourceBank resources = null;
        Integer soldiers = null;
        Integer victoryPoints = null;
        IPieceBank pieceBank = new PieceBank();

        while (reader.hasNext()) {
            String name = reader.nextName();

            switch (name) {
                case "color":
                    color = CatanColor.valueOf(reader.nextString().toUpperCase());
                    break;
                case "discarded":
                    discarded = reader.nextBoolean();
                    break;
                case "monuments":
                    monuments = reader.nextInt();
                    break;
                case "name":
                    playerName = reader.nextString();
                    break;
                case "newDevCards":
                    newDevCards = readDevCards(reader);
                    break;
                case "oldDevCards":
                    oldDevCards = readDevCards(reader);
                    break;
                case "playerIndex":
                    playerIndex = reader.nextInt();
                    break;
                case "playedDevCard":
                    playedDevCard = reader.nextBoolean();
                    break;
                case "playerID":
                    playerID = reader.nextInt();
                    break;
                case "resources":
                    resources = readResourceBank(reader);
                    break;
                case "soldiers":
                    soldiers = reader.nextInt();
                    break;
                case "victoryPoints":
                    victoryPoints = reader.nextInt();
                    break;
                case "settlements":
                    pieceBank.setAvailableSettlements(reader.nextInt());
                    break;
                case "cities":
                    pieceBank.setAvailableCities(reader.nextInt());
                    break;
                case "roads":
                    pieceBank.setAvailableRoads(reader.nextInt());
                    break;
                default:
                    throw new ModelException("Unrecognized token \"" + name + "\" in player.");
            }
        }
        reader.endObject();

        // TODO: make these checks asserts? (should we assume proper JSON format?)
        if (anyIsNull(color, discarded, monuments, playerName, newDevCards, oldDevCards, playerIndex,
                playedDevCard, playerID, resources, soldiers, victoryPoints)) {
            throw new ModelException("Failed to initialize player.");
        }

        IPlayer player = new Player(playerID, playerIndex, victoryPoints, monuments, soldiers, discarded, playedDevCard,
                playerName, color, pieceBank, resources, newDevCards, oldDevCards);

        return player;
    }

    /**
     * Takes in a Game object and formats it as JSON.
     *
     * @param theGame an object that stores all the information about the current state of the game
     * @return the string of JSON generated by retrieving all the values from the game object
     */
    @Override
    public String convertModelToJSON(IGame theGame) {
        return null;
    }

    private void readClientModel(JsonReader reader) throws IOException, ModelException {
        IGame newGame = new Game();

        // store the players in a list for the game object
        List<IPlayer> playerList = new ArrayList<>();
        for (IPlayer player : m_players.values()) {
            playerList.add(player);
        }
        newGame.setPlayers(playerList);

        // begin parsing
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "bank":
                    newGame.setResourceBank(readResourceBank(reader));
                    break;
                case "deck":
                    newGame.setDevCards(readDevCards(reader));
                    break;
                case "chat":
                    newGame.setChatHistory(readHistory(reader));
                    break;
                case "log":
                    newGame.setGameplayLog(readHistory(reader));
                    break;
                case "map":
                    newGame.setMap(readMap(reader));
                    break;
                case "players":
                    reader.skipValue(); // already read players
                    break;
                case "tradeOffer":
                    newGame.setTradeOffer(readTradeOffer(reader));
                    break;
                case "turnTracker":
                    reader.beginObject();
                    while (reader.hasNext()) {
                        String next = reader.nextName();
                        switch (next) {
                            case "currentTurn":
                                int currentPlayer = reader.nextInt();
                                newGame.setCurrentPlayer(m_players.get(currentPlayer));
                                break;
                            case "status":
                                newGame.setGameState(GameState.fromString(reader.nextString()));
                                break;
                            case "longestRoad":
                                newGame.setLongestRoad(m_players.get(reader.nextInt()));
                                assert newGame.getLongestRoad() != null;
                                break;
                            case "largestArmy":
                                newGame.setLargestArmy(m_players.get(reader.nextInt()));
                                assert newGame.getLargestArmy() != null;
                                break;
                            default:
                                throw new ModelException("Unrecognized token \"" + name + "\" turn tracker json.");
                        }
                    }
                    reader.endObject();
                    break;
                case "version":
                    newGame.setVersion(reader.nextInt());
                    break;
                case "winner":
                    int winner = reader.nextInt();
                    if (winner != -1) {
                        newGame.setWinner(m_players.get(winner));
                    }
                    break;
                default:
                    throw new ModelException("Unrecognized token \"" + name + "\".");
            }
        }

        reader.endObject();
    }

    private IResourceBank readResourceBank(JsonReader reader) throws IOException, ModelException {
        Map<String, Integer> resources = new HashMap<>();

        reader.beginObject();
        while (reader.hasNext()) {
            resources.put(reader.nextName(), reader.nextInt());
        }
        reader.endObject();

        if (resources.size() != ResourceBank.NUM_RESOURCES) {
            throw new ModelException("Error reading bank from json.");
        }

        return new ResourceBank(
            resources.get("wood"),
            resources.get("brick"),
            resources.get("sheep"),
            resources.get("wheat"),
            resources.get("ore")
        );
    }

    private IDevCardHand readDevCards(JsonReader reader) throws IOException, ModelException {
        Integer monopoly = null;
        Integer monument = null;
        Integer roadBuilding = null;
        Integer soldier = null;
        Integer yearOfPlenty = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "monopoly":
                    monopoly = reader.nextInt();
                    break;
                case "monument":
                    monument = reader.nextInt();
                    break;
                case "roadBuilding":
                    roadBuilding = reader.nextInt();
                    break;
                case "soldier":
                    soldier = reader.nextInt();
                    break;
                case "yearOfPlenty":
                    yearOfPlenty = reader.nextInt();
                    break;
                default:
                    throw new ModelException("Unrecognized token \"" + name + "\" in dev card json.");
            }
        }
        reader.endObject();

        if (anyIsNull(monopoly, monopoly, roadBuilding, soldier, yearOfPlenty)) {
            throw new ModelException("Reading dev card hand failed.");
        }

        return new DevCardHand(monopoly, monument, roadBuilding, soldier, yearOfPlenty);
    }

    private ILog readHistory(JsonReader reader) throws IOException, ModelException {
        ILog log = new Log();

        reader.beginObject();

        if (!reader.nextName().equals("lines")) {
            throw new ModelException("Error parsing history.");
        };
        reader.beginArray();

        while (reader.hasNext()) {
            String message = null;
            IPlayer source = null;

            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                switch (name) {
                    case "message":
                        message = reader.nextString();
                        break;
                    case "source":
                        String playerName = reader.nextString();

                        for (IPlayer player : m_players.values()) {
                            if (playerName.equals(player.getName())) {
                                source = player;
                                break;
                            }
                        }
                        if (source == null) {
                            throw new ModelException("Invalid name \"" + playerName + "\" in message/gameplay history.");
                        }
                        break;
                    default:
                        throw new ModelException("Unrecognized token \"" + name + "\" in map json.");
                }
            }
            reader.endObject();

            log.addMessage(source, message);
        }
        reader.endArray();
        reader.endObject();

        return log;
    }

    private ITradeOffer readTradeOffer(JsonReader reader) throws IOException, ModelException {
        IPlayer sender = null;
        IPlayer receiver = null;
        IResourceBank offer = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "sender":
                    sender = m_players.get(reader.nextInt());
                    break;
                case "receiver":
                    receiver = m_players.get(reader.nextInt());
                    break;
                case "offer":
                    offer = readResourceBank(reader);
                    break;
                default:
                    throw new ModelException("Unrecognized token \"" + name + "\" in trade offer json.");
            }
        }
        reader.endObject();

        if (anyIsNull(sender, receiver, offer)) {
            throw new ModelException("Failed reading trade offer.");
        }

        // TODO: THIS IS QUITE BAD! I am casting a resource bank to a bundle
        // WE ONLY NEED ONE CLASS for this
        // Separate bank / bundles just makes things to complicated
        return new TradeOffer(sender, receiver, /* FIXME! This is bad */(ResourceBundle)offer);
    }

    private ICatanMap readMap(JsonReader reader) throws IOException, ModelException {
        Map<HexLocation, ITile> tiles = new HashMap<>();
        Map<VertexLocation, ITown> towns = new HashMap<>();
        Map<EdgeLocation, IRoad> roads = new HashMap<>();
        Map<EdgeLocation, PortType> ports = new HashMap<>();
        HexLocation robber = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "hexes":
                    reader.beginArray();
                    while (reader.hasNext()) {
                        ITile tile = readMapTile(reader);
                        tiles.put(tile.location(), tile);
                    }
                    reader.endArray();
                    break;
                case "ports":
                    reader.beginArray();
                    while (reader.hasNext()) {
                        readPort(reader, ports);
                    }
                    reader.endArray();
                    break;
                case "roads":
                    reader.beginArray();
                    while (reader.hasNext()) {
                        IRoad road = readRoad(reader);
                        roads.put(road.getLocation(), road);
                        m_players.get(road.getOwner().getIndex()).addRoad(road); //add to player's list
                    }
                    reader.endArray();
                    break;
                case "settlements":
                case "cities":
                    reader.beginArray();
                    while (reader.hasNext()) {
                        ITown town = readTown(reader, name);
                        towns.put(town.getLocation(), town);
                        m_players.get(town.getOwner().getIndex()).addTown(town); //add to player's list
                    }
                    reader.endArray();
                    break;
                case "radius":
                    reader.skipValue();
                    break;
                case "robber":
                    robber = readHexLocation(reader);
                    break;
                default:
                    throw new ModelException("Unrecognized token \"" + name + "\" in map json.");
            }
        }
        reader.endObject();

        return new CatanMap(tiles, towns, roads, ports, robber);
    }

    private ITile readMapTile(JsonReader reader) throws IOException, ModelException {
        reader.beginObject();

        HexLocation loc = null;
        HexType hexType = HexType.DESERT;
        int number  = Tile.DESERT_NUMBER;

        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "resource":
                    hexType = readHexType(reader);
                    break;
                case "number":
                    number = reader.nextInt();
                    break;
                case "location":
                    loc = readHexLocation(reader);
                    break;
                default:
                    throw new ModelException("Unrecognized token \"" + name + "\" in tile map tile json.");
            }
        }

        reader.endObject();

        return new Tile(hexType, loc, number);
    }

    private HexType readHexType(JsonReader reader) throws IOException, ModelException {
        String name = reader.nextString();
        switch (name) {
            case "brick": return HexType.BRICK;
            case "ore":   return HexType.ORE;
            case "sheep": return HexType.SHEEP;
            case "wheat": return HexType.WHEAT;
            case "wood":  return HexType.WOOD;
            default:
                throw new ModelException("Unrecognized token \"" + name + "\" in hex/resource json.");
        }
    }

    private HexLocation readHexLocation(JsonReader reader) throws IOException, ModelException {
        Integer x = null;
        Integer y = null;

        reader.beginObject();

        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "x":
                    x = reader.nextInt();
                    break;
                case "y":
                    y = reader.nextInt();
                    break;
                default:
                    throw new ModelException("Unrecognized token \"" + name + "\" in hex location json.");
            }
        }

        reader.endObject();

        if (x == null || y == null) {
            throw new ModelException("Failed reading hex location.");
        }

        return new HexLocation(x, y);
    }

    private void readPort(JsonReader reader, Map<EdgeLocation, PortType> portArray) throws IOException, ModelException {
        HexLocation hexLoc = null;
        EdgeDirection edge = null;
        PortType portType = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "resource":
                    portType = PortType.valueOf(reader.nextString().toUpperCase()); // TODO: check that these work 0.o
                    break;
                case "ratio":
                    int ratio = reader.nextInt();
                    if (ratio == 3) {
                        portType = PortType.THREE;
                    }
                    break;
                case "location":
                    hexLoc = readHexLocation(reader);
                    break;
                case "direction":
                    edge = EdgeDirection.fromAbbreviation(reader.nextString());
                    break;
                default:
                    throw new ModelException("Unrecognized token \"" + name + "\" in port json.");
            }
        }
        reader.endObject();

        if (edge == null || hexLoc == null || portType == null) throw new ModelException("Port parsing failed");

        portArray.put(new EdgeLocation(hexLoc, edge), portType);
    }

    private IRoad readRoad(JsonReader reader) throws IOException, ModelException {
        IPlayer owner = null;
        EdgeLocation edge = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "owner":
                    int index = reader.nextInt();
                    owner = m_players.get(index);
                    break;
                case "location":
                    edge = readEdgeLocation(reader);
                    break;
                default:
                    throw new ModelException("Unrecognized token \"" + name + "\" in road.");
            }
        }
        reader.endObject();

        if (anyIsNull(owner, edge)) {
            throw new ModelException("Failed reading road.");
        }

        return new Road(owner, edge);
    }

    private EdgeLocation readEdgeLocation(JsonReader reader) throws IOException, ModelException {
        Integer x = null;
        Integer y = null;
        EdgeDirection dir = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "x":
                    x = reader.nextInt();
                    break;
                case "y":
                    y = reader.nextInt();
                    break;
                case "direction":
                    dir = EdgeDirection.fromAbbreviation(reader.nextString());
                    break;
                default:
                    throw new ModelException("Unrecognized token \"" + name + "\" in edge location.");
            }
        }
        reader.endObject();

        if (anyIsNull(x, y, dir)) {
            throw new ModelException("Failed reading road edge location.");
        }

        return new EdgeLocation(new HexLocation(x, y), dir);
    }

    private ITown readTown(JsonReader reader, String type) throws IOException, ModelException {
        IPlayer owner = null;
        VertexLocation vertex = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "owner":
                    int index = reader.nextInt();
                    owner = m_players.get(index);
                    break;
                case "location":
                    vertex = readVertexLocation(reader);
                    break;
                default:
                    throw new ModelException("Unrecognized token \"" + name + "\" in town.");
            }
        }
        reader.endObject();

        if (anyIsNull(owner, vertex)) {
            throw new ModelException("Failed reading road.");
        }

        if (type.equals("city")) {
            return new City(owner, vertex);
        }
        else {
            return new Settlement(owner, vertex);
        }
    }

    private VertexLocation readVertexLocation(JsonReader reader) throws IOException, ModelException {
        Integer x = null;
        Integer y = null;
        VertexDirection dir = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "x":
                    x = reader.nextInt();
                    break;
                case "y":
                    y = reader.nextInt();
                    break;
                case "direction":
                    dir = VertexDirection.valueOf(reader.nextString().toUpperCase());
                    break;
                default:
                    throw new ModelException("Unrecognized token \"" + name + "\" in vertex location.");
            }
        }
        reader.endObject();

        if (anyIsNull(x, y, dir)) {
            throw new ModelException("Failed reading vertex location.");
        }

        return new VertexLocation(new HexLocation(x, y), dir);
    }

    private static boolean anyIsNull(Object ... objects) {
        for (Object obj : objects) {
            if (obj == null) return true;
        }

        return false;
    }

    private static boolean anyEquals(Object value, Object ... objects) {
        for (Object obj : objects) {
            if (obj.equals(value)) return true;
        }

        return false;
    }

    public static void main(String[] args) {
        String path = "D:\\Users\\Wyatt\\Desktop\\School\\Fall 2014\\CS340\\game_info_example.json";
        //;String path = args[0];

        System.out.printf("Parsing %s...\n", path);
        try {
            String json = new String(
                java.nio.file.Files.readAllBytes(
                    java.nio.file.Paths.get(path)
                )
            );

            ModelSerializer s = new ModelSerializer();

            s.convertJSONtoModel(json);

        } catch (IOException | ModelException e) {
            System.out.println("There was problem!");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Parsing completed (successfully?)");
    }
}
