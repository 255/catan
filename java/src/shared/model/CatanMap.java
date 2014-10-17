package shared.model;

import shared.definitions.PortType;
import shared.locations.*;

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
    private HexLocation m_robber;

    /** Construct a new, blank Catan Map */
    public CatanMap(Map<HexLocation, ITile> tiles, Map<EdgeLocation, PortType> ports) {
        m_tiles = tiles;
        m_ports = ports;
        m_towns = new HashMap<>();
        m_roads = new HashMap<>();
        m_robber = null;
    }

    /** Construct a map with the specified objects and tiles */
    public CatanMap(Map<HexLocation, ITile> tiles, Map<VertexLocation, ITown> towns, Map<EdgeLocation, IRoad> roads,
                    Map<EdgeLocation, PortType> ports, HexLocation robber) throws ModelException {

        this.m_tiles = tiles;
        this.m_towns = towns;
        this.m_roads = roads;
        this.m_ports = ports;
        this.m_robber = robber;

        // check that all pieces on tiles
        for (EdgeLocation edge : m_ports.keySet()) {
            if (!isOnMap(edge)) throw new ModelException("Some pieces are off the map!");
        }

        for (EdgeLocation edge : m_roads.keySet()) {
            if (!isOnMap(edge)) throw new ModelException("Some pieces are off the map!");
        }

        for (VertexLocation vertex : m_towns.keySet()) {
            if (!isOnMap(vertex)) throw new ModelException("Some pieces are off the map!");
        }
        
        // Apparently we can't use Java 8 after all
/*
        if (!m_tiles.containsKey(robber)) {
            throw new ModelException("The robber is not on the map!");
        }
        else if (m_ports.keySet().stream().anyMatch((EdgeLocation edge) -> !isOnMap(edge))
                    || m_roads.keySet().stream().anyMatch((EdgeLocation loc) -> !isOnMap(loc))
                    || m_towns.keySet().stream().anyMatch((VertexLocation loc) -> !isOnMap(loc))) {
            throw new ModelException("Some pieces are off the map!");
        }
*/

        // Set robber flag in tile
        m_tiles.get(robber).placeRobber();
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
     * Get all of the cities on the map.
     *
     * @return a collection of cities (no settlements)
     */
    @Override
    public Collection<ITown> getCities() {
        Collection<ITown> cities = new ArrayList<>();
        for (ITown town : m_towns.values()) {
            if (town.getClass().equals(City.class)) {
                cities.add(town);
            }
        }

        return cities;
    }

    /**
     * Get all of the settlements on the map.
     *
     * @return a collection of cities (no settlements)
     */
    @Override
    public Collection<ITown> getSettlements() {
        Collection<ITown> settlements = new ArrayList<>();
        for (ITown town : m_towns.values()) {
            if (town.getClass().equals(Settlement.class)) {
                settlements.add(town);
            }
        }

        return settlements;
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

        return Collections.unmodifiableSet(ports);
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
        assert player != null && edge != null;

        edge = edge.getNormalizedLocation();

        // check is the edge is on land (not water)
        if (!isOnMap(edge)) {
            return false;
        }

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
     * Determine if a player can place a road during the game initialization rounds.
     * This function differs from canPlaceRoad in that roads do not have to be connected to anything.
     *
     * During the two initialization rounds, roads cannot be placed next to any settlements,
     * because the player must place a settlement next to the road.
     *
     * According to the spec, the roads are placed first, so there is no corresponding
     * "can place initial settlement" function (and place settlement does not enforce rules).
     *
     * @param player the player to look at
     * @param edge   the location where the player wants to place a road
     * @return a boolean value that reports if the player can place a road
     */
    @Override
    public boolean canPlaceInitialRoad(IPlayer player, EdgeLocation edge) {
        assert edge != null && player != null;

        edge = edge.getNormalizedLocation();

        // roads must be placed on the map, not on top of another road, and not next to a town
        return isOnMap(edge) && !m_roads.containsKey(edge) && getAdjacentTowns(edge).isEmpty();
    }

    /**
     * Check if a player can legally place two roads.
     * The validity of the second placement factors in the first placement.
     * This only checks physical placement, not the ability of the player to buy a road.
     * @param player the player who wants to place roads
     * @param firstEdge the location of the first road the player will place
     * @param secondEdge the second road location to place
     * @return true if the roads work, false if not
     */
    @Override
    public boolean canPlaceTwoRoads(IPlayer player, EdgeLocation firstEdge, EdgeLocation secondEdge) {
        assert player != null && firstEdge != null && secondEdge != null;

        firstEdge = firstEdge.getNormalizedLocation();
        secondEdge = secondEdge.getNormalizedLocation();

        boolean canPlaceBoth = false;

        if (canPlaceRoad(player, firstEdge)) {
            assert !m_roads.containsKey(firstEdge);

            // temporarily place the first road
            m_roads.put(firstEdge, new Road(player, firstEdge));

            canPlaceBoth = canPlaceRoad(player, secondEdge);

            m_roads.remove(firstEdge);
        }

        return canPlaceBoth;
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

        // check if vertex is on the map
        if (!isOnMap(vertex)) {
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

        ITown town = m_towns.get(vertex.getNormalizedLocation());

        return town != null && town instanceof Settlement && player.equals(town.getOwner());
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
        assert isOnMap(edge) : "Edge not on map.";

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
     * This does NOT check whether a player has enough resources, etc. or enforce any rules.
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
     * No error checking is done here.
     *
     * @param city   the city that is being placed
     * @param vertex the vertex on which to place the city.
     */
    @Override
    public void placeCity(City city, VertexLocation vertex) {
        vertex = vertex.getNormalizedLocation();

        assert m_towns.containsKey(vertex): "There not already a settlement placed at " + vertex.toString();
        assert m_towns.get(vertex) instanceof Settlement : "A city must be placed on a settlement only!";
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
        return m_robber;
    }

    /**
     * Change the location of the robber.
     * @param location the new hex location of the robber
     */
    @Override
    public void moveRobber(HexLocation location) {
        assert m_tiles.containsKey(location);

        m_tiles.get(m_robber).removeRobber();

        m_robber = location;
        m_tiles.get(location).placeRobber();
    }

    /**
     * Determine whether the specified location is a valid place for the robber.
     * The robber must always move and must stay on the map.
     *
     * @param location the new location for the robber
     * @return true if the location is valid
     */
    @Override
    public boolean canPlaceRobber(HexLocation location) {
        return !location.equals(m_robber) && isOnMap(location);
    }

    /**
     * Get the players who have towns around this tile and have resources to rob.
     *
     * @param tile
     * @param exclude the player to exclude from the set (the local player)
     * @return
     */
    @Override
    public Set<IPlayer> getRobbablePlayersOnTile(HexLocation tile, IPlayer exclude) {
        Set<IPlayer> players = new HashSet<>();

        for (VertexDirection dir : VertexDirection.values()) {
            VertexLocation loc = new VertexLocation(tile, dir).getNormalizedLocation();
            if (m_towns.containsKey(loc)) {
                IPlayer player = m_towns.get(loc).getOwner();

                if (!player.equals(exclude) && player.hasResources()) {
                    players.add(player);
                }
            }
        }

        return players;
    }

    /**
     * Get the tiles adjacent to the specified edge.
     * @param edge the edge around which to check for tiles
     * @return the tiles around the edge
     */
    private Collection<ITile> getAdjacentTiles(EdgeLocation edge) {
        assert edge != null;
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

    private boolean isOnMap(HexLocation hex) {
        return m_tiles.containsKey(hex);
    }

    /**
     * Determine whether the specified edge borders a valid tile on the Catan map.
     * @param edge the edge to test
     * @return whether the edge is on the map
     */
    private boolean isOnMap(EdgeLocation edge) {
        return m_tiles.containsKey(edge.getHexLoc())
                || m_tiles.containsKey(edge.getEquivalentEdge().getHexLoc());
    }

    /**
     * Determine whether the specified vertex is on a valid (non-water) tile on the Catan map.
     * @param vertex the edge to test
     * @return whether the edge is on the map
     */
    private boolean isOnMap(VertexLocation vertex) {
        HexLocation hexLoc =  vertex.getHexLoc();
        // test the vertex's hex loc
        if (m_tiles.containsKey(hexLoc)) {
            return true;
        }

        // check the other hex locs this is adjacent to
        for (EdgeDirection dir : vertex.getDir().getNeighboringEdgeDirections()) {
            if (m_tiles.containsKey(hexLoc.getNeighborLoc(dir))) {
                return true;
            }
        }

        // didn't find any of the locs...
        return false;
    }
}
