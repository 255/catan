package shared.model;

import com.sun.org.apache.xpath.internal.operations.Mod;

/**
 * Represents the pieces that a player has left to place on the map
 */
public class PieceBank implements IPieceBank {

    private int m_numRoads;
    private int m_numSettlements;
    private int m_numCities;

    /**
     * Constructs an empty PieceBank
     */
    public PieceBank() {
        m_numRoads = 0;
        m_numSettlements = 0;
        m_numCities = 0;
    }

    /**
     * Constructs a PieceBank that has specified numbers
     *
     * @param numRoads is the number to set the available roads
     * @param numSettlements is the number to set the available settlements
     * @param numCities is the number to set the available cities
     */
    public PieceBank(int numRoads, int numSettlements, int numCities) throws ModelException {
        setAvailableRoads(numRoads);
        setAvailableSettlements(numSettlements);
        setAvailableCities(numCities);
    }


    /**
     * Take a road from the piece bank. The bank's road count variable is decremented,
     * and a new city object is returned.
     * <p>
     * This will only be used server-side.
     *
     * @return the new road
     */
    @Override
    public void takeRoad() {
        m_numRoads--;
    }

    /**
     * Set the number of roads that will be available in the piece bank.
     *
     * @param availableRoads the number of available roads
     */
    @Override
    public void setAvailableRoads(int availableRoads) throws ModelException {
        if (availableRoads < 0) {
            throw new ModelException("Attempted to set a negative number of roads.");
        }
        m_numRoads = availableRoads;
    }

    /**
     * Get the number of roads in the bank.
     *
     * @return the number of available roads
     */
    @Override
    public int availableRoads() {
        return m_numRoads;
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
    public void takeCity() {
        m_numCities--;
    }

    /**
     * Set the number of cities that will be available in the piece bank.
     *
     * @param availableCities the number of available cities
     */
    @Override
    public void setAvailableCities(int availableCities) throws ModelException {
        if (availableCities < 0) {
            throw new ModelException("Attempted to set a negative number of cities.");
        }
        m_numCities = availableCities;
    }

    /**
     * Get the number of cities in the piece bank.
     *
     * @return the number of available cities
     */
    @Override
    public int availableCities() {
        return m_numCities;
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
    public void takeSettlement() {
        m_numSettlements--;
    }

    /**
     * Set the number of settlements that will be available in the piece bank.
     *
     * @param availableSettlements the number of available settlements
     */
    @Override
    public void setAvailableSettlements(int availableSettlements) throws ModelException {
        if (availableSettlements < 0) {
            throw new ModelException("Attempted to set a negative number of settlements.");
        }
        m_numSettlements = availableSettlements;
    }

    /**
     * Get the number of settlements in the piece bank.
     *
     * @return the number of available settlements
     */
    @Override
    public int availableSettlements() {
        return m_numSettlements;
    }
}
