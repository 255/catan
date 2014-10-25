package client.devcards;

import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import client.base.*;
import shared.model.*;

import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * "Dev card" controller implementation
 */
public class DevCardController extends Controller implements IDevCardController {
    private final static Logger logger = Logger.getLogger("catan");

	private IBuyDevCardView buyCardView;
	private IAction soldierAction;
	private IAction roadAction;
    private IServerModelFacade m_facade;
	
	/**
	 * DevCardController constructor
	 * 
	 * @param view "Play dev card" view
	 * @param buyCardView "Buy dev card" view
	 * @param soldierAction Action to be executed when the user plays a soldier card.  It calls "mapController.playSoldierCard()".
	 * @param roadAction Action to be executed when the user plays a road building card.  It calls "mapController.playRoadBuildingCard()".
	 */
	public DevCardController(IPlayDevCardView view, IBuyDevCardView buyCardView, 
								IAction soldierAction, IAction roadAction) {

		super(view);
		
		this.buyCardView = buyCardView;
		this.soldierAction = soldierAction;
		this.roadAction = roadAction;

        Game.getInstance().addObserver(this);
        m_facade = ServerModelFacade.getInstance();
	}

	public IPlayDevCardView getPlayCardView() {
		return (IPlayDevCardView)super.getView();
	}

	public IBuyDevCardView getBuyCardView() {
		return buyCardView;
	}

	@Override
	public void startBuyCard() {
		
		getBuyCardView().showModal();
	}

	@Override
	public void cancelBuyCard() {
		
		getBuyCardView().closeTopModal();
	}

	@Override
	public void buyCard() {
        try {
            m_facade.buyDevCard();
        } catch (ModelException e) {
            logger.log(Level.WARNING, "Buy card failed.", e);
        }
		getBuyCardView().closeTopModal();
	}

	@Override
	public void startPlayCard() {
		
		getPlayCardView().showModal();
	}

	@Override
	public void cancelPlayCard() {

		getPlayCardView().closeTopModal();
	}

	@Override
	public void playMonopolyCard(ResourceType resource) {
        try {
            m_facade.playMonopoly(resource);
        } catch (ModelException e) {
            logger.log(Level.WARNING, "Play monopoly card failed. - Model Exception", e);
        }
	}

	@Override
	public void playMonumentCard() {
        try {
            m_facade.playMonument();
        } catch (ModelException e) {
            logger.log(Level.WARNING, "Play monument card failed. - Model Exception", e);
        }
	}

	@Override
	public void playRoadBuildCard() {
		roadAction.execute();
	}

	@Override
	public void playSoldierCard() {
		soldierAction.execute();
	}

	@Override
	public void playYearOfPlentyCard(ResourceType resource1, ResourceType resource2) {
        try {
            m_facade.playYearOfPlenty(resource1, resource2);
        } catch (ModelException e) {
            logger.log(Level.WARNING, "Play year of plenty card failed. - Model Exception", e);
        }
	}

    @Override
    public void update(Observable o, Object arg) {
        initFromModel();
    }

    public void initFromModel() {
        logger.entering("client.devcards.DevCardController", "initFromModel");

        if (Game.getInstance().isNotInitialized()) {
            logger.fine("Not intializing DevCardController: the game object has not been initialized");
            return; // do nothing if the game object has not been created yet
        }

        IPlayer player = Game.getInstance().getLocalPlayer();
        IDevCardHand totalDevCards = player.getAllDevCards();
        //for each card type set enabled and amount
        for (DevCardType type : DevCardType.values()) {
            getPlayCardView().setCardEnabled(type, Game.getInstance().localPlayerIsPlaying() && player.canPlayDevCard(type));
            getPlayCardView().setCardAmount(type, totalDevCards.getCount(type));
        }
        logger.exiting("client.devcards.DevCardController", "initFromModel");
    }
}

