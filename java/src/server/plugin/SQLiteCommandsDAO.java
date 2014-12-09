package server.plugin;

import server.command.ICommand;
import server.persistence.ICommandsDAO;
import server.persistence.PersistenceException;
import shared.model.IGame;

import java.sql.ResultSet;

/**
 * Created by jeffreybacon on 12/7/14.
 */
public class SQLiteCommandsDAO extends AbstractSQLiteDAO implements ICommandsDAO {

    protected SQLiteCommandsDAO(SQLitePersistenceManager manager) {
        super(manager);
    }

    @Override
    public void saveCommand(ICommand command) throws PersistenceException {
        String sql = "insert into commands (commandsId, gameId, commandsData) values (?, ?, ?)";
        super.writeToDB(sql, command.getGame().getID(), command);
    }

    @Override
    public void loadCommands(IGame game) throws PersistenceException {
        String query = "select commandsData from users where gameId = ? nhbgb";
        ResultSet rs = super.readFromDB(query);

    }
}
