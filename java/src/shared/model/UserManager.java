package shared.model;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by jeffreybacon on 11/11/14.
 */
public class UserManager implements IUserManager {
    private int m_nextUserId;
    private List<IUser> m_users;

    public UserManager() {
        m_nextUserId = 0;
        m_users = new ArrayList<IUser>();
    }

    @Override
    public boolean createUser(String username, String password) {
        m_users.add(new User(username, password, m_nextUserId));
        m_nextUserId++;
        return true;
    }

    @Override
    public boolean doesUserExist(String username) {
        for (IUser user : m_users) {
            if(user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public IUser loginUser(String username, String password) {
        for (IUser user : m_users) {
            if(user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}
