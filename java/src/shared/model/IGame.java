package shared.model;

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
    /**
     * Return the current game state of an ongoing game.
     * @return the current game state
     */
    public GameState getGameState();

    /**
     * Return a reference to the player whose turn it is.
     * @return the current player
     */
    public IPlayer getCurrentPlayer();

    /**
     * Return a reference to the local player (who is in front of the computer screen).
     * @return the local player
     */
    public IPlayer getLocalPlayer();

    /**
     * Get the list of players in turn order.
     * @return the list of players in turn order
     */
    public List<IPlayer> getPlayers();

    /**
     * Get the available resources.
     * @return the available resources
     */
    public IResourceBundle getResourceBank();

    /**
     * Get a reference to the map.
     * @return the map
     */
    public IMap getMap();

    /**
     * Get the history of gameplay messages.
     * @return the list of gameplay log entries in order from oldest to newest
     */
    public ILog getGameplayLog();

    /**
     * Get the history of chat messages.
     * @return the list of chat entries in order from oldest to newest
     */
    public ILog getChatHistory();
}
