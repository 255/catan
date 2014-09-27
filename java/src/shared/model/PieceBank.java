package shared.model;

/**
 * Represents the pieces that a player has left to place on the map
 */
public class PieceBank implements IPieceBank {
    /**
     * Take a road from the piece bank. The bank's road count variable is decremented,
     * and a new city object is returned.
     * <p>
     * This will only be used server-side.
     *
     * @return the new road
     */
    @Override
    public IRoad takeRoad() {
        return null;
    }

    /**
     * Set the number of roads that will be available in the piece bank.
     *
     * @param availableRoads the number of available roads
     */
    @Override
    public void setAvailableRoads(int availableRoads) {

    }

    /**
     * Get the number of roads in the bank.
     *
     * @return the number of available roads
     */
    @Override
    public int availableRoads() {
        return 0;
    }

    /**
     * Take a city from the piece bank. The bank's city count variable is decremented,
     * the settlement count is incremented (since cities replace settlements), and a
     * new city object is returned.
     * <p>
     * This will only be used server-side.
     *
     * @return the new city
     */
    @Override
    public ICity takeCity() {
        return null;
    }

    /**
     * Set the number of cities that will be available in the piece bank.
     *
     * @param availableCities the number of available cities
     */
    @Override
    public void setAvailableCities(int availableCities) {

    }

    /**
     * Get the number of cities in the piece bank.
     *
     * @return the number of available cities
     */
    @Override
    public int availableCities() {
        return 0;
    }

    /**
     * Take a settlement from the piece bank. The bank's settlement count variable is
     * decremented and a new settlement object is returned.
     * <p>
     * This will only be used server-side.
     *
     * @return the new settlement
     */
    @Override
    public Settlement takeSettlement() {
        return null;
    }

    /**
     * Set the number of settlements that will be available in the piece bank.
     *
     * @param availableSettlements the number of available settlements
     */
    @Override
    public void setAvailableSettlements(int availableSettlements) {

    }

    /**
     * Get the number of settlements in the piece bank.
     *
     * @return the number of available settlements
     */
    @Override
    public int availableSettlements() {
        return 0;
    }
}
