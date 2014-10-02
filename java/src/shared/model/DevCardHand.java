package shared.model;

import shared.definitions.DevCardType;

import java.util.Collection;

/**
 * Represents the cards in a players hand
 */
public class DevCardHand implements IDevCardHand {
    private int m_monopoly;
    private int m_monument;
    private int m_roadBuilding;
    private int m_soldier;
    private int m_yearOfPlenty;

    public DevCardHand() {
        m_monopoly = 0;
        m_monument = 0;
        m_roadBuilding = 0;
        m_soldier = 0;
        m_yearOfPlenty = 0;
    }

    public DevCardHand(int monopoly, int monument, int roadBuilding, int soldier, int yearOfPlenty) {
        m_monopoly = monopoly;
        m_monument = monument;
        m_roadBuilding = roadBuilding;
        m_soldier = soldier;
        m_yearOfPlenty = yearOfPlenty;
    }

    /**
     * Get the count of how many development cards are in the hand.
     *
     * @return the number of development cards in the hand
     */
    @Override
    public int getCount() {
        return m_monopoly + m_monument + m_roadBuilding + m_soldier + m_yearOfPlenty;
    }

    /**
     * Get the count of how many development cards are in the hand.
     *
     * @param devCardType the type of development card to get the count of
     * @return the number of development cards of devCardType in the hand
     */
    @Override
    public int getCount(DevCardType devCardType) {
        switch (devCardType) {
            case MONOPOLY: return m_monopoly;
            case MONUMENT: return m_monument;
            case ROAD_BUILD: return m_roadBuilding;
            case SOLDIER: return m_soldier;
            case YEAR_OF_PLENTY: return m_yearOfPlenty;
            default:
                assert false;
                return -1;
        }
    }

    /**
     * Add one of the specified type of card to the development card hand.
     *
     * @param devCardType the type of development card to add to the hand
     */
    @Override
    public void add(DevCardType devCardType) {
        switch (devCardType) {
            case MONOPOLY: m_monopoly++; break;
            case MONUMENT: m_monument++; break;
            case ROAD_BUILD: m_roadBuilding++; break;
            case SOLDIER: m_soldier++; break;
            case YEAR_OF_PLENTY: m_yearOfPlenty++; break;
            default:
                assert false;
        }
    }

    /**
     * Remove one of the specified type of card from the development card hand.
     *
     * @param devCardType the type of development card to remove (play) from the hand
     */
    @Override
    public void remove(DevCardType devCardType) {
        switch (devCardType) {
            case MONOPOLY: m_monopoly--; break;
            case MONUMENT: m_monument--; break;
            case ROAD_BUILD: m_roadBuilding--; break;
            case SOLDIER: m_soldier--; break;
            case YEAR_OF_PLENTY: m_yearOfPlenty--; break;
            default:
                assert false;
        }
    }

    /**
     * Move all of the cards in this dev card hand to another dev card.
     * This will be used server-side to transfer cards from a player's new
     * hand to old/playable hand.
     *
     * @param devCardHand the dev card hand to which to transfer the cards in this hand
     */
    @Override
    public void transferAllCardsToHand(IDevCardHand devCardHand) {
        // TODO: not needed for client
    }
}
