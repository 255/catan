package server.handler;

import com.sun.net.httpserver.HttpExchange;
import server.facade.IUserFacade;
import shared.communication.CredentialsParams;
import shared.model.IUser;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpCookie;
import java.net.HttpURLConnection;

/**
 * A handler for registering and logging in.
 * @see server.Server#setupHandlers(com.sun.net.httpserver.HttpServer)
 */
public abstract class AbstractUserHandler extends AbstractHandler<CredentialsParams, IUser, IUserFacade> {
    /**
     * Setup a new LoginHandler.
     */
    public AbstractUserHandler(IUserFacade facade) {
        super(CredentialsParams.class, facade);
    }

    @Override
    protected void generateResponse(HttpExchange exch, IUser responseData) throws IOException {
        if (responseData != null) {
            HttpCookie userCookie = new HttpCookie("catan.user", gson.toJson(responseData));
            exch.getResponseHeaders().add("Set-cookie", userCookie.toString() + ";path=/;");

            // write success message
            exch.getResponseHeaders().add("Content-type", "text/plain");
            try (OutputStreamWriter responseBody = new OutputStreamWriter(exch.getResponseBody())) {
                responseBody.write("Welcome, " + responseData.getUsername() + ".");
                exch.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            }
        }
        else {
            exch.getResponseHeaders().add("Content-type", "text/plain");

            // write an error string
            try (OutputStreamWriter responseBody = new OutputStreamWriter(exch.getResponseBody())) {
                responseBody.write("Invalid user credentials were supplied.");
                exch.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            }
        }
    }}
