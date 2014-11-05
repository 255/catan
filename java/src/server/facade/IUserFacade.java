package server.facade;

import shared.communication.CredentialsParams;

/**
 * Created by Spencer Weight - 11/4/2014.
 */
public interface IUserFacade {

    /**
     * Used to create a login cookie and return it to the user
     * Swagger URL Equivalent: /user/login
     */
    //TODO change inputs and outputs for login()
    public boolean login(CredentialsParams creds);

    /**
     * Used to register a new user and then create a cookie to log them in
     * Swagger URL Equivalent: /user/register
     */
    //TODO change inputs and outputs for register()
    public boolean register(CredentialsParams creds);
}
