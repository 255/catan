package shared.model;

import shared.definitions.HexType;
import shared.definitions.PortType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * The Catan map. It is initialized in the concrete class constructor.
 *
 * @author StevenBarnett
 */
public interface ICatanMap {
    /**
     * Get all of the towns placed on the map.
     * @return the towns placed on the map
     */
    public Collection<ITown> getTowns();

    /**
     * Get all of the cities on the map.
     * @return a collection of cities (no settlements)
     */
    public Collection<ITown> getCities();

    /**
     * Get all of the settlements on the map.
     * @return a collection of cities (no settlements)
     */
    public Collection<ITown> getSettlements();

    /**
     * Get all of the roads placed on the map.
     * @return the roads placed on the map
     */
    public Collection<IRoad> getRoads();

    /**
     * Get all of the terrain tiles in the map
     * @return the tiles in the map
     */
    public Collection<ITile> getTiles();

    /**
     * Get all of the ports on the map by location.
     * @return a map of ports mapping location to type
     */
    public Map<EdgeLocation, PortType> getPorts();

    /**
     * Get the road placed on an edge.
     * @param edge the edge from which to get the road
     * @return the road placed on the specified edge, or null if there is none
     */
    public IRoad getRoad(EdgeLocation edge);

    /**
     * Get the port types to which the specified player has access.
     * @param player the player whose ports to get
     * @return the ports belonging to the specified player
     */
    public Set<PortType> getPlayersPorts(IPlayer player);

    /**
     * Get a reference to the town that is placed at the specified location.
     * If there is no town at vertexLoc, null is returned.
     * @param vertexLoc the location of the town to get
     * @return a reference to the town at vertexLoc or null of the vertex is empty
     */
    public ITown getTownAt(VertexLocation vertexLoc);

    /**
     * Determine if a player can place a road at the specified location.
     * @param player the player to look at
     * @param edge the location where the player wants to place a road
     * @return a boolean value that reports if the player can place a road
     */
    public boolean canPlaceRoad(IPlayer player, EdgeLocation edge);

    /**
     * Determine if a player can place a road during the game initialization rounds.
     * This function differs from canPlaceRoad in that roads do not have to be connected to anything.
     * According to the spec, the roads are placed first, so there is no corresponding
     * "can place initial settlement" function (and place settlement does not enforce rules).
     * @param player the player to look at
     * @param edge the location where the player wants to place a road
     * @return a boolean value that reports if the player can place a road
     */
    public boolean canPlaceInitialRoad(IPlayer player, EdgeLocation edge);

    /**
     * Determine if a player can place a road at the specified location.
     * @param player the player to look at
     * @param vertex the location where the player wants to place a settlement
     * @return a boolean value that reports if the player can place a settlement
     */
    public boolean canPlaceSettlement(IPlayer player, VertexLocation vertex);

    /**
     * Determine if a player can place a city at the specified location.
     * @param player the player to look at
     * @param vertex the location where the player wants to place a city
     * @return a boolean value that reports if the player can place a city
     */
    public boolean canPlaceCity(IPlayer player, VertexLocation vertex);

    /**
     * Place a road object at the specified edge.
     * The road is placed in the map's data structure AND the road's location is set to edge.
     * @param road the road that is being placed
     * @param edge the edge on which to place the road.
     */
    public void placeRoad(IRoad road, EdgeLocation edge);

    /**
     * Place a settlement object at the specified vertex.
     * The settlement is placed in the map's data structure AND the settlement's location is set to edge.
     * @param settlement the settlement that is being placed
     * @param vertex the vertex on which to place the settlement.
     */
    public void placeSettlement(Settlement settlement, VertexLocation vertex);

    /**
     * Place a city object at the specified vertex.
     * The city is placed in the map's data structure AND the city's location is set to edge.
     * @param city the city that is being placed
     * @param vertex the vertex on which to place the city.
     */
    public void placeCity(City city, VertexLocation vertex);

    /**
     * Get the location of the robber.
     * @return the location of the robber
     */
    public HexLocation getRobber();

    /**
     * Change the location of the robber.
     * @param location the new hex location of the robber
     */
    public void moveRobber(HexLocation location);
}
