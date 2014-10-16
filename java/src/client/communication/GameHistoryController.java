package client.communication;

import java.util.*;
import java.util.List;

import client.base.*;
import shared.definitions.*;
import shared.model.Game;
import shared.model.GameModelFacade;


/**
 * Game history controller implementation
 */
public class GameHistoryController extends Controller implements IGameHistoryController {

	public GameHistoryController(IGameHistoryView view) {
		
		super(view);

        //(Game)(GameModelFacade.getInstance().getGame()).addObserver(this);

		initFromModel();
	}
	
	@Override
	public IGameHistoryView getView() {
		
		return (IGameHistoryView)super.getView();
	}
	
	private void initFromModel() {
		//getView().setEntries(GameModelFacade.getInstance().getMoveHistory());
	}

    @Override
    public void update(Observable o, Object arg) {
        initFromModel();
    }
}

