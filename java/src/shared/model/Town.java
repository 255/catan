package shared.model;

import shared.locations.VertexLocation;

/**
 *
 */
public abstract class Town implements ITown {
    private IPlayer m_owner;
    private VertexLocation m_location;

    /**
     * Construct a new settlement.
     * @param owner the player who owns the town
     * @param location the place where the town is located
     */
    protected Town(IPlayer owner, VertexLocation location) {
        assert owner != null && location != null;

        setOwner(owner);
        setLocation(location);
    }

    /**
     * Returns the player that owns this town.
     *
     * @return player who owns town
     */
    @Override
    public IPlayer getOwner() {
        return m_owner;
    }

    /**
     * Sets the owner of this town.
     *
     * @param owner the player object who owns the town
     */
    public void setOwner(IPlayer owner) {
        m_owner = owner;
    }

    /**
     * Returns the location of this town.
     *
     * @return VertexLocation the location of the town
     */
    @Override
    public VertexLocation getLocation() {
        return m_location;
    }

    /**
     * Sets the location of this town.
     * The location must only be set once.
     *
     * @param location the location of the town
     */
    @Override
    public void setLocation(VertexLocation location) {
        m_location = location;
    }
}
