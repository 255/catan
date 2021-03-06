package shared.communication;

import shared.definitions.CatanColor;
import shared.model.IPlayer;

/**
 * Class that represents the player info
 *
 * Created by jeffreybacon on 11/6/14.
 */
public class PlayerInfo {
    public final int id;
    public final String name;
    public final CatanColor color;

    public PlayerInfo(int id, String name, CatanColor color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public PlayerInfo(IPlayer player) {
        this.id = player.getId();
        this.name = player.getName();
        this.color = player.getColor();
    }
}
