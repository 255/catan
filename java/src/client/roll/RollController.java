package client.roll;

import client.base.*;
import shared.model.Game;

import java.util.Observable;
import java.util.logging.Logger;


/**
 * Implementation for the roll controller
 */
public class RollController extends Controller implements IRollController {
    private final static Logger logger = Logger.getLogger("catan");

	private IRollResultView resultView;

	/**
	 * RollController constructor
	 * 
	 * @param view Roll view
	 * @param resultView Roll result view
	 */
	public RollController(IRollView view, IRollResultView resultView) {

		super(view);
		
		setResultView(resultView);

        Game.getInstance().addObserver(this);
    }
	
	public IRollResultView getResultView() {
		return resultView;
	}
	public void setResultView(IRollResultView resultView) {
		this.resultView = resultView;
	}

	public IRollView getRollView() {
		return (IRollView)getView();
	}
	
	@Override
	public void rollDice() {
		getResultView().showModal();
	}

    @Override
    public void update(Observable o, Object arg) {
        logger.entering("client.roll.RollController", "update", o);

        logger.exiting("client.roll.RollController", "update", o);
    }
}

