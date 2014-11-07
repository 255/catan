package server.handler;

import shared.communication.AbstractGameParams;
import shared.model.IGame;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.List;

/**
 * HTTP handlers that check for a user cookie and game cookie.
 */
public abstract class AbstractInGameHandler<ReqType extends AbstractGameParams, FacadeType> extends AbstractHandler<ReqType, IGame, FacadeType> {
    /**
     * Setup a new RequestHandler.
     */
    public AbstractInGameHandler(Class<ReqType> reqTypeClass, FacadeType facade) {
        super(reqTypeClass, facade);
    }

    /**
     * Look for the user and game cookies.
     * @param cookies the list of cookies the client has set
     * @param requestData the request sent by the client (in case cookie info should be attached to it)
     * @throws IOException
     */
    @Override
    protected void processRequestCookies(List<HttpCookie> cookies, ReqType requestData) throws IOException {
        boolean foundUser = false;
        boolean foundGame = false;

        for (HttpCookie cookie : cookies) {
            if (cookie.getName().equalsIgnoreCase("catan.user")) {
                requestData.setUserId(readUserID(cookie.getValue()));
                foundUser = true;
            }
            else if (cookie.getName().equalsIgnoreCase("catan.game")) {
                requestData.setGameId(Integer.parseInt(cookie.getValue()));
                foundGame = true;
            }
        }

        if (!foundUser || !foundGame) {
            throw new IOException("Failed to find required cookies.");
        }
    }
}

