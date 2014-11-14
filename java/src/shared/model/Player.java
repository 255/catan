package shared.model;

import shared.definitions.CatanColor;
import shared.definitions.DevCardType;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class represents a Player Object
 */
public class Player implements IPlayer {
    public static final int NO_PLAYER = -1;

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
     * Create a new player with a fresh new piece bank.
     * @return the newly intialized player
     */
    public static Player createNewPlayer(String name, int id, CatanColor color, int index) {
        Player player = new Player(name, id, color, index);
        player.m_pieceBank = PieceBank.generateInitial();

        return player;
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

    @Override
    public void addVictoryPoints(int amount) {
        m_victoryPoints += amount;
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
     * Have the player build a road.
     * The road must be owned by the player (this will be checked by an assertion).
     *
     * @param free if the purchase is free or not
     */
    @Override
    public IRoad buildRoad(boolean free) {
        assert m_pieceBank.availableRoads() > 0;

        if (!free) {
            assert canBuyRoad();
            m_resources.subtract(Prices.ROAD);
        }

        IRoad road = new Road(this);
        m_pieceBank.takeRoad();
        m_roads.add(road);

        return road;
    }

    @Override
    public City buildCity() {
        assert m_pieceBank.availableCities() > 0;
        assert canBuyCity();

        m_resources.subtract(Prices.CITY);

        City city = new City(this);
        m_pieceBank.takeCity();
        m_towns.add(city);

        return city;
    }

    @Override
    public Settlement buildSettlement(boolean free) {
        assert m_pieceBank.availableSettlements() > 0;

        if (!free) {
            assert canBuySettlement();
            m_resources.subtract(Prices.SETTLEMENT);
        }

        Settlement settlement = new Settlement(this);
        m_pieceBank.takeSettlement();
        m_towns.add(settlement);

        return settlement;
    }

    /**
     * Give the player a town (city or settlement).
     * The town must be owned by the player (this will be checked by an assertion).
     *
     * @param town the new town
     */
    @Override
    public void addTown(ITown town) {
        assert this.equals(town.getOwner());
        m_towns.add(town);
    }

    /**
     * Give the player a road.
     * The road must be owned by the player (this will be checked by an assertion).
     *
     * @param road the new road
     */
    @Override
    public void addRoad(IRoad road) {
        assert this.equals(road.getOwner());
        m_roads.add(road);
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
    public void incrementMonuments() {
        ++m_monuments;
    }

    @Override
    public int getSoldiers() {
        return m_soldiers;
    }

    @Override
    public void incrementSoldiers() { ++m_soldiers; }

    @Override
    public Collection<IRoad> getRoads() {
        return Collections.unmodifiableCollection(m_roads);
    }

    @Override
    public Collection<ITown> getTowns() {
        return Collections.unmodifiableCollection(m_towns);
    }

    @Override
    public boolean needsToDiscard() {
        return m_resources.getCount() > CatanConstants.MAX_SAFE_CARDS && !m_discarded;
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

    @Override
    public void addResources(IResourceBank rb) {
        m_resources.add(rb);
    }

    @Override
    public void removeResources(IResourceBank rb) {
        m_resources.subtract(rb);
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

    /**
     * Get all of the dev cards, whether new or playable.
     *
     * @return a dev card hand with the sum of the playable and new cards
     */
    @Override
    public IDevCardHand getAllDevCards() {
        return m_playableDevCards.sum(m_newDevCards);
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
        for (DevCardType card : DevCardType.values()) {
            if (canPlayDevCard(card)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Get whether the player has at least one of the specified type of dev card
     * and they have not played a card this round.
     * Also, allows for playing monuments if victory points is within reach.
     * This does not check whose turn it is!
     *
     * @param card the type
     * @return true if has one or more of card
     */
    @Override
    public boolean canPlayDevCard(DevCardType card) {
        // check that player has the card
        if (m_playableDevCards.getCount(card) <= 0) {
            return false;
        }

        // they must have two available road pieces for the road building card
        if (card == DevCardType.ROAD_BUILD && m_pieceBank.availableRoads() < 2) {
            return false;
        } // IN OFFICIAL RULES, can only play monuments if they will give you 10 victory points, but can play any time in spec
        else if (card == DevCardType.MONUMENT) {
            //return (m_victoryPoints + m_playableDevCards.getCount(DevCardType.MONUMENT)) >= CatanConstants.VICTORY_POINTS_TO_WIN;
            return true;
        }

        // true if have not played a card yet
        return !m_playedDevCard;
    }

    /**
     * Checks to see if the player can accept this trade.
     *
     * @param trade the amount with negative values indicating what the trade offer is demanding from the player
     * @return whether the player can afford the offer
     */
    @Override
    public boolean canAffordTrade(IResourceBank trade) {
        return m_resources.canAfford(trade.negate());
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

    @Override
    public int calculateVictoryPoints() {
        m_victoryPoints = 0;
        for (ITown town : m_towns) {
            m_victoryPoints += town.getVictoryPoints();
        }

        m_victoryPoints += m_monuments;

        return m_victoryPoints;
    }

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

    @Override
    public void setColor(CatanColor m_color) {
        this.m_color = m_color;
    }
}
