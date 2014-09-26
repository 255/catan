package shared.model;

import shared.definitions.PieceType;

/**
 *
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
