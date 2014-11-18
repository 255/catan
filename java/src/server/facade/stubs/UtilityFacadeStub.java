package server.facade.stubs;

import server.facade.IUtilityFacade;

import java.util.logging.Level;

/**
 * Created by Spencer Weight - 11/17/2014.
 */
public class UtilityFacadeStub implements IUtilityFacade {
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
