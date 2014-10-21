package client.turntracker;

import shared.definitions.CatanColor;
import client.base.*;
import shared.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.logging.Logger;


/**
 * Implementation for the turn tracker controller
 */
public class TurnTrackerController extends Controller implements ITurnTrackerController {

    private final static Logger logger = Logger.getLogger("catan");

	public TurnTrackerController(ITurnTrackerView view) {
		
		super(view);

        Game.getInstance().addObserver(this);
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
        // get the list of players
        List<IPlayer> players = Game.getInstance().getPlayers();

        // iterate through them and set up their box on the tracker
        for(IPlayer pl : players) {
            getView().initializePlayer(pl.getIndex(), pl.getName(), pl.getColor());
            getView().updatePlayer(
                            pl.getIndex(),
                            pl.getVictoryPoints(),
                            GameModelFacade.getInstance().isPlayersTurn(pl),
                            GameModelFacade.getInstance().playerHasLargestArmy(pl),
                            GameModelFacade.getInstance().playerHasLongestRoad(pl)
                        );
        }

        String gameState = Game.getInstance().localPlayerIsPlaying() ? "Finish Turn" : "Waiting for Other Players";

        getView().updateGameState(gameState, Game.getInstance().localPlayerIsPlaying());
	}

    @Override
    public void update(Observable o, Object arg) {
        initFromModel();
    }
}

