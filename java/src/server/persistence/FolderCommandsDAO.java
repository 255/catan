package server.persistence;

import server.command.ICommand;
import shared.model.IGame;

import java.util.List;

/**
 * Created by Wyatt on 12/6/2014.
 */
public class FolderCommandsDAO extends AbstractFolderDAO implements ICommandsDAO {
    public FolderCommandsDAO(FolderPersistenceManager folderPersistenceManager) throws PersistenceException {
        super(folderPersistenceManager, "commands");
    }

    @Override
    public void saveCommand(ICommand command) {
        // TODO
    }

    @Override
    public List<ICommand> loadCommands(IGame game) {
        // TODO
        return null;
    }
}
