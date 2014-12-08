package server.persistence;

import javax.sql.rowset.serial.SerialBlob;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

/**
 * Handle interacting with a SQLite database.
 */
public abstract class AbstractSQLiteDAO {
    private SQLitePersistenceManager m_persistenceManager;

    protected AbstractSQLiteDAO(SQLitePersistenceManager manager) {
        m_persistenceManager = manager;
    }

    protected void writeToDB(String sql, int id, Object obj, int id2) throws PersistenceException {
        try {
            m_persistenceManager.startTransaction();
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
            m_persistenceManager.endTransaction(true);
        } catch (PersistenceException e) {
            m_persistenceManager.endTransaction(false);
            throw new PersistenceException();
        }
    }

    protected List readFromDB(String sql) throws PersistenceException {
        try {
            m_persistenceManager.startTransaction();
            PreparedStatement stmt;
            ResultSet rs;
            List data = new ArrayList<>();
            try {
                stmt = m_persistenceManager.getConnection().prepareStatement(sql);
                rs = stmt.executeQuery();
                while(rs.next()) {
                    data.add(rs.getBlob(1));
                }
            } catch (SQLException e) {
                throw new PersistenceException();
            }
            m_persistenceManager.endTransaction(true);
            return data;
        } catch (PersistenceException e) {
            m_persistenceManager.endTransaction(false);
            throw new PersistenceException();
        }
    }
}
