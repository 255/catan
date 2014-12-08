package server.persistence;

import shared.model.GameManager;
import shared.model.IGame;
import shared.model.Game;
import shared.model.IGameManager;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by jeffreybacon on 12/7/14.
 */
public class SQLiteGamesDAO extends AbstractSQLiteDAO implements IGamesDAO {

    protected SQLiteGamesDAO(SQLitePersistenceManager manager) {
        super(manager);
    }

    @Override
    public void saveGame(IGame game) throws PersistenceException {
        String sql = "INSERT INTO Games (GameId, GameData) VALUES (?, ?)";
        writeToDB(sql, game.getID(), game, -1);
    }

    @Override
    public IGameManager loadGames() throws PersistenceException {
        IGameManager gameManager = new GameManager();

        String sql = "SELECT GameData FROM Games";
        List data = new ArrayList(readFromDB(sql));
        for(int i = 0; i < data.size(); i++) {
            try {
                ByteArrayInputStream byteStream = new ByteArrayInputStream((byte[])data.get(i));
                ObjectInputStream objectStream = new ObjectInputStream(byteStream);
                IGame game = (Game)objectStream.readObject();
                gameManager.loadGame(game);
            } catch (IOException e) {
                throw new PersistenceException();
            } catch (ClassNotFoundException e) {
                throw new PersistenceException();
            }
        }

        return gameManager;
    }
}
