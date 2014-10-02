package shared.model;

import client.communication.LogEntry;
import client.data.PlayerInfo;
import shared.definitions.PortType;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

/**
 * This is the interface for the GameFacade which handles all the manipulations of the game object
 */
public interface IGameFacade {

    /**
     * Takes an edge location and determines if a road can be placed on it
     *
     * @param edge the location of the side of a terrain hex
     *
     * @return a boolean value that reports if the user can place a road
     */
    public boolean canPlaceRoad(EdgeLocation edge);

    /**
     * Takes a vertex location and determines if a settlement can be placed on it
     *
     * @param vertex the location of a corner/intersection of terrain hexes
     *
     * @return a boolean value that reports if the user can place a settlement
     */
    public boolean canPlaceSettlement(VertexLocation vertex);

    /**
     * Takes a vertex location and determines if a city can be placed on it
     *
     * @param vertex the location of a corner/intersection of terrain hexes
     *
     * @return a boolean value that reports if the user can place a city
     */
    public boolean canPlaceCity(VertexLocation vertex);

    /**
     * Takes an edge location and places a road on it
     *
     * @param edge the location of the side of a terrain hex
     */
    public void placeRoad(EdgeLocation edge) throws ModelException;

    /**
     * Takes a vertex location and places a settlement on it
     *
     * @param vertex the location of an intersection of terrain hexes
     */
    public void placeSettlement(VertexLocation vertex) throws ModelException;

    /**
     * Takes a vertex location and places a city on it
     *
     * @param vertex the location of an intersection of terrain hexes
     */
    public void placeCity(VertexLocation vertex) throws ModelException;

    /**
     * Current player buys a DevCard
     */
    public void buyDevCard() throws ModelException;

    /**
     * Tells the server to play a soldier card
     * @param hex is the location to put the robber
     * @param victim is the player who is robbed
     */
    public void playSoldier(HexLocation hex, int victim) throws ModelException;

    /**
     * Play the Year of Plenty card
     */
    public void playYearOfPlenty();

    /**
     * Play the Road Building card
     */
    public void playRoadBuilding();

    /**
     * Play the Monopoly card
     */
    public void playMonopoly();

    /**
     * Play the Monument card
     */
    public void playMonument();

    /**
     * Tells the server to rob a player
     * @param hex the hex to place the robber on
     * @param victim the player index of the player being robbed
     */
    public void robPlayer(HexLocation hex, int victim) throws ModelException;

    /**
     * Returns the info for the current player
     *
     * @return the PlayerInfo object initialized with all the player card/piece counts
     */
    public IPlayer getCurPlayerInfo();

    /**
     * Returns the info for the current player's placed roads
     *
     * @return the Collection of IRoad objects initialized with all the locations of the current player's roads
     */
    public Collection<IRoad> getRoads();

    /**
     * Returns the info for the current player's placed settlements
     *
     * @return the Collection of ITown objects initialized with all the locations of the placed settlements
     */
    public Collection<ITown> getSettlements();

    /**
     * Returns the info for the current player's placed cities
     *
     * @return the Collection of ITown objects initialized with all the locations of the placed cities
     */
    public Collection<ITown> getCities();

    /**
     * Returns the info for the current player's placed settlements and cities
     *
     * @return the Collection of ITown objects initialized with all the locations of the placed settlements and cities
     */
    public Collection<ITown> getTowns();

    /**
     * Returns the info for the terrain hexes
     *
     * @return the Collection of ITile objects initialized with all the locations and types of terrain hexes
     */
    public Collection<ITile> getHexes();

    /**
     * Returns the terrain hex location for the current location of the robber
     *
     * @return the hex location of where the robber is currently placed
     */
    public HexLocation getRobber();

    /**
     * Returns the info for the current player's resource counts
     *
     * @return the ResourceBundle object containing the counts for the current player
     */
    public IResourceBank getPlayerResources();

    /**
     * Returns the ports the current player has
     *
     * @return the ports that the current player has
     */
    public Collection<PortType> getPlayerPorts();

    /**
     * Returns the chat history of the game
     *
     * @return the log initialized with all the chat messages
     */
    public ILog getChatHistory();

    /**
     * Returns the move/action history of the game
     *
     * @return the log initialized with all the move/action messages
     */
    public ILog getMoveHistory();

    // trading

    /**
     * accept an incoming trade
     * @param willAccept is true if the the trade is accepted, false otherwise
     */
    public void acceptTrade(boolean willAccept);

    /**
     * offer a trade to another player
     * @param offer the bundle of resources you are offering
     * @param recipientPlayerIndex the index of the player receiving the trade offer
     */
    public void offerTrade(ResourceBundle offer, int recipientPlayerIndex);

    /**
     * Trade with a port
     * @param ratio the ratio of trade. 2, 3, or 4 resources for one of any kind
     * @param giving the bundle of resources that are being given up
     * @param getting the bundle of resources that are being received
     */
    public void maritimeTrade(int ratio, ResourceBundle giving, ResourceBundle getting);

    /**
     * The current player will discard some cards
     * @param discardedCards the bundle of resource cards to discard
     */
    public void discardCards(ResourceBundle discardedCards);

    /**
     * The current player has rolled a number
     * @param rolledNumber the number that was rolled
     */
    public void rollNumber(int rolledNumber);

    /**
     * Finish up the current player's turn
     */
    public void finishTurn();
}
