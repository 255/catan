package shared.communication;

import shared.model.ResourceBank;

/**
 * Class that represents the DiscardCard params
 *
 * Created by jeffreybacon on 11/4/14.
 */
public class DiscardCardParams extends AbstractGameParams{
    public final int playerIndex;
    public final ResourceBank discardedCards;

    public DiscardCardParams(int playerIndex, ResourceBank discardedCards) {
        this.playerIndex = playerIndex;
        this.discardedCards = discardedCards;
    }
}
