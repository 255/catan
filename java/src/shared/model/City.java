package shared.model;

import shared.locations.VertexLocation;

/**
 * A city is a town that is worth two victory points and produces two resource per tile.
 *
 * @author Wyatt
 */
public class City extends Town {

    /**
     * Construct a new city with no owner and no location
     */
    public City(IPlayer owner) {
        super(owner, null);
    }

    /**
     * Construct a new city.
     *
     * @param owner    the player who owns the city
     * @param location the place where the city is located
     */
    public City(IPlayer owner, VertexLocation location) {
        super(owner, location);
    }

    @Override
    public int getVictoryPoints() {
        return 2;
    }

    @Override
    public int getResourceCount() {
        return 2;
    }
}
