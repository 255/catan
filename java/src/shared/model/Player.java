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
    private int m_victoryPoints;
    private int m_monuments;
    private int m_soldiers;
    private boolean m_discarded;
    private boolean m_playedDevCard;
    private String m_name;
    private String m_color;
    private IPieceBank m_pieceBank;
    private IResourceBank m_resources;
    private IDevCardHand m_newDevCards;
    private IDevCardHand m_playableDevCards;
    private Collection<Road> m_roads;
    private Collection<Settlement> m_settlements;
    private Collection<City> m_cities;

    /**
     * Creates a player from a string name, integer id, string color, and integer index
     *
     * @param name the display name of the player
     * @param id the player's unique id
     * @param color the color of the player's pieces
     * @param index the position in the list of players in a game
     */
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
        m_pieceBank = new PieceBank();
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
        return m_victoryPoints;
    }

    /**
     * Get the resources the player has.
     *
     * @return the resources currently held by the player.
     */
    @Override
    public IResourceBundle resources() {
        return new ResourceBundle(
                m_resources.getWood(),
                m_resources.getBrick(),
                m_resources.getSheep(),
                m_resources.getWheat(),
                m_resources.getOre()
        );
    }

    /**
     * Get the pieces (roads, settlements, cities) that the player has remaining in their "bank".
     *
     * @return the piece bank with the counts of the player's pieces
     */
    @Override
    public IPieceBank pieceBank() {
        return m_pieceBank;
    }

    /**
     * Give the player a road.
     * The road must be owned by the player (this will be checked by an assertion).
     *
     * @param road the new road
     */
    @Override
    public void addRoad(IRoad road) {
        m_roads.add((Road)road);
    }

    /**
     * Give the player a town (city or settlement).
     * The town must be owned by the player (this will be checked by an assertion).
     *
     * @param town the new town
     */
    @Override
    public void addTown(ITown town) {
        if(town instanceof Settlement) {
            m_settlements.add((Settlement) town);
        }
        else if (town instanceof  City) {
            m_cities.add((City) town);
        }
        else {
            //TODO not sure how we handle errors yet
        }
    }

    //*********//
    // Setters //
    //*********//

    // integers
    public void setId(int num) { m_id = num; }
    public void setIndex(int num) { m_index = num; }
    public void setMonuments(int num) { m_monuments = num; }
    public void setSoldiers(int num) { m_soldiers = num;}
    public void setVictoryPoints(int num) { m_victoryPoints = num;}

    // booleans
    public void setDiscarded(boolean actionCompleted) { m_discarded = actionCompleted; }
    public void setPlayedDevCard(boolean actionCompleted) { m_playedDevCard = actionCompleted; }

    // Strings
    public void setName(String name) { m_name = name; }
    public void setColor(String color) { m_color = color; }

    // Lists
    public void setRoads(Road r) { m_roads.add(r); }
    public void setSettlements(Settlement s) { m_settlements.add(s); }
    public void setCities(City c) { m_cities.add(c); }

    // other
    public void setPieceBank(IPieceBank pb) { m_pieceBank = pb; }
    public void setResources(IResourceBank rb) { m_resources = rb; }
    public void setNewDevCards(IDevCardHand newDevCards) { m_newDevCards = newDevCards; }
    public void setPlayableDevCards(IDevCardHand playableCards) { m_playableDevCards = playableCards; }

}
