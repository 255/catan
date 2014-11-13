package server.handler;

import com.sun.net.httpserver.HttpExchange;
import server.command.IllegalCommandException;
import server.facade.IJoinGameFacade;

import shared.communication.JoinGameRequestParams;
import shared.model.ModelException;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpCookie;
import java.net.HttpURLConnection;

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
        if (responseData != null) {
            HttpCookie gameCookie = new HttpCookie("catan.game", responseData.toString());

            exch.getResponseHeaders().add("Set-cookie", gameCookie.toString() + ";path=/;");
            exch.sendResponseHeaders(HttpURLConnection.HTTP_OK, -1);
        }
        else {
            // write an error string
            try (OutputStreamWriter responseBody = new OutputStreamWriter(exch.getResponseBody())) {
                exch.getResponseHeaders().add("Content-type", "text/plain");
                responseBody.write("Cannot join game.");
                exch.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            }
        }
    }

    /**
     * Extract the data from the request and return the result.
     *
     * @param requestData the data from the HTTP request
     * @return the data to return to the requester
     * @throws MissingCookieException if there was an error
     *                                in which case handle() sends back an empty error (500) response
     */
    @Override
    protected Integer exchangeData(JoinGameRequestParams requestData) throws MissingCookieException, IllegalCommandException, ModelException {
        return getFacade().join(requestData);
    }

    @Override
    protected void processRequestCookies(CookieJar cookies, JoinGameRequestParams requestData) throws IOException, MissingCookieException {
        for (Cookie cookie : cookies) {
            if (cookie.nameIs("catan.user")) {
                requestData.setUserId(readUserID(cookie.getValue()));
                return;
            }
        }

        throw new MissingCookieException("The user cookie is not set.");
    }
}

