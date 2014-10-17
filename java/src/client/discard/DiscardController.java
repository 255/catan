package client.discard;

import shared.definitions.*;
import client.base.*;
import client.misc.*;
import shared.model.*;

import java.util.Observable;


/**
 * Discard controller implementation
 */
public class DiscardController extends Controller implements IDiscardController {

	private IWaitView waitView;
    private int woodDiscard;
    private int brickDiscard;
    private int sheepDiscard;
    private int wheatDiscard;
    private int oreDiscard;

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

        initFromModel();
	}

	public IDiscardView getDiscardView() {
		return (IDiscardView)super.getView();
	}
	
	public IWaitView getWaitView() {
		return waitView;
	}

	@Override
	public void increaseAmount(ResourceType resource) {
        // positive values increase discard numbers
		updateCount(1, resource);
	}

	@Override
	public void decreaseAmount(ResourceType resource) {
        // negative values decrease discard numbers
        updateCount(-1, resource);
	}

	@Override
	public void discard() {
        IResourceBank discardBank =
                new ResourceBank(
                    woodDiscard,
                    brickDiscard,
                    sheepDiscard,
                    wheatDiscard,
                    oreDiscard
                );

        GameModelFacade.getInstance().getPlayerResources().subtract(discardBank);
        getDiscardView().closeModal();

        //TODO am I using too much logic? Should I be calling subtract from here?
        //TODO find out if other players are still discarding
        //TODO change state?
	}

    private void initFromModel() {
        // state checked and actions determined
        if(Game.getInstance().getGameState() != GameState.DISCARDING) {
            return;
        }

        // open the discard overlay
        getDiscardView().showModal();

        // start with nothing to discard
        woodDiscard = 0;
        brickDiscard = 0;
        sheepDiscard = 0;
        wheatDiscard = 0;
        oreDiscard = 0;
    }

    @Override
    public void update(Observable o, Object arg) {
        initFromModel();
    }

    //************************//
    // Private Helper Methods //
    //************************//

    // positive values increase discard amount
    // negative values decrease discard amount
    private void updateCount(int incDec, ResourceType r) {
        switch(r) {
            case WOOD: woodDiscard += incDec;
                break;
            case BRICK: brickDiscard += incDec;
                break;
            case SHEEP: sheepDiscard += incDec;
                break;
            case WHEAT: wheatDiscard += incDec;
                break;
            case ORE: oreDiscard += incDec;
                break;
            default:
                break;
        }
    }
}

