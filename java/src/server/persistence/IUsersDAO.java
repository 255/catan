package server.persistence;

import shared.model.IUser;
import shared.model.IUserManager;

/**
 * Created by Spencer Weight - 12/1/2014.
 */
public interface IUsersDAO {
    /**
     * Adds a user to the user table of the data persistence implementation
     *
     * @param newUser the User to add to the data persistence
     */
    public void add(IUser newUser);

    /**
     * Load all of the users stored in the data persistence implementation
     *
     * @return the UserManager object that contains all the users
     */
    public IUserManager load();
}
