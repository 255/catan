package shared.model;

import java.util.Collection;

/**
 * This class represents a Player Object
 */
public class Player implements IPlayer {

    private int m_id;
    private IResourceBank m_resources;
    private IDevCardHand m_newDevCards;
    private IDevCardHand m_playableDevCards;
    private Collection<IRoad> m_roads;
    private Collection<Settlement> m_settlements;
    private Collection<City> m_cities;


    /**
     * Calculates how many victory points this player has
     *
     * @return number of victory points
     */
    @Override
    public int victoryPoints() {
        return 0;
    }

    /**
     * Calculate the longest continuous road owned by this player.
     *
     * @return the (acyclic) length of the longest continuous road owned by this player
     */
    @Override
    public int roadLength() {
        return 0;
    }

    /**
     * Get the resources the player has.
     *
     * @return the resources currently held by the player.
     */
    @Override
    public IResourceBundle resources() {
        return null;
    }

    /**
     * Get the pieces (roads, settlements, cities) that the player has remaining in their "bank".
     *
     * @return the piece bank with the counts of the player's pieces
     */
    @Override
    public IPieceBank pieceBank() {
        return null;
    }

    /**
     * Give the player a road.
     * The road must be owned by the player (this will be checked by an assertion).
     *
     * @param road the new road
     */
    @Override
    public void addRoad(IRoad road) {

    }

    /**
     * Give the player a town (city or settlement).
     * The town must be owned by the player (this will be checked by an assertion).
     *
     * @param town the new town
     */
    @Override
    public void addTown(ITown town) {

    }
}
