package shared.model;

import client.data.PlayerInfo;
import client.network.HttpCommunicator;
import client.network.IServerProxy;
import client.network.NetworkException;
import client.network.ServerProxy;
import shared.definitions.PortType;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

/**
 * handles all the manipulations of the game object
 */
public class GameFacade implements IGameFacade {

    private IGame m_theGame;
    private IServerProxy m_theProxy;
    private static GameFacade m_theFacade = null;

    /**
     * This is a private constructor that is called only when the GameFacade has not been initialized yet
     */
    private GameFacade(IGame theGame, IServerProxy theProxy) {
        setGame(theGame);
        setProxy(theProxy);
    }

    /**
     * This static function is called to get the current instance of the GameFacade
     *
     * @return the current instance of the GameFacade
     */
    public static GameFacade getFacadeInstance() {
        if(m_theFacade == null)
            m_theFacade = new GameFacade(new Game(), new ServerProxy(new HttpCommunicator()));
        return m_theFacade;
    }

    /**
     * This function sets the Game object that the GameFacade will point at
     *
     * @param game the Game object to point the GameFacade at
     */
    public void setGame(IGame game) {
        assert game != null;

        m_theGame = game;
    }

    /**
     * Returns the game object
     *
     * @return the game object
     */
    public IGame getGame() {
        return m_theGame;
    }
    /**
     * Sets the server proxy member
     *
     * @param serverProxy the server proxy for the game facade to use
     */
    public void setProxy(IServerProxy serverProxy) {
        assert serverProxy != null;

        m_theProxy = serverProxy;
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
        boolean mapOpen = m_theGame.getMap().canPlaceRoad(m_theGame.getCurrentPlayer(),edge);
        // does the player have enough resources to build the road?
        boolean haveResources = m_theGame.getCurrentPlayer().canAfford(Prices.ROAD);

        return mapOpen && (isFreeRound() || haveResources);
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
        boolean mapOpen = m_theGame.getMap().canPlaceSettlement(m_theGame.getCurrentPlayer(), vertex);
        // does the player have enough resources?
        boolean haveResources = m_theGame.getCurrentPlayer().canAfford(Prices.SETTLEMENT);

        return mapOpen && (isFreeRound() || haveResources);
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
        boolean mapOpen = m_theGame.getMap().canPlaceCity(m_theGame.getCurrentPlayer(), vertex);
        // does the player have enough resources?
        boolean haveResources = m_theGame.getCurrentPlayer().canAfford(Prices.CITY);

        return mapOpen && (isFreeRound() || haveResources);
    }

    /**
     * Takes an available edge location on the map and places a road on it
     *
     * @param edge the location of the side of a terrain hex
     */
    @Override
    public void placeRoad(EdgeLocation edge) throws ModelException{
        assert edge != null;
        // check that the player can place the road
        assert canPlaceRoad(edge);

        try {
            m_theProxy.buildRoad(m_theGame.getCurrentPlayer().getIndex(), edge, isFreeRound());
        } catch (NetworkException e) {
            throw new ModelException(e);
        }
    }

    /**
     * Takes a vertex location and places a settlement on it
     *
     * @param vertex the location of an intersection of terrain hexes
     */
    @Override
    public void placeSettlement(VertexLocation vertex) throws ModelException {
        assert vertex != null;
        // check that the settlement can be placed
        assert canPlaceSettlement(vertex);

        try {
            m_theProxy.buildSettlement(m_theGame.getCurrentPlayer().getIndex(), vertex, isFreeRound());
        } catch (NetworkException e) {
            throw new ModelException(e);
        }
    }

    /**
     * Takes a vertex location and places a city on it
     *
     * @param vertex the location of an intersection of terrain hexes
     */
    @Override
    public void placeCity(VertexLocation vertex) throws ModelException {
        assert vertex != null;
        // check that the city can be placed
        assert canPlaceCity(vertex);

        try {
            m_theProxy.buildCity(m_theGame.getCurrentPlayer().getIndex(), vertex);
        } catch (NetworkException e) {
            throw new ModelException(e);
        }
    }

    /**
     * Current player buys a DevCard
     */
    @Override
    public void buyDevCard() throws ModelException {
        IPlayer curPlayer = m_theGame.getCurrentPlayer();
        assert curPlayer.canAfford(Prices.DEV_CARD);
        assert m_theGame.getDevCards().getCount() > 0;

        try {
            m_theProxy.buyDevCard(curPlayer.getIndex());
        } catch (NetworkException e) {
            throw new ModelException(e);
        }
    }

    /**
     * Tells the server to play a soldier card
     * @param hex is the location to put the robber
     * @param victim is the player who is robbed
     */
    @Override
    public void playSoldier(HexLocation hex, int victim) throws ModelException {
        assert hex != null;
        assert (victim >= 0 && victim <= 3);

        try {
            m_theProxy.playSoldier(m_theGame.getCurrentPlayer().getIndex(), hex, victim);
        } catch (NetworkException e) {
            throw new ModelException(e);
        }
    }

    /**
     * Play the Year of Plenty card
     */
    @Override
    public void playYearOfPlenty() {

    }

    /**
     * Play the Road Building card
     */
    @Override
    public void playRoadBuilding() {

    }

