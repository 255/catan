package shared.model;

import shared.definitions.DevCardType;

import java.util.Random;

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

    public DevCardHand(int monopoly, int monument, int roadBuilding, int soldier, int yearOfPlenty) throws ModelException {
        if (monopoly < 0 || monument < 0 || roadBuilding < 0 || soldier < 0 || yearOfPlenty < 0) {
            throw new ModelException("Cannot have a negative number of development cards.");
        }

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
     * Return the sum of the dev card hands. Neither object is changed.
     *
     * @param other the other dev card hand
     * @return a new object that contains the sum of the two dev card hands
     */
    @Override
    public IDevCardHand sum(IDevCardHand other) {
        DevCardHand o = (DevCardHand)other;
        try {
            return new DevCardHand(
                    this.m_monopoly + o.m_monopoly,
                    this.m_monument + o.m_monument,
                    this.m_roadBuilding + o.m_roadBuilding,
                    this.m_soldier + o.m_soldier,
                    this.m_yearOfPlenty + o.m_yearOfPlenty
            );
        } catch (ModelException e) {
            assert false : "Both DevCardHands should have positive numbers of cards!";
            return new DevCardHand();
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
    public void remove(DevCardType devCardType) throws ModelException {
        if (getCount(devCardType) <= 0) {
            throw new ModelException("Attempted to remove card type with a count of 0.");
        }

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

    @Override
    public DevCardType drawCard() {

        DevCardType drawnCard = null;

        Random rand = new Random();

        boolean foundCard = false;

        while (!foundCard) {
            drawnCard = DevCardType.randomDevCardType();
            if (this.getCount(drawnCard) > 0) {
                foundCard = true;
                try {
                    this.remove(drawnCard);
                } catch (ModelException ex) {
                    assert false : "DevCardType should not be given if not removed if there is none in the hand";
                }
            }
        }

        return drawnCard;
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

    public static DevCardHand generateInitial() {
        try {
            return new DevCardHand(
                    CatanConstants.TOTAL_MONOPOLY_CARDS,
                    CatanConstants.TOTAL_MONUMENT_CARDS,
                    CatanConstants.TOTAL_ROAD_BUILDING_CARDS,
                    CatanConstants.TOTAL_SOLDIER_CARDS,
                    CatanConstants.TOTAL_YEAR_OF_PLENTY_CARDS
            );
        } catch (ModelException e) {
            assert false : "The CatanConstants are messed up!";
            e.printStackTrace();
            return null;
        }
    }
}
