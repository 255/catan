package shared.model;

import client.data.PlayerInfo;
import shared.definitions.PortType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

import java.util.Collection;

/**
 * handles all the manipulations of the game object
 */
public class GameFacade implements IGameFacade {
    /**
     * Takes an edge location and determines if a road can be placed on it
     *
     * @param edge the location of the side of a terrain hex
     * @return a boolean value that reports if the user can place a road
     */
    @Override
    public boolean canPlaceRoad(EdgeLocation edge) {
        return false;
    }

    /**
     * Takes a vertex location and determines if a settlement can be placed on it
     *
     * @param vertex the location of a corner/intersection of terrain hexes
     * @return a boolean value that reports if the user can place a settlement
     */
    @Override
    public boolean canPlaceSettlement(VertexLocation vertex) {
        return false;
    }

    /**
     * Takes a vertex location and determines if a city can be placed on it
     *
     * @param vertex the location of a corner/intersection of terrain hexes
     * @return a boolean value that reports if the user can place a city
     */
    @Override
    public boolean canPlaceCity(VertexLocation vertex) {
        return false;
    }

    /**
     * Takes an edge location and places a road on it
     *
     * @param edge the location of the side of a terrain hex
     */
    @Override
    public void placeRoad(EdgeLocation edge) {

    }

    /**
     * Takes a vertex location and places a settlement on it
     *
     * @param vertex the location of an intersection of terrain hexes
     */
    @Override
    public void placeSettlement(VertexLocation vertex) {

    }

    /**
     * Takes a vertex location and places a city on it
     *
     * @param vertex the location of an intersection of terrain hexes
     */
    @Override
    public void placeCity(VertexLocation vertex) {

    }

    /**
     * Takes a hex location and places the robber on it
     *
     * @param hex the location of a terrain hex
     */
    @Override
    public void placeRobber(HexLocation hex) {

    }

    /**
     * Takes an IPlayer object and removes the given card object from the player
     *
     * @param player   the player to take cards from
     * @param cardType the Card object type to decrement from the player hand
     */
    @Override
    public void takeCardFromPlayer(IPlayer player, Object cardType) {

    }

    /**
     * Takes an IPlayer object and gives the given card object to the player
     *
     * @param player   the player to take cards from
     * @param cardType the Card object type to decrement from the player hand
     */
    @Override
    public void giveCardToPlayer(IPlayer player, Object cardType) {

    }

    /**
     * Returns the info for the current player
     *
     * @return the PlayerInfo object initialized with all the player card/piece counts
     */
    @Override
    public PlayerInfo getCurPlayerInfo() {
        return null;
    }

    /**
     * Returns the info for the current player's placed roads
     *
     * @return the Collection of IRoad objects initialized with all the locations of the current player's roads
     */
    @Override
    public Collection<IRoad> getRoads() {
        return null;
    }

    /**
     * Returns the info for the current player's placed settlements
     *
     * @return the Collection of ITown objects initialized with all the locations of the placed settlements
     */
    @Override
    public Collection<ITown> getSettlements() {
        return null;
    }

    /**
     * Returns the info for the current player's placed cities
     *
     * @return the Collection of ITown objects initialized with all the locations of the placed cities
     */
    @Override
    public Collection<ITown> getCities() {
        return null;
    }

    /**
     * Returns the info for the number pieces
     *
     * @return the Collection of ITile terrain hex objects initialized with all the locations of the number pieces
     */
    @Override
    public Collection<ITile> getNumberPieces() {
        return null;
    }

    /**
     * Returns the info for the terrain hexes
     *
     * @return the Collection of ITile objects initialized with all the locations and types of terrain hexes
     */
    @Override
    public Collection<ITile> getHexes() {
        return null;
    }

    /**
     * Returns the terrain hex location for the current location of the robber
     *
     * @return the hex location of where the robber is currently placed
     */
    @Override
    public HexLocation getRobber() {
        return null;
    }

    /**
     * Returns the info for the current player's resource counts
     *
     * @return the ResourceBundle object containing the counts for the current player
     */
    @Override
    public IResourceBundle getPlayerResources() {
        return null;
    }

    /**
     * Returns the ports the current player has
     *
     * @return the ports that the current player has
     */
    @Override
    public Collection<PortType> getPlayerPorts() {
        return null;
    }
}
