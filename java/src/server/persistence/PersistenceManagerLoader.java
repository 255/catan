package server.persistence;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

/**
 * @author Wyatt
 */
public class PersistenceManagerLoader implements IPersistenceManagerLoader {
    private static Logger logger = Logger.getLogger("catanserver");

    private Class<? extends IPersistenceManager> m_persistenceManagerClass;

    public PersistenceManagerLoader() {
        // default to no persistence
        m_persistenceManagerClass = NoPersistenceManager.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void loadPersistencePlugin(String option) throws InvalidPluginException {
        logger.entering("server.persistence.PersistenceManagerLoader", "loadPersistencePlugin");
        if (option == null) return;

        try {
            Path jarFile = Paths.get("plugins", option.concat(".jar"));
            if (!Files.isRegularFile(jarFile)) {
                throw new InvalidPluginException("Cannot read the file \"" + jarFile.toAbsolutePath() + "\".");
            }

            ClassLoader loader = URLClassLoader.newInstance(new URL[]{jarFile.toUri().toURL()}, getClass().getClassLoader());
            m_persistenceManagerClass = (Class<? extends IPersistenceManager>) loader.loadClass("plugin." + option);

            logger.fine("Using the " + option + " plugin for data persistence.");
        }
        catch (ClassNotFoundException | MalformedURLException e) {
            throw new InvalidPluginException("Error loading the persistence plugin \"" + option + "\".", e);
        }
        finally {
            logger.exiting("server.persistence.PersistenceManagerLoader", "loadPersistencePlugin");
        }
    }

    @Override
    public IPersistenceManager createPersistenceManager() throws InvalidPluginException {
        assert m_persistenceManagerClass != null;

        try {
            return m_persistenceManagerClass.newInstance();
        }
        catch (InstantiationException | IllegalAccessException e) {
            throw new InvalidPluginException("Failed to instantiate the persistence manager.", e);
        }
    }
}
