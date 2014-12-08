package server.plugin;

import server.command.ICommand;
import server.persistence.AbstractPersistenceManager;
import server.persistence.ICommandsDAO;
import server.persistence.PersistenceException;
import shared.model.IGame;
import shared.model.ModelException;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by Wyatt - 12/6/2014.
 */
public class FolderCommandsDAO extends AbstractFolderDAO implements ICommandsDAO {
    public FolderCommandsDAO(FolderPersistenceManager folderPersistenceManager) throws PersistenceException {
        super(folderPersistenceManager, "commands");
    }

    @Override
    public void saveCommand(ICommand command) throws PersistenceException {
        // TODO: implement checkpoint thingy
        String gameFolder = Integer.toString(command.getGame().getID());
        AbstractPersistenceManager.ensureDirectoryExists(getDirectory().resolve(gameFolder));
        writeFile(command, gameFolder, Integer.toString(command.getGame().getModelVersion()));
    }

    @Override
    public void loadCommands(IGame game) throws PersistenceException {
        SortedMap<Integer, ICommand> orderedCommands = new TreeMap<>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(getDirectory().resolve(Integer.toString(game.getID())))) {
            // read in all of the commands
            for (Path path : directoryStream) {
                ICommand command = readFile(path);
                orderedCommands.put(Integer.parseInt(path.getFileName().toString()), command);
            }
        }
        catch (IOException e) {
            throw new PersistenceException("Failed reading command.", e);
        }

        // execute commands on the game in order
        // TODO: the commands will be executed here
        try {
            for (ICommand command : orderedCommands.values()) {
                command.setGameAndPlayers(game);
                command.execute();
            }
        }
        catch (ModelException e) {
            throw new PersistenceException("Failed executing command.", e);
        }
    }
}
