package shared.model;

import com.sun.javafx.sg.prism.NGShape;
import shared.definitions.CatanColor;
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
    private CatanColor m_color;
    private IPieceBank m_pieceBank;
    private IResourceBank m_resources;
    private IDevCardHand m_newDevCards;
    private IDevCardHand m_playableDevCards;
    private Collection<IRoad> m_roads;
    private Collection<ITown> m_towns;

    public Player(int id, int index, int victoryPoints, int monuments, int soldiers, boolean discarded,
                  boolean playedDevCard, String name, CatanColor color, IPieceBank pieceBank, IResourceBank resources,
                  IDevCardHand newDevCards, IDevCardHand playableDevCards) throws ModelException {

        assert name != null && pieceBank != null && resources != null && newDevCards != null && playableDevCards != null;

        if (victoryPoints < 0 || monuments < 0 || soldiers < 0) {
            throw new ModelException("Attempted to initialize a player with invalid arguments.");
        }

        this.m_id = id;
        this.m_index = index;
        this.m_victoryPoints = victoryPoints;
        this.m_monuments = monuments;
        this.m_soldiers = soldiers;
        this.m_discarded = discarded;
        this.m_playedDevCard = playedDevCard;
        this.m_name = name;
        this.m_color = color;
        this.m_pieceBank = pieceBank;
        this.m_resources = resources;
        this.m_newDevCards = newDevCards;
        this.m_playableDevCards = playableDevCards;

        this.m_roads = new ArrayList<>();
        this.m_towns = new ArrayList<>();
    }

    /**
     * Creates a player from a string name, integer id, string color, and integer index
     *
     * @param name the display name of the player
     * @param id the player's unique id
     * @param color the color of the player's pieces
     * @param index the position in the list of players in a game
     */
    public Player(String name, int id, CatanColor color, int index) {
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
        m_newDevCards = new DevCardHand();
        m_playableDevCards = new DevCardHand();
        m_roads = new ArrayList<IRoad>();
        m_towns = new ArrayList<ITown>();
    }


    /**
     * Calculates how many victory points this player has
     *
     * @return number of victory points
     */
    @Override
    public int getVictoryPoints() {
        return m_victoryPoints;
    }

    /**
     * Get the pieces (roads, settlements, cities) that the player has remaining in their "bank".
     *
     * @return the piece bank with the counts of the player's pieces
     */
    @Override
    public IPieceBank getPieceBank() {
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
        m_roads.add(road);
    }

    /**
     * Give the player a town (city or settlement).
     * The town must be owned by the player (this will be checked by an assertion).
     *
     * @param town the new town
     */
    @Override
    public void addTown(ITown town) {
        m_towns.add(town);
    }

    //*********//
    // Getters //
    //*********//

    @Override
    public int getId() {
        return m_id;
    }

    @Override
    public int getIndex() {
        return m_index;
    }

    @Override
    public int getMonuments() {
        return m_monuments;
    }

    @Override
    public int getSoldiers() {
        return m_soldiers;
    }

    @Override
    public Collection<IRoad> getRoads() {
        return m_roads;
    }

    @Override
    public Collection<ITown> getTowns() {
        return m_towns;
    }

    @Override
    public boolean hasDiscarded() {
        return m_discarded;
    }

    @Override
    public boolean hasPlayedDevCard() {
        return m_playedDevCard;
    }

    @Override
    public String getName() {
        return m_name;
    }

    @Override
    public CatanColor getColor() {
        return m_color;
    }

    @Override
    public IResourceBank getResources() {
        return m_resources;
    }

    /**
     * Get whether the has any resources. This will be used to test if the player can be robbed from when placing
     * the robber.
     *
     * @return true if the user has resources
     */
    @Override
    public boolean hasResources() {
        return m_resources.getCount() > 0;
    }

    @Override
    public IDevCardHand getNewDevCards() {
        return m_newDevCards;
    }

    @Override
    public IDevCardHand getPlayableDevCards() {
        return m_playableDevCards;
    }

    @Override
    public boolean canAfford(IResourceBank purchase) {
        return m_resources.canAfford(purchase);
    }

    /**
     * Have enough money to buy a city and have place to put it and a piece to use.
     *
     * @return true if can buy city, false if not
     */
    @Override
    public boolean canBuyCity() {
        return m_pieceBank.availableCities() > 0 && m_resources.canAfford(Prices.CITY);
    }

    /**
     * Have enough money to buy a road and a piece to use.
     *
     * @return true if can buy road, false if not
     */
    @Override
    public boolean canBuyRoad() {
        return m_pieceBank.availableRoads() > 0 && m_resources.canAfford(Prices.ROAD);
    }

    /**
     * Have enough money to buy a settlement and a piece to use.
     *
     * @return true if can buy a settlement, false if not
     */
    @Override
    public boolean canBuySettlement() {
        return m_pieceBank.availableSettlements() > 0 && m_resources.canAfford(Prices.SETTLEMENT);
    }

    /**
     * Return true if the player has enough resources for a trade currently being offered to them.
     *
     * @return true if can accept trade, false if not enough resources (or no trade is offered currently)
     */
    @Override
    public boolean canAcceptTrade(IResourceBank asking) {
        return m_resources.canAfford(asking);
    }

    /**
     * Whether the user has dev cards to play in their new hand and has not played yet.
     *
     * @return true if user can play a card
     */
    @Override
    public boolean canPlayDevCard() {
        return m_playableDevCards.getCount() > 0 && !m_playedDevCard;
    }

    /**
     * Get whether the player has at least one of the specified type of dev card
     * and they have not played a card this round.
     *
     * @param card the type
     * @return true if has one or more of card
     */
    @Override
    public boolean canPlayDevCard(DevCardType card) {
        return !m_playedDevCard && (m_playableDevCards.getCount(card) > 0);
    }

    //*********//
    // Setters //
    //*********//

    // integers
    @Override
    public void setMonuments(int num) { m_monuments = num; }
    @Override
    public void setSoldiers(int num) { m_soldiers = num;}
    @Override
    public void setVictoryPoints(int num) { m_victoryPoints = num;}

    // booleans
    @Override
    public void setDiscarded(boolean actionCompleted) { m_discarded = actionCompleted; }
    @Override
    public void setPlayedDevCard(boolean actionCompleted) { m_playedDevCard = actionCompleted; }

    // other
    @Override
    public void setPieceBank(IPieceBank pb) { m_pieceBank = pb; }
    @Override
    public void setResources(IResourceBank rb) { m_resources = rb; }
    @Override
    public void setNewDevCards(IDevCardHand newDevCards) { m_newDevCards = newDevCards; }
    @Override
    public void setPlayableDevCards(IDevCardHand playableCards) { m_playableDevCards = playableCards; }
}
