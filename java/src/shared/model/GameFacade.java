package shared.model;

import client.data.PlayerInfo;
import shared.definitions.PortType;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import java.util.ArrayList;
import java.util.Collection;

/**
 * handles all the manipulations of the game object
 */
public class GameFacade implements IGameFacade {

    private IGame m_theGame;
    private static GameFacade m_theFacade = null;

    /**
     * This is a private constructor that is called only when the GameFacade has not been initialized yet
     */
    private GameFacade(IGame theGame) {
        setGameObject(theGame);
    }

    /**
     * This static function is called to get the current instance of the GameFacade
     *
     * @return the current instance of the GameFacade
     */
    public static GameFacade getFacadeInstance() {
        if(m_theFacade == null)
            m_theFacade = new GameFacade(new Game());
        return m_theFacade;
    }

    /**
     * This function sets the Game object that the GameFacade will point at
     *
     * @param theGame the Game object to point the GameFacade at
     */
    public void setGameObject(IGame theGame) {
        assert theGame != null;
        assert theGame instanceof Game;

        m_theGame = theGame;
    }

    /**
     * Takes an edge location and determines if a road can be placed on it
     *
     * @param edge the location of the side of a terrain hex
     * @return a boolean value that reports if the user can place a road
     */
    @Override
    public boolean canPlaceRoad(EdgeLocation edge) {
        if(edge == null)
            return false;

        // is the spot open on the map?
        boolean mapOpen = m_theGame.getMap().canPlaceRoad(m_theGame.getLocalPlayer(),edge);
        // does the player have enough resources to build the road?
        boolean haveResources = m_theGame.getLocalPlayer().canAfford(Prices.ROAD);

        return mapOpen && haveResources;
    }

    /**
     * Takes a vertex location and determines if a settlement can be placed on it
     *
     * @param vertex the location of a corner/intersection of terrain hexes
     * @return a boolean value that reports if the user can place a settlement
     */
    @Override
    public boolean canPlaceSettlement(VertexLocation vertex) {
        if(vertex == null)
            return false;

        // is the map open in at the vertex?
        boolean mapOpen = m_theGame.getMap().canPlaceSettlement(m_theGame.getLocalPlayer(), vertex);
        // does the player have enough resources?
        boolean haveResources = m_theGame.getLocalPlayer().canAfford(Prices.SETTLEMENT);

        return mapOpen && haveResources;
    }

    /**
     * Takes a vertex location and determines if a city can be placed on it
     *
     * @param vertex the location of a corner/intersection of terrain hexes
     * @return a boolean value that reports if the user can place a city
     */
    @Override
    public boolean canPlaceCity(VertexLocation vertex) {
        if(vertex == null)
            return false;

        // is there a settlement at the vertex?
        boolean mapOpen = m_theGame.getMap().canPlaceCity(m_theGame.getLocalPlayer(), vertex);
        // does the player have enough resources?
        boolean haveResources = m_theGame.getLocalPlayer().canAfford(Prices.CITY);

        return mapOpen && haveResources;
    }

    /**
     * Takes an available edge location on the map and places a road on it
     *
     * @param edge the location of the side of a terrain hex
     */
    @Override
    public void placeRoad(EdgeLocation edge) {
        assert edge != null;

        // check that the player can place the road // TODO error handling
        assert canPlaceRoad(edge);
        // get the player
        IPlayer p = m_theGame.getLocalPlayer();
        // take a road from the player's PieceBank
        IRoad r = p.getPieceBank().takeRoad();
        // set them as the owner
        r.setOwner(p);
        // place the road on the map
        m_theGame.getMap().placeRoad(r, edge);
        // subtract the resources from the player
        p.getResources().subtract(Prices.ROAD);
    }

    /**
     * Takes a vertex location and places a settlement on it
     *
     * @param vertex the location of an intersection of terrain hexes
     */
    @Override
    public void placeSettlement(VertexLocation vertex) {
        assert vertex != null;

        // check that the settlement can be placed //TODO error handling
        assert canPlaceSettlement(vertex);
        // get the player
        IPlayer p = m_theGame.getLocalPlayer();
        // take a settlement from the player's PieceBank
        Settlement s = p.getPieceBank().takeSettlement();
        // set them as the owner
        s.setOwner(p);
        // place the settlement on the map
        m_theGame.getMap().placeSettlement(s, vertex);
        // subtract the resources from the player
        p.getResources().subtract(Prices.SETTLEMENT);
    }

    /**
     * Takes a vertex location and places a city on it
     *
     * @param vertex the location of an intersection of terrain hexes
     */
    @Override
    public void placeCity(VertexLocation vertex) {
        assert vertex != null;

        // check that the city can be placed // TODO error handling
        assert canPlaceCity(vertex);
        // get the player
        IPlayer p = m_theGame.getLocalPlayer();
        // take a city from the player's PieceBank
        City c = p.getPieceBank().takeCity();
        // set them as the owner
        c.setOwner(p);
        // place the city on the map
        m_theGame.getMap().placeCity(c, vertex);
        // subtract the resources from the player
        p.getResources().subtract(Prices.CITY);
    }

