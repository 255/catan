package client.join;

import client.data.GameInfo;
import client.network.*;
import shared.definitions.CatanColor;
import client.base.*;
import client.data.*;
import client.misc.*;

import java.io.IOException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Implementation for the join game controller
 */
public class JoinGameController extends Controller implements IJoinGameController {
    private final static Logger logger = Logger.getLogger("catan");

	private INewGameView newGameView;
	private ISelectColorView selectColorView;
	private IMessageView messageView;
	private IAction joinAction;
    private IGameAdministrator m_admin;
    private GameInfo m_joinGame;

    private static final int c_millisecondsPerSecond = 1000;
    private static final int c_defaultPollingInterval = 3;
    private Timer m_timer;
	
	/**
	 * JoinGameController constructor
	 * 
	 * @param view Join game view
	 * @param newGameView New game view
	 * @param selectColorView Select color view
	 * @param messageView Message view (used to display error messages that occur while the user is joining a game)
	 */
	public JoinGameController(IJoinGameView view, INewGameView newGameView, 
								ISelectColorView selectColorView, IMessageView messageView) {

		super(view);

		setNewGameView(newGameView);
		setSelectColorView(selectColorView);
		setMessageView(messageView);

        m_admin = GameAdministrator.getInstance();
	}
	
	public IJoinGameView getJoinGameView() {
		
		return (IJoinGameView)super.getView();
	}
	
	/**
	 * Returns the action to be executed when the user joins a game
	 * 
	 * @return The action to be executed when the user joins a game
	 */
	public IAction getJoinAction() {
		
		return joinAction;
	}

	/**
	 * Sets the action to be executed when the user joins a game
	 * 
	 * @param value The action to be executed when the user joins a game
	 */
	public void setJoinAction(IAction value) {	
		
		joinAction = value;
	}
	
	public INewGameView getNewGameView() {
		
		return newGameView;
	}

	public void setNewGameView(INewGameView newGameView) {
		
		this.newGameView = newGameView;
	}
	
	public ISelectColorView getSelectColorView() {
		
		return selectColorView;
	}
	public void setSelectColorView(ISelectColorView selectColorView) {
		
		this.selectColorView = selectColorView;
	}
	
	public IMessageView getMessageView() {
		
		return messageView;
	}
	public void setMessageView(IMessageView messageView) {
		
		this.messageView = messageView;
	}

	@Override
	public void start() {
        int secondsBetweenPolls = c_defaultPollingInterval;
        m_timer = new Timer();
        m_timer.schedule(new QueryTask(), c_millisecondsPerSecond * secondsBetweenPolls, c_millisecondsPerSecond * secondsBetweenPolls);

        getGames();

		getJoinGameView().showModal();
	}

	@Override
	public void startCreateNewGame() {
		
		getNewGameView().showModal();
	}

	@Override
	public void cancelCreateNewGame() {
        getGames();
		
		getNewGameView().closeModal();
	}

	@Override
	public void createNewGame() {
        try {
            m_admin.createGame(getNewGameView().getRandomlyPlaceHexes(), getNewGameView().getUseRandomPorts(), getNewGameView().getUseRandomPorts(), getNewGameView().getTitle());
            getNewGameView().closeModal();
            getGames();
        } catch (NetworkException e) {
            logger.log(Level.WARNING, "Create game failed. - Network Exception", e);
            getMessageView().showModal();
            getMessageView().setTitle("Error!");
            getMessageView().setMessage("Create game failed.");
        } catch (IOException e) {
            logger.log(Level.WARNING, "Create game failed. - I/O Exception", e);
            getMessageView().showModal();
            getMessageView().setTitle("Error!");
            getMessageView().setMessage("Create game failed.");
        }
	}

	@Override
	public void startJoinGame(GameInfo game) {
        m_joinGame = game;

//        for (CatanColor color : CatanColor.values()) {
//            getSelectColorView().setColorEnabled(color, true);
//        }
//
//        for (PlayerInfo player : game.getPlayers()) {
//            if (player.getId() != m_admin.getLocalPlayerId()) {
//                getSelectColorView().setColorEnabled(player.getColor(), false);
//            }
//        }

        getColors();

		getSelectColorView().showModal();
	}

	@Override
	public void cancelJoinGame() {
	    m_timer.cancel();

		getJoinGameView().closeModal();
	}

	@Override
	public void joinGame(CatanColor color) {
        boolean success = false;
        try {
            success = m_admin.joinGame(m_joinGame.getId(), color);
        } catch (NetworkException e) {
            logger.log(Level.WARNING, "Join game failed. - Network Exception", e);
        }
		// If join succeeded
        if (success) {
            getSelectColorView().closeModal();
            getJoinGameView().closeModal();
            joinAction.execute();
        } else {
            getMessageView().showModal();
            getMessageView().setTitle("Error!");
            getMessageView().setMessage("Join game failed.");
        }
	}

    @Override
    public void update(Observable o, Object arg) {

    }

    private void getGames() {
        try {
            List<GameInfo> gameList = m_admin.listGames();
//            List<GameInfo> gameList = GameAdministrator.getInstance().listGames();
            GameInfo[] games = new GameInfo[gameList.size()];
            for (int i = 0; i < games.length; i++) {
                GameInfo tempGame = new GameInfo();
                tempGame.setId(gameList.get(i).getId());
                tempGame.setTitle(gameList.get(i).getTitle());
                List<PlayerInfo> players = gameList.get(i).getPlayers();
                for (int j = 0; j < players.size(); j++) {
                    if (players.get(j).getId() != -1) {
                        tempGame.addPlayer(new PlayerInfo(players.get(j).getId(), players.get(j).getPlayerIndex(), players.get(j).getName(), players.get(j).getColor()));
                    }
                }
                games[i] = tempGame;
            }

            PlayerInfo player = new PlayerInfo(m_admin.getLocalPlayerId(), -1, m_admin.getLocalPlayerName(), null);
//            PlayerInfo player = new PlayerInfo(GameAdministrator.getInstance().getLocalPlayerId(), -1, GameAdministrator.getInstance().getLocalPlayerName(), null);

            getJoinGameView().setGames(games, player);
        } catch (NetworkException e) {
            logger.log(Level.WARNING, "Update failed. - Network Exception", e);
        } catch (IOException e) {
            logger.log(Level.WARNING, "Update failed. - I/O Exception", e);
        }
    }

    private void getColors() {
        for (CatanColor color : CatanColor.values()) {
            getSelectColorView().setColorEnabled(color, true);
        }

        for (PlayerInfo player : m_joinGame.getPlayers()) {
            if (player.getId() != m_admin.getLocalPlayerId()) {
                getSelectColorView().setColorEnabled(player.getColor(), false);
            }
        }
    }

    class QueryTask extends TimerTask {
        public void run() {
//            getJoinGameView().closeModal();
            if (getJoinGameView().isModalShowing() && !getNewGameView().isModalShowing() && !getSelectColorView().isModalShowing() && !getMessageView().isModalShowing()) {
                getGames();
                getJoinGameView().showModal();
            }

            if (m_joinGame != null && getSelectColorView().isModalShowing()) {
//                getColors();
            }
        }
    }
}

