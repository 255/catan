package server.persistence;

import shared.model.GameManager;
import shared.model.IGame;
import shared.model.IGameManager;

/**
 * A GamesDAO that does nothing. It returns an empty GameManager for loadGames.
 */
public class NoPersistenceGamesDAO implements IGamesDAO {
    @Override
    public void saveGame(IGame game) {
    }

    @Override
    public IGameManager loadGames() {
        return new GameManager();
    }
}
