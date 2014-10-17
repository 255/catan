package client.turntracker;

import shared.definitions.CatanColor;
import client.base.*;
import shared.model.*;

import java.util.Observable;
import java.util.logging.Logger;


/**
 * Implementation for the turn tracker controller
 */
public class TurnTrackerController extends Controller implements ITurnTrackerController {

    private final static Logger logger = Logger.getLogger("catan");

	public TurnTrackerController(ITurnTrackerView view) {
		
		super(view);
		
		initFromModel();
	}
	
	@Override
	public ITurnTrackerView getView() {
		return (ITurnTrackerView)super.getView();
	}

	@Override
	public void endTurn() {
        try {
            ServerModelFacade.getInstance().finishTurn();
        } catch (Exception e) {
            logger.finest("ERROR ending turn! See endTurn() in the TurnTrackerController");
        }
    }
	
	private void initFromModel() {
        // get the local player's color
        CatanColor color = Game.getInstance().getLocalPlayer().getColor();
        getView().setLocalPlayerColor(color);

        // highlight the local player
        IPlayer p = Game.getInstance().getLocalPlayer();
        getView().updatePlayer(
                        p.getIndex(),
                        p.getVictoryPoints(),
                        true,
                        false,          //TODO how do we check if a player has largestArmy?
                        false           //TODO how do we check if a player has longestRoad?
                    );

        // TODO will I need to set the other player colors and info from here?
	}

    @Override
    public void update(Observable o, Object arg) {
        initFromModel();
    }
}

