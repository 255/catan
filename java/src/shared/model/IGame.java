package shared.model;

import shared.definitions.CatanColor;
import shared.definitions.PortType;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

import java.util.Collection;
import java.util.List;
import java.util.Observer;
import java.util.Set;

/**
 * The Game object holds pointers to all of the essential components of the game.
 * The serializer will create all of the objects for the game and then set the Game object to point to them.
 * The model facade will then point to the game.
 *
 * The game concrete class constructor will initialize its fields, so there are no setters.
 * @author Wyatt
 */
public interface IGame {
    /** Get the game's name */
    public String getName();

    /** Get the game's gameID */
    public Integer getID();

    /**
     * Reset the game.
     * Set all of its members to null.
     * This is used before re-initializing the game.
     */
    public void reset();

    /**
     * Get whether the game has been initialized yet.
     * This must be called before initializing from the model.
     *
     * @return true if the game is ready-to-go, false if not
     */
    public boolean isNotInitialized();

    /**
     * Get the dev card hand for the game.
     *
     * @return the game's dev cards
     */
    public IDevCardHand getDevCards();

    /**
     * Set the dev card bank for the game.
     *
     * @param m_devCards the new dev cards
     */
    public void setDevCards(IDevCardHand m_devCards);

    /**
     * Return the current game state of an ongoing game.
     *
     * @return the current game state
     */
    public GameState getGameState();

    /**
     * Set the game state value.
     *
     * @param state the new state
     */
    public void setGameState(GameState state);

    /**
     * Return a reference to the player whose turn it is.
     *
     * @return the current player
     */
    public IPlayer getCurrentPlayer();

    /**
     * Set the current player (whose turn it is)
     *
     * @param currentPlayer the new current player
     */
    public void setCurrentPlayer(IPlayer currentPlayer);

    /**
     * Get the list of players in turn order.
     *
     * @return the list of players in turn order
     */
    public List<IPlayer> getPlayers();

    /**
     * Get a player by turn index.
     * @param index the turn index
     * @return the player
     */
    public IPlayer getPlayer(int index) throws ModelException;

    /**
     * Set the list of players for the game
     *
     * @param players the list of players in TURN ORDER
     */
    public void setPlayers(List<IPlayer> players);

    /**
     * Get the available resources.
     *
     * @return the available resources
     */
    public IResourceBank getResourceBank();

    /**
     * Set the bank object for the game
     *
     * @param bank the bank
     */
    public void setResourceBank(IResourceBank bank);

    /**
     * Get a reference to the map.
     *
     * @return the map
     */
    public ICatanMap getMap();

    /**
     * Point to the new Catan map
     *
     * @param map the new map
     */
    public void setMap(ICatanMap map);

    public IPlayer getLongestRoad();

    public void setLongestRoad(IPlayer longestRoad);

    public IPlayer getLargestArmy();

    public void setLargestArmy(IPlayer largestArmy);

    public ITradeOffer getTradeOffer();

    public void setTradeOffer(ITradeOffer tradeOffer);

    /**
     * Get the history of gameplay messages.
     *
     * @return the list of gameplay log entries in order from oldest to newest
     */
    public ILog getGameplayLog();

    public void setGameplayLog(ILog gameplayLog);

    /**
     * Get the history of chat messages.
     *
     * @return the list of chat entries in order from oldest to newest
     */
    public ILog getChatHistory();

    public void setChatHistory(ILog chatHistory);

    public void setVersion(int version);

    public int getVersion();

    public void setWinner(IPlayer winner);

    public IPlayer getWinner();

    // Game state checking methods

    boolean isOfferingTrade(IPlayer player);

    boolean isBeingOfferedTrade(IPlayer player);

    /**
     * Get whether it is the player's turn and game state is playing, so the player can play cards, etc.
     *
     * @param player
     * @return true / false
     */
    public boolean isPlaying(IPlayer player);

    public boolean isDiscarding(IPlayer player);

    public boolean isRolling(IPlayer player);

    public boolean isRobbing(IPlayer player);

    public boolean isPlacingInitialPieces(IPlayer player);


    public boolean gameHasStarted();

    boolean canPlaceRoad(IPlayer player, EdgeLocation edge);

    boolean canPlaceSettlement(IPlayer player, VertexLocation vertex);

    boolean canBuildCity(IPlayer player, VertexLocation vertex);

    Collection<IPlayer> getRobbablePlayers(IPlayer player, HexLocation location);

    Set<PortType> getPlayerPorts(IPlayer player);

    boolean canBuyCity(IPlayer player);

    boolean canBuyRoad(IPlayer player);

    boolean canBuySettlement(IPlayer player);

    boolean canBuyDevCard(IPlayer player);

    boolean canAcceptTrade(IPlayer player);

    boolean canPlayDevCard(IPlayer player);

    boolean canPlayMonopoly(IPlayer player);

    boolean canPlaySoldier(HexLocation robberDestination, IPlayer player);

    boolean canPlayYearOfPlenty(IPlayer player, ResourceType r1, ResourceType r2);

    boolean canPlayMonument(IPlayer player);

    boolean canPlayRoadBuilding(IPlayer player, EdgeLocation edge1, EdgeLocation edge2);

    boolean hasLongestRoad(IPlayer player);

    boolean hasLargestArmy(IPlayer player);

    boolean isPlayersTurn(IPlayer player);

    // this method is just for determining from the GameState if it is a free round
    boolean isFreeRound();

    public void rollNumber(int number);

    /**
     * The ModelInitializer needs to tell the Game object when it is done updating.
     */
    public void updateComplete();

    public void addObserver(Observer o);

    public boolean joinGame(IUser user, CatanColor playerColor);

    public void robPlayer(IPlayer player, IPlayer victim, HexLocation hexLocation);

    public void finishTurn(IPlayer player);
}
