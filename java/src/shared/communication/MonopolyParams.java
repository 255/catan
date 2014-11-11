package shared.communication;

import shared.definitions.ResourceType;

/**
 * Class that represents the Monopoly params
 *
 * Created by jeffreybacon on 11/4/14.
 */
public class MonopolyParams extends AbstractGameParams{
    public final ResourceType resource;
    public final int playerIndex;

    public MonopolyParams(ResourceType resource, int playerIndex) {
        this.resource = resource;
        this.playerIndex = playerIndex;
    }
}
