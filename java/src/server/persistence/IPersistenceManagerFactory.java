package server.persistence;

/**
 * Created by Spencer Weight - 12/1/2014.
 */
public interface IPersistenceManagerFactory {

    /**
     * Handles the command line options that are passed in
     *
     * @param option the command line option to parse
     */
    public void parseCommandLineOption(String option);
}
