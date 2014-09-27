package shared.model;

import shared.definitions.PieceType;

/**
 * A city is a town that is worth two victory points and produces two resource per tile.
 *
 * @author Wyatt
 */
public class City extends Town {
    @Override
    public int getVictoryPoints() {
        return 2;
    }

    @Override
    public IResourceBundle getResources() {
        return null;
    }

    @Override
    public PieceType pieceType() {
        return PieceType.CITY;
    }
}
