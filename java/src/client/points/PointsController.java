package client.points;

import client.base.*;
import shared.model.Game;
import shared.model.GameModelFacade;

import java.util.Observable;
import java.util.logging.Logger;


/**
 * Implementation for the points controller
 */
public class PointsController extends Controller implements IPointsController {

    private final static Logger logger = Logger.getLogger("catan");

	private IGameFinishedView finishedView;
	
	/**
	 * PointsController constructor
	 * 
	 * @param view Points view
	 * @param finishedView Game finished view, which is displayed when the game is over
	 */
	public PointsController(IPointsView view, IGameFinishedView finishedView) {
		
		super(view);
		
		setFinishedView(finishedView);

        Game.getInstance().addObserver(this);

        initFromModel();
	}
	
	public IPointsView getPointsView() {
		
		return (IPointsView)super.getView();
	}
	
	public IGameFinishedView getFinishedView() {
		return finishedView;
	}
	public void setFinishedView(IGameFinishedView finishedView) {
		this.finishedView = finishedView;
	}

	private void initFromModel() {
        if (!Game.getInstance().isNotInitialized()) {
            getPointsView().setPoints(GameModelFacade.getInstance().getCurrentPlayer().getVictoryPoints());
        } else {
            logger.fine("Game was not initialized");
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        initFromModel();
    }
}

