package client.discard;

import shared.definitions.*;
import client.base.*;
import client.misc.*;
import shared.model.Game;
import shared.model.GameModelFacade;
import shared.model.GameState;
import shared.model.IResourceBank;

import java.util.Observable;


/**
 * Discard controller implementation
 */
public class DiscardController extends Controller implements IDiscardController {

	private IWaitView waitView;
    private int woodCount;
    private int brickCount;
    private int sheepCount;
    private int wheatCount;
    private int oreCount;

	/**
	 * DiscardController constructor
	 * 
	 * @param view View displayed to let the user select cards to discard
	 * @param waitView View displayed to notify the user that they are waiting for other players to discard
	 */
	public DiscardController(IDiscardView view, IWaitView waitView) {

		super(view);

        Game.getInstance().addObserver(this);

		this.waitView = waitView;
	}

	public IDiscardView getDiscardView() {
		return (IDiscardView)super.getView();
	}
	
	public IWaitView getWaitView() {
		return waitView;
	}

	@Override
	public void increaseAmount(ResourceType resource) {
		switch(resource) {

        }
	}

	@Override
	public void decreaseAmount(ResourceType resource) {
        switch(resource) {

        }
	}

	@Override
	public void discard() {
		
		getDiscardView().closeModal();
	}

    private void initFromModel() {
        // state checked and actions determined
        if(Game.getInstance().getGameState() != GameState.DISCARDING) {
            return;
        }

        getDiscardView().showModal();

        // resource numbers retrieved from game model
        IResourceBank rb = GameModelFacade.getInstance().getPlayerResources();
        woodCount = rb.getWood();
        brickCount = rb.getBrick();
        sheepCount = rb.getSheep();
        wheatCount = rb.getWheat();
        oreCount = rb.getOre();
    }

    @Override
    public void update(Observable o, Object arg) {
        initFromModel();
    }
}

