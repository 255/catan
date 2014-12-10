package server.plugin;

import server.persistence.IUsersDAO;
import server.persistence.PersistenceException;
import shared.model.IUser;
import shared.model.IUserManager;
import shared.model.User;
import shared.model.UserManager;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.sql.ResultSet;
import java.util.List;

/**
 * Created by jeffreybacon on 12/7/14.
 */
public class SQLiteUsersDAO extends AbstractSQLiteDAO implements IUsersDAO {

    public SQLiteUsersDAO(SQLitePersistenceManager manager) {
        super(manager);
    }

    @Override
    public void addUser(IUser newUser) throws PersistenceException {
        String query = "insert into users (userId, userData) values (?, ?)";
        super.writeToDB(query, newUser.getId(), newUser);
    }

    @Override
    public IUserManager loadUsers() throws PersistenceException {
        String query = "select * from users";
        List users = super.readFromDB(query, -1);

        IUserManager userManager = new UserManager();

        try {
            for (Object user : users) {
                userManager.loadUser((User)user);
            }
        } catch (Exception ex) {
            throw new PersistenceException(ex);
        }

        return userManager;
    }
}
