package shared.communication;

import java.util.List;

/**
 * Class that represents the GamesList response
 *
 * Created by test on 11/5/2014.
 */
public class GamesList {
    public final List<GameInfo> games;

    public GamesList(List<GameInfo> games) {
        this.games = games;
    }
}
