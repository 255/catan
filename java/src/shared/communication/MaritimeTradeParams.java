package shared.communication;

/**
 * Created by jeffreybacon on 11/4/14.
 */
public class MaritimeTradeParams extends AbstractGameParams{
    public final int playerIndex;
    public final int ratio;
    public final String inputResource;
    public final String outputResource;

    public MaritimeTradeParams(int playerIndex, int ratio, String inputResource, String outputResource) {
        this.playerIndex = playerIndex;
        this.ratio = ratio;
        this.inputResource = inputResource;
        this.outputResource = outputResource;
    }
}
