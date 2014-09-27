package shared.model;

import shared.definitions.DevCardType;

import java.util.Collection;

/**
 * Represents the cards in a players hand
 */
public class DevCardHand implements IDevCardHand {

    private Collection<DevCardType> m_cardStack;

    /**
     * Creates a DevCard hand with the specified collection of cards
     *
     * @param cardStack a Collection of cardTypes
     */
    DevCardHand(Collection<DevCardType> cardStack) {
        setCardStack(cardStack);
    }

    /**
     * Get the count of how many development cards are in the hand.
     *
     * @param devCardType the type of development card to get the count of
     * @return the number of development cards of devCardType in the hand
     */
    @Override
    public int getCount(DevCardType devCardType) {
        return m_cardStack.size();
    }

    /**
     * Add one of the specified type of card to the development card hand.
     *
     * @param devCardType the type of development card to add to the hand
     */
    @Override
    public void add(DevCardType devCardType) {
        m_cardStack.add(devCardType);
    }

    /**
     * Remove one of the specified type of card from the development card hand.
     *
     * @param devCardType the type of development card to remove (play) from the hand
     */
    @Override
    public void remove(DevCardType devCardType) {
        m_cardStack.remove(devCardType);
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
        for(DevCardType type : m_cardStack) {
            devCardHand.add(type);
        }
    }

    /**
     * Select a random development card to remove from this dev card hand.
     *
     * @return the type of card that was drawn, or null if no cards are in the hand
     */
    @Override
    public DevCardType drawRandom() {
        // how do we want to handle this?
        //      create the random logic from here
        //      or
        //      create a random function in the controller and use it to draw a specific card

        return null;
    }

    //*********//
    // Setters //
    //*********//

    /**
     * Sets the cards in the player's hand
     *
     * @param cardStack the cards in the player's hand
     */
    private void setCardStack(Collection<DevCardType> cardStack) {
        m_cardStack = cardStack;
    }
}
