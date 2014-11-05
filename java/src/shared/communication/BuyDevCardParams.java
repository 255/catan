package shared.communication;

/**
 * Created by jeffreybacon on 11/4/14.
 */
public class BuyDevCardParams extends AbstractGameParams{
    public final int playerIndex;

    public BuyDevCardParams(int playerIndex) {
        this.playerIndex = playerIndex;
    }
}
