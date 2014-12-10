package server.plugin;

import server.persistence.IGamesDAO;
import server.persistence.PersistenceException;
import shared.model.GameManager;
import shared.model.IGame;
import shared.model.Game;
import shared.model.IGameManager;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by jeffreybacon on 12/7/14.
 */
public class SQLiteGamesDAO extends AbstractSQLiteDAO implements IGamesDAO {

    protected SQLiteGamesDAO(SQLitePersistenceManager manager) {
        super(manager);
    }

    @Override
    public void saveGame(IGame game) throws PersistenceException {
        String readSql = "select * from games where gameId = ?";
        ResultSet rs = readFromDB(readSql, game.getID());
        try {
            if(rs.next()) {
                String sql = "update games set gameData = ? where gameId = ?";
                updateDB(sql, game, game.getID());
            } else {
                String sql = "insert into games (gameId, gameData) values (?, ?)";
                writeToDB(sql, game.getID(), game);
            }
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    @Override
    public IGameManager loadGames() throws PersistenceException {
        IGameManager gameManager = new GameManager();

        String sql = "select * from games";
        List games = readFromDB(sql, -1, "gameData");
        int index = 1;
        try {
            for (Object game : games) {
                gameManager.loadGame((Game) game);
            }
        } catch (Exception e) {
            throw new PersistenceException(e);
        }

        return gameManager;
    }
}
