package shared.model;

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
     */
    public void takeRoad();

    /**
     * Set the number of roads that will be available in the piece bank.
     * @param availableRoads the number of available roads
     */
    public void setAvailableRoads(int availableRoads);

    /**
     * Get the number of roads in the bank.
     *
     * @return the number of available roads
     */
    public int availableRoads();

    /**
     * Take a city from the piece bank. The bank's city count variable is decremented,
     * the settlement count is incremented (since cities replace settlements), and a
     * new city object is returned.
     *
     * This will only be used server-side.
     * @return the new city
     */
    public void takeCity();

    /**
     * Set the number of cities that will be available in the piece bank.
     * @param availableCities the number of available cities
     */
    public void setAvailableCities(int availableCities);

    /**
     * Get the number of cities in the piece bank.
     *
     * @return the number of available cities
     */
    public int availableCities();

    /**
     * Take a settlement from the piece bank. The bank's settlement count variable is
     * decremented and a new settlement object is returned.
     *
     * This will only be used server-side.
     * @return the new settlement
     */
    public void takeSettlement();

    /**
     * Set the number of settlements that will be available in the piece bank.
     * @param availableSettlements the number of available settlements
     */
    public void setAvailableSettlements(int availableSettlements);

    /**
     * Get the number of settlements in the piece bank.
     * @return the number of available settlements
     */
    public int availableSettlements();
}