package shared.communication;

import shared.definitions.ResourceType;

/**
 * Class that represents the MaritimeTrade params
 *
 * Created by jeffreybacon on 11/4/14.
 */
public class MaritimeTradeParams extends AbstractGameParams{
    public final int playerIndex;
    public final int ratio;
    public final ResourceType inputResource;
    public final ResourceType outputResource;

    public MaritimeTradeParams(int playerIndex, int ratio, ResourceType inputResource, ResourceType outputResource) {
        this.playerIndex = playerIndex;
        this.ratio = ratio;
        this.inputResource = inputResource;
        this.outputResource = outputResource;
    }
}
