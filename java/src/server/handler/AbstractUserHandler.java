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
        if (responseData == null) {
            exch.getResponseHeaders().add("Content-type", "text/plain");

            // write an error string
            try (OutputStreamWriter responseBody = new OutputStreamWriter(exch.getResponseBody())) {
                responseBody.write("Invalid user credentials were supplied.");
            }

            exch.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
        }
        else {
            // TODO: THIS USER COOKIE WILL NOT BE CORRECTLY FORMATTED! IMPLEMENT THIS
            HttpCookie userCookie = new HttpCookie("catan.user", responseData.toString());
            exch.getResponseHeaders().add("Set-cookie", userCookie.toString() + ";path=/;");
            exch.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        }
    }}
