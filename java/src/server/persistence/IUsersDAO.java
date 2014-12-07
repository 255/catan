package server.persistence;

import shared.model.IUser;
import shared.model.IUserManager;

/**
 * The Database Access Object for storing and loading user accounts.
 */
public interface IUsersDAO {
    /**
     * Adds a user to the user table of the data persistence implementation
     *
     * @param newUser the User to add to the data persistence
     */
    public void addUser(IUser newUser) throws PersistenceException;

    /**
     * Load all of the users stored in the data persistence implementation
     *
     * @return the UserManager object that contains all the users
     */
    public IUserManager loadUsers() throws PersistenceException;
}
