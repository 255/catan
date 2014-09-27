package shared.model;

import shared.definitions.HexType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

import java.util.Collection;
import java.util.Map;

/**
 * The Catan map. It is initialized in the concrete class constructor.
 *
 * @author StevenBarnett
 */
public interface IMap {
    /**
     * Get all of the towns placed on the map.
     * @return the towns placed on the map
     */
    public Collection<ITown> getTowns();

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
     * Determine if a player can place a road at the specified location.
     * @param player the player to look at
     * @param vertex the location where the player wants to place a settlement
     * @return a boolean value that reports if the player can place a settlement
     */
    public boolean canPlaceSettlement(IPlayer player, VertexLocation vertex);

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
     * Determine if a player can place a city at the specified location.
     * @param player the player to look at
     * @param vertex the location where the player wants to place a city
     * @return a boolean value that reports if the player can place a city
     */
    public boolean canPlaceCity(IPlayer player, VertexLocation vertex);

    /**
     * Get the roads adjacent to the specified vertex location. The collection
     * will be empty if there are no roads adjacent to the vertex.
     *
     * This is useful for checking if a vertex is a valid site for a settlement.
     * @param vertexLoc the vertex location around which to look for roads
     * @return the roads adjacent to the vertex
     */
    public Collection<IRoad> getRoadsAdjacentToVertex(VertexLocation vertexLoc);

    /**
     * Determine whether the specified vertex location has towns placed at adjacent vertices.
     * @param vertexLoc the location around which to look for cities
     * @return true if the vertex has cities on adjacent vertices, false otherwise
     */
    public boolean hasAdjacentCities(VertexLocation vertexLoc);

    /**
     * Get the road placed on an edge.
     * @param edge the edge from which to get the road
     * @return the road placed on the specified edge, or null if there is none
     */
    public IRoad getRoad(EdgeLocation edge);

    /**
     * Get the roads that connect to the specified edge, excluding the road on the edge,
     * if there is one.
     * This will be used for canPlaceTown functions and players will use this to find their longest road.
     * @param edge the edge around which to check for adjacent roads
     * @return the roads adjacent to the specified edge, excluding the road on the edge, if any
     */
    public Collection<IRoad> getConnectingRoads(EdgeLocation edge);

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
