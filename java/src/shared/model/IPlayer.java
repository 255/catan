package shared.model;

/**
 * Interface representing one of the four players in an ongoing game
 *
 * @author StevenBarnett
 */
public interface IPlayer {
    /**
     * Calculates how many victory points this player has
     *
     * @return number of victory points
     */
    public int victoryPoints();

    /**
     * Get the resources the player has.
     * @return the resources currently held by the player.
     */
    public IResourceBundle resources();

    /**
     * Get the pieces (roads, settlements, cities) that the player has remaining in their "bank".
     * @return the piece bank with the counts of the player's pieces
     */
    public IPieceBank pieceBank();

    /**
     * Give the player a road.
     * The road must be owned by the player (this will be checked by an assertion).
     * @param road the new road
     */
    public void addRoad(IRoad road);

    /**
     * Give the player a town (city or settlement).
     * The town must be owned by the player (this will be checked by an assertion).
     * @param town the new town
     */
    public void addTown(ITown town);
}
