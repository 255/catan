package shared.communication;

import shared.locations.EdgeLocation;

/**
 * Created by jeffreybacon on 11/4/14.
 */
public class BuildRoadParams extends AbstractGameParams{
    public final int playerIndex;
    public final EdgeLocation roadLocation;
    public final boolean free;

    public BuildRoadParams(int playerIndex, EdgeLocation roadLocation, boolean free) {
        this.playerIndex = playerIndex;
        this.roadLocation = roadLocation;
        this.free = free;
    }
}
