package plugin;

import server.command.ICommand;
import server.persistence.AbstractFolderDAO;
import server.persistence.ICommandsDAO;
import server.persistence.PersistenceException;
import shared.model.IGame;

import java.util.List;

/**
 * Created by Wyatt - 12/6/2014.
 */
public class FolderCommandsDAO extends AbstractFolderDAO implements ICommandsDAO {
    public FolderCommandsDAO(FolderPersistenceManager folderPersistenceManager) throws PersistenceException {
        super(folderPersistenceManager, "commands");
    }

    @Override
    public void saveCommand(ICommand command) throws PersistenceException {
        // TODO
    }

    @Override
    public List<ICommand> loadCommands(IGame game) throws PersistenceException {
        // TODO
        return null;
    }
}
