package server.persistence;

import shared.model.User;
import shared.model.UserManager;

/**
 * Created by Spencer Weight - 12/1/2014.
 */
public interface IUserDAO {

    public void add(User newUser);

    public UserManager load();
}
