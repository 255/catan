package shared.model;

import shared.definitions.PortType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

import java.util.*;

/**
 * The Catan map.
 *
 * All edge and vertices are normalized.
 *
 * @author Wyatt
 */
public class CatanMap implements ICatanMap {
    private Map<HexLocation, ITile> m_tiles;
    private Map<VertexLocation, ITown> m_towns;
    private Map<EdgeLocation, IRoad> m_roads;
    private Map<EdgeLocation, PortType> m_ports;
    private HexLocation robber;

    /** Construct a new, empty Catan Map */
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
     * Get all of the ports on the map by location.
     * @return an unmodifiable map of ports mapping location to type
     */
    @Override
    public Map<EdgeLocation, PortType> getPorts() {
        return Collections.unmodifiableMap(m_ports);
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
     * Get the road placed on an edge.
     * @param edge the edge from which to get the road
     * @return the road placed on the specified edge, or null if there is none
     */
    @Override
    public IRoad getRoad(EdgeLocation edge) {
        return m_roads.get(edge.getNormalizedLocation());
    }

    /**
     * Get the port types to which the specified player has access.
     *
     * @param player the player whose ports to get
     * @return the ports belonging to the specified player
     */
    @Override
    public Set<PortType> getPlayersPorts(IPlayer player) {
        Set<PortType> ports = new HashSet<>();

        for (Map.Entry<EdgeLocation, PortType> entry : m_ports.entrySet()) {
            for (ITown town : getAdjacentTowns(entry.getKey())) {
                if (player.equals(town.getOwner())) {
                    ports.add(entry.getValue());
                }
            }
        }

        return ports;
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
        Collection<ITown> neighbors = getAdjacentTowns(edge);
        for (ITown neighborTown : neighbors) {
            if (player.equals(neighborTown.getOwner())) {
                return true;
            }
        }

        // TODO: Verify rules w/ regard to roads broken by opponent's settlement
        // check if there is a connecting road
        for (IRoad neighborRoad : getAdjacentRoads(edge)) {
            if (player.equals(neighborRoad.getOwner())
                    && roadIsNotBrokenByOpponentTown(player, edge, neighborRoad)) {
                return true;
            }
        }

        return false;
    }


    /**
     * A player cannot build a road off of an opponent's settlement, even if they have a road on the other side.
     * @param player the player who wants to build a road`
     * @param proposedRoadLocation the edge where they want to build
     * @param currentRoad the current road that connects to this edge
     * @return whether the new road would be broken by an opponent's town
     */
    private boolean roadIsNotBrokenByOpponentTown(IPlayer player, EdgeLocation proposedRoadLocation, IRoad currentRoad) {
        ITown town = getTownBetweenEdges(proposedRoadLocation, currentRoad.getLocation());

        return town == null || player.equals(town.getOwner());
    }

    /**
     * Determine if a player can place a road at the specified location.
     * @param player the player to look at
     * @param vertex the location where the player wants to place a settlement
     * @return a boolean value that reports if the player can place a settlement
     */
    @Override
    public boolean canPlaceSettlement(IPlayer player, VertexLocation vertex) {
        vertex = vertex.getNormalizedLocation();

        // check if the vertex is occupied
        if (m_towns.containsKey(vertex)) {
            return false;
        }

        // check if there are any adjacent towns
        if (!getAdjacentTowns(vertex).isEmpty()) {
            return false;
        }

        // check that the player has a road connecting to this vertex
        for (IRoad road : getAdjacentRoads(vertex)) {
            if (player.equals(road.getOwner())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Determine if a player can place a city at the specified location.
     * @param player the player to look at
     * @param vertex the location where the player wants to place a city
     * @return a boolean value that reports if the player can place a city
     */
    @Override
    public boolean canPlaceCity(IPlayer player, VertexLocation vertex) {
        assert player != null && vertex != null;

        vertex = vertex.getNormalizedLocation();

        ITown town = m_towns.get(vertex);

        return town != null && player.equals(town.getOwner());
    }

    /**
     * Place a road object at the specified edge.
     * The road is placed in the map's data structure AND the road's location is set to edge.
     * @param road the road that is being placed
     * @param edge the edge on which to place the road.
     */
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
     * @param edge the edge around which to check for tiles
     * @return the tiles around the edge
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
     * Get the roads that connect to the specified edge, excluding the road on the edge,
     * if there is one.
     * @param edge the edge around which to check for adjacent roads
     * @return the roads adjacent to the specified edge, excluding the road on the edge, if any
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
     * Get the roads adjacent/connected to the specified vertex.
     * @param vertex the vertex around which to get adjacent roads
     * @return the roads connecting to the vertex, if any
     */
    private Collection<IRoad> getAdjacentRoads(VertexLocation vertex) {
        Collection<IRoad> roads = new ArrayList<>();
        for (EdgeLocation neighbor : vertex.getAdjacentEdges()) {
            if (m_roads.containsKey(neighbor)) {
                roads.add(m_roads.get(neighbor));
            }
        }

        return roads;
    }

    /**
     * Get the towns adjacent/connected to the specified edge.
     * @param edge edge around which to get towns
     * @return the towns the road leads to, if any
     */
    private Collection<ITown> getAdjacentTowns(EdgeLocation edge) {
        Collection<ITown> towns = new ArrayList<>();

        for (VertexLocation neighbor : edge.getAdjacentVertices()) {
            if (m_towns.containsKey(neighbor)) {
                towns.add(m_towns.get(neighbor));
            }
        }

        return towns;
    }

    /**
     * Get the towns adjacent/connected to the specified vertex.
     * @param vertex the vertex around which to get towns
     * @return the towns adjacent to the vertex, if any
     */
    private Collection<ITown> getAdjacentTowns(VertexLocation vertex) {
        Collection<ITown> towns = new ArrayList<>();

        for (VertexLocation neighbor : vertex.getAdjacentVertices()) {
            if (m_towns.containsKey(neighbor)) {
                towns.add(m_towns.get(neighbor));
            }
        }

        return towns;
    }

    /**
     * Get the town between the specified edges, if there is one.
     * @param edge1 an edge
     * @param edge2 the other edge
     * @return the town between the two edges, or null if there is none
     */
    private ITown getTownBetweenEdges(EdgeLocation edge1, EdgeLocation edge2) {
        return m_towns.get(EdgeLocation.getVertexBetweenEdges(edge1, edge2));
    }
}
