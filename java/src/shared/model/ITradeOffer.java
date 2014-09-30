package shared.model;

/**
 * Created by jeffreybacon on 9/27/14.
 */
public interface ITradeOffer {
    /**
     * Get the sender of the trade offer
     * @return the index of the sender
     */
    public IPlayer getSender();

    /**
     * Set the sender of the trade offer
     * @param sender
     */
    public void setSender(IPlayer sender);

    /**
     * Get the receiver of the trade offer
     * @return the index of the receiver
     */
    public IPlayer getReceiver();

    /**
     * Set the receiver of the trade offer
     * @param receiver
     */
    public void setReceiver(IPlayer receiver);

    /**
     * Get the resources of the trade offer
     * @return the resource bundle representing the trade
     */
    public IResourceBundle getOffer();

    /**
     * Set the resources of the trade offer
     * @param offer
     */
    public void setOffer(IResourceBundle offer);
}
