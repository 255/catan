package server.persistence;

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

    protected void writeToDB(String sql, int id, Object obj, int id2) throws PersistenceException {
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
            if (id2 != -1) {
                stmt.setInt(3, id2);
            }

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

    protected ResultSet readFromDB(String sql) throws PersistenceException {
        PreparedStatement stmt;
        ResultSet rs;
        try {
            stmt = m_persistenceManager.getConnection().prepareStatement(sql);
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            throw new PersistenceException();
        }
        m_persistenceManager.endTransaction(true);
        return rs;
    }
}
