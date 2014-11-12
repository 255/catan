package shared.model;

import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.PortType;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

import java.util.*;

/**
 * Created by jeffreybacon on 9/25/14.
 */
public class Game extends Observable implements IGame {
    private GameState m_state;
    private IPlayer m_currentPlayer;
    private IPlayer m_localPlayer;
    private List<IPlayer> m_players;
    private IResourceBank m_resourceBank;
    private IDevCardHand m_devCards;
    private ICatanMap m_map;
    private IPlayer m_longestRoad;
    private IPlayer m_largestArmy;
    private ITradeOffer m_tradeOffer;
    private ILog m_gameplayLog;
    private ILog m_chatHistory;
    private int m_version;
    private IPlayer m_winner;

    public Game() {
        reset();
    }

    public Game(String gameName, boolean randomPorts, boolean randomTiles, boolean randomNumbers) {
        // TODO:use for server-side game construction from GameManager
    }

    @Override
    public void reset() {
        m_state = null;
        m_currentPlayer = null;
        m_localPlayer = null;
        m_players = null;
        m_resourceBank = null;
        m_devCards = null;
        m_map = null;
        m_longestRoad = null;
        m_largestArmy = null;
        m_tradeOffer = null;
        m_gameplayLog = null;
        m_chatHistory = null;
        m_version = -1;
        m_winner = null;
    }

    /**
     * Get whether the game has been initialized yet.
     * This must be called before initializing from the model.
     * @return true if the game is ready-to-go, false if not
     */    @Override
    public boolean isNotInitialized() {
        // only tradeOffer, winner, longestRoad, and largestArmy can be null
        return (
                m_state == null
             || m_currentPlayer == null
             || m_localPlayer == null
             || m_players == null
             || m_resourceBank == null
             || m_devCards == null
             || m_map == null
             || m_gameplayLog == null
             || m_chatHistory == null
             || m_version == -1
        );
    }

    @Override
    public IDevCardHand getDevCards() {
        return m_devCards;
    }

    @Override
    public void setDevCards(IDevCardHand devCards) {
        assert devCards != null;
        this.m_devCards = devCards;
        setChanged();
    }

    @Override
    public GameState getGameState() {
        return m_state;
    }

    @Override
    public void setGameState(GameState state) {
        assert state != null;
        m_state = state;
        setChanged();
    }

    @Override
    public IPlayer getCurrentPlayer() {
        return m_currentPlayer;
    }

    @Override
    public void setCurrentPlayer(IPlayer currentPlayer) {
        assert currentPlayer != null;
        m_currentPlayer = currentPlayer;
        setChanged();
    }

    @Override
    public IPlayer getLocalPlayer() {
        return m_localPlayer;
    }

    @Override
    public void setLocalPlayer(IPlayer localPlayer) {
        assert localPlayer != null;
        m_localPlayer = localPlayer;
        setChanged();
    }

    @Override
    public List<IPlayer> getPlayers() {
        return m_players;
    }

    @Override
    public void setPlayers(List<IPlayer> players) {
        assert players != null;
        m_players = players;
        setChanged();
    }

    /**
     * Get the list of players in turn order excluding the local player.
     *
     * @return the list of non-local players in turn order
     */
    @Override
    public List<IPlayer> getNonLocalPlayers() {
        List<IPlayer> nonLocalPlayers = new ArrayList<>(m_players.size() - 1);

        for (IPlayer player : m_players) {
            if (!player.equals(m_localPlayer)) {
                nonLocalPlayers.add(player);
            }
        }

        assert nonLocalPlayers.size() == 3 : "There should be 3 non-local players, but there are not!";

        return nonLocalPlayers;
    }

    /**
     * Get the available resources.
     *
     * @return the available resources
     */
    @Override
    public IResourceBank getResourceBank() {
        return m_resourceBank;
    }

    @Override
    public void setResourceBank(IResourceBank bank) {
        assert bank != null;
        m_resourceBank = bank;
        setChanged();
    }

    @Override
    public ICatanMap getMap() {
        return m_map;
    }

    @Override
    public void setMap(ICatanMap map) {
        assert map != null;
        m_map = map;
        setChanged();
    }

    @Override
    public IPlayer getLongestRoad() {
        return m_longestRoad;
    }

    @Override
    public void setLongestRoad(IPlayer longestRoad) {
        m_longestRoad = longestRoad;
        setChanged();
    }

    @Override
    public IPlayer getLargestArmy() {
        return m_largestArmy;
    }

    @Override
    public void setLargestArmy(IPlayer largestArmy) {
        m_largestArmy = largestArmy;
        setChanged();
    }

    @Override
    public ITradeOffer getTradeOffer() {
        return m_tradeOffer;
    }

    @Override
    public void setTradeOffer(ITradeOffer tradeOffer) {
        assert tradeOffer != null;
        m_tradeOffer = tradeOffer;
        setChanged();
    }

    @Override
    public ILog getGameplayLog() {
        return m_gameplayLog;
    }

