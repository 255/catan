package shared.model;

/**
 * The possible states during a player's turn.
 * @author Wyatt
 */
public enum GameState {
    FIRST_ROUND,
    SECOND_ROUND,
    ROLLING,
    ROBBING,
    PLAYING,
    DISCARDING;

    public static GameState fromString(String str) {
        switch (str.toLowerCase()) {
            case "rolling":     return ROLLING;
            case "robbing":     return ROBBING;
            case "playing":     return PLAYING;
            case "discarding":  return DISCARDING;
            case "firstround":  return FIRST_ROUND;
            case "secondround": return SECOND_ROUND;
            default:
                throw new IllegalArgumentException("Invalid argument: \"" + str + "\" is not a valid game state.");
        }
    }
}
