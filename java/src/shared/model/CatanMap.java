package shared.model;

import shared.definitions.PortType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * The Catan map.
 *
 * All edge and vertices are normalized.
 *
 * @author Wyatt
 */
public class CatanMap implements IMap {
    private Map<HexLocation, ITile> m_tiles;
    private Map<VertexLocation, ITown> m_towns;
    private Map<EdgeLocation, IRoad> m_roads;
    private Map<EdgeLocation, PortType> m_ports;
    private HexLocation robber;

    public CatanMap() {
        m_tiles = new HashMap<>();
        m_towns = new HashMap<>();
        m_roads = new HashMap<>();
        m_ports = new HashMap<>();
    }

    /**
     * Get all of the towns placed on the map.
     * @return the towns placed on the map
     */
    @Override
    public Collection<ITown> getTowns() {
        return m_towns.values();
    }

    /**
     * Get all of the roads placed on the map.
     * @return the roads placed on the map
     */
    @Override
    public Collection<IRoad> getRoads() {
        return m_roads.values();
    }

    /**
     * Get all of the terrain tiles in the map
     * @return the tiles in the map
     */
    @Override
    public Collection<ITile> getTiles() {
        return m_tiles.values();
    }

    /**
     * Get a reference to the town that is placed at the specified location.
     * If there is no town at vertexLoc, null is returned.
     * @param vertexLoc the location of the town to get
     * @return a reference to the town at vertexLoc or null of the vertex is empty
     */
    @Override
    public ITown getTownAt(VertexLocation vertexLoc) {
        return m_towns.get(vertexLoc.getNormalizedLocation());
    }

    /**
     * Determine if a player can place a road at the specified location.
     * To place a road, the player must have a city at an adjacent vertex or
     * another road at an adjacent edge. The edge must also be unoccupied.
     *
     * The player must have enough resources to purchase a road. This function
     * DOES NOT check the player's resources, however.
     *
     * @param player the player who is placing the road
     * @param edge the location where the player wants to place a road
     * @return a boolean value that reports if the player can place a road
     */
    @Override
    public boolean canPlaceRoad(IPlayer player, EdgeLocation edge) {
        edge = edge.getNormalizedLocation();

        assert m_tiles.containsKey(edge.getHexLoc()) : "Invalid edge object.";

        // check if a road is already placed
        if (m_roads.containsKey(edge)) {
            return false;
        }

        // check if there is a neighboring town
        for (ITown neighborTown : getAdjacentTowns(edge)) {
            if (neighborTown.getOwner().equals(player)) {
                return true;
            }
        }

        // TODO: Verify rules w/ regard to roads broken by opponent's settlement
        // TODO: this does NOT check from "broken" roads
        // check if there is a connecting road
        for (IRoad neighborRoad : getAdjacentRoads(edge)) {
            if (neighborRoad.getOwner().equals(player)) {
                return true;
            }
        }

        return false; //TODO
    }

    /**
     * Determine if a player can place a road at the specified location.
     * @param player the player to look at
     * @param vertex the location where the player wants to place a settlement
     * @return a boolean value that reports if the player can place a settlement
     */
    @Override
    public boolean canPlaceSettlement(IPlayer player, VertexLocation vertex) {
        return false; //TODO
    }

    /**
     * Determine if a player can place a city at the specified location.
     * @param player the player to look at
     * @param vertex the location where the player wants to place a city
     * @return a boolean value that reports if the player can place a city
     */
    @Override
    public boolean canPlaceCity(IPlayer player, VertexLocation vertex) {
      return false; //TODO
    }

    @Override
    public void placeRoad(IRoad road, EdgeLocation edge) {
        assert road != null && edge != null;

        edge = edge.getNormalizedLocation();

        assert !m_roads.containsKey(edge) :  "There is already a road placed at " + edge.toString();
        assert road.getLocation() == null : "The road " + road.toString() + " already thinks it's placed!";

        m_roads.put(edge, road);
        road.setLocation(edge);
    }

    /**
     * Place a settlement object at the specified vertex.
     * The settlement is placed in the map's data structure AND the settlement's location is set to edge.
     *
     * @param settlement the settlement that is being placed
     * @param vertex     the vertex on which to place the settlement.
     */
    @Override
    public void placeSettlement(Settlement settlement, VertexLocation vertex) {
        vertex = vertex.getNormalizedLocation();

        assert !m_towns.containsKey(vertex) : "There is already a settlement placed at " + vertex.toString();
        assert settlement.getLocation() == null : "The settlement " + settlement.toString() + " already thinks it's placed!";

        m_towns.put(vertex, settlement);
        settlement.setLocation(vertex);
    }

