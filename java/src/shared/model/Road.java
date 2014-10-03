package shared.model;

import shared.locations.EdgeLocation;

/**
 * This class represents a Road object that is placed on the Map
 */
public class Road implements IRoad {

    private IPlayer m_owner;
    private EdgeLocation m_location;

    /**
     * Creates a road that has no owner and no location
     */
    public Road(IPlayer owner) {
        assert owner != null;

        m_owner = owner;
        setLocation(null);
    }

    /**
     * Creates a road with an owner and a location
     *
     * @param owner the IPlayer that owns the road
     * @param location the EdgeLocation of where the road is placed on the map
     */
    public Road(IPlayer owner, EdgeLocation location) {
        assert owner != null && location != null;

        m_owner = owner;
        setLocation(location);
    }

    /**
     * Get the owner of this road.
     * @return the player that owns the road
     */
    public IPlayer getOwner() {
        return m_owner;
    }

    /**
     * Get the location at which this road is placed.
     * @return the location at which this road is placed
     */
    @Override
    public EdgeLocation getLocation() {
        return m_location;
    }

    /**
     * Set where the road is placed.
     * This function should only ever be called once. (Roads cannot be moved.)
     * @param edge the edge where the road is placed
     */
    @Override
    public void setLocation(EdgeLocation edge) {
        m_location = edge.getNormalizedLocation();
    }
}
