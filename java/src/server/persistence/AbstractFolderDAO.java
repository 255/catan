package server.persistence;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Handle interactions with the folder persistence implementation.
 */
public abstract class AbstractFolderDAO<T> {
    private FolderPersistenceManager m_persistenceManager;
    private Path m_dataDir;

    protected AbstractFolderDAO(FolderPersistenceManager manager, String subdirectory) throws PersistenceException {
        m_persistenceManager = manager;

        m_dataDir = m_persistenceManager.getRootDirectory().resolve(subdirectory);

        if (!Files.isDirectory(m_dataDir)) {
            try {
                Files.createDirectory(m_dataDir);
            }
            catch (IOException e) {
                throw new PersistenceException("Cannot create persistence subfolder.", e);
            }
        }
    }

    protected Path getDirectory() {
        return m_dataDir;
    }

    protected static void writeFile(Object object, Path file) throws PersistenceException {
        try (ObjectOutputStream writer = new ObjectOutputStream(Files.newOutputStream(file))) {
            writer.writeObject(object);
        }
        catch (IOException e) {
            throw new PersistenceException("Failed serializing object.", e);
        }
    }

    @SuppressWarnings("unchecked")
    protected static <T> T readFile(Path file) throws PersistenceException {
        try (ObjectInputStream reader = new ObjectInputStream(Files.newInputStream(file))) {
            return (T) reader.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new PersistenceException("Failed reading game from disk.", e);
        }
    }
}
