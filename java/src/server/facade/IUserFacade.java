package server.facade;

import shared.communication.CredentialsParams;

/**
 * Created by Spencer Weight - 11/4/2014.
 */
public interface IUserFacade {

    /**
     * Used to create a login cookie and return it to the user
     * Swagger URL Equivalent: /user/login
     *
     * @param creds is the set of credentials to be used for logging in
     * @return boolean containing true or false depending on if the login was successful
     */
    public boolean login(CredentialsParams creds);

    /**
     * Used to register a new user and then create a cookie to log them in
     * Swagger URL Equivalent: /user/register
     *
     * @param creds is the set of credentials to be used for registering/logging in
     * @return boolean containing true or false depending on if the login was successful
     */
    public boolean register(CredentialsParams creds);
}
