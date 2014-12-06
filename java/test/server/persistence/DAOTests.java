package server.persistence;

import org.junit.Test;
import shared.model.IUser;
import shared.model.User;

import static org.junit.Assert.assertTrue;

/**
 * Created by Spencer Weight - 12/6/2014.
 */
public class DAOTests {
    @Test
    public void folderUserDAOtest() {
        boolean passed = true;

        try {
            FolderPersistenceManager pm = new FolderPersistenceManager(10);
            IUsersDAO folderUser = new FolderUserDAO(pm);

            IUser myBuddy = new User("myBuddy", "abc123", 12);
            folderUser.addUser(myBuddy);

        } catch (PersistenceException e) {
            System.out.println("Failed to write to disk\n" + e.getMessage());
            e.printStackTrace();
            passed = false;
        }

        assertTrue(passed);
    }
}
