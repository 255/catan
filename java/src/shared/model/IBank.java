package shared.model;

import shared.definitions.DevCardType;

/**
 *  This represents the cards held by the game that are common to all players,
 *  i.e., resources and development cards.
 * @author Wyatt
 */
public interface IBank {
    /**
     * Set the dev cards in the bank.
     * @param devCardHand the set of cards to use
     */
    public void setDevCards(IDevCardHand devCardHand);

    /**
     * Draw a random card from the bank's dev card hand.
     *
     * This will be only used server-side.
     * @return the type of dev card that was drawn, or null if none are available
     */
    public DevCardType drawDevCard();

    /**
     * Set the available resources in the bank.
     * @param resources the new resources that will be available in the bank
     */
    public void setResources(IResourceBundle resources);

    /**
     * Draw the specified resources from the resource bank. The amount of
     * resources successfully drawn are returned. If the bank does not contain
     * enough resources to satisfy the request, only the amount that can be
     * drawn are returned.
     *
     * This will only be used server-side.
     * @param requestedResources the resources that are being drawn
     * @return the resources that were
     */
    public IResourceBundle drawResources(IResourceBundle requestedResources);
}
