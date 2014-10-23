package client.domestic;

import client.data.PlayerInfo;
import shared.definitions.*;
import client.base.*;
import client.misc.*;
import shared.model.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.String.format;


/**
 * Domestic trade controller implementation
 */
public class DomesticTradeController extends Controller implements IDomesticTradeController {
    private final static Logger logger = Logger.getLogger("catan");

	private IDomesticTradeOverlay tradeOverlay;
	private IWaitView waitOverlay;
	private IAcceptTradeOverlay acceptOverlay;

    private PendingOffer m_tradeOffer;
    private int m_recipient;
    private boolean m_needToInitializePlayersList;

    public static final int NO_PLAYER = -1;

	/**
	 * DomesticTradeController constructor
	 * 
	 * @param tradeView Domestic trade view (i.e., view that contains the "Domestic Trade" button)
	 * @param tradeOverlay Domestic trade overlay (i.e., view that lets the user propose a domestic trade)
	 * @param waitOverlay Wait overlay used to notify the user they are waiting for another player to accept a trade
	 * @param acceptOverlay Accept trade overlay which lets the user accept or reject a proposed trade
	 */
	public DomesticTradeController(IDomesticTradeView tradeView, IDomesticTradeOverlay tradeOverlay,
									IWaitView waitOverlay, IAcceptTradeOverlay acceptOverlay) {

		super(tradeView);
		
		setTradeOverlay(tradeOverlay);
		setWaitOverlay(waitOverlay);
		setAcceptOverlay(acceptOverlay);

        m_tradeOffer = null;
        m_recipient = NO_PLAYER;
        m_needToInitializePlayersList = true;

        Game.getInstance().addObserver(this);
    }
	
	public IDomesticTradeView getTradeView() {
		return (IDomesticTradeView)super.getView();
	}

	public IDomesticTradeOverlay getTradeOverlay() {
		return tradeOverlay;
	}

	public void setTradeOverlay(IDomesticTradeOverlay tradeOverlay) {
		this.tradeOverlay = tradeOverlay;
	}

	public IWaitView getWaitOverlay() {
		return waitOverlay;
	}

	public void setWaitOverlay(IWaitView waitView) {
		this.waitOverlay = waitView;
	}

	public IAcceptTradeOverlay getAcceptOverlay() {
		return acceptOverlay;
	}

	public void setAcceptOverlay(IAcceptTradeOverlay acceptOverlay) {
		this.acceptOverlay = acceptOverlay;
	}

	@Override
	public void startTrade() {
        logger.entering("client.domestic.DomesticTradeController", "startTrade");

        getTradeOverlay().reset();

        m_tradeOffer = new PendingOffer();
        updateButtons();

		getTradeOverlay().showModal();
        logger.exiting("client.domestic.DomesticTradeController", "startTrade");
	}

	@Override
	public void decreaseResourceAmount(ResourceType resource) {
        m_tradeOffer.decrement(resource);
        updateButtons();
	}

	@Override
	public void increaseResourceAmount(ResourceType resource) {
        m_tradeOffer.increment(resource);
        updateButtons();
	}

	@Override
	public void sendTradeOffer() {
		getTradeOverlay().closeModal();

        try {
            ServerModelFacade.getInstance().offerTrade(m_tradeOffer.toResourceBank(), m_recipient);
            getWaitOverlay().showModal();
        }
        catch (ModelException e) {
            logger.log(Level.WARNING, "Sending trade offer failed.", e);
        }
	}

	@Override
	public void setPlayerToTradeWith(int playerIndex) {
        assert playerIndex >= NO_PLAYER && playerIndex < 4;
        m_recipient = playerIndex;
        updateButtons();
	}

	@Override
	public void setResourceToReceive(ResourceType resource) {
        m_tradeOffer.setOfferType(resource, OfferType.RECEIVE);
        updateButtons();
	}

	@Override
	public void setResourceToSend(ResourceType resource) {
        m_tradeOffer.setOfferType(resource, OfferType.SEND);
        updateButtons();
	}

	@Override
	public void unsetResource(ResourceType resource) {
        m_tradeOffer.setOfferType(resource, OfferType.NONE);
        updateButtons();
	}

	@Override
	public void cancelTrade() {
		getTradeOverlay().closeModal();
        getTradeOverlay().reset();
        m_tradeOffer = null;
        m_recipient = NO_PLAYER;
	}

