package server.facade;

import server.persistence.IPersistenceManager;
import server.persistence.PersistenceException;
import shared.communication.CredentialsParams;
import shared.model.IUser;
import shared.model.IUserManager;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Spencer Weight - 11/5/2014.
 */
public class UserFacade implements IUserFacade {
    private static Logger logger = Logger.getLogger("catanserver");

    private IUserManager m_userManager;
    private IPersistenceManager m_persistenceManager;

    public UserFacade(IUserManager userManager, IPersistenceManager persistenceManager) {
        m_userManager = userManager;
        m_persistenceManager = persistenceManager;
    }

    /**
     * Used to create a login cookie and return it to the user
     * Swagger URL Equivalent: /user/login
     *
     * @param creds is the set of credentials to be used for logging in
     * @return IUser - the user who logged in
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
     * @return IUser - the user who was registered
     */
    @Override
    public IUser register(CredentialsParams creds) {
        IUser user = m_userManager.createUser(creds.username, creds.password);

        if (user != null) {
            try {
                m_persistenceManager.startTransaction();
                m_persistenceManager.createUsersDAO().addUser(user);
                m_persistenceManager.endTransaction(true);
            }
            catch (PersistenceException e) {
                m_persistenceManager.endTransaction(false);
                logger.log(Level.WARNING, "Failed to store newly registered user.", e);
            }
        }

        return user;
    }
}
