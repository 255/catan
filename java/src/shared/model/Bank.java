package shared.model;

import shared.definitions.DevCardType;

/**
 * Created by jeffreybacon on 9/25/14.
 */
public class Bank implements IBank{
    private IDevCardHand m_devCards;
    private IResourceBank m_resources;

    public Bank(IDevCardHand devCards, IResourceBundle resources) {
        setDevCards(devCards);
        setResources(resources);
    }

    @Override
    public void setDevCards(IDevCardHand devCardHand) {
        m_devCards = devCardHand;
    }

    @Override
    public DevCardType drawDevCard() {
        return null; //m_devCards.drawRandom();
    }

    @Override
    public void setResources(IResourceBundle resources) {
        m_resources = new ResourceBank(resources.getWood(), resources.getBrick(), resources.getSheep(), resources.getWheat(), resources.getOre());
    }

    @Override
    public IResourceBundle drawResources(IResourceBundle requestedResources) {
        return m_resources.subtract(requestedResources);
    }

    @Override
    public IResourceBundle getResources() {
        return m_resources.getResources();
    }
}
