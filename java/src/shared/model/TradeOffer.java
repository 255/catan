package shared.model;

/**
 * Created by jeffreybacon on 9/27/14.
 */
public class TradeOffer implements ITradeOffer{

    private IPlayer m_sender;
    private IPlayer m_receiver;
    private IResourceBank m_offer;

    public TradeOffer(IPlayer sender, IPlayer receiver, IResourceBank offer) {
        setSender(sender);
        setReceiver(receiver);
        setOffer(offer);
    }

    @Override
    public IPlayer getSender() {
        return m_sender;
    }

    @Override
    public void setSender(IPlayer sender) {
        m_sender = sender;
    }

    @Override
    public IPlayer getReceiver() {
        return m_receiver;
    }

    @Override
    public void setReceiver(IPlayer receiver) {
        m_receiver = receiver;
    }

    @Override
    public IResourceBank getOffer() {
        return m_offer;
    }

    @Override
    public void setOffer(IResourceBank offer) {
        m_offer = offer;
    }
}
