package server.plugin;

import server.persistence.IUsersDAO;
import server.persistence.PersistenceException;
import shared.model.IUser;
import shared.model.IUserManager;
import shared.model.User;
import shared.model.UserManager;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.sql.ResultSet;

/**
 * Created by jeffreybacon on 12/7/14.
 */
public class SQLiteUsersDAO extends AbstractSQLiteDAO implements IUsersDAO {

    protected SQLiteUsersDAO(SQLitePersistenceManager manager) {
        super(manager);
    }

    @Override
    public void addUser(IUser newUser) throws PersistenceException {
        String query = "insert into users (userId, userData) values (?, ?)";
        super.writeToDB(query, newUser.getId(), newUser);
    }

    @Override
    public IUserManager loadUsers() throws PersistenceException {
        String query = "select userId, userData from users";
        ResultSet rs = super.readFromDB(query);

        IUserManager userManager = new UserManager();

        try {
            while (rs.next()) {
                byte[] byteArray = (byte[]) rs.getObject(1);
                ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                User user = (User) objectInputStream.readObject();
                userManager.loadUser(user);
            }
        } catch (Exception ex) {

        }

        return userManager;
    }
}
