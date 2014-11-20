package server.handler;

import server.facade.IMovesFacade;
import shared.communication.IGameParams;

/**
 * A handler for all /moves commands.
 * <p/>
 * Since exactly one line of code needs to be defined to have a complete move handler, these handlers will be
 * defined inline where they are created in Server.java.
 */
public abstract class AbstractMovesHandler<ReqType extends IGameParams> extends AbstractInGameHandler<ReqType, IMovesFacade> {
    /**
     * Setup a new handler for a move.
     *
     * @param reqTypeClass the class of the request -- Java is dumb so this has to be provided along with the generic param
     * @param facade       the facade to use
     */
    public AbstractMovesHandler(Class<ReqType> reqTypeClass, IMovesFacade facade) {
        super(reqTypeClass, facade);
    }
}
