package shared.model;

/**
 * The object
 * @author Wyatt
 */
public interface Game {
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
}
