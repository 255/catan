package shared.model;

import shared.locations.HexLocation;

/**
 * Created by sdaltonb on 9/20/14.
 *
 * Interface that represents the robber
 */
public interface IRobber
{
    /**
     * Changes the location of the robber to a different tile
     *
     * @param hexLoc the new hex location of the robber
     */
    public void placeRobber(HexLocation hexLoc);
}
