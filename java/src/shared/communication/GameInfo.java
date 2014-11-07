package shared.communication;

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
}
