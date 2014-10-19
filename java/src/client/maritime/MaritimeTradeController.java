package client.maritime;

import shared.definitions.*;
import client.base.*;
import shared.model.*;

import java.util.Iterator;
import java.util.Observable;
import java.util.Set;
import java.util.logging.Logger;


/**
 * Implementation for the maritime trade controller
 */
public class MaritimeTradeController extends Controller implements IMaritimeTradeController {

    private final static Logger logger = Logger.getLogger("catan");

	private IMaritimeTradeOverlay tradeOverlay;
    private ResourceType m_gettingResource;
    private ResourceType m_givingResource;

	public MaritimeTradeController(IMaritimeTradeView tradeView, IMaritimeTradeOverlay tradeOverlay) {
		
		super(tradeView);

		setTradeOverlay(tradeOverlay);

        Game.getInstance().addObserver(this);
	}
	
	public IMaritimeTradeView getTradeView() {
		
		return (IMaritimeTradeView)super.getView();
	}
	
	public IMaritimeTradeOverlay getTradeOverlay() {
		return tradeOverlay;
	}

	public void setTradeOverlay(IMaritimeTradeOverlay tradeOverlay) {
		this.tradeOverlay = tradeOverlay;
	}

	@Override
	public void startTrade() {
        // Assuming player has ports and resources to trade

        // quit if it is not the player's turn
        if(Game.getInstance().getGameState() == GameState.PLAYING) {
            return;
        }

		getTradeOverlay().showModal();
	}

	@Override
	public void makeTrade() {
        Set<PortType> playerPorts = GameModelFacade.getInstance().getPlayerPorts();
        int ratio = 4;
        for (PortType pt : playerPorts) {
            if(pt == convertRTypeToPType(m_gettingResource)) {
                ratio = 2;
            }
            else if (pt == PortType.THREE)
            {
                ratio = 3;
            }
        }

        try {
            ServerModelFacade.getInstance().maritimeTrade(ratio, m_givingResource, m_gettingResource);
        } catch (ModelException e) {
            logger.fine("ERROR: MaritimeTrade failed in the makeTrade() method");
        }

        unsetGetValue();
        unsetGiveValue();
        getTradeOverlay().closeModal();
	}

	@Override
	public void cancelTrade() {
        unsetGetValue();
        unsetGiveValue();

		getTradeOverlay().closeModal();
	}

	@Override
	public void setGetResource(ResourceType resource) {
        m_gettingResource = resource;
	}

	@Override
	public void setGiveResource(ResourceType resource) {
        m_givingResource = resource;
	}

	@Override
	public void unsetGetValue() {
        m_gettingResource = null;
	}

	@Override
	public void unsetGiveValue() {
        m_givingResource = null;
	}

    @Override
    public void update(Observable o, Object arg) {
        initFromModel();
    }

    private void initFromModel() {
        unsetGetValue();
        unsetGiveValue();
        if(!getTradeOverlay().isModalShowing()) getTradeOverlay().showModal();
    }

    //************************//
    // Private Helper Methods //
    //************************//
    private PortType convertRTypeToPType(ResourceType r) {
        switch (r) {
            case WOOD: return PortType.WOOD;
            case BRICK: return PortType.BRICK;
            case SHEEP: return PortType.SHEEP;
            case WHEAT: return PortType.WHEAT;
            case ORE: return PortType.ORE;
            default: return null;
        }
    }
}

