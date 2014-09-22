package shared.model;

import shared.definitions.PieceType;

/**
 * Interface that represents a town on the board.
 * A town is placed on a vertex, and provides resources and
 * victory points to a player.
 *
 * @author StevenBarnett
 */
public interface ITown {

    /**
     * Returns the number of victory points this town is worth
     *
     * @return number of victory points
     */
    public int victoryPoints();

    /**
     * Returns the player that owns this town
     *
     * @return player who owns town
     */
    public IPlayer owner();

    /**
     * Returns the resources that this house
     *
     * @return bundle of resources
     */
    public IResourceBundle getResources();

    /**
     * Returns what type of piece this town is
     *
     * @return type of piece
     */
    public PieceType pieceType();
}
