package shared.model;

import shared.definitions.CatanColor;

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

    public int getIndex();

    public int getMonuments();

    public int getSoldiers();

    public boolean hasDiscarded();

    public boolean hasPlayedDevCard();

    public String getName();

    public CatanColor getColor();

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

    IResourceBank getResources();

    public IDevCardHand getNewDevCards();

    public IDevCardHand getPlayableDevCards();
}
