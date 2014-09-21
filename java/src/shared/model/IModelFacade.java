package shared.model;

import client.data.PlayerInfo;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

/**
 * Created by Spencer Weight on 9/20/2014.
 */
public interface IModelFacade {

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
     * Takes a hex location and determines if the robber can be placed on it
     *
     * @param hex the location of a terrain hex
     *
     * @return a boolean value that reports if the user can place the robber on the specified hex
     */
    public boolean canPlaceRobber(HexLocation hex);

    /**
     * Takes an edge location and places a road on it
     *
     * @param edge the location of the side of a terrain hex
     */
    public void placeRoad(EdgeLocation edge);

    /**
     * Takes a vertex location and places a settlement on it
     *
     * @param vertex the location of an intersection of terrain hexes
     */
    public void placeSettlement(VertexLocation vertex);

    /**
     * Takes a vertex location and places a city on it
     *
     * @param vertex the location of an intersection of terrain hexes
     */
    public void placeCity(VertexLocation vertex);

    /**
     * Takes a hex location and places the robber on it
     *
     * @param hex the location of a terrain hex
     */
    public void placeRobber(HexLocation hex);

    /**
     * Takes an IPlayer object and removes the given card object from the player
     *
     * @param player the player to take cards from
     * @param cardType the Card object type to decrement from the player hand
     */
    public void takeCardFromPlayer(IPlayer player, Object cardType);

    /**
     * Takes an IPlayer object and gives the given card object to the player
     *
     * @param player the player to take cards from
     * @param cardType the Card object type to decrement from the player hand
     */
    public void giveCardToPlayer(IPlayer player, Object cardType);

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

    /**
     * Returns the info for the current player
     *
     * @return the PlayerInfo object initialized with all the player card/piece counts
     */
    public PlayerInfo getCurPlayerInfo();

    /**
     * Returns the info for the current player's placed roads
     *
     * @return the RoadInfo object initialized with all the locations of the current player's roads
     */
    public RoadInfo getRoads();

    /**
     * Returns the info for the current player's placed settlements
     *
     * @return the TownInfo object initialized with all the locations of the current player's settlements
     */
    public TownInfo getSettlements();

    /**
     * Returns the info for the current player's placed cities
     *
     * @return the TownInfo object initialized with all the locations of the current player's cities
     */
    public TownInfo getCities();

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
    public IResourceBundle getCurPlayerResources();

    /**
     * Takes in a chat message to be added to the chat log
     *
     * @param message the string of text to add to the chat log
     */
    public void sendChat(String message);






}
