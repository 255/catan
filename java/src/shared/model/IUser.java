package shared.model;

/**
 * Interface that represents a user that is registered on the server
 *
 * @author StevenBarnett
 */
public interface IUser {

    /**
     * Gets the username of the user
     *
     * @return username of user
     */
    public String getUsername();

    /**
     * Gets the password of the user
     *
     * @return password of user
     */
    public String getPassword();

    /**
     * Gets the id of the user. This is also
     * known as the player id
     *
     * @return id of user
     */
    public int getId();
}
