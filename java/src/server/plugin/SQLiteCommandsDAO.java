package server.plugin;

import server.command.ICommand;
import server.persistence.AbstractPersistenceManager;
import server.persistence.ICommandsDAO;
import server.persistence.PersistenceException;
import shared.model.IGame;
import shared.model.User;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
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

        String checkpointQuery = "select commandsData from commands where gameId = ?";
        ResultSet rs = super.readFromDB(checkpointQuery, command.getGame().getID());

        int commandsSaved = 0;

        try {
            while (rs.next()) {
                ++commandsSaved;
            }

            if (commandsSaved < getPersistenceManager().getCommandsBetweenCheckpoints()) {
                String sql = "insert into commands (commandsId, gameId, commandsData) values (?, ?, ?)";
                super.writeToDB(sql, command.getGame().getID(), command);
            } else {
                String sql = "update games set gameData = ? where gameId = ?";
                super.updateDB(sql, command.getGame(), command.getGame().getID());

                sql = "delete from commands where gameId = ?";
                super.deleteFromDB(sql, command.getGame().getID());

                sql = "insert into commands (commandsId, gameId, commandsData) values (?, ?, ?)";
                super.writeToDB(sql, command.getGame().getID(), command);
            }
        } catch (SQLException ex) {

        }
    }

    @Override
    public void loadCommands(IGame game) throws PersistenceException {
        String query = "select commandsData from users where gameId = ? nhbgb";
        ResultSet rs = super.readFromDB(query, game.getID());

        SortedMap<Integer, ICommand> orderedCommands = new TreeMap<>();

        try {
            while (rs.next()) {
                byte[] byteArray = (byte[]) rs.getObject("commandData");
                ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                ICommand command = (ICommand) objectInputStream.readObject();

                orderedCommands.put(rs.getRow(), command);
            }
        } catch (Exception ex) {

        }

        try {
            for (ICommand command : orderedCommands.values()) {
                command.setGameAndPlayers(game);
                command.execute();
            }
        } catch (Exception ex) {

        }

        String sqlQuery = "update games set gameData = ? where gameId = ?";
        super.updateDB(sqlQuery, game, game.getID());

        sqlQuery = "delete from commands where gameId = ?";
        super.deleteFromDB(sqlQuery, game.getID());
    }
}