	@Override
	public void acceptTrade(boolean willAccept) {
        getAcceptOverlay().closeModal();
        getAcceptOverlay().reset();

        try {
            ServerModelFacade.getInstance().acceptTrade(willAccept);
        }
        catch (ModelException e) {
            logger.log(Level.WARNING, "Failed to %s trade".format(willAccept ? "accept" : "reject"), e);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        logger.entering("client.domestic.DomesticTradeController", "update");

        if (!Game.getInstance().gameHasStarted()) {
            logger.finer("Game has not started yet, not initializing.");
            logger.exiting("client.domestic.DomesticTradeController", "update");
            return;
        }

        // initialize players list only once
        // TODO: what if player changes color?
        if (m_needToInitializePlayersList) {
            getTradeOverlay().setPlayers(PlayerInfo.fromPlayers(Game.getInstance().getNonLocalPlayers()));
            m_needToInitializePlayersList = false;
            getTradeOverlay().setCancelEnabled(true); //just to be sure -- this never needs to be disabled
        }

        // initialize the buttons and dialogs
        if (Game.getInstance().localPlayerIsPlaying()) {
            getTradeView().enableDomesticTrade(true);

            // player sent a trade: keep the dialog open
            if (Game.getInstance().localPlayerIsOfferingTrade()) {
                if (!waitOverlay.isModalShowing()) {
                    waitOverlay.showModal();
                }
            }
            else if (waitOverlay.isModalShowing()) {
                waitOverlay.closeModal();
            }
        }
        else {
            getTradeView().enableDomesticTrade(false);

            if (Game.getInstance().localPlayerIsBeingOfferedTrade()) {
                // if the accept modal is not showing yet, initialize it and show it
                initializeAcceptOverlay();
            }
        }

        logger.exiting("client.domestic.DomesticTradeController", "update");
    }

    private void initializeAcceptOverlay() {
        if (acceptOverlay.isModalShowing()) {
            return;
        }

        acceptOverlay.reset();
        acceptOverlay.setAcceptEnabled(GameModelFacade.getInstance().canAcceptTrade());

        IResourceBank tradeResources = Game.getInstance().getTradeOffer().getOffer();

        for (ResourceType resourceType : ResourceType.values()) {
            int resourceCount = tradeResources.getCount(resourceType);

            if (resourceCount > 0) {
                acceptOverlay.addGetResource(resourceType, resourceCount);
            }
            else if (resourceCount < 0) {
                acceptOverlay.addGiveResource(resourceType, -resourceCount);
            }
        }

        acceptOverlay.showModal();
    }

    private void updateButtons() {
        updateResourceButtons();

        boolean resourcesChosen = m_tradeOffer.hasTradeSet();
        boolean playerChosen = m_recipient != NO_PLAYER;
        if (resourcesChosen && playerChosen) {
            getTradeOverlay().setTradeEnabled(true);
            getTradeOverlay().setStateMessage("Trade!");
        }
        else {
            getTradeOverlay().setTradeEnabled(false);

            if (!resourcesChosen) {
                getTradeOverlay().setStateMessage("select the resources you want to trade");
            }
            else {
                getTradeOverlay().setStateMessage("choose with whom you want to trade");
            }
        }
    }

    private void updateResourceButtons() {
        IResourceBank playerResourcees = GameModelFacade.getInstance().getPlayerResources();

        // look at each resource in the current offer and decide if buttons should be enabled
        for (Map.Entry<ResourceType, PendingResourceOffer> entry : m_tradeOffer.entrySet()) {
            ResourceType resource = entry.getKey();
            PendingResourceOffer offer = entry.getValue();

            // set the amount here, not in the view...
            getTradeOverlay().setResourceAmount(resource, offer.amount);

            // now determine the up/down state of the buttons
            if (offer.type == OfferType.NONE) {
                getTradeOverlay().setResourceAmountChangeEnabled(resource, false, false);
            }
            else {
                boolean canDecrease = (offer.amount > 0);
                boolean canIncrease;

                if (offer.type == OfferType.SEND) {
                    canIncrease = (offer.amount < playerResourcees.getCount(resource));
                }
                else { // type is RECEIVE
                    assert offer.type == OfferType.RECEIVE;
                    canIncrease = true;
                }

                getTradeOverlay().setResourceAmountChangeEnabled(resource, canIncrease, canDecrease);
            }
        }
    }

    //
    // Inner classes that represent and control the state of the pending trade offer
    //

    /** What the local player will do with the resources */
    private enum OfferType {
        SEND,
        NONE,
        RECEIVE,
    }

    /** A resource and a type (sending / receiving) */
    private static class PendingResourceOffer {
        public int amount;
        public OfferType type;

        public PendingResourceOffer() {
            amount = 0;
            type = OfferType.NONE;
        }
    }

    /** the current state of the trade offer */
    private static class PendingOffer extends HashMap<ResourceType, PendingResourceOffer> {
        public PendingOffer() {
            for (ResourceType resourceType : ResourceType.values()) {
                put(resourceType, new PendingResourceOffer());
            }
        }

        public void decrement(ResourceType type) {
            get(type).amount--;
        }

        public void increment(ResourceType type) {
            get(type).amount++;
        }

        public void setOfferType(ResourceType resource, OfferType offerType) {
            get(resource).type = offerType;
            get(resource).amount = 0;
        }

        public boolean hasTradeSet() {
            boolean sending   = false;
            boolean receiving = false;

            for (PendingResourceOffer offer : values()) {
                if (offer.amount > 0) {
                    if (offer.type == OfferType.SEND) {
                        sending = true;
                    }
                    else if (offer.type == OfferType.RECEIVE) {
                        receiving = true;
                    }
                }
            }

            return sending && receiving;
        }

        // convert the pending offer to a resource bank
        public ResourceBank toResourceBank() {
            ResourceBank resources = new ResourceBank();

            for (Entry<ResourceType, PendingResourceOffer> entry : this.entrySet()) {
                if (entry.getValue().type == OfferType.SEND) {
                    resources.setCount(entry.getKey(), entry.getValue().amount);
                } // negative values indicate the use wants to receive that resource
                else if (entry.getValue().type == OfferType.RECEIVE) {
                    resources.setCount(entry.getKey(), -entry.getValue().amount);
                }
            }

            return resources;
        }
    }
}