    /**
     * Place a city object at the specified vertex.
     * The city is placed in the map's data structure AND the city's location is set to edge.
     *
     * @param city   the city that is being placed
     * @param vertex the vertex on which to place the city.
     */
    @Override
    public void placeCity(City city, VertexLocation vertex) {
        vertex = vertex.getNormalizedLocation();

        assert !m_towns.containsKey(vertex) : "There is already a city placed at " + vertex.toString();
        assert city.getLocation() == null : "The city " + city.toString() + " already thinks it's placed!";

        m_towns.put(vertex, city);
        city.setLocation(vertex);
    }

    /**
     * Get the roads adjacent to the specified vertex location. The collection
     * will be empty if there are no roads adjacent to the vertex.
     *
     * This is useful for checking if a vertex is a valid site for a settlement.
     * @param vertexLoc the vertex location around which to look for roads
     * @return the roads adjacent to the vertex
     */
    @Override
    public Collection<IRoad> getRoadsAdjacentToVertex(VertexLocation vertexLoc) {
        return null; //TODO
    }

    /**
     * Determine whether the specified vertex location has towns placed at adjacent vertices.
     * @param vertexLoc the location around which to look for cities
     * @return true if the vertex has cities on adjacent vertices, false otherwise
     */
    @Override
    public boolean hasAdjacentCities(VertexLocation vertexLoc) {
        return false; //TODO
    }

    /**
     * Get the road placed on an edge.
     * @param edge the edge from which to get the road
     * @return the road placed on the specified edge, or null if there is none
     */
    @Override
    public IRoad getRoad(EdgeLocation edge) {
        return m_roads.get(edge.getNormalizedLocation());
    }

    /**
     * Get the roads that connect to the specified edge, excluding the road on the edge,
     * if there is one.
     * This will be used for canPlaceTown functions and players will use this to find their longest road.
     * @param edge the edge around which to check for adjacent roads
     * @return the roads adjacent to the specified edge, excluding the road on the edge, if any
     */
    @Override
    public Collection<IRoad> getConnectingRoads(EdgeLocation edge) {
        return null; //TODO
    }

    /**
     * Get the location of the robber.
     * @return the location of the robber
     */
    @Override
    public HexLocation getRobber() {
        return robber;
    }

    /**
     * Change the location of the robber.
     * @param location the new hex location of the robber
     */
    @Override
    public void moveRobber(HexLocation location) {
        assert m_tiles.containsKey(location);

        m_tiles.get(robber).removeRobber();

        robber = location;
        m_tiles.get(location).placeRobber();
    }

    /**
     * Get the tiles adjacent to the specified edge.
     * @param edge
     * @return
     */
    private Collection<ITile> getAdjacentTiles(EdgeLocation edge) {
        edge = edge.getNormalizedLocation();

        Collection<ITile> tiles = new ArrayList<>();

        HexLocation hexLoc = edge.getHexLoc();
        HexLocation otherHexLoc = hexLoc.getNeighborLoc(edge.getDir());

        // exterior edges are only adjacent to one tile, so
        // check if the tile is a "water" tile
        if (m_tiles.containsKey(hexLoc)) {
            tiles.add(m_tiles.get(hexLoc));
        }

        if (m_tiles.containsKey(otherHexLoc)) {
            tiles.add(m_tiles.get(otherHexLoc));
        }

        return tiles;
    }

    /**
     * Get the vertices connected to the specified edge.
     * @param edge the edge whose adjacent vertices to get
     * @return the two adjacent vertices
     */
    private static VertexLocation[] getAdjacentVertices(EdgeLocation edge) {
        VertexDirection[] vertexDirs = edge.getDir().getNeighboringVertexDirections();
        return new VertexLocation[] {
                new VertexLocation(edge.getHexLoc(), vertexDirs[0]).getNormalizedLocation(),
                new VertexLocation(edge.getHexLoc(), vertexDirs[1]).getNormalizedLocation(),
        };
    }

    /**
     * Get the roads adjacent/connected to the specified road.
     * @param edge
     * @return
     */
    private Collection<IRoad> getAdjacentRoads(EdgeLocation edge) {
        Collection<IRoad> roads = new ArrayList<>();
        for (EdgeLocation neighbor : edge.getAdjacentEdges()) {
            if (m_roads.containsKey(neighbor)) {
                roads.add(m_roads.get(neighbor));
            }
        }

        return roads;
    }

    /**
     * Get the roads adjacent/connected to the specified road.
     * @param edge
     * @return
     */
    private Collection<ITown> getAdjacentTowns(EdgeLocation edge) {
        Collection<ITown> towns = new ArrayList<>();
        for (VertexLocation neighbor : getAdjacentVertices(edge)) {
            if (m_towns.containsKey(neighbor)) {
                towns.add(m_towns.get(neighbor));
            }
        }

        return towns;
    }
}
