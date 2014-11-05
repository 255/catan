package shared.communication;

/**
 * Created by jeffreybacon on 11/4/14.
 */
public class AcceptTradeParams extends AbstractGameParams{
    public final int playerIndex;
    public final boolean willAccept;

    public AcceptTradeParams(int playerIndex, boolean willAccept) {
        this.playerIndex = playerIndex;
        this.willAccept = willAccept;
    }
}
