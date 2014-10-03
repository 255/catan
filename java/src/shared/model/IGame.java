package shared.model;

import javax.imageio.event.IIOReadProgressListener;
import java.util.List;

/**
 * The Game object holds pointers to all of the essential components of the game.
 * The serializer will create all of the objects for the game and then set the Game object to point to them.
 * The model facade will then point to the game.
 *
 * The game concrete class constructor will initialize its fields, so there are no setters.
 * @author Wyatt
 */
public interface IGame {
    IDevCardHand getDevCards();

    void setDevCards(IDevCardHand m_devCards);

    /**
     * Return the current game state of an ongoing game.
     * @return the current game state
     */
    public GameState getGameState();

    /**
     * Set the game state value.
     * @param state the new state
     */
    public void setGameState(GameState state);

    /**
     * Return a reference to the player whose turn it is.
     * @return the current player
     */
    public IPlayer getCurrentPlayer();

    /**
     * Set the current player (whose turn it is)
     * @param currentPlayer the new current player
     */
    public void setCurrentPlayer(IPlayer currentPlayer);

    /**
     * Return a reference to the local player (who is in front of the computer screen).
     * @return the local player
     */
    public IPlayer getLocalPlayer();

    /**
     * Set which player is the local player // TODO
     * @param localPlayer
     */
    public void setLocalPlayer(IPlayer localPlayer);

    /**
     * Get the list of players in turn order.
     * @return the list of players in turn order
     */
    public List<IPlayer> getPlayers();

    /**
     * Set the list of players for the game
     * @param players the list of players in TURN ORDER
     */
    public void setPlayers(List<IPlayer> players);

    /**
     * Get the available resources.
     * @return the available resources
     */
    public IResourceBank getResourceBank();

    /**
     * Set the bank object for the game
     * @param bank the bank
     */
    public void setResourceBank(IResourceBank bank);

    /**
     * Get a reference to the map.
     * @return the map
     */
    public ICatanMap getMap();

    /**
     * Point to the new Catan map
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
     * @return the list of gameplay log entries in order from oldest to newest
     */
    public ILog getGameplayLog();

    public void setGameplayLog(ILog gameplayLog);

    /**
     * Get the history of chat messages.
     * @return the list of chat entries in order from oldest to newest
     */
    public ILog getChatHistory();

    public void setChatHistory(ILog chatHistory);

    public void setVersion(int version);

    public int getVersion(int version);

    public void setWinner(IPlayer winner);

    public IPlayer getWinner();

    /**
     * Get whether it is the local player's turn and game state is playing, so the player can play cards, etc.
     * @return true / false
     */
    public boolean localPlayerCanPlay();
}
