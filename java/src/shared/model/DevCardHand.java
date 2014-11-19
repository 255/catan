package shared.model;

import shared.definitions.DevCardType;

import java.util.Random;

/**
 * Represents the cards in a players hand
 */
public class DevCardHand implements IDevCardHand {
    private int monopoly;
    private int monument;
    private int roadBuilding;
    private int soldier;
    private int yearOfPlenty;

    public DevCardHand() {
        monopoly = 0;
        monument = 0;
        roadBuilding = 0;
        soldier = 0;
        yearOfPlenty = 0;
    }

    public DevCardHand(int monopoly, int monument, int roadBuilding, int soldier, int yearOfPlenty) throws ModelException {
        if (monopoly < 0 || monument < 0 || roadBuilding < 0 || soldier < 0 || yearOfPlenty < 0) {
            throw new ModelException("Cannot have a negative number of development cards.");
        }

        this.monopoly = monopoly;
        this.monument = monument;
        this.roadBuilding = roadBuilding;
        this.soldier = soldier;
        this.yearOfPlenty = yearOfPlenty;
    }

    /**
     * Get the count of how many development cards are in the hand.
     *
     * @return the number of development cards in the hand
     */
    @Override
    public int getCount() {
        return monopoly + monument + roadBuilding + soldier + yearOfPlenty;
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
            case MONOPOLY: return monopoly;
            case MONUMENT: return monument;
            case ROAD_BUILD: return roadBuilding;
            case SOLDIER: return soldier;
            case YEAR_OF_PLENTY: return yearOfPlenty;
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
                    this.monopoly + o.monopoly,
                    this.monument + o.monument,
                    this.roadBuilding + o.roadBuilding,
                    this.soldier + o.soldier,
                    this.yearOfPlenty + o.yearOfPlenty
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
            case MONOPOLY: monopoly++; break;
            case MONUMENT: monument++; break;
            case ROAD_BUILD: roadBuilding++; break;
            case SOLDIER: soldier++; break;
            case YEAR_OF_PLENTY: yearOfPlenty++; break;
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
        assert getCount(devCardType) > 0 : "Attempted to remove card type with a count of 0.";

        switch (devCardType) {
            case MONOPOLY: monopoly--; break;
            case MONUMENT: monument--; break;
            case ROAD_BUILD: roadBuilding--; break;
            case SOLDIER: soldier--; break;
            case YEAR_OF_PLENTY: yearOfPlenty--; break;
            default:
                assert false;
        }
    }

    @Override
    public DevCardType drawCard() {

        DevCardType drawnCard = null;

        final Random rand = new Random();

        int randomNumber = rand.nextInt(getCount());

        if (randomNumber < soldier) {
            drawnCard = DevCardType.SOLDIER;
        } else if (randomNumber < soldier + yearOfPlenty) {
            drawnCard = DevCardType.YEAR_OF_PLENTY;
        } else if (randomNumber < soldier + yearOfPlenty + monopoly) {
            drawnCard = DevCardType.MONOPOLY;
        } else if (randomNumber < soldier + yearOfPlenty + monopoly + roadBuilding) {
            drawnCard = DevCardType.ROAD_BUILD;
        } else { // if (randomNumber < soldier + yearOfPlenty + monopoly + roadBuilding + monument) {
            drawnCard = DevCardType.MONUMENT;
        }

        this.remove(drawnCard);

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
        ((DevCardHand)devCardHand).addHand(this);
        monopoly     = 0;
        monument     = 0;
        roadBuilding = 0;
        soldier      = 0;
        yearOfPlenty = 0;
    }

    private void addHand(DevCardHand devCardHand) {
        monopoly     += devCardHand.monopoly;
        monument     += devCardHand.monument;
        roadBuilding += devCardHand.roadBuilding;
        soldier      += devCardHand.soldier;
        yearOfPlenty += devCardHand.yearOfPlenty;
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
