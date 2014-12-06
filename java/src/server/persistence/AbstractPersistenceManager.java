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

        if (!Files.isDirectory(ROOT_DIR)) {
            try {
                Files.createDirectory(ROOT_DIR);
            }
            catch (IOException e) {
                throw new PersistenceException("Failed to create the root directory for persistence.", e);
            }
        }
    }

    /**
     * Get the root directory in which this persistence manager should store its data.
     * @return the root directory Path
     * @throws PersistenceException if the root directory does not exist and cannot be created
     */
    Path getRootDirectory() {
        return ROOT_DIR;
    }

    public final int getCommandsBetweenCheckpoints() {
        return m_commandsBetweenCheckpoints;
    }
}
