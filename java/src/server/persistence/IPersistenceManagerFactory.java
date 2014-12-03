package server.persistence;

/**
 * Abstract factory for persistence managers.
 */
public interface IPersistenceManagerFactory {
    /**
     * Construct and return a peristence manager.
     * Chooses the persistence plugin based on what is provided on the command line.
     *
     * @param option the name of the persistence plugin, as provided on the command line
     * @return the appropriate persistence manager
     */
    public IPersistenceManager createPersistenceManager(String option);
}
