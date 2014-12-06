package server.persistence;

import shared.model.IUser;
import shared.model.IUserManager;

/**
 * Created by Spencer Weight - 12/5/2014.
 */
public class FolderUserDAO extends AbstractFolderDAO<IUser> implements IUsersDAO {
    protected FolderUserDAO(FolderPersistenceManager manager) throws PersistenceException {
        super(manager, "users");
    }

    /**
     * Adds a user to the user table of the data persistence implementation
     *
     * @param newUser the User to add to the data persistence
     */
    @Override
    public void addUsers(IUser newUser) {
        // write a file with the serialized data for ONE user to the disk
        // the working directory is ./Catan/Java/dist
        // the users folder will be in the WorkingDir/Persistence/FolderStorage

        // look at the Blob interface
    }

    /**
     * Load all of the users stored in the data persistence implementation
     *
     * @return the UserManager object that contains all the users
     */
    @Override
    public IUserManager loadUsers() {
        // load all the users files from the directory
        // add the users to a user manager
        // return the user manager

        return null;
    }
}
