package client.discard;

import shared.definitions.*;
import client.base.*;
import client.misc.*;
import shared.model.*;

import java.util.List;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Discard controller implementation
 */
public class DiscardController extends Controller implements IDiscardController {

	private IWaitView waitView;
    private final static Logger logger = Logger.getLogger("catan");
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
        // if the local player has mot discarded, open the discard overlay
        boolean localNeedToDiscard = Game.getInstance().getLocalPlayer().needsToDiscard();
        if(localNeedToDiscard) {
            getDiscardView().showModal();
        }
        // if the local player does not need to discard, open the wait view
        else {
            getWaitView().showModal();
        }
        // when the poller returns, initFromModel will be run again

        //boolean localNeedToDiscard = Game.getInstance().getLocalPlayer().needsToDiscard();

        // if the local player needs to discard and the modal is showing, discard counts and close modal
        if(getDiscardView().isModalShowing() && localNeedToDiscard) {
            IResourceBank discardBank =
                new ResourceBank(woodDiscard, brickDiscard, sheepDiscard, wheatDiscard, oreDiscard);

            try {
                ServerModelFacade.getInstance().discardCards(discardBank);
            } catch (ModelException e) {
                logger.log(Level.WARNING, e.getMessage() + " : DiscardController.discard() had an error", e);
            }

            getDiscardView().closeModal();

            // the server should be tracking if the user discarded or not
        }
        // if the modal is showing and the player has already discarded
            // close the discard modal as the local player has finished discarding
        else if (getDiscardView().isModalShowing()) {
            getDiscardView().closeModal();
        }


        // TODO should the wait modal stay open while other players are discarding, where/when would I close it
        // keep the waitView open until it is determined that all players have discarded
        getWaitView().showModal();

        // check if all the players have discarded
        if(everyoneHasDiscarded()) return;

        // if no one needs to discard, the wait view should be closed
        getWaitView().closeModal();
	}

    @Override
    public void update(Observable o, Object arg) {
        initFromModel();
    }

    // run once, do not open modals in this method
    private void initFromModel() {
        // check that the state is set to DISCARDING
        if(Game.getInstance().getGameState() != GameState.DISCARDING) {
            // close all discarding modals
            if(getWaitView().isModalShowing()) getWaitView().closeModal();
            if(getDiscardView().isModalShowing()) getDiscardView().closeModal();
            return;
        }

        //TODO fix discard controller

        // start with nothing to discard
        woodDiscard = 0;
        brickDiscard = 0;
        sheepDiscard = 0;
        wheatDiscard = 0;
        oreDiscard = 0;
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

    private boolean everyoneHasDiscarded() {
        List<IPlayer> players = Game.getInstance().getPlayers();
        int localIndex = Game.getInstance().getLocalPlayer().getIndex();
        for(IPlayer p : players) {
            if(p.needsToDiscard() && p.getIndex() != localIndex) {
                return false;
            }
        }
        return true;
    }
}

