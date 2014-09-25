package shared.model;

import shared.definitions.HexType;
import shared.definitions.ResourceType;
import shared.locations.HexLocation;

/**
 * Class representing a tile on the board.
 * A tile contains a certain type of resource, and
 * will provide that resource to towns that are on a
 * vertex adjacent to that tile. A tile also contains
 * a number indicating the probability that that number
 * will be rolled.
 *
 * @author StevenBarnett
 */
public interface ITile {
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

    /**
     * Place the robber on this hex.
     * The hex must not already have the robber on it.
     */
    public void placeRobber();

    /**
     * Remove the robber from this hex.
     * The hex must already have the robber on it.
     */
    public void removeRobber();
}
