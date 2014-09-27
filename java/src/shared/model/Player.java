package shared.model;

import shared.definitions.DevCardType;

import java.util.Collection;
import java.util.ArrayList;

/**
 * This class represents a Player Object
 */
public class Player implements IPlayer {

    private int m_id;
    private int m_index;
    private String m_color;
    private String m_name;
    private int m_victoryPoints;
    private boolean m_discarded;
    private int m_monuments;
    private int m_soldiers;
    private boolean m_playedDevCard;
    private IResourceBank m_resources;
    private IDevCardHand m_newDevCards;
    private IDevCardHand m_playableDevCards;
    private Collection<Road> m_roads;
    private Collection<Settlement> m_settlements;
    private Collection<City> m_cities;


    Player(String name, int id, String color, int index) {
        m_id = id;
        m_index = index;
        m_color = color;
        m_name = name;

        m_victoryPoints = 0;
        m_monuments = 0;
        m_soldiers = 0;
        m_discarded = false;
        m_playedDevCard = false;
        m_resources = new ResourceBank();
        m_newDevCards = new DevCardHand(new ArrayList<DevCardType>());
        m_playableDevCards = new DevCardHand(new ArrayList<DevCardType>());
        m_roads = new ArrayList<Road>();
        m_settlements = new ArrayList<Settlement>();
        m_cities = new ArrayList<City>();
    }


    /**
     * Calculates how many victory points this player has
     *
     * @return number of victory points
     */
    @Override
    public int victoryPoints() {

        // these are going to be retrieved from the server?

        return m_victoryPoints;
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
