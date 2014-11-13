package shared.model;

/**
 * Created by jeffreybacon on 11/11/14.
 */
public class User implements IUser {
    private String name;
    private transient String m_password;
    private int playerID;

    public User(String username, String password, int id) {
        name = username;
        m_password = password;
        playerID = id;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public String getPassword() {
        return m_password;
    }

    @Override
    public int getId() {
        return playerID;
    }
}
