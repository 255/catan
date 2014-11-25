package server.facade;

import client.network.GameAdministrator;
import client.network.IGameAdministrator;
import client.network.NetworkException;
import org.junit.*;
import server.Server;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Spencer Weight - 11/23/2014.
 */
public class UserFacadeTest {

    private static IGameAdministrator m_gameAdmin;
    private static String m_user;
    private static String m_user2;
    private static String m_pass;
    private static String m_pass2;


    @BeforeClass
    public static void setUp() {
        m_gameAdmin = GameAdministrator.getInstance();
        m_user = "testUser";
        m_user2 = "user2";
        m_pass = "abc123";
        m_pass2 = "123abc";

        String[] args = {};
        Server.main(args);
    }

    @AfterClass
    public static void tearDown() {
        m_gameAdmin = null;
        m_user = null;
        m_user2 = null;
        m_pass = null;
        m_pass2 = null;
    }

    @Test
    public void testRegister() {

        boolean successfulRegister = createAUser(m_user, m_pass);

        assertTrue("Register did not succeed", successfulRegister);

        boolean userDoesNotExist = createAUser(m_user, m_pass);

        assertFalse("There is already an existing user with that name", userDoesNotExist);
    }

    @Test
    public void testLogin() {
        boolean login = loginAttempt(m_user, m_pass);

        assertFalse("The user has not been registered and the login " +
                "of the user returned true when it should have been false",
                login);

        boolean userCreated = createAUser(m_user, m_pass);
        login = loginAttempt(m_user, m_pass);

        assertTrue("The user was not created", userCreated);
        assertTrue("The user was unable to login after successfully being registered", login);
    }

    //************************//
    // private helper methods //
    //************************//

    private boolean createAUser(String u, String p) {
        boolean userDoesNotExist = false;
        try {
            userDoesNotExist = m_gameAdmin.register(u, p);
        } catch (NetworkException e) {
            System.out.println(e.getMessage() + " : User failed to register");
        }

        return userDoesNotExist;
    }

    private boolean loginAttempt(String u, String p) {
        boolean successfulLogin = false;
        try {
            successfulLogin = m_gameAdmin.login(m_user, m_pass);
        } catch (NetworkException e) {
            System.out.println(e.getMessage());
        }

        return successfulLogin;
    }
}
