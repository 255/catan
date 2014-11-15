package shared.model;

import shared.definitions.CatanColor;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 * Created by jeffreybacon on 11/11/14.
 */
public class GameManager implements IGameManager{
    private static Logger logger = Logger.getLogger("catanserver");

    private int m_nextGameId;
    private HashMap<Integer, IGame> m_games;

    public GameManager() {
        m_nextGameId = 0;
        m_games = new HashMap<>();

        // TODO: remove this debugging code
        try {
            createGame("Deep Blue vs. Garry Kasparov", false, false, false);
        } catch (ModelException e) {
            assert false;
            e.printStackTrace();
        }
    }

    @Override
    public boolean joinGame(int gameId, IUser user, CatanColor playerColor) {
        if (m_games.containsKey(gameId)) {
            return m_games.get(gameId).joinGame(user, playerColor);
        }
        else {
            return false;
        }
    }

    @Override
    public IGame createGame(String gameName, boolean randomPorts, boolean randomTiles, boolean randomNumbers) throws ModelException {
        IGame newGame = new Game(gameName, m_nextGameId, randomPorts, randomTiles, randomNumbers);
        m_games.put(m_nextGameId, newGame);
        m_nextGameId++;
        return newGame;
    }

    @Override
    public IGame getGame(int gameIndex) throws ModelException {
        if (m_games.containsKey(gameIndex)) {
            return m_games.get(gameIndex);
        }
        else {
            throw new ModelException("Invalid game requested.");
        }
    }

    @Override
    public void removeGame(int gameIndex) {
        m_games.remove(gameIndex);
    }

    /**
     * Get a list of games.
     *
     * @return a list of games
     */
    @Override
    public Collection<IGame> listGames() {
        return Collections.unmodifiableCollection(m_games.values());
    }
}
