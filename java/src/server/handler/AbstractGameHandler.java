package server.handler;

import com.sun.net.httpserver.HttpExchange;
import server.ServerException;
import server.facade.JoinGameFacade;
import shared.communication.AbstractGameParams;
import shared.communication.JoinGameRequestParams;
import shared.model.Game;

import java.io.IOException;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.util.List;

/**
 * HTTP handlers that check for a user cookie and game cookie.
 * TODO: may make separate game and move handlers...
 */
public abstract class AbstractGameHandler<ReqType extends AbstractGameParams, FacadeType> extends AbstractRequestHandler<ReqType, Game> {
    private FacadeType m_facade;

    /**
     * Setup a new RequestHandler.
     */
    public AbstractGameHandler(Class<ReqType> reqTypeClass, FacadeType facade) {
        super(reqTypeClass);
        m_facade = facade;
    }

    /**
     * Get the facade used by this game handler.
     * @return the facade
     */
    protected FacadeType getFacade() {
        return m_facade;
    }

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