    /**
     * Play the Monopoly card
     */
    @Override
    public void playMonopoly() {

    }

    /**
     * Play the Monument card
     */
    @Override
    public void playMonument() {

    }

    /**
     * Tells the server to rob a player
     * @param hex the hex to place the robber on
     * @param victim the player index of the player being robbed
     */
    @Override
    public void robPlayer(HexLocation hex, int victim) throws ModelException {
        assert hex != null;
        assert (victim >= 0 && victim <= 3);

        try {
            m_theProxy.robPlayer(m_theGame.getCurrentPlayer().getIndex(), victim, hex);
        } catch (NetworkException e) {
            throw new ModelException(e);
        }
    }

    /**
     * Returns the info for the current player
     *
     * @return the PlayerInfo object initialized with all the player card/piece counts
     */
    @Override
    public IPlayer getCurPlayerInfo() {
        assert m_theGame.getCurrentPlayer() != null;

        return m_theGame.getCurrentPlayer();
    }

    /**
     * Returns the info for the current player's placed roads
     *
     * @return the Collection of IRoad objects initialized with all the locations of the current player's roads
     */
    @Override
    public Collection<IRoad> getRoads() {
        assert m_theGame.getCurrentPlayer().getRoads() != null;

        return m_theGame.getCurrentPlayer().getRoads();
    }

    /**
     * Returns the info for the current player's placed settlements
     *
     * @return the Collection of ITown objects initialized with all the locations of the placed settlements
     */
    @Override
    public Collection<ITown> getSettlements() {
        Collection<ITown> towns = m_theGame.getCurrentPlayer().getTowns();

        assert towns != null;

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
        Collection<ITown> towns = m_theGame.getCurrentPlayer().getTowns();

        assert towns != null;

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
        assert m_theGame.getCurrentPlayer().getTowns() != null;

        return m_theGame.getCurrentPlayer().getTowns();
    }

    /**
     * Returns the info for the terrain hexes
     *
     * @return the Collection of ITile objects initialized with all the locations and types of terrain hexes
     */
    @Override
    public Collection<ITile> getHexes() {
        assert m_theGame.getMap().getTiles() != null;

        return m_theGame.getMap().getTiles();
    }

    /**
     * Returns the terrain hex location for the current location of the robber
     *
     * @return the hex location of where the robber is currently placed
     */
    @Override
    public HexLocation getRobber() {
        assert m_theGame.getMap().getRobber() != null;

        return m_theGame.getMap().getRobber();
    }

    /**
     * Returns the info for the current player's resource counts
     *
     * @return the ResourceBank object containing the counts for the current player
     */
    @Override
    public IResourceBank getPlayerResources() {
        assert m_theGame.getCurrentPlayer().getResources() != null;

        return m_theGame.getCurrentPlayer().getResources();
    }

    /**
     * Returns the ports the current player has
     *
     * @return the ports that the current player has
     */
    @Override
    public Collection<PortType> getPlayerPorts() {
        Collection<PortType> ports = m_theGame.getMap().getPlayersPorts(m_theGame.getCurrentPlayer());

        assert ports != null;

        return ports;
    }

    /**
     * Returns the chat history of the game
     *
     * @return the log initialized with all the chat messages
     */
    @Override
    public ILog getChatHistory() {
        ILog chat = m_theGame.getChatHistory();

        assert chat != null;

        return chat;
    }

    /**
     * Returns the move/action history of the game
     *
     * @return the log initialized with all the move/action messages
     */
    @Override
    public ILog getMoveHistory() {
        ILog gameplay = m_theGame.getGameplayLog();

        assert gameplay != null;

        return gameplay;
    }

    /**
     * accept an incoming trade
     *
     * @param willAccept is true if the the trade is accepted, false otherwise
     */
    @Override
    public void acceptTrade(boolean willAccept) {

    }

    /**
     * offer a trade to another player
     *
     * @param offer                the bundle of resources you are offering
     * @param recipientPlayerIndex the index of the player receiving the trade offer
     */
    @Override
    public void offerTrade(ResourceBundle offer, int recipientPlayerIndex) {

    }

    /**
     * Trade with a port
     *
     * @param ratio   the ratio of trade. 2, 3, or 4 resources for one of any kind
     * @param giving  the bundle of resources that are being given up
     * @param getting the bundle of resources that are being received
     */
    @Override
    public void maritimeTrade(int ratio, ResourceBundle giving, ResourceBundle getting) {

    }

    /**
     * The current player will discard some cards
     *
     * @param discardedCards the bundle of resource cards to discard
     */
    @Override
    public void discardCards(ResourceBundle discardedCards) {

    }

    /**
     * The current player has rolled a number
     *
     * @param rolledNumber the number that was rolled
     */
    @Override
    public void rollNumber(int rolledNumber) {

    }

    /**
     * Finish up the current player's turn
     */
    @Override
    public void finishTurn() {

    }

    // this method is just for determining from the GameState if it is a free round
    private boolean isFreeRound() {
        GameState gs = m_theGame.getGameState();
        return (gs == GameState.FIRST_ROUND || gs == GameState.SECOND_ROUND);
    }
}
