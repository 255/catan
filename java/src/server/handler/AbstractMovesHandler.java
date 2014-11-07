package server.handler;

import server.facade.IMovesFacade;
import shared.communication.AbstractGameParams;

/**
 * A handler for all /moves commands.
 */
public abstract class AbstractMovesHandler<ReqType extends AbstractGameParams> extends AbstractInGameHandler<ReqType, IMovesFacade> {
    /**
     * Setup a new handler for a move.
     *
     * @param reqTypeClass the class of the request -- Java is dumb so this has to be provided along with the generic param
     * @param facade the facade to use
     */
    public AbstractMovesHandler(Class<ReqType> reqTypeClass, IMovesFacade facade) {
        super(reqTypeClass, facade);
    }
}
