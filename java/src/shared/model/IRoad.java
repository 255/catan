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


    /**
     * Set where the road is placed.
     * This function should only ever be called once. (Roads cannot be moved.)
     * @param edge the edge where the road is placed
     */
    public void setLocation(EdgeLocation edge);

    //*********//
    // Setters //
    //*********//

    /**
     * Set the owner of the road to the value of an IPlayer object
     * @param owner the owner of the road
     */
    public void setOwner(IPlayer owner);

    /**
     * Set the hex EdgeLocation to the value of an EdgeLocation
     * @param location the location of the road on the map
     */
    public void setlocation(EdgeLocation location);
}
