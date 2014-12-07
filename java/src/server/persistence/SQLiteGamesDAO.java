package server.persistence;

import shared.model.IGame;
import shared.model.IGameManager;

/**
 * Created by jeffreybacon on 12/7/14.
 */
public class SQLiteGamesDAO extends AbstractSQLiteDAO implements IGamesDAO {

    protected SQLiteGamesDAO() {

    }

    @Override
    public void saveGame(IGame game) throws PersistenceException {

    }

    @Override
    public IGameManager loadGames() throws PersistenceException {
        return null;
    }
}
