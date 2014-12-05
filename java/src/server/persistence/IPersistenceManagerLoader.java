package server.persistence;

/**
 * Manage the persistence plugins and construct persistence managers.
 * Instances of this class will be configured with the number of commands
 * to go between updates to the game's checkpoint, and this parameter will
 * be passed to the persistence managers it constructs.
 */
public interface IPersistenceManagerLoader {
    /**
     * Load the specified persistence manager plugin.
     * Chooses the persistence plugin based on what is provided on the command line.
     *
     * @param option the name of the persistence plugin, as provided on the command line
     */
    public void loadPersistencePlugin(String option) throws InvalidPluginException;

    /**
     * Construct and return a persistence manager.
     * If a persistence manager plugin has not been loaded, then a stub manager that does nothing is returned.
     *
     * @return an instance of the persistence manager class
     */
    public IPersistenceManager createPersistenceManager() throws InvalidPluginException;
}
