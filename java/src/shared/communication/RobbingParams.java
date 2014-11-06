package shared.communication;

import shared.locations.HexLocation;

/**
 * Created by jeffreybacon on 11/4/14.
 */
public class RobbingParams extends AbstractGameParams{
    public final int playerIndex;
    public final int victimIndex;
    public final HexLocation location;

    public RobbingParams(int playerIndex, int victimIndex, HexLocation location) {
        this.playerIndex = playerIndex;
        this.victimIndex = victimIndex;
        this.location = location;
    }
}
