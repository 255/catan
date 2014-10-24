package shared.model;

import shared.definitions.CatanColor;
import shared.definitions.DevCardType;

import java.util.Collection;

/**
 * Interface representing one of the four players in an ongoing game
 *
 * @author StevenBarnett
 */
public interface IPlayer {
    // integers
    public void setMonuments(int num);

    public void setSoldiers(int num);

    public void setVictoryPoints(int num);

    // booleans
    public void setDiscarded(boolean actionCompleted);

    public void setPlayedDevCard(boolean actionCompleted);

    /**
     * Calculates how many victory points this player has
     *
     * @return number of victory points
     */
    public int getVictoryPoints();

    // other
    public void setPieceBank(IPieceBank pb);

    public void setResources(IResourceBank rb);

    public void setNewDevCards(IDevCardHand newDevCards);

    public void setPlayableDevCards(IDevCardHand playableCards);

    // getters
    public int getId();

    public int getIndex();

    public String getName();

    public CatanColor getColor();

    public int getMonuments();

    public int getSoldiers();

    public Collection<IRoad> getRoads();

    public Collection<ITown> getTowns();

    /**
     * Return true if needs to discard (assuming a 7 was rolled)
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

    public IResourceBank getResources();

    /**
     * Get whether the has any resources. This will be used to test if the player can be robbed from when placing
     * the robber.
     * @return true if the user has resources
     */
    public boolean hasResources();

    public IDevCardHand getNewDevCards();

    public IDevCardHand getPlayableDevCards();
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
     * @param purchase
     * @return whether the player can afford the purchase
     */
    public boolean canAfford(IResourceBank purchase);

    /**
     * Checks to see if the player can accept this trade.
     * @param trade the amount with negative values indicating what the trade offer is demanding from the player
     * @return whether the player can afford the offer
     */
    public boolean canAffordTrade(IResourceBank trade);
}
