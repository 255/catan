package shared.model;

import shared.definitions.PieceType;
import shared.locations.VertexLocation;

/**
 * Interface that represents a town on the board.
 * A town is placed on a vertex, and provides resources and
 * victory points to a player.
 *
 * @author StevenBarnett
 */
public interface ITown {

    /**
     * Returns the player that owns this town.
     *
     * @return player who owns town
     */
    public IPlayer getOwner();

    /**
     * Sets the owner of this town.
     *
     * @param owner the player object who owns the town
     */
    public void setOwner(IPlayer owner);

    /**
     * Returns the location of this town.
     *
     * @return VertexLocation the location of the town
     */
    public VertexLocation getLocation();

    /**
     * Sets the location of this town.
     * The location must only be set once.
     * @param location the location of the town
     */
    public void setLocation(VertexLocation location);

    /**
     * Returns the number of victory points this town is worth.
     *
     * @return number of victory points
     */
    public int getVictoryPoints();

    /**
     * Returns the resources that this house.
     *
     * @return bundle of resources
     */
    public IResourceBundle getResources();

    /**
     * Returns what type of piece this town is.
     *
     * @return type of piece
     */
    public PieceType pieceType();
}
