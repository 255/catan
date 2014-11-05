package server.facade;

import shared.communication.CredentialsParams;

/**
 * Created by Spencer Weight - 11/5/2014.
 */
public class UserFacade implements IUserFacade{
    /**
     * Used to create a login cookie and return it to the user
     * Swagger URL Equivalent: /user/login
     */
    @Override
    public boolean login(CredentialsParams creds) {
        return false;
    }

    /**
     * Used to register a new user and then create a cookie to log them in
     * Swagger URL Equivalent: /user/register
     */
    @Override
    public boolean register(CredentialsParams creds) {
        return false;
    }
}
