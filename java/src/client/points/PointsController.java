package client.points;

import client.base.*;
import shared.model.CatanConstants;
import shared.model.Game;
import shared.model.GameModelFacade;
import shared.model.GameState;

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
        if (Game.getInstance().getWinner() != null) {
            getFinishedView().setWinner(Game.getInstance().getWinner().getName(), Game.getInstance().getWinner().equals(Game.getInstance().getLocalPlayer()));

            if (!getFinishedView().isModalShowing()) {
                getFinishedView().showModal();
            }
        }

        // only set up to 10 points so that the view doesn't get angry at us
        int points = GameModelFacade.getInstance().getCurrentPlayer().getVictoryPoints();
        assert points >= 0;

        if (points <= CatanConstants.VICTORY_POINTS_TO_WIN) {
            getPointsView().setPoints(points);
        }
        else {
            getPointsView().setPoints(CatanConstants.VICTORY_POINTS_TO_WIN);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        initFromModel();
    }
}

