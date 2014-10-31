package client.turntracker;

import shared.definitions.CatanColor;
import client.base.*;
import shared.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Implementation for the turn tracker controller
 */
public class TurnTrackerController extends Controller implements ITurnTrackerController {

    private final static Logger logger = Logger.getLogger("catan");
    private boolean m_playersInitialized;

	public TurnTrackerController(ITurnTrackerView view) {
		
		super(view);

        Game.getInstance().addObserver(this);

        m_playersInitialized = false;
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
            logger.log(Level.WARNING, "ERROR: See endTurn() in the TurnTrackerController.", e);
        }
    }
	
    @Override
    public void update(Observable o, Object arg) {
        initFromModel();
    }

	private void initFromModel() {
        // get the list of players
        List<IPlayer> players = Game.getInstance().getPlayers();
        if(players.size() < CatanConstants.NUM_PLAYERS) {
            return;
        }

        // iterate through them and set up their box on the tracker
        for(IPlayer pl : players) {
            // first round initializes the turn tracker
            if (!m_playersInitialized) {
                getView().initializePlayer(pl.getIndex(), pl.getName(), pl.getColor());
            }
            getView().updatePlayer(
                            pl.getIndex(),
                            pl.getVictoryPoints(),
                            GameModelFacade.getInstance().isPlayersTurn(pl),
                            GameModelFacade.getInstance().playerHasLargestArmy(pl),
                            GameModelFacade.getInstance().playerHasLongestRoad(pl),
                            pl.getColor()
                        );
        }
        m_playersInitialized = true;

        String gameState;
        if (Game.getInstance().localPlayerIsPlaying()) {
            gameState = "Finish Turn";
        }
        else if (Game.getInstance().localPlayerIsBeingOfferedTrade()) {
            gameState = "Accept or Reject Trade";
        }
        else if (Game.getInstance().localPlayerIsDiscarding()) {
            gameState = "Discard Cards";
        }
        else if (Game.getInstance().localPlayerIsRolling()) {
            gameState = "Roll the Dice";
        }
        else if (Game.getInstance().localPlayerIsRobbing()) {
            gameState = "Place the Robber";
        }
        else if (Game.getInstance().localPlayerIsPlacingInitialPieces()) {
            gameState = "Place Initial Pieces";
        }
        else {
            gameState = "Waiting for Other Players";
        }

        getView().setLocalPlayerColor((Game.getInstance().getLocalPlayer().getColor()));
        getView().updateGameState(gameState, Game.getInstance().localPlayerIsPlaying());
	}
}

