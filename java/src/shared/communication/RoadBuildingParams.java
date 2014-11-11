package shared.communication;

import shared.locations.EdgeLocation;

/**
 * Class that represents the RoadBuilding params
 *
 * Created by jeffreybacon on 11/4/14.
 */
public class RoadBuildingParams extends AbstractGameParams{
    public final int playerIndex;
    public final EdgeLocation spot1;
    public final EdgeLocation spot2;

    public RoadBuildingParams(int playerIndex, EdgeLocation spot1, EdgeLocation spot2) {
        this.playerIndex = playerIndex;
        this.spot1 = spot1;
        this.spot2 = spot2;
    }
}
