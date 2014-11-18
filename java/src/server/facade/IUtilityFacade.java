package server.facade;

import java.util.logging.Level;

/**
 * A facade to support /util operations.
 * Created by Spencer Weight - 11/4/2014
 */
public interface IUtilityFacade {

    /**
     * Changes the logging level of the server
     * Swagger URL Equivalent: /util/changeLogLevel
     *
     * @param lvl is the logging level to be passed to the server
     */
    public void changeLogLevel(Level lvl);
}
