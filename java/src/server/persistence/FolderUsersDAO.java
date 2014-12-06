package server.persistence;

import shared.model.IUser;
import shared.model.IUserManager;
import java.util.logging.Logger;

/**
 * Created by Spencer Weight - 12/5/2014.
 */
public class FolderUsersDAO extends AbstractFolderDAO implements IUsersDAO {
    private static Logger logger = Logger.getLogger("catanserver");
    
    protected FolderUsersDAO(FolderPersistenceManager manager) throws PersistenceException {
        super(manager, "users");
    }

    /**
     * Adds a user to the user table of the data persistence implementation
     *
     * @param newUser the User to add to the data persistence
     */
    @Override
    public void addUser(IUser newUser) throws PersistenceException {
        writeFile(newUser, getDirectory().resolve(newUser.getUsername() + ".dat"));
    }

    /**
     * Load all of the users stored in the data persistence implementation
     *
     * @return the UserManager object that contains all the users
     */
    @Override
    public IUserManager loadUsers() throws PersistenceException {
        // load all the users files from the directory
        // add the users to a user manager
        // return the user manager

        return null;
    }
}
