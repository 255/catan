package shared.communication;

/**
 * Created by jeffreybacon on 11/4/14.
 */
public class MonopolyParams extends AbstractGameParams{
    public final String resource;
    public final int playerIndex;

    public MonopolyParams(String resource, int playerIndex) {
        this.resource = resource;
        this.playerIndex = playerIndex;
    }
}
