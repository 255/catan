package shared.model;

import client.network.HttpCommunicator;
import client.network.IServerProxy;
import client.network.NetworkException;
import client.network.ServerProxy;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

import java.util.Random;

/**
 * Handles all the calls to the ServerProxy
 */
public class ServerModelFacade implements IServerModelFacade {
    private IServerProxy m_theProxy;
    private static ServerModelFacade m_theFacade = null;

    /**
     * This is a private constructor that is called only when the ServerModelFacade has not been initialized yet
     */
    private ServerModelFacade(IServerProxy theProxy) {
        setServerProxy(theProxy);
    }

    /**
     * This static function is called to get the current instance of the GameModelFacade
     *
     * @return the current instance of the GameModelFacade
     */
    public static ServerModelFacade getInstance() {
        if(m_theFacade == null)
            m_theFacade = new ServerModelFacade(new ServerProxy(new HttpCommunicator()));
        return m_theFacade;
    }

    /**
     * This function sets the Game object that the GameModelFacade will point at
     *
     * @param theProxy is the proxy object to point the ServerModelFacade at
     */
    @Override
    public void setServerProxy(IServerProxy theProxy) {
        assert theProxy != null;
        m_theProxy = theProxy;
    }

    /**
     * Takes in a chat message to be added to the chat log
     *
     * @param message the string of text to add to the chat log
     */
    @Override
    public void sendChat(String message) throws ModelException {
        assert message != null;

        try {
            m_theProxy.sendChat(Game.getInstance().getLocalPlayer().getIndex(), message);
        } catch (NetworkException e) {
            throw new ModelException(e);
        }
    }

