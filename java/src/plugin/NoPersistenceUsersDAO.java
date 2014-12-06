package plugin;

import server.persistence.IUsersDAO;
import shared.model.IUser;
import shared.model.IUserManager;
import shared.model.UserManager;

/**
 * A UsersDAO that does nothing. It returns an empty UserManager for loadUsers.
 */
public class NoPersistenceUsersDAO implements IUsersDAO {
    @Override
    public void add(IUser newUser) {
    }

    @Override
    public IUserManager loadUsers() {
        return new UserManager();
    }
}
