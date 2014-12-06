package server.persistence;

import shared.model.GameManager;
import shared.model.IGame;
import shared.model.IGameManager;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Database access object for folders.
 */
public class FolderGamesDAO extends AbstractFolderDAO implements IGamesDAO {
    FolderGamesDAO(FolderPersistenceManager manager) throws PersistenceException {
        super(manager, "games");
    }

    @Override
    public void saveGame(IGame game) throws PersistenceException {
        writeFile(game, getDirectory().resolve(Integer.toString(game.getID())));
    }

    @Override
    public IGameManager loadGames() throws PersistenceException {
        IGameManager gameManager = new GameManager();

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(getDirectory())) {
            for (Path path : directoryStream) {
                gameManager.loadGame(AbstractFolderDAO.<IGame>readFile(path));
            }
        } catch (IOException e) {
            throw new PersistenceException(e);
        }

        return gameManager;
    }
}
