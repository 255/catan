package server.facade;

import java.util.logging.Level;

/**
 * Created by Spencer Weight - 11/5/2014.
 */
public class UtilityFacade implements IUtilityFacade{
    /**
     * Changes the logging level of the server
     * Swagger URL Equivalent: /util/changeLogLevel
     *
     * @param lvl is the logging level to be passed to the server
     */
    @Override
    public void changeLogLevel(Level lvl) {

    }
}