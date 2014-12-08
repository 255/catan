package server.persistence;

import shared.model.IUser;
import shared.model.IUserManager;

/**
 * Created by jeffreybacon on 12/7/14.
 */
public class SQLiteUsersDAO extends AbstractSQLiteDAO implements IUsersDAO {

    protected SQLiteUsersDAO(SQLitePersistenceManager manager) {
        super(manager);
    }

    @Override
    public void addUser(IUser newUser) throws PersistenceException {

    }

    @Override
    public IUserManager loadUsers() throws PersistenceException {
        return null;
    }
}
