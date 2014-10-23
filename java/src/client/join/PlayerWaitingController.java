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

//    private static final int c_millisecondsPerSecond = 1000;
//    private static final int c_defaultPollingInterval = 3;
//    private Timer m_timer;

    private Game m_game;

    //private GameInfo m_joinGame;

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
//        int secondsBetweenPolls = c_defaultPollingInterval;
//        m_timer = new Timer();
//        m_timer.schedule(new QueryTask(), c_millisecondsPerSecond * secondsBetweenPolls, c_millisecondsPerSecond * secondsBetweenPolls);

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
        if (Game.getInstance().gameHasStarted()) {
            if (getView().isModalShowing()) {
                getView().closeTopModal();
            }
        }
        else {
            List<IPlayer> playerList = m_game.getPlayers();
            PlayerInfo[] players = new PlayerInfo[playerList.size()];
            for (int i = 0; i < playerList.size(); i++) {
                players[i] = new PlayerInfo(playerList.get(i).getId(), playerList.get(i).getIndex(), playerList.get(i).getName(), playerList.get(i).getColor());
            }
            getView().setPlayers(players);
        }
    }

//    public void updatePlayers() {
//        try {
//            List<PlayerInfo> playerList = m_admin.listGames().get(0).getPlayers();
//            PlayerInfo[] players = new PlayerInfo[playerList.size()];
//            for (int i = 0; i < playerList.size(); i++) {
//                players[i] = new PlayerInfo(playerList.get(i).getId(), playerList.get(i).getPlayerIndex(), playerList.get(i).getName(), playerList.get(i).getColor());
//            }
//            getView().setPlayers(players);
//        } catch (NetworkException e) {
//            logger.log(Level.WARNING, "Update players failed. - Network Exception", e);
//        } catch (IOException e) {
//            logger.log(Level.WARNING, "Update players failed. - I/O Exception", e);
//        }
//        if (!getView().isModalShowing()) {
//            m_timer.cancel();
//        }
//    }

//    class QueryTask extends TimerTask {
//        public void run() {
//            updatePlayers();
//        }
//    }
}

