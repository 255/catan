package shared.model;

import shared.model.ModelException;

/**
 * A bank of pieces. Each player has a fixed number of pieces available to them.
 * @author Wyatt
 */
public interface IPieceBank {
    /**
     * Take a road from the piece bank. The bank's road count variable is decremented,
     * and a new city object is returned.
     *
     * This will only be used server-side.
     * @return the new road
     * @throws ModelException if no roads are available
     */
    public IRoad takeRoad();

    /**
     * Set the number of roads that will be available in the piece bank.
     * @param availableRoads the number of available roads
     */
    public void setAvailableRoads(int availableRoads);

    /**
     * Take a city from the piece bank. The bank's city count variable is decremented,
     * the settlement count is incremented (since cities replace settlements), and a
     * new city object is returned.
     *
     * This will only be used server-side.
     * @return the new city
     * @throws ModelException if no cities are available
     */
    public ICity takeCity();

    /**
     * Set the number of cities that will be available in the piece bank.
     * @param availableCities the number of available cities
     */
    public void setAvailableCities(int availableCities);

    /**
     * Take a settlement from the piece bank. The bank's settlement count variable is
     * decremented and a new settlement object is returned.
     *
     * This will only be used server-side.
     * @return the new settlement
     * @throws ModelException if no settlements are available
     */
    public ISettlement takeSettlement();

    /**
     * Set the number of settlements that will be available in the piece bank.
     * @param availableSettlements the number of available settlements
     */
    public void setAvailableSettlements(int availableSettlements);

}