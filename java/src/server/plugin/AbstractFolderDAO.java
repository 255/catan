package server.plugin;

import server.persistence.AbstractPersistenceManager;
import server.persistence.PersistenceException;
import server.plugin.FolderPersistenceManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Handle interactions with the folder persistence implementation.
 */
public abstract class AbstractFolderDAO {
    private FolderPersistenceManager m_persistenceManager;
    private Path m_dataDir;

    protected AbstractFolderDAO(FolderPersistenceManager manager, String subdirectory) throws PersistenceException {
        m_persistenceManager = manager;

        m_dataDir = m_persistenceManager.getRootDirectory().resolve(subdirectory);
    }

    protected Path getDirectory() throws PersistenceException {
        AbstractPersistenceManager.ensureDirectoryExists(m_dataDir);
        return m_dataDir;
    }

    protected FolderPersistenceManager getPersistenceManager() {
        return m_persistenceManager;
    }

    /**
     * Delete all of the data controlled by this DAO.
     */
    public void clear() throws PersistenceException {
        // TODO: support subdirectories in commands DAO
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(getDirectory())) {
            for (Path path : directoryStream) {
                // delete everything, ignoring subfolders
                if (Files.isRegularFile(path)) {
                    Files.delete(path);
                }
            }
        }
        catch (IOException e) {
            throw new PersistenceException("Failed to delete persistence data.", e);
        }
    }

    /**
     * Serialize an object to a file in the DAO's working directory.
     * @param object the object to serialize
     * @param fileOrSubdirectroyName the file name or subdirectory of the file
     * @param fileOrSubdirectoryNames additional (optional) subdirectories -- the last arg must be the file name
     * @throws PersistenceException if writing the object fails
     */
    protected void writeFile(Object object, String fileOrSubdirectroyName, String... fileOrSubdirectoryNames) throws PersistenceException {
        Path file = getDirectory().resolve(Paths.get(fileOrSubdirectroyName, fileOrSubdirectoryNames));
        writeFile(object, file);
    }

    /**
     * Serialize an object to a file.
     * @param object the object to serialize
     * @param file the file where it should be put
     * @throws PersistenceException if writing the object failes
     */
    protected static void writeFile(Object object, Path file) throws PersistenceException {
        try (ObjectOutputStream writer = new ObjectOutputStream(Files.newOutputStream(file))) {
            writer.writeObject(object);
        }
        catch (IOException e) {
            throw new PersistenceException("Failed serializing object.", e);
        }
    }

    /**
     * Read a serialized object from a file.
     * @param file the file to read
     * @param <T> the type of the object to cast (usually inferred)
     * @return the object cast into the correct type
     * @throws PersistenceException
     */
    @SuppressWarnings("unchecked")
    protected static <T> T readFile(Path file) throws PersistenceException {
        try (ObjectInputStream reader = new ObjectInputStream(Files.newInputStream(file))) {
            return (T) reader.readObject();
        }
        catch (ClassNotFoundException | IOException e) {
            throw new PersistenceException("Failed reading the file " + file + " from disk.", e);
        }
    }
}
