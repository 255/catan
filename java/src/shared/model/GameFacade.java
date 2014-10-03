package shared.model;

import client.network.HttpCommunicator;
import client.network.IServerProxy;
import client.network.NetworkException;
import client.network.ServerProxy;
import shared.definitions.DevCardType;
import shared.definitions.PortType;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

import java.util.Collection;

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
    @Override
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
    @Override
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
        if(edge == null || (m_theGame.getGameState() != GameState.PLAYING && !isFreeRound()))
            return false;

        // is the spot open on the map?
        boolean mapOpen = m_theGame.getMap().canPlaceRoad(m_theGame.getLocalPlayer(),edge);
        // does the player have enough resources to build the road?
        boolean haveResources = m_theGame.getLocalPlayer().canAfford(Prices.ROAD);

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
        if(vertex == null || (m_theGame.getGameState() != GameState.PLAYING && !isFreeRound()))
            return false;

        // is the map open in at the vertex?
        boolean mapOpen = m_theGame.getMap().canPlaceSettlement(m_theGame.getLocalPlayer(), vertex);
        // does the player have enough resources?
        boolean haveResources = m_theGame.getLocalPlayer().canAfford(Prices.SETTLEMENT);

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
        if(vertex == null || (m_theGame.getGameState() != GameState.PLAYING && !isFreeRound()))
            return false;

        // is there a settlement at the vertex?
        boolean mapOpen = m_theGame.getMap().canPlaceCity(m_theGame.getLocalPlayer(), vertex);
        // does the player have enough resources?
        boolean haveResources = m_theGame.getLocalPlayer().canAfford(Prices.CITY);

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
        assert playingOnlyPreconditions();
        // check that the player can place the road
        assert canPlaceRoad(edge);

        try {
            m_theProxy.buildRoad(m_theGame.getLocalPlayer().getIndex(), edge, isFreeRound());
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
        assert playingOnlyPreconditions();
        // check that the settlement can be placed
        assert canPlaceSettlement(vertex);

        try {
            m_theProxy.buildSettlement(m_theGame.getLocalPlayer().getIndex(), vertex, isFreeRound());
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
        assert playingOnlyPreconditions();
        // check that the city can be placed
        assert canPlaceCity(vertex);

        try {
            m_theProxy.buildCity(m_theGame.getLocalPlayer().getIndex(), vertex);
        } catch (NetworkException e) {
            throw new ModelException(e);
        }
    }

    /**
     * Current player buys a DevCard
     */
    @Override
    public void buyDevCard() throws ModelException {
        IPlayer curPlayer = m_theGame.getLocalPlayer();
        assert curPlayer.canAfford(Prices.DEV_CARD);
        assert m_theGame.getDevCards().getCount() > 0;
        assert playingOnlyPreconditions();

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
        assert !(hex.equals(m_theGame.getMap().getRobber()));
        assert (victim >= 0 && victim <= 3);
        assert m_theGame.getPlayers().get(victim).getResources().getCount() > 0;
        assert genDevCardPreConditions(DevCardType.SOLDIER);

        try {
            m_theProxy.playSoldier(m_theGame.getLocalPlayer().getIndex(), hex, victim);
        } catch (NetworkException e) {
            throw new ModelException(e);
        }
    }

    /**
     * Play the Year of Plenty card
     * @param r1 the first resource to take from the bank
     * @param r2 the second resource to take from the bank
     */
    @Override
    public void playYearOfPlenty(ResourceType r1, ResourceType r2) throws ModelException {
        IPlayer curPlayer = m_theGame.getLocalPlayer();
        IResourceBank rb = m_theGame.getResourceBank();
        IResourceBank request = new ResourceBank();
        request.add(IResourceBank.resourceToBank(r1));
        request.add(IResourceBank.resourceToBank(r2));
        assert rb.canAfford(request);
        assert genDevCardPreConditions(DevCardType.YEAR_OF_PLENTY);

        try {
            m_theProxy.playYearOfPlenty(curPlayer.getIndex(), r1, r2);
        } catch (NetworkException e) {
            throw new ModelException(e);
        }
    }

    /**
     * Play the Road Building card
     * @param e1 location of the first road
     * @param e2 location of the second road
     */
    @Override
    public void playRoadBuilding(EdgeLocation e1, EdgeLocation e2) throws ModelException {
        IPlayer curPlayer = m_theGame.getLocalPlayer();

        // TODO might be a bug where it doesn't detect if the second road can be built on the first road's location
        assert canPlaceRoad(e1) && canPlaceRoad(e2);
        assert curPlayer.getPieceBank().availableRoads() >= 2;
        assert genDevCardPreConditions(DevCardType.ROAD_BUILD);

        try {
            m_theProxy.playRoadBuilding(curPlayer.getIndex(), e1, e2);
        } catch (NetworkException e) {
            throw new ModelException(e);
        }
    }

    /**
     * Play the Monopoly card
     * @param rType the type of resource to monopolize
     */
    @Override
    public void playMonopoly(ResourceType rType) throws ModelException {
        assert genDevCardPreConditions(DevCardType.MONOPOLY);

        try {
            m_theProxy.playMonopoly(m_theGame.getLocalPlayer().getIndex(), rType);
        } catch (NetworkException e) {
            throw new ModelException(e);
        }
    }

    /**
     * Play the Monument card
     */
    @Override
    public void playMonument() throws ModelException {
        assert genDevCardPreConditions(DevCardType.MONUMENT);

        try {
            m_theProxy.playMonument(m_theGame.getLocalPlayer().getIndex());
        } catch (NetworkException e) {
            throw new ModelException(e);
        }
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
            m_theProxy.robPlayer(m_theGame.getLocalPlayer().getIndex(), victim, hex);
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
     * Returns the info for the current player's resource counts
     *
     * @return the ResourceBank object containing the counts for the current player
     */
    @Override
    public IResourceBank getPlayerResources() {
        assert m_theGame.getLocalPlayer().getResources() != null;

        return m_theGame.getLocalPlayer().getResources();
    }

    /**
     * Returns the ports the current player has
     *
     * @return the ports that the current player has
     */
    @Override
    public Collection<PortType> getPlayerPorts() {
        Collection<PortType> ports = m_theGame.getMap().getPlayersPorts(m_theGame.getLocalPlayer());

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
    public void acceptTrade(boolean willAccept, IResourceBank tradeBundle) throws ModelException {
        IPlayer p = m_theGame.getLocalPlayer();
        assert m_theGame.getTradeOffer().getReceiver().equals(p);
        if(willAccept)
            assert p.getResources().canAfford(tradeBundle);

        try {
            m_theProxy.acceptTrade(p.getIndex(), willAccept);
        } catch (NetworkException e) {
            throw new ModelException(e);
        }
    }

    /**
     * offer a trade to another player
     *
     * @param offer                the bundle of resources you are offering
     * @param recipientPlayerIndex the index of the player receiving the trade offer
     */
    @Override
    public void offerTrade(IResourceBank offer, int recipientPlayerIndex) throws ModelException {
        IPlayer p = m_theGame.getLocalPlayer();
        assert p.canAfford(offer);
        assert playingOnlyPreconditions();

        try {
            m_theProxy.offerTrade(p.getIndex(), offer, recipientPlayerIndex);
        } catch (NetworkException e) {
            throw new ModelException(e);
        }
    }

    /**
     * Trade with a port
     *
     * @param ratio   the ratio of trade. 2, 3, or 4 resources for one of any kind
     * @param giving  the bundle of resources that are being given up
     * @param getting the bundle of resources that are being received
     */
    @Override
    public void maritimeTrade(int ratio, ResourceType giving, ResourceType getting) throws ModelException {
        IPlayer p = m_theGame.getLocalPlayer();
        assert p.getResources().getCount(giving) >= ratio;
        assert playingOnlyPreconditions();

        try {
            m_theProxy.maritimeTrade(p.getIndex(), ratio, giving, getting);
        } catch (NetworkException e) {
            throw new ModelException(e);
        }
    }

    /**
     * The current player will discard some cards
     *
     * @param discardedCards the bundle of resource cards to discard
     */
    @Override
    public void discardCards(IResourceBank discardedCards) throws ModelException {
        IPlayer p = m_theGame.getLocalPlayer();
        assert (m_theGame.getGameState() == GameState.DISCARDING);
        assert p.getResources().getCount() > 7;
        assert p.canAfford(discardedCards);

        try {
            m_theProxy.discardCards(p.getIndex(), discardedCards);
        } catch (NetworkException e) {
            throw new ModelException(e);
        }
    }

    /**
     * The current player has rolled a number
     *
     * @param rolledNumber the number that was rolled
     */
    @Override
    public void rollNumber(int rolledNumber) throws ModelException {
        IPlayer p = m_theGame.getLocalPlayer();
        assert rolledNumber >= 2 && rolledNumber <= 12;
        assert m_theGame.getGameState() == GameState.ROLLING;
        assert p.equals(m_theGame.getLocalPlayer()); // it's the local player's turn

        try {
            m_theProxy.rollNumber(p.getIndex(), rolledNumber);
        } catch (NetworkException e) {
            throw new ModelException(e);
        }
    }

    /**
     * Finish up the current player's turn
     */
    @Override
    public void finishTurn() throws ModelException {
        IPlayer p = m_theGame.getLocalPlayer();
        assert m_theGame.getGameState() == GameState.PLAYING;
        assert playingOnlyPreconditions();

        try {
            m_theProxy.finishTurn(p.getIndex());
        } catch (NetworkException e) {
            throw new ModelException(e);
        }
    }

    // this method is just for determining from the GameState if it is a free round
    private boolean isFreeRound() {
        GameState gs = m_theGame.getGameState();
        return (gs == GameState.FIRST_ROUND || gs == GameState.SECOND_ROUND);
    }

    // this method checks the general preconditions for dev cards outlined in the REST API
    private boolean genDevCardPreConditions(DevCardType dType) {
        IPlayer curP = m_theGame.getLocalPlayer();
        boolean hasCard = (curP.getPlayableDevCards().getCount(dType) > 0);
        boolean noCardsPlayed = curP.hasPlayedDevCard();
        boolean curPlayerTurn = m_theGame.getLocalPlayer().equals(curP);
        boolean statusIsPlaying = (m_theGame.getGameState() == GameState.PLAYING);

        return  hasCard && noCardsPlayed && curPlayerTurn && statusIsPlaying;
    }

    // this method checks for the General Preconditions for 'Playing' only moves
    private boolean playingOnlyPreconditions() {
        IPlayer p = m_theGame.getLocalPlayer();
        boolean localPlayerTurn = p.equals(m_theGame.getLocalPlayer());
        boolean inPlayingState = m_theGame.getGameState() == GameState.PLAYING;

        return localPlayerTurn && inPlayingState;
    }
}
