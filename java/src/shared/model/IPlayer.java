package shared.model;

/**
 * Created by sdaltonb on 9/20/14.
 *
 * Interface representing one of the four players in an ongoing game
 */
public interface IPlayer {
    /**
     * Calculates how many victory points this player has
     *
     * @return number of victory points
     */
    public int victoryPoints();

    /**
     * Calculate the longest continuous road owned by this player.
     * @return the (acyclic) length of the longest continuous road owned by this player
     */
    public int roadLength();

    /**
     * Get the resources the player has.
     * @return the resources currently held by the player.
     */
    public IResourceBundle getResources();

    /**
     * Get the pieces (roads, settlements, cities) that the player has remaining in their "bank".
     * @return the piece bank with the counts of the player's pieces
     */
    public IPieceBank getPieceBank();

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
