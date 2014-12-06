package server.persistence;

import org.junit.Test;
import shared.model.IUser;
import shared.model.IUserManager;
import shared.model.User;

import static org.junit.Assert.assertTrue;

/**
 * Created by Spencer Weight - 12/6/2014.
 */
public class DAOTests {
    @Test
    public void folderUserDAOtest() {
        boolean userWritten = true;

        try {
            FolderPersistenceManager pm = new FolderPersistenceManager(10);
            IUsersDAO folderUser = new FolderUsersDAO(pm);

            IUser myBuddy = new User("myBuddy", "abc123", 12);
            folderUser.addUser(myBuddy);

        } catch (PersistenceException e) {
            System.out.println("Failed to write to disk\n" + e.getMessage());
            e.printStackTrace();
            userWritten = false;
        }

        assertTrue("The user file was not written", userWritten);

        boolean userRead = true;
        boolean userExists = true;

        try {
            FolderPersistenceManager pm = new FolderPersistenceManager(10);
            IUsersDAO folderUser = new FolderUsersDAO(pm);

            IUserManager um = folderUser.loadUsers();

            userExists = um.doesUserExist("myBuddy");
        } catch (PersistenceException e) {
            System.out.println("Failed to read user from disk\n" + e.getMessage());
            e.printStackTrace();
            userRead = false;
        }

        assertTrue("The user file was not read", userRead);
        assertTrue("The user file was read, but the user manager repoted the user as not existing", userExists);
    }
}
