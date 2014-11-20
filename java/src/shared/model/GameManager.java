package shared.model;

import shared.definitions.CatanColor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 * Created by jeffreybacon - 11/11/14.
 */
public class GameManager implements IGameManager{
    private static Logger logger = Logger.getLogger("catanserver");

    private int m_nextGameId;
    private HashMap<Integer, IGame> m_games;

    public GameManager() {
        m_nextGameId = 0;
        m_games = new HashMap<>();
    }

    @Override
    public boolean joinGame(int gameId, IUser user, CatanColor playerColor) throws ModelException {
        if (m_games.containsKey(gameId)) {
            return m_games.get(gameId).joinGame(user, playerColor);
        }
        else {
            throw new ModelException("There is no game with id " + gameId + "!");
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
            throw new ModelException("Invalid game " + gameIndex + " requested.");
        }
    }

    @Override
    public Collection<IGame> listGames() {
        Collection<IGame> ongoingGames = new ArrayList<>();

        for (IGame game : m_games.values()) {
            // only list games with no winner yet
            if (game.getWinner() == null) {
                ongoingGames.add(game);
            }
        }

        return ongoingGames;
    }

    @Override
    public void loadGame(IGame game) {
        if (m_games.containsKey(game.getID())) {
            // destroy the old game, just in case
            m_games.get(game.getID()).reset();
            // TODO: we may need to update pointers in command objects if we end up serializing those
            // or just store IDs instead
        }

        m_games.put(game.getID(), game);
    }

    /**
     * Resets the next game id variable to 0
     */
    @Override
    public void resetNextGameId() {
        m_nextGameId = 0;
    }
}
