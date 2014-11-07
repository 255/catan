package shared.communication;

import shared.locations.VertexLocation;

/**
 * Class that represents the BuildCity params
 *
 * Created by jeffreybacon on 11/4/14.
 */
public class BuildCityParams extends AbstractGameParams{
    public final int playerIndex;
    public final VertexLocation vertexLocation;

    public BuildCityParams(int playerIndex, VertexLocation vertexLocation) {
        this.playerIndex = playerIndex;
        this.vertexLocation = vertexLocation;
    }
}
