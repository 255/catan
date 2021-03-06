package shared.communication;

import shared.model.IGame;
import shared.model.IPlayer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Class that represents game info
 *
 * Created by jeffreybacon on 11/6/14.
 */
public class GameInfo {
    public final int id;
    public final String title;
    public final List<PlayerInfo> players;

    public GameInfo(int id, String title, List<PlayerInfo> players) {
        this.id = id;
        this.title = title;
        this.players = players;
    }

    public GameInfo(IGame game) {
        id = game.getID();
        title = game.getName();

        players = new ArrayList<>();
        for (IPlayer player : game.getPlayers()) {
            players.add(new PlayerInfo(player));
        }
    }

    /** Convert a collection of Game objects to an array of GameInfo objects */
    public static GameInfo[] toGameInfoArray(Collection<IGame> games) {
        GameInfo[] gameInfos = new GameInfo[games.size()];

        int index = 0;
        for (IGame game : games) {
            gameInfos[index++] = new GameInfo(game);
        }

        return gameInfos;
    }
}
