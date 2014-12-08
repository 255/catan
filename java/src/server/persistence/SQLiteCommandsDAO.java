package server.persistence;

import server.command.ICommand;
import shared.model.IGame;

import java.util.List;

/**
 * Created by jeffreybacon on 12/7/14.
 */
public class SQLiteCommandsDAO extends AbstractSQLiteDAO implements ICommandsDAO {

    protected SQLiteCommandsDAO() {

    }

    @Override
    public void saveCommand(ICommand command) throws PersistenceException {

    }

    @Override
    public List<ICommand> loadCommands(IGame game) throws PersistenceException {
        return null;
    }
}
