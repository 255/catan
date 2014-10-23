package client.join;

import client.base.*;
import client.data.PlayerInfo;
import client.network.*;
import shared.model.Game;
import shared.model.IPlayer;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Implementation for the player waiting controller
 */
public class PlayerWaitingController extends Controller implements IPlayerWaitingController, Observer {
    private final static Logger logger = Logger.getLogger("catan");

    private Game m_game;

	public PlayerWaitingController(IPlayerWaitingView view) {

		super(view);

        m_game = Game.getInstance();
        m_game.addObserver(this);
	}

	@Override
	public IPlayerWaitingView getView() {

		return (IPlayerWaitingView)super.getView();
	}

	@Override
	public void start() {
        try {
            getView().setAIChoices(GameAdministrator.getInstance().listAI());
        } catch (NetworkException e) {
            logger.log(Level.WARNING, "When attempting to list AI's, this error was thrown: " + e.getMessage(), e);
        }

        if (!Game.getInstance().gameHasStarted()) {
            getView().showModal();
        }
	}

	@Override
	public void addAI() {
        try {
            GameAdministrator.getInstance().addAI(getView().getSelectedAI());
        } catch (NetworkException ex) {
            logger.finer("When adding an AI, this error was thrown: " + ex.getMessage());
        }
	}

    @Override
    public void update(Observable o, Object arg) {
        initFromModel();
    }

    private void initFromModel() {
        if (Game.getInstance().gameHasStarted()) {
            if (getView().isModalShowing()) {
                getView().closeModal();
            }
        }
        else {
            List<IPlayer> playerList = m_game.getPlayers();
            PlayerInfo[] players = new PlayerInfo[playerList.size()];
            for (int i = 0; i < playerList.size(); i++) {
                players[i] = new PlayerInfo(playerList.get(i).getId(), playerList.get(i).getIndex(), playerList.get(i).getName(), playerList.get(i).getColor());
                System.out.println("Player: " + playerList.get(i).getName());
            }
            getView().setPlayers(players);
        }
    }
}

