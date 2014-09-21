package shared.model;

/**
 * Created by sdaltonb on 9/20/14.
 *
 * Interface representing a player in the game
 */
public interface IPlayer
{
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
     * Give the player a road. The road must be owned by the player.
     * @param
     */
    public void addRoad(IRoad road);

    /**
     * Give the player a town (city or settlement). The town must be owned by the player.
     * @param
     */
    public void addTown(ITown town);
}
