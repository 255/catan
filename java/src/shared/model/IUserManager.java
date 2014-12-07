package shared.model;

/**
 * Interface that manages all the users that are registered on the server
 *
 * @author StevenBarnett
 */
public interface IUserManager {

    /**
     * Creates and stores a new user
     *
     * @param username username of the new user
     * @param password password of the new user
     * @return bool indicating that the operation was successful
     */
    public IUser createUser(String username, String password);

    /**
     * Checks that a user exists on the server
     *
     * @param username value to use when checking against usernames already on the server
     * @return bool indicating whether the user already exists
     */
    public boolean doesUserExist(String username);

    /**
     * Checks that a user exists on the server. If so,
     * returns that user object in order to log
     * the user into a specified game.
     *
     * @param username username of user attempting to log in
     * @param password password of user attempting to log in
     * @return user object if the user exists, otherwise returns null
     */
    public IUser loginUser(String username, String password);

    /**
     * Get user by ID (from cookie).
     * This throws an exception if the user does not exist
     * @param id the user's ID
     * @return the user
     */
    public IUser getUser(int id) throws ModelException;

    /**
     * Add a complete user object that was loaded from disk.
     * Also updates the next ID of the user manager to a valid value.
     * @param user the user object to add to the UserManager
     * @exception ModelException if the user is a duplicate
     */
    public void loadUser(IUser user) throws ModelException;
}