    /**
     * Takes a hex location and places the robber on it
     *
     * @param hex the location of a terrain hex
     */
    @Override
    public void placeRobber(HexLocation hex) {
        assert hex != null;

        // move robber to the given hex
        m_theGame.getMap().moveRobber(hex);
    }

    /**
     * Takes an IPlayer object and removes the given resource card from the player
     *
     * @param player   the player to take cards from
     * @param cardType the resource card type to decrement from the player hand
     */
    @Override
    public void takeCardFromPlayer(IPlayer player, ResourceType cardType) {
        assert player != null;
        assert cardType != null;

        // bundle the resource
        IResourceBundle rb = IResourceBundle.resourceToBundle(cardType);
        // remove the specified resource from the provided player object
        player.getResources().subtract(rb);
        // add the resource to the resources of this player
        m_theGame.getLocalPlayer().getResources().add(rb);
    }

    /**
     * Takes an IPlayer object and gives the given card object to the player
     *
     * @param player   the player to take cards from
     * @param cardType the Card object type to decrement from the player hand
     */
    @Override
    public void giveCardToPlayer(IPlayer player, ResourceType cardType) {
        assert player != null;
        assert cardType != null;

        // bundle the resource
        IResourceBundle rb = IResourceBundle.resourceToBundle(cardType);
        // take the resource from the bank of this player
        m_theGame.getLocalPlayer().getResources().subtract(rb);
        // add the resource to the provided player object
        player.getResources().add(rb);

    }

    /**
     * Returns the info for the current player
     *
     * @return the PlayerInfo object initialized with all the player card/piece counts
     */
    @Override
    public PlayerInfo getCurPlayerInfo() {
        // make a new PlayerInfo
        PlayerInfo pInfo = new PlayerInfo();

        // get the player
        IPlayer p = m_theGame.getLocalPlayer();

        // initialize player info
        pInfo.setId(p.getId());
        pInfo.setPlayerIndex(p.getIndex());
        pInfo.setName(p.getName());
        pInfo.setColor(p.getColor());

        // return the player info
        return pInfo;
    }

    /**
     * Returns the info for the current player's placed roads
     *
     * @return the Collection of IRoad objects initialized with all the locations of the current player's roads
     */
    @Override
    public Collection<IRoad> getRoads() {
        return m_theGame.getLocalPlayer().getRoads();
    }

    /**
     * Returns the info for the current player's placed settlements
     *
     * @return the Collection of ITown objects initialized with all the locations of the placed settlements
     */
    @Override
    public Collection<ITown> getSettlements() {
        Collection<ITown> towns = m_theGame.getLocalPlayer().getTowns();
        Collection<ITown> settlements = new ArrayList<ITown>();

        for(ITown s : towns) {
            if(s instanceof Settlement) {
                settlements.add(s);
            }
        }

        return settlements;
    }

    /**
     * Returns the info for the current player's placed cities
     *
     * @return the Collection of ITown objects initialized with all the locations of the placed cities
     */
    @Override
    public Collection<ITown> getCities() {
        Collection<ITown> towns = m_theGame.getLocalPlayer().getTowns();
        Collection<ITown> cities = new ArrayList<ITown>();

        for(ITown city : towns) {
            if(city instanceof City) {
                cities.add(city);
            }
        }

        return cities;
    }

    /**
     * Returns the info for the current player's placed settlements and cities
     *
     * @return the Collection of ITown objects initialized with all the locations of the placed settlements and cities
     */
    @Override
    public Collection<ITown> getTowns() {
        return m_theGame.getLocalPlayer().getTowns();
    }

    /**
     * Returns the info for the terrain hexes
     *
     * @return the Collection of ITile objects initialized with all the locations and types of terrain hexes
     */
    @Override
    public Collection<ITile> getHexes() {
        return m_theGame.getMap().getTiles();
    }

    /**
     * Returns the terrain hex location for the current location of the robber
     *
     * @return the hex location of where the robber is currently placed
     */
    @Override
    public HexLocation getRobber() {
        return m_theGame.getMap().getRobber();
    }

    /**
     * Returns the info for the current player's resource counts
     *
     * @return the ResourceBundle object containing the counts for the current player
     */
    @Override
    public IResourceBundle getPlayerResources() {
        return m_theGame.getLocalPlayer().getResources().getResources();
    }

    /**
     * Returns the ports the current player has
     *
     * @return the ports that the current player has
     */
    @Override
    public Collection<PortType> getPlayerPorts() {
        return m_theGame.getMap().getPlayersPorts(m_theGame.getLocalPlayer());
    }
}
