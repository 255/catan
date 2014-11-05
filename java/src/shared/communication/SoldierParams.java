package shared.communication;

import shared.locations.HexLocation;

/**
 * Created by jeffreybacon on 11/4/14.
 */
public class SoldierParams extends AbstractGameParams{
    public final int playerIndex;
    public final int victimIndex;
    public final HexLocation location;

    public SoldierParams(int playerIndex, int victimIndex, HexLocation location) {
        this.playerIndex = playerIndex;
        this.victimIndex = victimIndex;
        this.location = location;
    }
}
