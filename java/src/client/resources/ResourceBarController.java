package client.resources;

import java.util.*;

import client.base.*;
import shared.model.*;


/**
 * Implementation for the resource bar controller
 */
public class ResourceBarController extends Controller implements IResourceBarController {

	private Map<ResourceBarElement, IAction> elementActions;
	
	public ResourceBarController(IResourceBarView view) {

		super(view);
		
		elementActions = new HashMap<ResourceBarElement, IAction>();

        GameModelFacade.instance().getGame().addObserver(this);
	}

	@Override
	public IResourceBarView getView() {
		return (IResourceBarView)super.getView();
	}

	/**
	 * Sets the action to be executed when the specified resource bar element is clicked by the user
	 * 
	 * @param element The resource bar element with which the action is associated
	 * @param action The action to be executed
	 */
	public void setElementAction(ResourceBarElement element, IAction action) {

		elementActions.put(element, action);
	}

	@Override
	public void buildRoad() {
		executeElementAction(ResourceBarElement.ROAD);
	}

	@Override
	public void buildSettlement() {
		executeElementAction(ResourceBarElement.SETTLEMENT);
	}

	@Override
	public void buildCity() {
		executeElementAction(ResourceBarElement.CITY);
	}

	@Override
	public void buyCard() {
		executeElementAction(ResourceBarElement.BUY_CARD);
	}

	@Override
	public void playCard() {
		executeElementAction(ResourceBarElement.PLAY_CARD);
	}
	
	private void executeElementAction(ResourceBarElement element) {
		
		if (elementActions.containsKey(element)) {
			
			IAction action = elementActions.get(element);
			action.execute();
		}
	}

    public void initFromModel() {

        IPlayer localPlayer = GameModelFacade.instance().getLocalPlayer();

        IResourceBank resourceCards = localPlayer.getResources();
        getView().setElementAmount(ResourceBarElement.WOOD, resourceCards.getWood());
        getView().setElementAmount(ResourceBarElement.BRICK, resourceCards.getBrick());
        getView().setElementAmount(ResourceBarElement.SHEEP, resourceCards.getSheep());
        getView().setElementAmount(ResourceBarElement.WHEAT, resourceCards.getWheat());
        getView().setElementAmount(ResourceBarElement.ORE, resourceCards.getOre());

        getView().setElementEnabled(ResourceBarElement.ROAD, GameModelFacade.instance().canBuyRoad());
        getView().setElementAmount(ResourceBarElement.ROAD, localPlayer.getPieceBank().availableRoads());

        getView().setElementEnabled(ResourceBarElement.SETTLEMENT, GameModelFacade.instance().canBuySettlement());
        getView().setElementAmount(ResourceBarElement.SETTLEMENT, localPlayer.getPieceBank().availableSettlements());

        getView().setElementEnabled(ResourceBarElement.CITY, GameModelFacade.instance().canBuyCity());
        getView().setElementAmount(ResourceBarElement.CITY, localPlayer.getPieceBank().availableCities());

        getView().setElementEnabled(ResourceBarElement.BUY_CARD, GameModelFacade.instance().canBuyDevCard());

        getView().setElementEnabled(ResourceBarElement.PLAY_CARD, GameModelFacade.instance().localPlayerIsPlaying());

        getView().setElementAmount(ResourceBarElement.SOLDIERS, localPlayer.getSoldiers());
    }

    @Override
    public void update(Observable o, Object arg) {
        initFromModel();
    }
}

