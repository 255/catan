package shared.model;

/**
 * Created by jeffreybacon on 9/27/14.
 */
public class TradeOffer implements ITradeOffer{

    private int m_sender;
    private int m_receiver;
    private IResourceBundle m_offer;

    public TradeOffer(int sender, int receiver, IResourceBundle offer) {
        setSender(sender);
        setReceiver(receiver);
        setOffer(offer);
    }

    @Override
    public int getSender() {
        return m_sender;
    }

    @Override
    public void setSender(int sender) {
        m_sender = sender;
    }

    @Override
    public int getReceiver() {
        return m_receiver;
    }

    @Override
    public void setReceiver(int receiver) {
        m_receiver = receiver;
    }

    @Override
    public IResourceBundle getOffer() {
        return m_offer;
    }

    @Override
    public void setOffer(IResourceBundle offer) {
        m_offer = offer;
    }
}