    @Override
    public void setGameplayLog(ILog gameplayLog) {
        assert gameplayLog != null;
        m_gameplayLog = gameplayLog;
        setChanged();
    }

    @Override
    public ILog getChatHistory() {
        return m_chatHistory;
    }

    @Override
    public void setChatHistory(ILog chatHistory) {
        assert chatHistory != null;
        m_chatHistory = chatHistory;
        setChanged();
    }

    @Override
    public int getVersion(int version) {
        return m_version;
    }

    @Override
    public void setVersion(int version) {
        m_version = version;
        setChanged();
    }

    @Override
    public IPlayer getWinner() {
        return m_winner;
    }

    @Override
    public void setWinner(IPlayer winner) {
        assert winner != null;
        m_winner = winner;
        setChanged();
    }

    /**
     * Return true if it is the local player's turn.
     *
     * @return true if it is the local player's turn
     */
    @Override
    public boolean isLocalPlayersTurn() {
        return m_currentPlayer.equals(m_localPlayer);
    }

    @Override
    public boolean localPlayerIsOfferingTrade() {
        return m_tradeOffer != null && m_localPlayer.equals(m_tradeOffer.getSender());
    }

    @Override
    public boolean localPlayerIsBeingOfferedTrade() {
        return m_tradeOffer != null && m_localPlayer.equals(m_tradeOffer.getReceiver());
    }

    /**
     * Get whether it is the local player's turn and game state is playing, so the player can play cards, etc.
     * @return true / false
     */
    @Override
    public boolean localPlayerIsPlaying() {
        return localPlayerAndGameState(GameState.PLAYING);
    }

    @Override
    public boolean localPlayerIsDiscarding() {
        return (m_state == GameState.DISCARDING) && m_localPlayer.needsToDiscard();
    }

    @Override
    public boolean localPlayerIsRolling() {
        return localPlayerAndGameState(GameState.ROLLING);
    }

    @Override
    public boolean localPlayerIsRobbing() {
        return localPlayerAndGameState(GameState.ROBBING);
    }

    @Override
    public boolean localPlayerIsPlacingInitialPieces() {
        return gameHasStarted()
               && (localPlayerAndGameState(GameState.FIRST_ROUND) || localPlayerAndGameState(GameState.SECOND_ROUND));
    }

    @Override
    public boolean gameHasStarted() {
        return m_players.size() == CatanConstants.NUM_PLAYERS;
    }

    @Override
    public boolean localPlayerAndGameState(GameState state) {
        return m_localPlayer.equals(m_currentPlayer) && m_state == state;
    }

        /**
     * Takes an edge location and determines if a road can be placed on it
     * This does NOT check if the player can afford the road.
     *
     * @param edge the location of the side of a terrain hex
     * @return a boolean value that reports if the user can place a road
     */
    @Override
    public boolean canPlaceRoad(EdgeLocation edge) {
        if (!isLocalPlayersTurn()) {
            return false;
        }

        // road placement rules are different for initial roads
        if (isFreeRound()) {
            return getMap().canPlaceInitialRoad(getLocalPlayer(), edge);
        }

        // if playing, check the map with normal rules
        if (getGameState() == GameState.PLAYING) {
            return getMap().canPlaceRoad(getLocalPlayer(), edge);
        }

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
        assert vertex != null;

        // check if it's the player's turn
        if (!isLocalPlayersTurn()) {
            return false;
        }

        // check if map is open (same logic for initial round and normal playing
        if (!getMap().canPlaceSettlement(getLocalPlayer(), vertex)) {
            return false;
        }

        // check if a normal gameplay
        if (getGameState() == GameState.PLAYING) {
            return getLocalPlayer().canBuySettlement();
        }

        // assert that (if it's a free round) the player has enough pieces
        assert !isFreeRound() || getLocalPlayer().getPieceBank().availableSettlements() > 0;

        // if it's a free round, they can place
        return isFreeRound();
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

        if (getGameState() != GameState.PLAYING) {
            return false;
        }

        // is there a settlement at the vertex?
        boolean mapOpen = getMap().canPlaceCity(getLocalPlayer(), vertex);
        // does the player have enough resources?
        boolean haveResourcesAndPiece = getLocalPlayer().canBuyCity();

        return mapOpen && (isFreeRound() || haveResourcesAndPiece);
    }

    /**
     * Get a list of players around a hex that can be robbed (all with towns except local player)
     *
     * @param location the location being robbed
     * @return a collection of players (may be empty)
     */
    @Override
    public Collection<IPlayer> getRobbablePlayers(HexLocation location) {
        return getMap().getRobbablePlayersOnTile(location, getLocalPlayer());
    }

    /**
     * Returns the info for the current player's resource counts
     *
     * @return the ResourceBank object containing the counts for the current player
     */
    @Override
    public IResourceBank getPlayerResources() {
        assert getLocalPlayer().getResources() != null;

        return getLocalPlayer().getResources();
    }

    /**
     * Returns the ports the local player has
     *
     * @return the ports that the local player has
     */
    @Override
    public Set<PortType> getLocalPlayerPorts() {
        Set<PortType> ports = getMap().getPlayersPorts(getLocalPlayer());

        assert ports != null;

        return ports;
    }

