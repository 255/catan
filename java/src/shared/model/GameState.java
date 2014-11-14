package shared.model;

/**
 * The possible states during a player's turn.
 * @author Wyatt
 */
public enum GameState {
    FirstRound,
    SecondRound,
    Rolling,
    Robbing,
    Playing,
    Discarding;

    public static GameState fromString(String str) {
        switch (str.toLowerCase()) {
            case "rolling":     return Rolling;
            case "robbing":     return Robbing;
            case "playing":     return Playing;
            case "discarding":  return Discarding;
            case "firstround":  return FirstRound;
            case "secondround": return SecondRound;
            default:
                throw new IllegalArgumentException("Invalid argument: \"" + str + "\" is not a valid game state.");
        }
    }
}
