package server.plugin;

import server.persistence.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

    private static final String DB_FILE_NAME = "catandb.sqlite";
    private static final Path DB_FILE = Paths.get("data", DB_FILE_NAME);
	private static final String CONNECTION_URL = "jdbc:sqlite:" + DB_FILE;

	/**
	 * Initialize the database by loading the database driver
	 */
	public static void initialize() throws PersistenceException {
		logger.entering("server.plugin.SQLitePersistenceManager", "initialize");

		try {
			// Load the database driver
			final String driver = "org.sqlite.JDBC";
			Class.forName(driver);
		}
        catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE, "The database driver failed to load.", e);
			throw new PersistenceException(e);
		}

        logger.exiting("server.plugin.SQLitePersistenceManager", "initialize");
	}

    private static final ThreadLocal<Connection> connection = new ThreadLocal<>();

    public SQLitePersistenceManager(int commandsBetweenCheckpoints) throws PersistenceException {
        super(commandsBetweenCheckpoints);

        initialize();

        if (!Files.exists(DB_FILE)) {
            try {
                Files.copy(Paths.get("..", "CLEAN_" + DB_FILE_NAME), DB_FILE);
            }
            catch (IOException e) {
                throw new PersistenceException("Database file " + DB_FILE + " not found and can't create it for some reason.!");
            }
        }
    }

    /**
     * Begins a transaction with the persistence layer.
     */
    public void startTransaction() throws PersistenceException {
        try {
            assert (getConnection() == null);
            setConnection(DriverManager.getConnection(CONNECTION_URL));
            getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }



    /**
     * Ends a transaction with the persistence layer.
     */
    public void endTransaction(boolean commit) {
        assert getConnection() != null : "Trying to end a transaction with a null connection!";

        try {
            if (commit) {
                getConnection().commit();
            } else {
                getConnection().rollback();
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Failed to end transaction.", e);
        } finally {
            if (getConnection() != null) {
                try {
                    getConnection().close();
                } catch (SQLException e) {
                    logger.log(Level.WARNING, "Failed to close connection.", e);
                }
            }

            setConnection(null);
        }
    }

    public Connection getConnection() {
        return connection.get();
    }

    private void setConnection(Connection conn) {
        connection.set(conn);
    }

    /**
     * Delete any stored data on disk.
     */
    public void clear() throws PersistenceException {
        startTransaction();

        try {
            executeSQLUpdate(getConnection(), "delete from users");
            executeSQLUpdate(getConnection(), "delete from games");
            executeSQLUpdate(getConnection(), "delete from commands");
        }
        catch (SQLException e) {
            endTransaction(false);
            throw new PersistenceException("Failed to clear tables.", e);
        }

        endTransaction(true);
    }

    private static void executeSQLUpdate(Connection connection, String sql) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.executeUpdate();
        }
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
