package shared.communication;

/**
 * Class that wraps the player index param
 *
 * Created by jeffreybacon on 11/6/14.
 */
public class PlayerIndexParam extends AbstractGameParams{
    public final int playerIndex;

    public PlayerIndexParam(int playerIndex) {
        this.playerIndex = playerIndex;
    }
}
