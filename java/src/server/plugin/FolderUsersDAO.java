package server.plugin;

import server.persistence.IUsersDAO;
import server.persistence.PersistenceException;
import shared.model.IUser;
import shared.model.IUserManager;
import shared.model.ModelException;
import shared.model.UserManager;

import java.io.File;
import java.util.logging.Logger;

/**
 * Created by Spencer Weight - 12/5/2014.
 */
public class FolderUsersDAO extends AbstractFolderDAO implements IUsersDAO {
    private static Logger logger = Logger.getLogger("catanserver");
    
    public FolderUsersDAO(FolderPersistenceManager manager) throws PersistenceException {
        super(manager, "users");
    }

    /**
     * Adds a user to the user table of the data persistence implementation
     *
     * @param newUser the User to add to the data persistence
     */
    @Override
    public void addUser(IUser newUser) throws PersistenceException {
        assert newUser != null;
        writeFile(newUser, newUser.getUsername() + ".dat");
    }

    /**
     * Load all of the users stored in the data persistence implementation
     *
     * @return the UserManager object that contains all the users
     */
    @Override
    public IUserManager loadUsers() throws PersistenceException {
        // create an empty user manager
        IUserManager um = new UserManager();

        // get user files from the directory
        File folder = new File(getDirectory().toString());
        File[] userFiles = folder.listFiles();

        if (userFiles == null) {
            throw new PersistenceException("Failed to list user files.");
        }

        // iterate through the user files and add them to the user manager
        for(File f : userFiles) {
            IUser u = readFile(f.toPath());
            try {
                um.loadUser(u);
            }
            catch (ModelException e) {
                throw new PersistenceException("Failed loading user.", e);
            }
        }

        // return the user manager
        return um;
    }
}
