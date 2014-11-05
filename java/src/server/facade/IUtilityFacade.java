package server.facade;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Spencer Weight - 11/4/2014
 */
public interface IUtilityFacade {

    /**
     * Changes the logging level of the server
     * Swagger URL Equivalent: /util/changeLogLevel
     */
    // TODO define the inputs and outputs for changeLogLevel()
    public void changeLogLevel(Level lvl);
}
