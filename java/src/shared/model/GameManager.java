package shared.model;

import shared.definitions.CatanColor;
import java.util.HashMap;

/**
 * Created by jeffreybacon on 11/11/14.
 */
public class GameManager implements IGameManager{
    private int m_nextGameId;
    private HashMap<Integer, IGame> m_games;

    public GameManager() {
        m_nextGameId = 0;
        m_games = new HashMap<Integer, IGame>();
    }

    @Override
    public IGame joinGame(int gameId, IUser user, CatanColor playerColor) {
        m_games.get(gameId).joinGame(user, playerColor);
        return m_games.get(gameId);
    }

    @Override
    public IGame createGame(String gameName, boolean randomPorts, boolean randomTiles, boolean randomNumbers) {
        IGame newGame = new Game(gameName, randomPorts, randomTiles, randomNumbers);
        m_games.put(m_nextGameId, newGame);
        m_nextGameId++;
        return newGame;
    }

    @Override
    public IGame getGame(int gameIndex) {
        return m_games.get(gameIndex);
    }

    @Override
    public void removeGame(int gameIndex) {
        m_games.remove(gameIndex);
    }
}
