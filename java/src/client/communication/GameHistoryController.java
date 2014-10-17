package client.communication;

import java.util.*;
import java.util.List;
import java.util.logging.Logger;

import client.base.*;
import shared.definitions.*;
import shared.model.Game;
import shared.model.GameModelFacade;
import shared.model.ModelException;


/**
 * Game history controller implementation
 */
public class GameHistoryController extends Controller implements IGameHistoryController {

    private final static Logger logger = Logger.getLogger("catan");

    public GameHistoryController(IGameHistoryView view) {
		
		super(view);

        Game.getInstance().addObserver(this);
	}
	
	@Override
	public IGameHistoryView getView() {
		return (IGameHistoryView)super.getView();
	}
	
	private void initFromModel() {
        if (!Game.getInstance().isNotInitialized()) {
            getView().setEntries(GameModelFacade.getInstance().getMoveHistory().getMessages());
        } else {
            logger.fine("Game is not initialized");
        }
	}

    @Override
    public void update(Observable o, Object arg) {
        initFromModel();
    }
}

