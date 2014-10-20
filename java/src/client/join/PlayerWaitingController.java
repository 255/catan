package client.join;

import client.base.*;
import client.data.GameInfo;
import client.data.PlayerInfo;
import client.network.*;

import java.io.IOException;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;


/**
 * Implementation for the player waiting controller
 */
public class PlayerWaitingController extends Controller implements IPlayerWaitingController {
    private final static Logger logger = Logger.getLogger("catan");

    private static final int c_millisecondsPerSecond = 1000;
    private static final int c_defaultPollingInterval = 3;
    private Timer m_timer;

    private IGameAdministrator m_admin;
    private GameInfo m_joinGame;

	public PlayerWaitingController(IPlayerWaitingView view) {

		super(view);

        m_admin = GameAdministrator.getInstance();
	}

	@Override
	public IPlayerWaitingView getView() {

		return (IPlayerWaitingView)super.getView();
	}

	@Override
	public void start() {
        int secondsBetweenPolls = c_defaultPollingInterval;
        m_timer = new Timer();
        m_timer.schedule(new QueryTask(), c_millisecondsPerSecond * secondsBetweenPolls, c_millisecondsPerSecond * secondsBetweenPolls);
		getView().showModal();
	}

	@Override
	public void addAI() {

		// TEMPORARY
		getView().closeModal();
	}

    @Override
    public void update(Observable o, Object arg) {

    }

    public void updatePlayers() {
        try {
            List<PlayerInfo> playerList = m_admin.listGames().get(0).getPlayers();
            PlayerInfo[] players = new PlayerInfo[playerList.size()];
            for (int i = 0; i < playerList.size(); i++) {
                players[i] = new PlayerInfo(playerList.get(i).getId(), playerList.get(i).getPlayerIndex(), playerList.get(i).getName(), playerList.get(i).getColor());
            }
            getView().setPlayers(players);
        } catch (NetworkException e) {
            logger.log(Level.WARNING, "Update players failed. - Network Exception", e);
        } catch (IOException e) {
            logger.log(Level.WARNING, "Update players failed. - I/O Exception", e);
        }
        if (!getView().isModalShowing()) {
            m_timer.cancel();
        }
    }

    class QueryTask extends TimerTask {
        public void run() {
            updatePlayers();
        }
    }
}

