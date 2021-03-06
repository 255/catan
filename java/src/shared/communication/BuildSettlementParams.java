package shared.communication;

import shared.locations.VertexLocation;

/**
 * Class that represents the BuildSettlement params
 *
 * Created by jeffreybacon on 11/4/14.
 */
public class BuildSettlementParams extends AbstractGameParams{
    public final int playerIndex;
    public final VertexLocation vertexLocation;
    public final boolean free;

    public BuildSettlementParams(int playerIndex, VertexLocation vertexLocation, boolean free) {
        this.playerIndex = playerIndex;
        this.vertexLocation = vertexLocation;
        this.free = free;
    }
}
