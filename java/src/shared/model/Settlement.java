package shared.model;

import shared.definitions.PieceType;
import shared.locations.VertexLocation;

/**
 * A settlement is a town that is worth one victory point and produces one resource per tile.
 *
 * @author Wyatt
 */
public class Settlement extends Town {
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
    public IResourceBundle getResources() {
        return null;
    }

    @Override
    public PieceType pieceType() {
        return PieceType.SETTLEMENT;
    }
}
