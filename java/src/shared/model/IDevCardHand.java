package shared.model;

import shared.definitions.DevCardType;

import java.io.Serializable;

/**
 * A collection of development cards.
 * The game's bank and each player have a hand of development cards.
 * @author Wyatt
 */
public interface IDevCardHand extends Serializable {
    /**
     * Get the count of how many development cards are in the hand.
     * @return the number of development cards in the hand
     */
    public int getCount();

    /**
     * Get the count of how many development cards are in the hand.
     * @param devCardType the type of development card to get the count of
     * @return the number of development cards of devCardType in the hand
     */
    public int getCount(DevCardType devCardType);

    /**
     * Return the sum of the dev card hands. Neither object is changed.
     * @param other the other dev card hand
     * @return a new object that contains the sum of the two dev card hands
     */
    public IDevCardHand sum(IDevCardHand other);

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
     * Draws a random card from the DevCardHand
     *
     * @return the type of dev card that was drawn
     */
    public DevCardType drawCard();
}
