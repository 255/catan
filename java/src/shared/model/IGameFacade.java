package shared.model;

import client.data.PlayerInfo;
import shared.definitions.PortType;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

import java.util.Collection;

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
    public void takeCardFromPlayer(IPlayer player, ResourceType cardType);

    /**
     * Takes an IPlayer object and gives the given card object to the player
     *
     * @param player the player to take cards from
     * @param cardType the Card object type to decrement from the player hand
     */
    public void giveCardToPlayer(IPlayer player, ResourceType cardType);

    /**
     * Returns the info for the current player
     *
     * @return the PlayerInfo object initialized with all the player card/piece counts
     */
    public PlayerInfo getCurPlayerInfo();

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
    public IResourceBundle getPlayerResources();

    /**
     * Returns the ports the current player has
     *
     * @return the ports that the current player has
     */
    public Collection<PortType> getPlayerPorts();
}
