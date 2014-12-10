package server.persistence;

import org.junit.Test;
import server.plugin.*;
import shared.model.*;

import java.util.Collection;

import static org.junit.Assert.assertTrue;

/**
 * Created by Spencer Weight - 12/6/2014.
 */
public class DAOTests {
    @Test
    public void folderUserDAOtest() {
        boolean usersWritten = true;

        try {
            FolderPersistenceManager pm = new FolderPersistenceManager(10);
            IUsersDAO folderUser = new FolderUsersDAO(pm);

            IUser myBuddy = new User("myBuddy", "abc123", 12);
            IUser myBuddy1 = new User("myBuddy1", "abc123", 13);
            IUser myBuddy2 = new User("myBuddy2", "abc123", 14);
            IUser myBuddy3 = new User("myBuddy3", "abc123", 15);
            folderUser.addUser(myBuddy);
            folderUser.addUser(myBuddy1);
            folderUser.addUser(myBuddy2);
            folderUser.addUser(myBuddy3);

        } catch (PersistenceException e) {
            System.out.println("Failed to write to disk\n" + e.getMessage());
            e.printStackTrace();
            usersWritten = false;
        }

        assertTrue("The user file was not written", usersWritten);

        boolean userRead = true;
        boolean userExists = true;
        boolean userExists1 = true;
        boolean userExists2 = true;
        boolean userExists3 = true;

        try {
            FolderPersistenceManager pm = new FolderPersistenceManager(10);
            IUsersDAO folderUser = new FolderUsersDAO(pm);

            IUserManager um = folderUser.loadUsers();

            userExists = um.doesUserExist("myBuddy");
            userExists1 = um.doesUserExist("myBuddy1");
            userExists2 = um.doesUserExist("myBuddy2");
            userExists3 = um.doesUserExist("myBuddy3");
        } catch (PersistenceException e) {
            System.out.println("Failed to read user from disk\n" + e.getMessage());
            e.printStackTrace();
            userRead = false;
        }

        assertTrue("The user file was not read", userRead);
        assertTrue("The user file was read, but the user manager repoted some users as not existing",
                userExists && userExists1 && userExists2 && userExists3);
    }

    @Test
    public void sqlUserDAOtest() {
        boolean usersWritten = true;

        try {
            SQLitePersistenceManager pm = new SQLitePersistenceManager(10);

            pm.clear();

            pm.startTransaction();

            IUsersDAO sqlUser = new SQLiteUsersDAO(pm);

            IUser myBuddy = new User("myBuddy", "abc123", 12);
            IUser myBuddy1 = new User("myBuddy1", "abc123", 13);
            IUser myBuddy2 = new User("myBuddy2", "abc123", 14);
            IUser myBuddy3 = new User("myBuddy3", "abc123", 15);
            sqlUser.addUser(myBuddy);
            sqlUser.addUser(myBuddy1);
            sqlUser.addUser(myBuddy2);
            sqlUser.addUser(myBuddy3);

            pm.endTransaction(true);

        } catch (PersistenceException e) {
            System.out.println("Failed to write to SQLite database\n" + e.getMessage());
            e.printStackTrace();
            usersWritten = false;
        }

        assertTrue("The user file was not written", usersWritten);

        boolean userRead = true;
        boolean userExists = true;
        boolean userExists1 = true;
        boolean userExists2 = true;
        boolean userExists3 = true;

        try {
            SQLitePersistenceManager pm = new SQLitePersistenceManager(10);

            pm.startTransaction();

            IUsersDAO sqlUser = new SQLiteUsersDAO(pm);

            IUserManager um = sqlUser.loadUsers();

            userExists = um.doesUserExist("myBuddy");
            userExists1 = um.doesUserExist("myBuddy1");
            userExists2 = um.doesUserExist("myBuddy2");
            userExists3 = um.doesUserExist("myBuddy3");

            pm.endTransaction(true);

        } catch (PersistenceException e) {
            System.out.println("Failed to read user from disk\n" + e.getMessage());
            e.printStackTrace();
            userRead = false;
        }

        assertTrue("The user file was not read", userRead);
        assertTrue("The user file was read, but the user manager repoted some users as not existing",
                userExists && userExists1 && userExists2 && userExists3);
    }

    @Test
    public void sqlGamesDAOTest() {
        boolean gamesWritten = true;

        try {
            SQLitePersistenceManager pm = new SQLitePersistenceManager(10);
            pm.startTransaction();

            IGamesDAO sqlGames = pm.createGamesDAO();

            IGame game1 = new Game();
            IGame game2;
            IGame game3;
            try {
                game2 = new Game("My Game", 2, true, true, true);
                game3 = new Game("Another Game", 3, false, false, false);
            } catch (ModelException e) {
                throw new PersistenceException();
            }

            sqlGames.saveGame(game1);
            sqlGames.saveGame(game2);
            sqlGames.saveGame(game3);

            pm.endTransaction(true);

        } catch (PersistenceException e) {
            System.out.println("Failed to write to SQLite database\n" + e.getMessage());
            e.printStackTrace();
            gamesWritten = false;
        }

        assertTrue("The games file was not written", gamesWritten);

        boolean gamesRead = true;

        try {
            SQLitePersistenceManager pm = new SQLitePersistenceManager(10);
            pm.startTransaction();

            IGamesDAO sqlGames = pm.createGamesDAO();

            IGameManager gm = sqlGames.loadGames();

            Collection<IGame> games = gm.listGames();

//            gameExists = gm.doesUserExist("myBuddy");
//            userExists1 = um.doesUserExist("myBuddy1");
//            userExists2 = um.doesUserExist("myBuddy2");
//            userExists3 = um.doesUserExist("myBuddy3");

            pm.endTransaction(true);

        } catch (PersistenceException e) {
            System.out.println("Failed to read user from disk\n" + e.getMessage());
            e.printStackTrace();
            gamesRead = false;
        }
    }

}
