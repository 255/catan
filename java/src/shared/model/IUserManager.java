package shared.model;

/**
 * @author StevenBarnett
 */
public interface IUserManager {

    public boolean createUser(String username, String password);

    public boolean doesUserExist(String username);

    public IUser loginUser(String username, String password);
}
