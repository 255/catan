package client.points;

import client.base.*;
import shared.model.GameModelFacade;

import java.util.Observable;


/**
 * Implementation for the points controller
 */
public class PointsController extends Controller implements IPointsController {

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
        getPointsView().setPoints(GameModelFacade.getInstance().getCurrentPlayer().getVictoryPoints());
	}

    @Override
    public void update(Observable o, Object arg) {
        initFromModel();
    }
}

