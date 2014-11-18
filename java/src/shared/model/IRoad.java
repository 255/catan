package shared.model;

import shared.locations.EdgeLocation;

import java.io.Serializable;

/**
 * This is a placed road. Road objects are not created for roads in a player's piece bank.
 *
 * @author Wyatt
 */
public interface IRoad extends Serializable {
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

    /**
     * Set where the road is placed.
     * This function should only ever be called once. (Roads cannot be moved.)
     * @param edge the edge where the road is placed
     */
    public void setLocation(EdgeLocation edge);
}