    /**
     * Takes an available edge location on the map and places a road on it
     *
     * @param edge the location of the side of a terrain hex
     */
    @Override
    public void placeRoad(EdgeLocation edge) throws ModelException {
        assert edge != null;

        if (!GameModelFacade.getInstance().canPlaceRoad(edge)) {
            throw new ModelException("Preconditions for action not met.");
        }

        try {
            m_theProxy.buildRoad(Game.getInstance().getLocalPlayer().getIndex(), edge, GameModelFacade.getInstance().isFreeRound());
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
        if (!GameModelFacade.getInstance().canPlaceSettlement(vertex)) {
            throw new ModelException("Preconditions for action not met.");
        }

        try {
            m_theProxy.buildSettlement(Game.getInstance().getLocalPlayer().getIndex(), vertex, GameModelFacade.getInstance().isFreeRound());
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
        if (!GameModelFacade.getInstance().canPlaceCity(vertex)) {
            throw new ModelException("Preconditions for action not met.");
        }

        try {
            m_theProxy.buildCity(Game.getInstance().getLocalPlayer().getIndex(), vertex);
        } catch (NetworkException e) {
            throw new ModelException(e);
        }
    }

    /**
     * Current player buys a DevCard
     */
    @Override
    public void buyDevCard() throws ModelException {
        if (!GameModelFacade.getInstance().canBuyDevCard()) {
            throw new ModelException("Preconditions for action not met.");
        }

        IPlayer curPlayer = Game.getInstance().getLocalPlayer();
        try {
            m_theProxy.buyDevCard(curPlayer.getIndex());
        } catch (NetworkException e) {
            throw new ModelException(e);
        }
    }

    /**
     * Tells the server to play a soldier card
     * @param hex is the location to put the robber
     * @param victim is the player who is robbed, or null if no one is being robbed
     */
    @Override
    public void playSoldier(HexLocation hex, IPlayer victim) throws ModelException {
        assert hex != null;

        if (!GameModelFacade.getInstance().canPlaySoldier(hex)) {
            throw new ModelException("Preconditions for action not met.");
        }

        try {
            m_theProxy.playSoldier(Game.getInstance().getLocalPlayer().getIndex(), hex, (victim == null ? -1 : victim.getIndex()));
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
        if (!GameModelFacade.getInstance().canPlayYearOfPlenty(r1, r2)) {
            throw new ModelException("Preconditions for action not met.");
        }

        try {
            m_theProxy.playYearOfPlenty(Game.getInstance().getLocalPlayer().getIndex(), r1, r2);
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
        if (!GameModelFacade.getInstance().canPlayRoadBuilding(e1, e2)) {
            throw new ModelException("Preconditions for action not met.");
        }

        try {
            m_theProxy.playRoadBuilding(Game.getInstance().getLocalPlayer().getIndex(), e1, e2);
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
        if (!GameModelFacade.getInstance().canPlayMonopoly(rType)) {
            throw new ModelException("Preconditions for action not met.");
        }

        try {
            m_theProxy.playMonopoly(Game.getInstance().getLocalPlayer().getIndex(), rType);
        } catch (NetworkException e) {
            throw new ModelException(e);
        }
    }

    /**
     * Play the Monument card
     */
    @Override
    public void playMonument() throws ModelException {
        if (!GameModelFacade.getInstance().canPlayMonument()) {
            throw new ModelException("Preconditions for action not met.");
        }

        try {
            m_theProxy.playMonument(Game.getInstance().getLocalPlayer().getIndex());
        } catch (NetworkException e) {
            throw new ModelException(e);
        }
    }

    /**
     * Tells the server to rob a player
     * @param hex the hex to place the robber on
     * @param victim is the player who is robbed, or null if no one is being robbed
     */
    @Override
    public void robPlayer(HexLocation hex, IPlayer victim) throws ModelException {
        assert hex != null;
// TODO
        try {
            m_theProxy.robPlayer(Game.getInstance().getLocalPlayer().getIndex(), (victim == null ? -1 : victim.getIndex()), hex);
        } catch (NetworkException e) {
            throw new ModelException(e);
        }
    }

    /**
     * accept an incoming trade
     *
     * @param willAccept is true if the the trade is accepted, false otherwise
     */
    @Override
    public void acceptTrade(boolean willAccept) throws ModelException {
        if (willAccept && !GameModelFacade.getInstance().canAcceptTrade()) {
            throw new ModelException("Preconditions for action not met.");
        }

        IPlayer p = Game.getInstance().getLocalPlayer();
        assert Game.getInstance().getTradeOffer().getReceiver().equals(p);

        try {
            m_theProxy.acceptTrade(p.getIndex(), willAccept);
        } catch (NetworkException e) {
            throw new ModelException(e);
        }
    }

    /**
     * offer a trade to another player
     *  @param offer                the bundle of resources you are offering
     * @param recipientPlayer the index of the player receiving the trade offer
     */
    @Override
    public void offerTrade(IResourceBank offer, IPlayer recipientPlayer) throws ModelException {
        IPlayer p = Game.getInstance().getLocalPlayer();

        if (!p.canAfford(offer) || !Game.getInstance().localPlayerIsPlaying()) {
            throw new ModelException("Preconditions for action not met.");
        }

        try {
            m_theProxy.offerTrade(p.getIndex(), offer, recipientPlayer.getIndex());
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
        assert ratio == 2 || ratio == 3 || ratio == 4;

        IPlayer p = Game.getInstance().getLocalPlayer();

        if (!Game.getInstance().localPlayerIsPlaying() || p.getResources().getCount(giving) < ratio) {
            throw new ModelException("Preconditions for action not met.");
        }

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
        IPlayer p = Game.getInstance().getLocalPlayer();

        if (Game.getInstance().getGameState() != GameState.DISCARDING
                || p.getResources().getCount() <= 7
                || !p.canAfford(discardedCards)) {
            throw new ModelException("Preconditions for action not met.");
        }

        try {
            m_theProxy.discardCards(p.getIndex(), discardedCards);
        } catch (NetworkException e) {
            throw new ModelException(e);
        }
    }

    /**
     * The current player rolls a number
     */
    @Override
    public int rollNumber() throws ModelException {
        // the range is 10 (2 - 12)
        final int ROLL_NUMBERS_RANGE = 10;
        final int MINIMUM_ROLL       = 2;
        final Random randomNumberGenerator = new Random();

        int rolledNumber = randomNumberGenerator.nextInt(ROLL_NUMBERS_RANGE) + MINIMUM_ROLL;
        assert rolledNumber >= 2 && rolledNumber <= 12;

        IPlayer p = Game.getInstance().getLocalPlayer();
        if (Game.getInstance().getGameState() != GameState.ROLLING || !p.equals(Game.getInstance().getCurrentPlayer())) {
            throw new ModelException("Preconditions for action not met.");
        }

        try {
            m_theProxy.rollNumber(p.getIndex(), rolledNumber);
        }
        catch (NetworkException e) {
            throw new ModelException(e);
        }

        return rolledNumber;
    }

    /**
     * Finish up the current player's turn
     */
    @Override
    public void finishTurn() throws ModelException {
        IPlayer p = Game.getInstance().getLocalPlayer();

        if (!Game.getInstance().localPlayerIsPlaying()) {
            throw new ModelException("Preconditions for action not met.");
        }

        try {
            m_theProxy.finishTurn(p.getIndex());
        } catch (NetworkException e) {
            throw new ModelException(e);
        }
    }
}
