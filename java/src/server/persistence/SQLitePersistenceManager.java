package server.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A persistence manager that uses a SQLite database.
 */
public class SQLitePersistenceManager extends AbstractPersistenceManager {
    private static Logger logger = Logger.getLogger("catanserver");

    private static final ThreadLocal<Connection> connection = new ThreadLocal<>();

    private static String dbName;
    private static String connectionURL;

    protected SQLitePersistenceManager(int commandsBetweenCheckpoints) throws PersistenceException {
        super(commandsBetweenCheckpoints);
        dbName = Paths.get("data") + File.separator + "catandb.sqlite";
        connectionURL = "jdbc:sqlite:" + dbName;
    }

    /**
     * Begins a transaction with the persistence layer.
     */
    public void startTransaction() throws PersistenceException {
        try {
            connection.set(DriverManager.getConnection(connectionURL));
            connection.get().setAutoCommit(false);
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    /**
     * Ends a transaction with the persistence layer.
     */
    public void endTransaction(boolean commit) {
        try {
            if (commit) {
                connection.get().commit();
            } else {
                connection.get().rollback();
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Failed to end transaction.", e);
        } finally {
            if (connection.get() != null) {
                try {
                    connection.get().close();
                } catch (SQLException e) {
                    logger.log(Level.WARNING, "Failed to close connection.", e);
                }
            }
        }

        connection.set(null);
    }

    public Connection getConnection() {
        return connection.get();
    }

    /**
     * Delete any stored data on disk.
     */
    public void clear() throws PersistenceException {
        startTransaction();

        String sql;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            sql = "delete from Users";
            stmt = getConnection().prepareStatement(sql);
            rs = stmt.executeQuery();

            sql = "delete from Groups";
            stmt = getConnection().prepareStatement(sql);
            rs = stmt.executeQuery();

            sql = "delete from Commands";
            stmt = getConnection().prepareStatement(sql);
            rs = stmt.executeQuery();

        } catch (SQLException e) {
            throw new PersistenceException();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                throw new PersistenceException();
            }
        }

        endTransaction(true);
    }

    /**
     * Creates the Database Access Object that will handle users.
     * @return a users DAO
     */
    public IUsersDAO createUsersDAO() {
        return new SQLiteUsersDAO(this);
    }

    /**
     * Creates the Database Access Object that will handle games.
     * @return a games DAO
     */
    public IGamesDAO createGamesDAO() {
        return new SQLiteGamesDAO(this);
    }

    /**
     * Creates the Database Access Object that will handle commands.
     * @return a commands DAO
     */
    public ICommandsDAO createCommandsDAO() {
        return new SQLiteCommandsDAO(this);
    }
}
