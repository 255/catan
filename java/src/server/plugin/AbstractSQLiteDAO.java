package server.plugin;

import server.persistence.PersistenceException;
import server.plugin.SQLitePersistenceManager;

import javax.sql.rowset.serial.SerialBlob;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Handle interacting with a SQLite database.
 */
public abstract class AbstractSQLiteDAO {
    private SQLitePersistenceManager m_persistenceManager;

    protected AbstractSQLiteDAO(SQLitePersistenceManager manager) {
        m_persistenceManager = manager;
    }

    protected void writeToDB(String sql, int id, Object obj) throws PersistenceException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
            objectStream.writeObject(obj);
            objectStream.close();
            Blob blob = new SerialBlob(byteStream.toByteArray());

            stmt = m_persistenceManager.getConnection().prepareStatement(sql);

            stmt.setInt(1, id);
            stmt.setBlob(2, blob);

            rs = stmt.executeQuery();
        } catch (SQLException e) {
            throw new PersistenceException();
        } catch (IOException e) {
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
    }

    protected ResultSet readFromDB(String sql, int queryValue) throws PersistenceException {
        PreparedStatement stmt;
        ResultSet rs;
        try {
            stmt = m_persistenceManager.getConnection().prepareStatement(sql);
            if (queryValue != -1) {
                stmt.setInt(1, queryValue);
            }
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            throw new PersistenceException();
        }
        m_persistenceManager.endTransaction(true);
        return rs;
    }

    protected void deleteFromDB(String sql, int queryValue) throws PersistenceException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = m_persistenceManager.getConnection().prepareStatement(sql);
            stmt.setInt(1, queryValue);
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
        m_persistenceManager.endTransaction(true);
    }

    protected void updateDB(String sql, Object obj, int id) throws PersistenceException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
            objectStream.writeObject(obj);
            objectStream.close();
            Blob blob = new SerialBlob(byteStream.toByteArray());

            stmt = m_persistenceManager.getConnection().prepareStatement(sql);

            stmt.setBlob(1, blob);
            stmt.setInt(2, id);

            rs = stmt.executeQuery();
        } catch (SQLException e) {
            throw new PersistenceException();
        } catch (IOException e) {
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
    }


    protected SQLitePersistenceManager getPersistenceManager() {
        return m_persistenceManager;
    }

}
