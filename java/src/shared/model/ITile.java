package shared.model;

import shared.definitions.HexType;
import shared.definitions.ResourceType;
import shared.locations.HexLocation;

/**
 * Created by sdaltonb on 9/20/14.
 *
 *
 */
public interface ITile
{
    /**
     * Returns the type of this tile
     *
     * @return hex type of this tile
     */
    public HexType type();

    /**
     * Returns the location of this tile
     *
     * @return location of this tile
     */
    public HexLocation location();

    /**
     * Returns the type of resource this tile gives out
     *
     * @return resource type
     */
    public ResourceType resource();

    /**
     * Returns the number token that is placed on this tile
     *
     * @return number of token
     */
    public int numberToken();

    /**
     * Determines if this tile has the robber placed on it.
     *
     * @return a flag indicating whether the tile has the robber
     */
    public boolean hasRobber();
}
