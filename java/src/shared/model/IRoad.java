package shared.model;

import shared.locations.EdgeLocation;

/**
 * This is a placed road. Road objects are not created for roads in a player's piece bank.
 *
 * @author Wyatt
 */
public interface IRoad {
    /**
     * Get the owner of this road.
     * @return the player that owns the road
     */
    public IPlayer getOwner();

    /**
     * Get the location at which this road is placed.
     * @return the location at which this road is placed
     */
    public EdgeLocation getLocation();
}
