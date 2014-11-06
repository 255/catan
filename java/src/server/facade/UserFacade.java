package server.facade;

import shared.communication.CredentialsParams;

/**
 * Created by Spencer Weight - 11/5/2014.
 */
public class UserFacade implements IUserFacade{
    /**
     * Used to create a login cookie and return it to the user
     * Swagger URL Equivalent: /user/login
     *
     * @param creds is the set of credentials to be used for logging in
     * @return boolean containing true or false depending on if the login was successful
     */
    @Override
    public boolean login(CredentialsParams creds) {
        return false;
    }

    /**
     * Used to register a new user and then create a cookie to log them in
     * Swagger URL Equivalent: /user/register
     *
     * @param creds is the set of credentials to be used for registering/logging in
     * @return boolean containing true or false depending on if the login was successful
     */
    @Override
    public boolean register(CredentialsParams creds) {
        return false;
    }
}
