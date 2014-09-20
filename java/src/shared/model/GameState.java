package shared.model;

/**
 * The possible states during game play. The game's current state and the
 * current player
 * @author Wyatt
 */
public enum GameState {
    FIRST_ROUND,
    SECOND_ROUND,
    ROLLING,
    ROBBING,
    PLAYING,
    DISCARDING,
}