    /**
     * Have enough money to buy a city and have place to put it and a piece to use.
     *
     * @return true if can buy city, false if not
     */
    @Override
    public boolean canBuyCity() {
        return localPlayerIsPlaying() && getLocalPlayer().canBuyCity();
    }

    /**
     * Have enough money to buy a road and a piece to use.
     *
     * @return true if can buy road, false if not
     */
    @Override
    public boolean canBuyRoad() {
        return localPlayerIsPlaying() && getLocalPlayer().canBuyRoad();
    }

    /**
     * Have enough money to buy a settlement and a piece to use.
     *
     * @return true if can buy a settlement, false if not
     */
    @Override
    public boolean canBuySettlement() {
        return localPlayerIsPlaying() && getLocalPlayer().canBuySettlement();
    }

    /**
     * Have enough money to buy a dev card and a card is available to buy
     *
     * @return true if can buy
     */
    @Override
    public boolean canBuyDevCard() {
        return localPlayerIsPlaying() && getLocalPlayer().canAfford(Prices.DEV_CARD)
                && getDevCards().getCount() > 0;
    }

    /**
     * Return true if the player has enough resources for a trade currently being offered to them.
     *
     * @return true if can accept trade, false if not enough resources (or no trade is offered currently)
     */
    @Override
    public boolean canAcceptTrade() {
        // is there a trade offer?
        if (m_tradeOffer == null) {
            return false;
        }

        // these shouldn't matter, but testing anyway
        if (getGameState() != GameState.PLAYING || !getCurrentPlayer().equals(m_tradeOffer.getSender())) {
            return false;
        }

        // check that the trade is for the player and that they can afford it
        return  getLocalPlayer().equals(m_tradeOffer.getReceiver())
                && getLocalPlayer().canAffordTrade(m_tradeOffer.getOffer());
    }

    /**
     * Whether the local player can play any dev cards.
     *
     * @return true if the user can play a dev card
     */
    @Override
    public boolean canPlayDevCard() {
        return localPlayerIsPlaying() && getLocalPlayer().canPlayDevCard();
    }

    /**
     * Get whether the local player can play this specific dev card.
     *
     * @return true if the user can play this card
     */
    @Override
    public boolean canPlayMonopoly(ResourceType resource) {
        return localPlayerIsPlaying()
                && getLocalPlayer().canPlayDevCard(DevCardType.MONOPOLY);
    }

    /**
     * Get whether the local player can play this specific dev card.
     * @param robberDestination new location of the robber
     * @return true if the user can play this card
     */
    @Override
    public boolean canPlaySoldier(HexLocation robberDestination) {
        return localPlayerIsPlaying()
                && getLocalPlayer().canPlayDevCard(DevCardType.SOLDIER) // has and can play card
                && !robberDestination.equals(getMap().getRobber()); // moved robber
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
        return localPlayerIsPlaying()
                && getLocalPlayer().canPlayDevCard(DevCardType.YEAR_OF_PLENTY)
                && getResourceBank().getCount(r1) > 0 && getResourceBank().getCount(r2) > 0;
    }

    /**
     * Get whether the local player can play this specific dev card.
     *
     * @return true if the user can play this card
     */
    @Override
    public boolean canPlayMonument() {
        return localPlayerIsPlaying()
                && getLocalPlayer().canPlayDevCard(DevCardType.MONUMENT);
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

        return localPlayerIsPlaying()
                && getLocalPlayer().canPlayDevCard(DevCardType.ROAD_BUILD)
                && getMap().canPlaceTwoRoads(getLocalPlayer(), edge1, edge2);
    }

    /**
     * Get the local player's color.
     *
     * @return the local player's color
     */
    @Override
    public CatanColor getLocalColor() {
        return getLocalPlayer().getColor();
    }

    @Override
    public boolean playerHasLongestRoad(IPlayer player) {
        return player.equals(getLongestRoad());
    }

    @Override
    public boolean playerHasLargestArmy(IPlayer player) {
        return player.equals(getLargestArmy());
    }

    @Override
    public boolean isPlayersTurn(IPlayer player) {
        return player.equals(getCurrentPlayer());
    }

    // this method is just for determining from the GameState if it is a free round
    @Override
    public boolean isFreeRound() {
        GameState gs = getGameState();
        return (gs == GameState.FIRST_ROUND || gs == GameState.SECOND_ROUND);
    }

    /**
     * The ModelInitializer needs to tell the Game object when it is done updating.
     */
    @Override
    public void updateComplete() {
        assert !isNotInitialized();
        notifyObservers();
    }

    @Override
    public void joinGame(IUser user, CatanColor playerColor) {
        // checks if player is already in game, if so exits method
        for(IPlayer player : m_players) {
            if(player.getId() == user.getId()) {
                return;
            }
        }

        // if player is not already in game they are added to the game
        assert m_players.size() < 4;
        m_players.add(new Player(user.getUsername(), user.getId(), playerColor, m_players.size()));
    }
}
