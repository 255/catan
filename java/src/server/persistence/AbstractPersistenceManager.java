package server.persistence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A class that provides common services to persistence managers.
 * Forces AbstractPersistenceManagers to have a constructor that handles commands between checkpoints.
 */
public abstract class AbstractPersistenceManager implements IPersistenceManager {
    private static final Path ROOT_DIR = Paths.get("data");
    private int m_commandsBetweenCheckpoints;

    protected AbstractPersistenceManager(int commandsBetweenCheckpoints) throws PersistenceException {
        m_commandsBetweenCheckpoints = commandsBetweenCheckpoints;

        ensureDirectoryExists(ROOT_DIR);
    }

    /**
     * Check if a directory exists. If it does not, create it.
     * @param directory the directory to check
     * @throws PersistenceException if the directory does not exist and cannot be created
     */
    public static void ensureDirectoryExists(Path directory) throws PersistenceException {
        if (!Files.isDirectory(directory)) {
            try {
                Files.createDirectory(directory);
            }
            catch (IOException e) {
                throw new PersistenceException("Failed to create persistence subdirectory: " + directory, e);
            }
        }
    }

    /**
     * Get the root directory in which this persistence manager should store its data.
     * @return the root directory Path
     * @throws PersistenceException if the root directory does not exist and cannot be created
     */
    public Path getRootDirectory() {
        return ROOT_DIR;
    }

    public final int getCommandsBetweenCheckpoints() {
        return m_commandsBetweenCheckpoints;
    }
}
