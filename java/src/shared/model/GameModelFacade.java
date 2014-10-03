package shared.model;

import shared.definitions.DevCardType;
import shared.definitions.PortType;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

import java.util.Set;

/**
 * handles all the manipulations of the game object
 */
public class GameModelFacade implements IGameModelFacade {
    private IGame m_theGame;
    private static GameModelFacade m_theFacade = null;

    /**
     * This is a private constructor that is called only when the GameModelFacade has not been initialized yet
     */
    private GameModelFacade(IGame theGame) {
        setGame(theGame);
    }

    /**C:\Program Files\Java\jdk1.8.0_20
     * This static function is called to get the current instance of the GameModelFacade
     *
     * @return the current instance of the GameModelFacade
     */
    public static GameModelFacade getInstance() {
        if (m_theFacade == null) {
            m_theFacade = new GameModelFacade(new Game());
        }
        return m_theFacade;
    }

    /**
     * This function sets the Game object that the GameModelFacade will point at
     *
     * @param game the Game object to point the GameModelFacade at
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
     * Takes an edge location and determines if a road can be placed on it
     *
     * @param edge the location of the side of a terrain hex
     * @return a boolean value that reports if the user can place a road
     */
    @Override
    public boolean canPlaceRoad(EdgeLocation edge) {
        if (!m_theGame.localPlayerCanPlay()) {
            return false;
        }

        // is the spot open on the map?
        boolean mapOpen = m_theGame.getMap().canPlaceRoad(m_theGame.getLocalPlayer(), edge);
        // does the player have enough resources to build the road?
        boolean haveResourcesAndPiece = m_theGame.getLocalPlayer().canBuySettlement();

        return mapOpen && haveResourcesAndPiece;
    }

    /** During first or second rounds, the rules for road placement are different.
     * This function must used in the first rounds instead of canPlaceRoad
     * and must not be used anywhere else. (This is verified by assertions.)
     * @param edge the edge where to place the road
     * @return true if first or second round and road placement is valid for local player
     */
    @Override
    public boolean canPlaceInitialRoad(EdgeLocation edge) {
        assert isFreeRound();
        assert edge != null;

        if (!isFreeRound() || m_theGame.getLocalPlayer().getPieceBank().availableCities() == 0) {
            return false;
        }

        return m_theGame.getMap().canPlaceInitialRoad(m_theGame.getLocalPlayer(), edge);
    }

    /**
     * Takes a vertex location and determines if a settlement can be placed on it
     *
     * @param vertex the location of a corner/intersection of terrain hexes
     * @return a boolean value that reports if the user can place a settlement
     */
    @Override
    public boolean canPlaceSettlement(VertexLocation vertex) {
        assert vertex != null;

        if (m_theGame.getGameState() != GameState.PLAYING) {
            return false;
        }

        // is the map open in at the vertex?
        boolean mapOpen = m_theGame.getMap().canPlaceSettlement(m_theGame.getLocalPlayer(), vertex);
        // does the player have enough resources?
        boolean haveResources = m_theGame.getLocalPlayer().canAfford(Prices.SETTLEMENT);
        // does the player have enough pieces
        boolean hasPiece = m_theGame.getLocalPlayer().getPieceBank().availableSettlements() > 0;


        return mapOpen && (isFreeRound() || haveResources) && hasPiece;
    }

    /**
     * Takes a vertex location and determines if a city can be placed on it
     *
     * @param vertex the location of a corner/intersection of terrain hexes
     * @return a boolean value that reports if the user can place a city
     */
    @Override
    public boolean canPlaceCity(VertexLocation vertex) {
        assert vertex != null;

        if (m_theGame.getGameState() != GameState.PLAYING) {
            return false;
        }

        // is there a settlement at the vertex?
        boolean mapOpen = m_theGame.getMap().canPlaceCity(m_theGame.getLocalPlayer(), vertex);
        // does the player have enough resources?
        boolean haveResourcesAndPiece = m_theGame.getLocalPlayer().canBuyCity();

        return mapOpen && (isFreeRound() || haveResourcesAndPiece);
    }

