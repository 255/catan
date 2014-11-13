package server.facade;

import shared.communication.CredentialsParams;
import shared.model.IUser;
import shared.model.IUserManager;

/**
 * Created by Spencer Weight - 11/5/2014.
 */
public class UserFacade implements IUserFacade{
    private IUserManager m_userManager;

    public UserFacade(IUserManager userManager) {
        m_userManager = userManager;
    }

    /**
     * Used to create a login cookie and return it to the user
     * Swagger URL Equivalent: /user/login
     *
     * @param creds is the set of credentials to be used for logging in
     * @return boolean containing true or false depending on if the login was successful
     */
    @Override
    public IUser login(CredentialsParams creds) {
        return m_userManager.loginUser(creds.username, creds.password);
    }

    /**
     * Used to register a new user and then create a cookie to log them in
     * Swagger URL Equivalent: /user/register
     *
     * @param creds is the set of credentials to be used for registering/logging in
     * @return boolean containing true or false depending on if the login was successful
     */
    @Override
    public IUser register(CredentialsParams creds) {
        return null;
    }
}
