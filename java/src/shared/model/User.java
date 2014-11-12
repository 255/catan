package shared.model;

/**
 * Created by jeffreybacon on 11/11/14.
 */
public class User implements IUser {
    private String m_username;
    private String m_password;
    private int m_id;

    public User(String username, String password, int id) {
        m_username = username;
        m_password = password;
        m_id = id;
    }

    @Override
    public String getUsername() {
        return m_username;
    }

    @Override
    public String getPassword() {
        return m_password;
    }

    @Override
    public int getId() {
        return m_id;
    }
}
