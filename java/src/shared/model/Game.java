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
    // only used server side
    private transient String m_name;
    private transient Integer m_id;

    private GameState m_state;
    private IPlayer m_currentPlayer;
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
        m_name = null;
        m_id = null;
        reset();
    }

    /**
     * Create a new game
     * @param gameName the name of the game
     * @param randomPorts whether to use random ports
     * @param randomTiles whether to randomize tile locations
     * @param randomNumbers whether to randomize number placement
     */
    public Game(String gameName, int id, boolean randomPorts, boolean randomTiles, boolean randomNumbers) throws ModelException {
        m_name = gameName;
        m_id = id;

        // TODO:use for server-side game construction from GameManager

        m_state = GameState.FIRST_ROUND;
        m_currentPlayer = null;
        m_players = new ArrayList<>();
        m_resourceBank = ResourceBank.generateInitial();
        m_devCards = DevCardHand.generateInitial();
        m_map = new MapGenerator(randomPorts, randomTiles, randomNumbers).generateMap();
        m_longestRoad = null;
        m_largestArmy = null;
        m_tradeOffer = null;
        m_gameplayLog = new Log();
        m_chatHistory = new Log();
        m_version = 0;
        m_winner = null;
    }

    @Override
    public void reset() {
        m_state = null;
        m_currentPlayer = null;
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
     * Get the game's name
     */
    @Override
    public String getName() {
        return m_name;
    }

    /**
     * Get the game's gameID
     */
    @Override
    public Integer getID() {
        return m_id;
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

    @Override
    public boolean isOfferingTrade(IPlayer player) {
        return m_tradeOffer != null && player.equals(m_tradeOffer.getSender());
    }

    @Override
    public boolean isBeingOfferedTrade(IPlayer player) {
        return m_tradeOffer != null && player.equals(m_tradeOffer.getReceiver());
    }

    /**
     * Get whether it is the player's turn and game state is playing, so the player can play cards, etc.
     * @return true / false
     * @param player the player
     */
    @Override
    public boolean isPlaying(IPlayer player) {
        return playerAndGameState(player, GameState.PLAYING);
    }

    @Override
    public boolean isDiscarding(IPlayer player) {
        return (m_state == GameState.DISCARDING) && player.needsToDiscard();
    }

    @Override
    public boolean isRolling(IPlayer player) {
        return playerAndGameState(player, GameState.ROLLING);
    }

    @Override
    public boolean isRobbing(IPlayer player) {
        return playerAndGameState(player, GameState.ROBBING);
    }

    @Override
    public boolean isPlacingInitialPieces(IPlayer player) {
        return gameHasStarted()
               && (playerAndGameState(player, GameState.FIRST_ROUND) || playerAndGameState(player, GameState.SECOND_ROUND));
    }

    @Override
    public boolean gameHasStarted() {
        return m_players.size() == CatanConstants.NUM_PLAYERS;
    }

    private boolean playerAndGameState(IPlayer player, GameState state) {
        return player.equals(m_currentPlayer) && m_state == state;
    }

    /**
     * Takes an edge location and determines if a road can be placed on it
     * This does NOT check if the player can afford the road.
     *
     * @param player the player
     * @param edge the location of the side of a terrain hex
     * @return a boolean value that reports if the user can place a road
     */
    @Override
    public boolean canPlaceRoad(IPlayer player, EdgeLocation edge) {
        if (!isPlayersTurn(player)) {
            return false;
        }

        // road placement rules are different for initial roads
        if (isFreeRound()) {
            return getMap().canPlaceInitialRoad(player, edge);
        }

        // if playing, check the map with normal rules
        if (getGameState() == GameState.PLAYING) {
            return getMap().canPlaceRoad(player, edge);
        }

        return false;
    }

    /**
     * Takes a vertex location and determines if a settlement can be placed on it
     *
     *
     * @param player the player the player
     * @param vertex the location of a corner/intersection of terrain hexes
     * @return a boolean value that reports if the user can place a settlement
     */
    @Override
    public boolean canPlaceSettlement(IPlayer player, VertexLocation vertex) {
        assert vertex != null;

        // check if it's the player's turn
        if (!isPlayersTurn(player)) {
            return false;
        }

        // check if map is open (same logic for initial round and normal playing
        if (!getMap().canPlaceSettlement(player, vertex)) {
            return false;
        }

        // check if a normal gameplay
        if (getGameState() == GameState.PLAYING) {
            return player.canBuySettlement();
        }

        // assert that (if it's a free round) the player has enough pieces
        assert !isFreeRound() || player.getPieceBank().availableSettlements() > 0;

        // if it's a free round, they can place
        return isFreeRound();
    }

    /**
     * Takes a vertex location and determines if a city can be placed on it
     *
     *
     * @param player the player
     * @param vertex the location of a corner/intersection of terrain hexes
     * @return a boolean value that reports if the user can place a city
     */
    @Override
    public boolean canBuildCity(IPlayer player, VertexLocation vertex) {
        assert vertex != null;

        if (getGameState() != GameState.PLAYING) {
            return false;
        }

        // is there a settlement at the vertex?
        boolean mapOpen = getMap().canPlaceCity(player, vertex);

        // does the player have enough resources?
        return mapOpen && (isFreeRound() || player.canBuyCity());
    }

    /**
     * Get a list of players around a hex that can be robbed (all with towns except local player)
     *
     * @param player the player
     * @param location the location being robbed
     * @return a collection of players (may be empty)
     */
    @Override
    public Collection<IPlayer> getRobbablePlayers(IPlayer player, HexLocation location) {
        return getMap().getRobbablePlayersOnTile(location, player);
    }

    /**
     * Returns the ports the player has
     *
     * @return the ports that the player has
     * @param player the player
     */
    @Override
    public Set<PortType> getPlayerPorts(IPlayer player) {
        assert player != null;
        Set<PortType> ports = getMap().getPlayersPorts(player);

        assert ports != null;

        return ports;
    }

    /**
     * Have enough money to buy a city and have place to put it and a piece to use.
     *
     * @return true if can buy city, false if not
     * @param player the player
     */
    @Override
    public boolean canBuyCity(IPlayer player) {
        return isPlaying(player) && player.canBuyCity();
    }

    /**
     * Have enough money to buy a road and a piece to use.
     *
     * @return true if can buy road, false if not
     * @param player the player
     */
    @Override
    public boolean canBuyRoad(IPlayer player) {
        return isPlaying(player) && player.canBuyRoad();
    }

    /**
     * Have enough money to buy a settlement and a piece to use.
     *
     * @return true if can buy a settlement, false if not
     * @param player the player
     */
    @Override
    public boolean canBuySettlement(IPlayer player) {
        return isPlaying(player) && player.canBuySettlement();
    }

    /**
     * Have enough money to buy a dev card and a card is available to buy
     *
     * @return true if can buy
     * @param player the player
     */
    @Override
    public boolean canBuyDevCard(IPlayer player) {
        return isPlaying(player) && player.canAfford(Prices.DEV_CARD)
                && getDevCards().getCount() > 0;
    }

    /**
     * Return true if the player has enough resources for a trade currently being offered to them.
     *
     * @return true if can accept trade, false if not enough resources (or no trade is offered currently)
     * @param player the player
     */
    @Override
    public boolean canAcceptTrade(IPlayer player) {
        // is there a trade offer?
        if (m_tradeOffer == null) {
            return false;
        }

        // these shouldn't matter, but testing anyway
        if (getGameState() != GameState.PLAYING || !getCurrentPlayer().equals(m_tradeOffer.getSender())) {
            return false;
        }

        // check that the trade is for the player and that they can afford it
        return  player.equals(m_tradeOffer.getReceiver())
                && player.canAffordTrade(m_tradeOffer.getOffer());
    }

    /**
     * Whether the local player can play any dev cards.
     *
     * @return true if the user can play a dev card
     * @param player the player
     */
    @Override
    public boolean canPlayDevCard(IPlayer player) {
        return isPlaying(player) && player.canPlayDevCard();
    }

    /**
     * Get whether the player can play this specific dev card.
     *
     * @return true if the user can play this card
     */
    @Override
    public boolean canPlayMonopoly(IPlayer player) {
        return isPlaying(player)
                && player.canPlayDevCard(DevCardType.MONOPOLY);
    }

    /**
     * Get whether the local player can play this specific dev card.
     * @param robberDestination new location of the robber
     * @param player the player
     * @return true if the user can play this card
     */
    @Override
    public boolean canPlaySoldier(HexLocation robberDestination, IPlayer player) {
        return isPlaying(player)
                && player.canPlayDevCard(DevCardType.SOLDIER) // has and can play card
                && !robberDestination.equals(getMap().getRobber()); // moved robber
    }

    /**
     * Get whether the player can play this specific dev card.
     * NOTE: The preconditions specified in the document seem wrong to me. You need BOTH resources to play the card?
     *       What if there is only one kind of card left.
     * @return true if the user can play this card
     * @param player the player
     * @param r1 resource
     * @param r2 resource
     */
    @Override
    public boolean canPlayYearOfPlenty(IPlayer player, ResourceType r1, ResourceType r2) {

        return isPlaying(player) && player.canPlayDevCard(DevCardType.YEAR_OF_PLENTY)
                    && (r1.equals(r2) ? getResourceBank().getCount(r1) > 1 : getResourceBank().getCount(r1) > 0 && getResourceBank().getCount(r2) > 0);
    }

    /**
     * Get whether the player can play this specific dev card.
     *
     * @return true if the user can play this card
     * @param player the player
     */
    @Override
    public boolean canPlayMonument(IPlayer player) {
        return isPlaying(player)
                && player.canPlayDevCard(DevCardType.MONUMENT);
    }

    /**
     * Get whether the player can play this specific dev card.
     *
     * @return true if the user can play this card
     * @param player the player
     * @param edge1 first road
     * @param edge2 second road
     */
    @Override
    public boolean canPlayRoadBuilding(IPlayer player, EdgeLocation edge1, EdgeLocation edge2) {
        assert edge1 != null && edge2 != null;

        return isPlaying(player)
                && player.canPlayDevCard(DevCardType.ROAD_BUILD)
                && getMap().canPlaceTwoRoads(player, edge1, edge2);
    }

    @Override
    public boolean hasLongestRoad(IPlayer player) {
        return player.equals(getLongestRoad());
    }

    @Override
    public boolean hasLargestArmy(IPlayer player) {
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
    public void rollNumber(int number) {
        m_map.distributeResources(number);
    }

    @Override
    public boolean joinGame(IUser user, CatanColor playerColor) {
        // checks if player is already in game, if so exits method
        for(IPlayer player : m_players) {
            if(player.getId() == user.getId()) {
                player.setColor(playerColor);
                return true;
            }
        }

        // if player is not already in game they are added to the game
        if (m_players.size() < 4) {
            IPlayer player = Player.createNewPlayer(user.getUsername(), user.getId(), playerColor, m_players.size());
            m_players.add(player);

            // if this is the first player added, make it their turn
            if (m_players.size() == 1) {
                m_currentPlayer = m_players.get(0);
            }

            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void robPlayer(IPlayer player, IPlayer victim, HexLocation hexLocation) {

        // Move the robber to the specified hex location
        m_map.moveRobber(hexLocation);

        // Choose a random card from the victim's hand
        ResourceType resource = m_resourceBank.drawRandom();

        // Remove that random card from the victim's hand
        victim.getResources().subtract(1, resource);

        // Add that random card to the player's hand
        player.getResources().add(1, resource);
    }

    @Override
    public void finishTurn(IPlayer player) {
        // doing a diff of the model json from demo server to see what changed
    }
}
