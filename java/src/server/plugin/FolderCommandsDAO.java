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
        // write the new command to the disk
        String gameFolder = Integer.toString(command.getGame().getID());
        AbstractPersistenceManager.ensureDirectoryExists(getDirectory().resolve(gameFolder));
        writeFile(command, gameFolder, Integer.toString(command.getGame().getModelVersion()));
        saveCheckpoint(command);
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
        try {
            for (ICommand command : orderedCommands.values()) {
                command.setGameAndPlayers(game);
                command.execute();
            }
        }
        catch (ModelException e) {
            throw new PersistenceException("Failed executing command.", e);
        }

        // TODO: implement checkpoint thingy loading - needs code review
        // after all the commands have been applied to the game
        // save the game and clear the folder
        saveGameAndClear(game);
    }

    private void saveCheckpoint(ICommand command) throws PersistenceException {
        // TODO: implement checkpoint thingy saving - needs code review
        // check the number of commands in the commands directory for the game
        DirectoryStream<Path> directoryStream = null;
        try {
            directoryStream = Files.newDirectoryStream(getDirectory().resolve(Integer.toString(command.getGame().getID())));
        } catch (IOException e) {
            throw new PersistenceException("Failed to read in the commands directory", e);
        }

        // if the number of saved commands is N or greater
        int numCommands = 0;
        for(Path p : directoryStream) {
            numCommands++;
        }

        // save game and clear the folder
        if(numCommands >= getPersistenceManager().getCommandsBetweenCheckpoints()) {
            saveGameAndClear(command);
        }
    }

    private void saveGameAndClear(ICommand command) throws PersistenceException {
        saveGameAndClear(command.getGame());
    }

    private void saveGameAndClear(IGame game) throws PersistenceException {
        // save the current game to disk
        getPersistenceManager().createGamesDAO().saveGame(game);

        // clear the commands folder
        clear();
    }
}
