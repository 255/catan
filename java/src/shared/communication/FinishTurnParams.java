package shared.communication;

/**
 * Created by jeffreybacon on 11/4/14.
 */
public class FinishTurnParams extends AbstractGameParams{
    public final int playerIndex;

    public FinishTurnParams(int playerIndex) {
        this.playerIndex = playerIndex;
    }
}
