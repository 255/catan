package server.plugin;

import server.command.ICommand;
import server.persistence.AbstractPersistenceManager;
import server.persistence.ICommandsDAO;
import server.persistence.PersistenceException;
import shared.model.IGame;
import shared.model.ModelException;
import shared.model.User;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by jeffreybacon on 12/7/14.
 */
public class SQLiteCommandsDAO extends AbstractSQLiteDAO implements ICommandsDAO {

    protected SQLiteCommandsDAO(SQLitePersistenceManager manager) {
        super(manager);
    }

    @Override
    public void saveCommand(ICommand command) throws PersistenceException {

        String checkpointQuery = "select commandData from commands where gameId = ?";
        List<ICommand> commands = super.readFromDB(checkpointQuery, command.getGame().getID(), "commandData");

        int commandsSaved = commands.size();

        if (commandsSaved < getPersistenceManager().getCommandsBetweenCheckpoints()) {
            String sql = "insert into commands (gameId, commandData) values (?, ?)";
            super.writeToDB(sql, command.getGame().getID(), command);
        } else {
            String sql = "update games set gameData = ? where gameId = ?";
            super.updateDB(sql, command.getGame(), command.getGame().getID());

            sql = "delete from commands where gameId = ?";
            super.deleteFromDB(sql, command.getGame().getID());

            sql = "insert into commands (gameId, commandData) values (?, ?)";
            super.writeToDB(sql, command.getGame().getID(), command);
        }
    }

    @Override
    public void loadCommands(IGame game) throws PersistenceException {
        String query = "select commandData from users where gameId = ?";
        List<ICommand> commands = super.readFromDB(query, game.getID(), "commandData");

        SortedMap<Integer, ICommand> orderedCommands = new TreeMap<>();

        for (int i = 0; i < commands.size(); ++i) {
            orderedCommands.put(i, commands.get(i));
        }

        try {
            for (ICommand command : orderedCommands.values()) {
                command.setGameAndPlayers(game);
                command.execute();
            }
        } catch (ModelException e) {
            throw new PersistenceException("Failed executing stored command.", e);
        }

        String sqlQuery = "update games set gameData = ? where gameId = ?";
        super.updateDB(sqlQuery, game, game.getID());

        sqlQuery = "delete from commands where gameId = ?";
        super.deleteFromDB(sqlQuery, game.getID());
    }
}