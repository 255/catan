package shared.model;

import shared.definitions.HexType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

import java.util.Collection;

/**
 * The Catan map.
 *
 * @author Wyatt
 */
public class Map implements IMap {
    /**
     * Get all of the towns placed on the map.
     * @return the towns placed on the map
     */
    public Collection<ITown> getTowns() {
      return null; //TODO
    }

    /**
     * Get all of the roads placed on the map.
     * @return the roads placed on the map
     */
    public Collection<IRoad> getRoads() {
      return null; //TODO
    }

    /**
     * Get all of the terrain tiles in the map
     * @return the tiles in the map
     */
    public Collection<ITile> getTiles() {
      return null; //TODO
    }

    /**
     * Get a reference to the town that is placed at the specified location.
     * If there is no town at vertexLoc, null is returned.
     * @param vertexLoc the location of the town to get
     * @return a reference to the town at vertexLoc or null of the vertex is empty
     */
    public ITown getTownAt(VertexLocation vertexLoc) {
      return null; //TODO
    }

    /**
     * Determine if a player can place a road at the specified location.
     * @param player the player to look at
     * @param edge the location where the player wants to place a road
     * @return a boolean value that reports if the player can place a road
     */
    public boolean canPlaceRoad(IPlayer player, EdgeLocation edge) {
      return false; //TODO
    }

    /**
     * Determine if a player can place a road at the specified location.
     * @param player the player to look at
     * @param vertex the location where the player wants to place a settlement
     * @return a boolean value that reports if the player can place a settlement
     */
    public boolean canPlaceSettlement(IPlayer player, VertexLocation vertex) {
      return false; //TODO
    }

    /**
     * Determine if a player can place a city at the specified location.
     * @param player the player to look at
     * @param vertex the location where the player wants to place a city
     * @return a boolean value that reports if the player can place a city
     */
    public boolean canPlaceCity(IPlayer player, VertexLocation vertex) {
      return false; //TODO
    }

    /**
     * Get the roads adjacent to the specified vertex location. The collection
     * will be empty if there are no roads adjacent to the vertex.
     *
     * This is useful for checking if a vertex is a valid site for a settlement.
     * @param vertexLoc the vertex location around which to look for roads
     * @return the roads adjacent to the vertex
     */
    public Collection<IRoad> getRoadsAdjacentToVertex(VertexLocation vertexLoc) {
      return null; //TODO
    }

    /**
     * Determine whether the specified vertex location has towns placed at adjacent vertices.
     * @param vertexLoc the location around which to look for cities
     * @return true if the vertex has cities on adjacent vertices, false otherwise
     */
    public boolean hasAdjacentCities(VertexLocation vertexLoc) {
      return false; //TODO
    }

    /**
     * Get the road placed on an edge.
     * @param edge the edge from which to get the road
     * @return the road placed on the specified edge, or null if there is none
     */
    public boolean getRoad(EdgeLocation edge) {
      return false; //TODO
    }

    /**
     * Get the roads that connect to the specified edge, excluding the road on the edge,
     * if there is one.
     * This will be used for canPlaceTown functions and players will use this to find their longest road.
     * @param edge the edge around which to check for adjacent roads
     * @return the roads adjacent to the specified edge, excluding the road on the edge, if any
     */
    public Collection<IRoad> getConnectingRoads(EdgeLocation edge) {
      return null; //TODO
    }

    /**
     * Get the location of the robber.
     * @return the location of the robber
     */
    public HexLocation getRobber() {
      return null; //TODO
    }

    /**
     * Change the location of the robber.
     * @param location the new hex location of the robber
     */
    public void moveRobber(HexLocation location) {
      //TODO
    }
}
