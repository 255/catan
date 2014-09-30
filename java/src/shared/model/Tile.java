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
public class Tile implements ITile {
    private HexType m_type;
    private HexLocation m_location;
    private int m_number;
    private boolean m_robber = false;

    /** The number of the desert hex (there is none -- it is 0) */
    public static final int DESERT_NUMBER = 0;

    public Tile(HexType type, HexLocation location, int number) {
      assert type != null;
      assert location != null;
      assert !(type == HexType.DESERT && number != DESERT_NUMBER)
          : "Cannot create Desert hex with a number other than DESERT_NUMBER";

      m_type = type;
      m_location = location;
      m_number = number;
    }

    /**
     * Returns the type of this tile
     *
     * @return hex type of this tile
     */
    @Override
    public HexType type() {
      return m_type;
    }

    /**
     * Returns the location of this tile
     *
     * @return location of this tile
     */
    @Override
    public HexLocation location() {
      return m_location;
    }

    /**
     * Returns the type of resource this tile gives out
     *
     * @return resource type
     */
    @Override
    public ResourceType resource() {
      return m_type.getResource();
    }

    /**
     * Returns the number token that is placed on this tile
     * Desert hexes return 0.
     *
     * @return number of token
     */
    @Override
    public int numberToken() {
      return m_number;
    }

    /**
     * Determines if this tile has the robber placed on it.
     *
     * @return a flag indicating whether the tile has the robber
     */
    @Override
    public boolean hasRobber() {
      return m_robber;
    }

    /**
     * Place the robber on this hex.
     * The hex must not already have the robber on it.
     */
    @Override
    public void placeRobber() {
      assert !m_robber : "Attempted to place the robber on a tile that already has the robber.";

      m_robber = true;
    }

    /**
     * Remove the robber from this hex.
     * The hex must already have the robber on it.
     */
    @Override
    public void removeRobber() {
      assert m_robber : "Attempted to remove the robber from a tile with no robber.";

      m_robber = false;
    }
}
