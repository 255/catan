package server.handler;

import com.sun.net.httpserver.HttpExchange;
import server.ServerException;
import server.command.IllegalCommandException;
import server.facade.IJoinGameFacade;

import shared.communication.JoinGameRequestParams;

import java.io.IOException;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.util.List;

/**
 * The handler for join games requests. This is the only handler that checks only the user cookie.
 * The join handler also sets the game cookie.
 */
public class JoinHandler extends AbstractHandler<JoinGameRequestParams, Integer, IJoinGameFacade> {
    /**
     * Setup a new RequestHandler.
     */
    public JoinHandler(IJoinGameFacade joinGameFacade) {
        super(JoinGameRequestParams.class, joinGameFacade);
    }

    @Override
    protected void generateResponse(HttpExchange exch, Integer responseData) throws IOException {
        if (responseData == null) {
            exch.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
        }
        else {
            HttpCookie gameCookie = new HttpCookie("catan.game", responseData.toString());
            exch.getResponseHeaders().add("Set-cookie", gameCookie.toString() + ";path=/;");
            exch.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        }
    }

    /**
     * Extract the data from the request and return the result.
     *
     * @param requestData the data from the HTTP request
     * @return the data to return to the requester
     * @throws server.ServerException if there was an error
     *                                in which case handle() sends back an empty error (500) response
     */
    @Override
    protected Integer exchangeData(JoinGameRequestParams requestData) throws ServerException, IllegalCommandException {
        return getFacade().join(requestData);
    }

    @Override
    protected void processRequestCookies(List<HttpCookie> cookies, JoinGameRequestParams requestData) throws IOException {
        for (HttpCookie cookie : cookies) {
            if (cookie.getName().equalsIgnoreCase("catan.user")) {
                requestData.setUserId(readUserID(cookie.getValue()));
                return;
            }
        }

        throw new IOException("Failed to find user cookie.");
    }
}

