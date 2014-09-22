package shared.model;

import shared.definitions.DevCardType;

/**
 * A collection of development cards.
 * @author Wyatt
 */
public interface IDevCardHand {
    /**
     * Get the count of how many development cards are in the hand.
     * @param devCardType the type of development card to get the count of
     * @return the number of development cards of devCardType in the hand
     */
    public int getCount(DevCardType devCardType);

    /**
     * Add one of the specified type of card to the development card hand.
     * @param devCardType the type of development card to add to the hand
     */
    public void add(DevCardType devCardType);

    /**
     * Remove one of the specified type of card from the development card hand.
     * @param devCardType the type of development card to remove (play) from the hand
     */
    public void remove(DevCardType devCardType);

    /**
     * Move all of the cards in this dev card hand to another dev card.
     * This will be used server-side to transfer cards from a player's new
     * hand to old/playable hand.
     * @param devCardHand the dev card hand to which to transfer the cards in this hand
     */
    public void transferAllCardsToHand(IDevCardHand devCardHand);

    /**
     * Select a random development card to remove from this dev card hand.
     * @return the type of card that was drawn, or null if no cards are in the hand
     */
    public DevCardType drawRandom();
}