    /**
     * Returns the info for the current player
     *
     * @return the PlayerInfo object initialized with all the player card/piece counts
     */
    @Override
    public IPlayer getCurrentPlayer() {
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
    public Set<PortType> getPlayerPorts() {
        Set<PortType> ports = m_theGame.getMap().getPlayersPorts(m_theGame.getLocalPlayer());

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
     * Have enough money to buy a city and have place to put it and a piece to use.
     *
     * @return true if can buy city, false if not
     */
    @Override
    public boolean canBuyCity() {
        return m_theGame.localPlayerCanPlay() && m_theGame.getLocalPlayer().canBuyCity();
    }

    /**
     * Have enough money to buy a road and a piece to use.
     *
     * @return true if can buy road, false if not
     */
    @Override
    public boolean canBuyRoad() {
        return m_theGame.localPlayerCanPlay() && m_theGame.getLocalPlayer().canBuyRoad();
    }

    /**
     * Have enough money to buy a settlement and a piece to use.
     *
     * @return true if can buy a settlement, false if not
     */
    @Override
    public boolean canBuySettlement() {
        return m_theGame.localPlayerCanPlay() && m_theGame.getLocalPlayer().canBuySettlement();
    }

    /**
     * Have enough money to buy a dev card and a card is available to buy
     *
     * @return true if can buy
     */
    @Override
    public boolean canBuyDevCard() {
        return m_theGame.localPlayerCanPlay() && m_theGame.getLocalPlayer().canAfford(Prices.DEV_CARD)
                && m_theGame.getDevCards().getCount() > 0;
    }

    /**
     * Return true if the player has enough resources for a trade currently being offered to them.
     *
     * @return true if can accept trade, false if not enough resources (or no trade is offered currently)
     */
    @Override
    public boolean canAcceptTrade() {
        ITradeOffer tradeOffer = m_theGame.getTradeOffer();
        return tradeOffer != null && m_theGame.getLocalPlayer().equals(tradeOffer.getReceiver());
    }

    /**
     * Whether the local player can play any dev cards.
     *
     * @return true if the user can play a dev card
     */
    @Override
    public boolean canPlayDevCard() {
        return m_theGame.localPlayerCanPlay() && m_theGame.getLocalPlayer().canPlayDevCard();
    }

    /**
     * Get whether the local player can play this specific dev card.
     *
     * @return true if the user can play this card
     */
    @Override
    public boolean canPlayMonopoly(ResourceType resource) {
        return m_theGame.localPlayerCanPlay() && m_theGame.getLocalPlayer().canPlayDevCard(DevCardType.MONOPOLY);
    }

    /**
     * Get whether the local player can play this specific dev card.
     * @param robberDestination new location of the robber
     * @return true if the user can play this card
     */
    @Override
    public boolean canPlaySoldier(HexLocation robberDestination) {
        return m_theGame.localPlayerCanPlay() && m_theGame.getLocalPlayer().canPlayDevCard(DevCardType.SOLDIER) // has and can play card
                && !robberDestination.equals(m_theGame.getMap().getRobber()); // moved robber
    }

    /**
     * Get whether the local player can play this specific dev card.
     * NOTE: The preconditions specified in the document seem wrong to me. You need BOTH resources to play the card?
     *       What if there is only one kind of card left.
     * @return true if the user can play this card
     * @param r1 resource
     * @param r2 resource
     */
    @Override
    public boolean canPlayYearOfPlenty(ResourceType r1, ResourceType r2) {
        IResourceBank bank = m_theGame.getResourceBank();
        return m_theGame.localPlayerCanPlay() && bank.getCount(r1) > 0 && bank.getCount(r2) > 0;
    }

    /**
     * Get whether the local player can play this specific dev card.
     *
     * @return true if the user can play this card
     */
    @Override
    public boolean canPlayMonument() {
        return m_theGame.localPlayerCanPlay() && m_theGame.getLocalPlayer().canPlayDevCard(DevCardType.MONUMENT);
    }

    /**
     * Get whether the local player can play this specific dev card.
     *
     * @return true if the user can play this card
     * @param edge1 first road
     * @param edge2 second road
     */
    @Override
    public boolean canPlayRoadBuilding(EdgeLocation edge1, EdgeLocation edge2) {
        assert edge1 != null && edge2 != null;

        ICatanMap map = m_theGame.getMap();
        IPlayer player = m_theGame.getLocalPlayer();

        return m_theGame.localPlayerCanPlay() && map.canPlaceTwoRoads(player, edge1, edge2);
    }

    /**
     * Get whether the local player can play this specific dev card.
     *
     * @param destination
     * @return true if the user can play this card
     */
    @Override
    public boolean canRobPlayer(HexLocation destination) {
        return false;
    }

    // this method is just for determining from the GameState if it is a free round
    @Override
    public boolean isFreeRound() {
        GameState gs = m_theGame.getGameState();
        return (gs == GameState.FIRST_ROUND || gs == GameState.SECOND_ROUND);
    }
}