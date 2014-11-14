package shared.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jeffreybacon on 11/11/14.
 */
public class UserManager implements IUserManager {
    private int m_nextUserId;
    private Map<Integer, IUser> m_users;

    public UserManager() {
        m_nextUserId = 0;
        m_users = new HashMap<>();

        // TODO: REMOVE THIS TEST CODE
        createUser("Sam", "sam");
        createUser("Pete", "pete");
        createUser("Mark", "mark");
        createUser("You", "you");
    }

    @Override
    public boolean createUser(String username, String password) {
        m_users.put(m_nextUserId, new User(username, password, m_nextUserId));
        m_nextUserId++;
        return true;
    }

    @Override
    public boolean doesUserExist(String username) {
        for (IUser user : m_users.values()) {
            if(user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public IUser loginUser(String username, String password) {
        for (IUser user : m_users.values()) {
            if(user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Get user by ID (from cookie).
     * This throws an exception if the user does not exist
     *
     * @param id the user's ID
     * @return the user
     */
    @Override
    public IUser getUser(int id) throws ModelException {
        if (m_users.containsKey(id)) {
            return m_users.get(id);
        }
        else {
            throw new ModelException("No user with ID " + id + " exists.");
        }
    }
}
