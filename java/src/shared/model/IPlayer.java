package shared.model;

import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;

import java.io.Serializable;
import java.util.Collection;

/**
 * Interface representing one of the four players in an ongoing game
 *
 * @author StevenBarnett
 */
public interface IPlayer extends Serializable {
    // integers
    public void giveMonument();

    public int giveSoldier();

    public int giveVictoryPoints(int num);

    public int removeVictoryPoints(int num);

    // booleans
    public void setDiscarded(boolean actionCompleted);

    public void setPlayedDevCard(boolean actionCompleted);

    /**
     * Calculates how many victory points this player has
     *
     * @return number of victory points
     */
    public int getVictoryPoints();

    public int calculateVictoryPoints();

    public void addVictoryPoints(int amount);

    public void setResources(IResourceBank rb);

    public void addResources(IResourceBank rb);

    public void addResources(int count, ResourceType type);

    public void removeResources(IResourceBank rb);

    // getters
    public int getId();

    public int getIndex();

    public String getName();

    public CatanColor getColor();

    public int getMonuments();

    public void incrementMonuments();

    public int getSoldiers();

    public void incrementSoldiers();

    public Collection<IRoad> getRoads();

    public Collection<ITown> getTowns();

    /**
     * Return true if needs to discard (assuming a 7 was rolled)
     * Checks if the player has discarded and how many cards they have.
     * @return true if needs to discard
     */
    public boolean needsToDiscard();

    public boolean hasDiscarded();

    public boolean hasPlayedDevCard();

    /**
     * Get the pieces (roads, settlements, cities) that the player has remaining in their "bank".
     * @return the piece bank with the counts of the player's pieces
     */
    public IPieceBank getPieceBank();

    /**
     * Build a new road. The road is constructed and returned.
     * The road is not placed yet.
     *
     * @param free if free, no resources are subtracted; otherwise, the player spends the appropriate amount
     * @return the new road
     */
    public IRoad buildRoad(boolean free);

    /**
     * Build a city.
     * Cities always cost resources, so this amount is subtracted from the player's resource bank.
     * @return the new city
     * @param settlement the settlemnt that the city is replacing
     */
    public City buildCity(ITown settlement);

    /**
     * Build a new settlement.
     * @param free whether to charge the player for the settlement
     * @return the new settlement
     */
    public Settlement buildSettlement(boolean free);

    /**
     * Give the player a town (city or settlement).
     * The town must be owned by the player (this will be checked by an assertion).
     * @param town the new town
     */
    public void addTown(ITown town);

    /**
     * Give the player a road.
     * The road must be owned by the player (this will be checked by an assertion).
     * @param road the new road
     */
    public void addRoad(IRoad road);

    public IResourceBank getResources();

    /**
     * Get whether the has any resources. This will be used to test if the player can be robbed from when placing
     * the robber.
     * @return true if the user has resources
     */
    public boolean hasResources();

    public IDevCardHand getNewDevCards();

    public IDevCardHand getPlayableDevCards();

    /** Move the new dev cards to the old dev cards */
    public void moveDevCards();

    /**
     * Remove a dev card from the player's playable dev cards (play it)
     * Set the player's hasPlayedDevCard flag as appropriate
     * This does NOT perform any logic related to the card itself -- it only removes it from the player's hand
     * and sets their has played flag.
     * @param type the type of the development card to play
     */
    public void playDevCard(DevCardType type);

    /**
     * Get all of the dev cards, whether new or playable.
     * @return a dev card hand with the sum of the playable and new cards
     */
    public IDevCardHand getAllDevCards();
        /**
     * Have enough money to buy a city and have place to put it and a piece to use.
     * @return true if can buy city, false if not
     */
    public boolean canBuyCity();

    /**
     * Have enough money to buy a road and a piece to use.
     * @return true if can buy road, false if not
     */
    public boolean canBuyRoad();

    /**
     * Have enough money to buy a settlement and a piece to use.
     * @return true if can buy a settlement, false if not
     */
    public boolean canBuySettlement();

    /**
     * Return true if the player has enough resources for a trade currently being offered to them.
     * @return true if can accept trade, false if not enough resources (or no trade is offered currently)
     */
    public boolean canAcceptTrade(IResourceBank asking);

    /**
     * Whether the user has dev cards to play in their new hand and has not played yet.
     * @return true if user can play a card
     */
    public boolean canPlayDevCard();

    /**
     * Get whether the player has at least one of the specified type of dev card
     * and they have not played a card this round.
     * @param card the type
     * @return true if has one or more of card
     */
    public boolean canPlayDevCard(DevCardType card);

    /**
     * Checks to see if player can afford a specific purchase
     * @param purchase the resources of the item being purchased
     * @return whether the player can afford the purchase
     */
    public boolean canAfford(IResourceBank purchase);

    /**
     * Checks to see if the player can accept this trade.
     * @param trade the amount with negative values indicating what the trade offer is demanding from the player
     * @return whether the player can afford the offer
     */
    public boolean canAffordTrade(IResourceBank trade);

    /**
     * Set the player's color (when re-joining)
     * @param m_color the new color
     */
    public void setColor(CatanColor m_color);

    public void discardCards(IResourceBank cards);
}
