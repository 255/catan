package shared.model;

import shared.definitions.PieceType;

/**
 * A settlement is a town that is worth one victory point and produces one resource per tile.
 *
 * @author Wyatt
 */
public class Settlement extends Town {
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
