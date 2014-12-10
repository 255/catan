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
        String sql = "insert into Games (GameId, GameData) values (?, ?)";
        writeToDB(sql, game.getID(), game);
    }

    @Override
    public IGameManager loadGames() throws PersistenceException {
        IGameManager gameManager = new GameManager();

        String sql = "select GameData from Games";
        List games = readFromDB(sql, -1);
        int index = 1;
//        try {
//            while(rs.next()) {
//                try {
//                    ByteArrayInputStream byteStream = new ByteArrayInputStream(((byte[])rs.getObject(index++)));
//                    ObjectInputStream objectStream = new ObjectInputStream(byteStream);
//                    IGame game = (Game)objectStream.readObject();
//                    gameManager.loadGame(game);
//                } catch (IOException e) {
//                    throw new PersistenceException();
//                } catch (ClassNotFoundException e) {
//                    throw new PersistenceException();
//                }
//            }
//        } catch (SQLException e) {
//            throw new PersistenceException();
//        }

        return gameManager;
    }
}
