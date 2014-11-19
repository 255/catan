package shared.model;

import shared.locations.VertexLocation;

/**
 * A settlement is a town that is worth one victory point and produces one resource per tile.
 *
 * @author Wyatt
 */
public class Settlement extends Town {

    /**
     * Constructs a new settlement with no owner and no location.
     */
    public Settlement(IPlayer owner) {
        super(owner, null);
    }

    /**
     * Construct a new settlement.
     *
     * @param owner    the player who owns the settlement
     * @param location the place where the settlement is located
     */
    public Settlement(IPlayer owner, VertexLocation location) {
        super(owner, location);
    }

    @Override
    public int getVictoryPoints() {
        return 1;
    }

    @Override
    public int getResourceCount() {
        return 1;
    }

}
